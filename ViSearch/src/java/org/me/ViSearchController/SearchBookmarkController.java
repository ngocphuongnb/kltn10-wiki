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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.URIException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
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
        String keySearch = "";
        int pagesize = 10;
        int currentpage = 1;
        long numRow = 0;
        int type = 0;
        String sPaging = "/ViSearch/SearchBookmarkController?";
        int sortedType = 0;
        List<FacetField> listFacet = null;
        Map<String, Map<String, List<String>>> highLight;
        QueryResponse rsp;
        try {
            if (request.getParameter("currentpage") != null) {
                currentpage = Integer.parseInt(request.getParameter("currentpage"));
            }
            server = SolrJConnection.getSolrServer("bookmark");
            int start = (currentpage - 1) * pagesize;

            if (request.getParameter("KeySearch") != null) {
                keySearch = request.getParameter("KeySearch");
                sPaging += "&KeySearch=" + keySearch;
            }
            type = Integer.parseInt(request.getParameter("type"));
            if (request.getParameter("sp") != null) {
                String sCollation = OnCheckSpelling(keySearch);
                if (sCollation != null && sCollation.equals("") == false) {
                    request.setAttribute("Collation", sCollation);
                }
            }
            switch (type) {
                case 0:
                    if (request.getParameter("sp") != null) {
                        String sCollation = OnCheckSpelling(keySearch);
                        if (sCollation.equals("") == false) {
                            request.setAttribute("Collation", sCollation);
                        }
                    }
                    rsp = OnSearchSubmit(keySearch, start, pagesize, sortedType);
                    docs = rsp.getResults();
                    highLight = rsp.getHighlighting();
                    request.setAttribute("HighLight", highLight);
                    // Get Facet
                    listFacet = rsp.getFacetFields();
                    break;
                case 1:
                    rsp = OnMLT(keySearch, start, pagesize);
                    docs = rsp.getResults();
                    highLight = rsp.getHighlighting();
                    if (highLight != null) {
                        request.setAttribute("HighLight", highLight);
                    }
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
                        // Neu la text thi them ""
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
                    facetNameValue = "";
                    qf = "";
                    sPaging += "&type=3";
                    if (request.getParameter("w") != null) {
                        //  facetName = request.getParameter("FacetName");
                        // sPaging += "&FacetName=" + facetName;
                        String w = "";
                        if (request.getParameter("w") != null) {
                            w = request.getParameter("w");
                        }
                        String h = "";
                        if (request.getParameter("h") != null) {
                            h = request.getParameter("h");
                        }
                        //facetNameValue = createFacetValue(w, h);
                        //  sPaging += "&FacetValue=" + facetValue;
                    }
                    rsp = OnSearchSubmitStandard(keySearch, facetNameValue, start, pagesize);

                    highLight = rsp.getHighlighting();
                    request.setAttribute("HighLight", highLight);
                    docs = rsp.getResults();
                    break;
                case 4:
                    String field = "";
                    sPaging += "&type=4";
                    if (request.getParameter("f") != null) {
                        field = request.getParameter("f");
                    }
                    rsp = OnSearchSubmitByField(field, start, pagesize);

                    highLight = rsp.getHighlighting();
                    request.setAttribute("HighLight", highLight);
                    docs = rsp.getResults();
                    break;
                default:
                    break;
            }


            request.setAttribute("KeySearch", keySearch);
            if (docs != null) {
                numRow = docs.getNumFound();
                //numRow = docs.size();
                int numpage = (int) (numRow / pagesize);

                if (numRow % pagesize > 0) {
                    numpage++;
                }

                sPaging = Paging.getPaging(numpage, pagesize, currentpage, sPaging);
                request.setAttribute("Docs", docs);
                request.setAttribute("Pagging", sPaging);
                request.setAttribute("NumRow", numRow);
                request.setAttribute("NumPage", numpage);
                request.setAttribute("ListFacet", listFacet);
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

    private QueryResponse OnSearchSubmitByField(String field, int start, int pagesize) throws SolrServerException {
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
