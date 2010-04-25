/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.ViSearchController;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.me.SolrConnection.SolrJConnection;

/**
 *
 * @author VinhPham
 */
public class SearchController extends HttpServlet {

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
        String keySearch = "";
        int pagesize = 10;
        int currentpage = 1;
        long numRow = 0;
        String sPaging = "";
        try {

            if (request.getParameter("currentpage") != null) {
                currentpage = Integer.parseInt(request.getParameter("currentpage"));
            }

            if (request.getParameter("KeySearch") != null) {
                keySearch = request.getParameter("KeySearch");
                keySearch = URLDecoder.decode(keySearch, "UTF-8");

                SolrServer server = SolrJConnection.getSolrServer();
                SolrQuery solrQuery = new SolrQuery();
                //solrQuery.setQueryType("dismax");

                solrQuery.setQuery(keySearch);

                //solrQuery.setFacet(true);
                solrQuery.setHighlight(true);
                solrQuery.addHighlightField("title");
                solrQuery.addHighlightField("text");
                solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
                solrQuery.setHighlightSimplePost("</em>");
                solrQuery.setHighlightRequireFieldMatch(false);
                int start = (currentpage - 1) * pagesize;
                solrQuery.setStart(start);
                solrQuery.setRows(pagesize);
                // query.set

                //query.addSortField("price", SolrQuery.ORDER.asc);
                QueryResponse rsp = server.query(solrQuery);

                // Result
                SolrDocumentList docs = rsp.getResults();
                Map<String, Map<String, List<String>>> highLight = rsp.getHighlighting();
                numRow = docs.getNumFound();
                int numpage = (int) (numRow / pagesize);

                if (numRow % pagesize > 0) {
                    numpage++;
                }

                sPaging = getPaging(numpage, pagesize, currentpage, keySearch, "/ViSearch/SearchController");
                request.setAttribute("Docs", docs);
                request.setAttribute("HighLight", highLight);
                request.setAttribute("KeySearch", URLEncoder.encode(keySearch, "UTF-8"));
                request.setAttribute("Pagging", sPaging);
                request.setAttribute("NumRow", numRow);
                request.setAttribute("NumPage", numpage);
                String url = "/index.jsp";
                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(url);
                rd.forward(request, response);
            }
        } finally {
            String url = "/ViSearch/index.jsp";
            response.sendRedirect(url);
            out.close();
        }
    }

    public String getPaging(int numpage, int pagesize, int currentpage, String keysearch, String URL) {
        String Paging;
        int page = 0;
        if (currentpage > 1) {
            page = currentpage - 1;
            Paging = "<a href=\"" + URL + "?currentpage=1&KeySearch=" + keysearch + "\">[First]</a> ";
            Paging += "<a href=\"" + URL + "?currentpage=" + page + "&KeySearch=" + keysearch + "\">[Previous]</a> ";
        } else {
            Paging = "[First]";
            Paging += "[Previous]";
        }
        if (currentpage > 2) {
            page = currentpage - 2;
        } else {
            page = 1;
        }
        for (int tam = page + 5; page <= numpage && page < tam; page++) {
            if (page == currentpage) {
                Paging += "<font color='#FF0000'>" + page + "</font> ";
            } else {
                Paging += "<a href=\"" + URL + "?currentpage=" + page + "&KeySearch=" + keysearch + "\">" + page + "</a> ";
            }
        }

        if (currentpage < numpage) {
            page = currentpage + 1;
            Paging += "<a href=\"" + URL + "?currentpage=" + page + "&KeySearch=" + keysearch + "\">[Next]</a> ";
            Paging += "<a href=\"" + URL + "?currentpage=" + numpage + "&KeySearch=" + keysearch + "\">[Last]</a> ";
        } else {
            Paging += "[Next]";
            Paging += "[Last]";
        }
        return Paging;
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
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
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
