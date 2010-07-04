/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.ViSearchController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MoreLikeThisParams;
import org.me.SolrConnection.SolrJConnection;
import org.me.Utils.MyString;
import org.me.Utils.Paging;
import org.me.dto.MemberDTO;

/**
 *
 * @author tuandom
 */
public class SearchBookmarkController extends HttpServlet {

    SolrServer server;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SolrServerException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        SolrDocumentList docs = new SolrDocumentList();
        SolrDocumentList NewestDocs = new SolrDocumentList();
        String keySearch = "";
        int pagesize = 10;
        int currentpage = 1;
        long numRow = 0;
        String sPaging = "/ViSearch/SearchBookmarkController?";
        int sortedType = 0;
        List<FacetField> listFacet = null;
        int type = -1;
        QueryResponse rsp;
        Map<String, Map<String, List<String>>> highLight = null;
        if (request.getParameter("type") != null) {
            type = Integer.parseInt(request.getParameter("type"));
        }
        try {
            if (request.getParameter("currentpage") != null) {
                currentpage = Integer.parseInt(request.getParameter("currentpage"));
            }
            server = SolrJConnection.getSolrServer("bookmark");
            int start = (currentpage - 1) * pagesize;
            HttpSession session = request.getSession();
            MemberDTO mem;
            if (request.getParameter("KeySearch") != null || type != -1) {

                keySearch = request.getParameter("KeySearch");
                request.setAttribute("KeySearch", keySearch);
                sPaging += "&KeySearch=" + keySearch;
                String filter = request.getParameter("Filter");
                request.setAttribute("Filter", filter);
                if (request.getParameter("sp") != null) {
                    String sCollation = OnCheckSpelling(keySearch);
                    if (sCollation != null && sCollation.equals("") == false) {
                        request.setAttribute("Collation", sCollation);
                    }
                }
                switch (type) {
                    case 0:
                        if (session.getAttribute("Member") != null) {
                            mem = (MemberDTO) session.getAttribute("Member");
                            rsp = OnSearchSubmit0(keySearch, filter, mem.getId(), start, pagesize, sortedType);
                            docs = rsp.getResults();
                            highLight = rsp.getHighlighting();
                            if (highLight != null) {
                                request.setAttribute("HighLight", highLight);
                            }
                            listFacet = rsp.getFacetFields();
                        }
                        break;
                    case 1:
                        rsp = OnMLT(keySearch, start, pagesize);
                        docs = rsp.getResults();
                        highLight = rsp.getHighlighting();
                        listFacet = rsp.getFacetFields();
                        break;
                    case 2:
                        String qf = "";
                        String qv = "";
                        if (request.getParameter("qf") != null) {
                            qf = request.getParameter("qf");
                            qv = request.getParameter("qv");
                            sPaging += "&qf=" + qf;
                            sPaging += "&qv=" + qv;
                        }
                        String facetNameValue = " +(" + qf + ":" + qv + ")";
                        if (qf.equals("searchtype")) {
                            facetNameValue = " +(" + qf + ":\"" + qv + "\")";
                        }
                        rsp = OnSearchSubmitStandard(keySearch, facetNameValue, start, pagesize);
                        docs = rsp.getResults();
                        highLight = rsp.getHighlighting();
                        request.setAttribute("HighLight", highLight);
                        // Get Facet
                        listFacet = rsp.getFacetFields();
                        break;
                    case 3:
                        break;
                    case 4:
                        String field = "";
                        sPaging += "&type=4";
                        if (request.getParameter("f") != null) {
                            field = request.getParameter("f");
                        }
                        rsp = OnSearchSubmitByField(field, start, pagesize);
                        docs = rsp.getResults();
                        highLight = rsp.getHighlighting();

                        break;
                    default:
                        break;
                }
            } else {
                if (session.getAttribute("Member") != null) {
                    mem = (MemberDTO) session.getAttribute("Member");
                    rsp = OnSearchSubmitByMember(mem.getId(), start, pagesize, sortedType);
                    docs = rsp.getResults();
                }
            }
            if (docs != null) {
                numRow = docs.getNumFound();
                //numRow = docs.size();
                int numpage = (int) (numRow / pagesize);

                if (numRow % pagesize > 0) {
                    numpage++;
                }
                rsp = OnSearchSubmitNewestDocument(0, 10);
                NewestDocs = rsp.getResults();
                sPaging = Paging.getPaging(numpage, pagesize, currentpage, sPaging);
                request.setAttribute("Docs", docs);
                request.setAttribute("NewestDocs", NewestDocs);
                request.setAttribute("Pagging", sPaging);
                request.setAttribute("NumRow", numRow);
                request.setAttribute("NumPage", numpage);
                request.setAttribute("ListFacet", listFacet);
                request.setAttribute("HighLight", highLight);
            }
            String url = "/showBookmark.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (Exception e) {
            out.print(e.getMessage());
        } finally {
            out.close();
        }
    }

    QueryResponse OnSearchSubmit0(String keySearch, String strFilter, int memberid, int start, int pagesize, int sortedType) throws SolrServerException {

        String[] arrFilter = strFilter.split("_");

        SolrQuery solrQuery = new SolrQuery();
        String query = "";
        String strQuery = "";
        if (MyString.CheckSigned(keySearch)) {
            query += "bookmarkname:(\"" + keySearch + "\")^10 || bookmarkname:(" + keySearch + ")^8";
        } else {
            query += "bookmarkname:(\"" + keySearch + "\")^10 || bookmarkname:(" + keySearch + ")^8 || "
                    + "bookmarkname_unsigned:(\"" + keySearch + "\")^9 || bookmarkname_unsigned:(" + keySearch + ")^6";
        }

        String[] arrStrFilter = new String[arrFilter.length];
        for (int i = 0; i < arrStrFilter.length; i++) {
            if (arrFilter[i].equals("1")) {
                arrStrFilter[i] = "memberid:" + memberid;
            }

            if (arrFilter[i].equals("2")) {
                arrStrFilter[i] = "priority:1";
            }
        }

        strFilter = arrStrFilter[0];
        for (int i = 1; i < arrStrFilter.length; i++) {
            strFilter += " || " + arrStrFilter[i];
        }

        strQuery = "(" + query + ") && (" + strFilter + ")";
        // Facet
        solrQuery.setFacet(true);
        solrQuery.addFacetField("searchtype");
        solrQuery.setFacetLimit(10);
        solrQuery.setFacetMinCount(1);
        // End Facet

        solrQuery.setQuery(strQuery);
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("bookmarkname");
        solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.setHighlightRequireFieldMatch(true);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    QueryResponse OnSearchSubmitStandard(String keySearch, String facetNameValue, int start, int pagesize) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        String query = "+(";
        if (MyString.CheckSigned(keySearch)) {
            query += "bookmarkname:(\"" + keySearch + "\")^10 || bookmarkname:(" + keySearch + ")^8";
        } else {
            query += "bookmarkname:(\"" + keySearch + "\")^10 || bookmarkname:(" + keySearch + ")^8 || "
                    + "bookmarkname_unsigned:(\"" + keySearch + "\")^9 || bookmarkname_unsigned:(" + keySearch + ")^6";
        }
        query += ")";
        query += facetNameValue;


        solrQuery.setQuery(query);

        // Facet
        solrQuery.setFacet(true);
        solrQuery.addFacetField("searchtype");
        solrQuery.setFacetLimit(10);
        solrQuery.setFacetMinCount(1);
        // End Facet

        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("bookmarkname");
        solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.setHighlightRequireFieldMatch(true);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    QueryResponse OnMLT(String q, int start, int pagesize) throws SolrServerException, MalformedURLException, UnsupportedEncodingException {
        SolrQuery query = new SolrQuery();

        // Facet
        query.setFacet(true);
        query.addFacetField("bookmarkname");
        query.setFacetLimit(10);
        query.setFacetMinCount(1);
        // End Facet

        query.setQueryType("/" + MoreLikeThisParams.MLT);
        query.set(MoreLikeThisParams.MATCH_INCLUDE, false);
        query.set(MoreLikeThisParams.MIN_DOC_FREQ, 1);
        query.set(MoreLikeThisParams.MIN_TERM_FREQ, 1);
        query.set(MoreLikeThisParams.SIMILARITY_FIELDS, "bookmarkname");

        query.setQuery("bookmarkname:" + MyString.cleanQueryTerm(q));
        //query.setQuery(ClientUtils.escapeQueryChars(q));
        query.setStart(start);
        query.setRows(pagesize);
        query.setHighlight(true);
        query.addHighlightField("bookmarkname");
        query.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        query.setHighlightSimplePost("</em>");
        query.setHighlightRequireFieldMatch(true);
        QueryResponse rsp = server.query(query);
        return rsp;
    }

    QueryResponse OnSearchSubmit(String keySearch, int start, int pagesize, int sortedType) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();

        String query = "";
        if (MyString.CheckSigned(keySearch)) {
            query += "bookmarkname:(\"" + keySearch + "\")^10 || bookmarkname:(" + keySearch + ")^8";
        } else {
            query += "bookmarkname:(\"" + keySearch + "\")^10 || bookmarkname:(" + keySearch + ")^8 || "
                    + "bookmarkname_unsigned:(\"" + keySearch + "\")^9 || bookmarkname_unsigned:(" + keySearch + ")^6";
        }

        // Facet
        solrQuery.setFacet(true);
        solrQuery.addFacetField("searchtype");
        solrQuery.setFacetLimit(10);
        solrQuery.setFacetMinCount(1);
        // End Facet

        solrQuery.setQuery(query);
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("bookmarkname");
        solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.setHighlightRequireFieldMatch(true);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    QueryResponse OnSearchSubmitByField(String field, int start, int pagesize) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();

        String query = "";
        query = "searchtype:" + field;

        // Facet
        solrQuery.setFacet(true);
        solrQuery.addFacetField("searchtype");
        solrQuery.setFacetLimit(10);
        solrQuery.setFacetMinCount(1);
        // End Facet

        solrQuery.setQuery(query);
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("bookmarkname");
        solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.setHighlightRequireFieldMatch(true);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    QueryResponse OnSearchSubmitNewestDocument(int start, int pagesize) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();

        String queryValue = "";
        String query = "";
        Calendar cl = Calendar.getInstance();
        String str1thangqua = cl.get(Calendar.YEAR) + "-" + cl.get(Calendar.MONTH) + "-" + cl.get(Calendar.DAY_OF_MONTH)
                + "T" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND) + "." + cl.get(Calendar.MILLISECOND) + "Z";

        queryValue = "[" + str1thangqua + " TO NOW]";

        query += " +(date_created:" + queryValue + ")";

        solrQuery.setQuery(query);

        solrQuery.setHighlightRequireFieldMatch(true);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
        solrQuery.set("fl", "docid, searchtype, bookmarkname");
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    QueryResponse OnSearchSubmitByMember(int id, int start, int pagesize, int sortedType) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        String query = "memberid:" + id;
        solrQuery.setQuery(query);
        solrQuery.setSortField("date_created", SolrQuery.ORDER.desc);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    String OnCheckSpelling(String q) throws org.apache.commons.httpclient.URIException, IOException {
        String result = "";
        HttpClient client = new HttpClient();
        //&spellcheck.build=true
        String url = "http://localhost:8983/solr/image/spell?q=" + q + "&spellcheck=true&spellcheck.collate=true&spellcheck.dictionary=jarowinkler&wt=json";
        url = URIUtil.encodeQuery(url);
        GetMethod get = new GetMethod(url);

        get.setRequestHeader(new Header("User-Agent", "localhost bot admin@localhost.com"));

        int status = client.executeMethod(get);
        String charSet = get.getResponseCharSet();
        if (charSet == null) {
            charSet = "UTF-8";
        }
        String body = convertStreamToString(get.getResponseBodyAsStream(), charSet);

        try {
            JSONObject json = (JSONObject) JSONSerializer.toJSON(body);
            JSONObject ob = json.getJSONObject("spellcheck");
            JSONArray cluster = ob.getJSONArray("suggestions");
            if (cluster.size() > 0) {
                result = cluster.getString(cluster.size() - 1);
            }
            get.releaseConnection();
            return result;
        } catch (Exception x) {
            return null;
        }
    }

    public String convertStreamToString(InputStream is, String encode) throws IOException {
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, encode));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                is.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SolrServerException ex) {
            Logger.getLogger(SearchBookmarkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SolrServerException ex) {
            Logger.getLogger(SearchBookmarkController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
