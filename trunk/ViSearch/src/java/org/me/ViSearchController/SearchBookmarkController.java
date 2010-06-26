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
import java.util.List;
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
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
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
        String keySearch = "";
        int pagesize = 10;
        int currentpage = 1;
        long numRow = 0;
        String sPaging = "/ViSearch/SearchBookmarkController?";
        int sortedType = 0;
        List<FacetField> listFacet = null;

        try {
            if (request.getParameter("currentpage") != null) {
                currentpage = Integer.parseInt(request.getParameter("currentpage"));
            }
            server = SolrJConnection.getSolrServer("bookmark");
            int start = (currentpage - 1) * pagesize;
            HttpSession session = request.getSession();
            MemberDTO mem;
            if (request.getParameter("KeySearch") != null) {
                QueryResponse rsp;
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

                if (session.getAttribute("Member") != null) {
                    mem = (MemberDTO) session.getAttribute("Member");
                    rsp = OnSearchSubmit(keySearch, filter, mem.getId(), start, pagesize, sortedType);
                    docs = rsp.getResults();
                    listFacet = rsp.getFacetFields();
                }
            } else {
                if (session.getAttribute("Member") != null) {
                    mem = (MemberDTO) session.getAttribute("Member");
                    QueryResponse rsp;
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

    QueryResponse OnSearchSubmit(String keySearch, String strFilter, int memberid, int start, int pagesize, int sortedType) throws SolrServerException {

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

    String OnCheckSpelling(String q) throws SolrServerException, URIException, HttpException, IOException {
        String result = "";
        HttpClient client = new HttpClient();
        //&spellcheck.build=true
        String url = "http://localhost:8983/solr/bookmark/spell?q=" + q + "&spellcheck=true&spellcheck.collate=true&spellcheck.dictionary=jarowinkler&wt=json";
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
