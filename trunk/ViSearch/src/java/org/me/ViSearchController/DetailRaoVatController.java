/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.ViSearchController;

import java.io.IOException;
import java.io.PrintWriter;
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
 * @author tuandom
 */
public class DetailRaoVatController extends HttpServlet {

    SolrServer server;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String keySearchId = "";
        SolrDocumentList docs = new SolrDocumentList();
        try {
            server = SolrJConnection.getSolrServer();

            if (request.getParameter("id") != null) {
                keySearchId = request.getParameter("id");
                QueryResponse rsp;
                try {
                    rsp = OnSearchSubmit(keySearchId);
                    docs = rsp.getResults();
                } catch (SolrServerException ex) {
                    Logger.getLogger(DetailRaoVatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String url = "/raovat_details.jsp";
            request.setAttribute("Docs", docs);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);
        } finally {
            out.close();
        }
    }

    QueryResponse OnSearchSubmit(String id) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();

        solrQuery.setQuery("id:"+id);

        // Facet
        solrQuery.setFacet(true);
        solrQuery.addFacetField("category");
        //solrQuery.addFacetField("username");
        solrQuery.setFacetLimit(10);
        solrQuery.setFacetMinCount(1);
        // End Facet

//        solrQuery.setHighlight(true);
//        solrQuery.addHighlightField("title");
//        solrQuery.addHighlightField("body");
//        solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
//        solrQuery.setHighlightSimplePost("</em>");
//        solrQuery.setHighlightRequireFieldMatch(true);
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
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
        processRequest(request, response);
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
        processRequest(request, response);
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
