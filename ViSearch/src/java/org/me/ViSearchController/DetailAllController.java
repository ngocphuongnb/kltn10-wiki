/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.ViSearchController;

import java.io.IOException;
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
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MoreLikeThisParams;
import org.me.SolrConnection.SolrJConnection;
import org.me.bus.ParameterBUS;
import org.me.bus.TrackingBUS;
import org.me.dto.MemberDTO;
import org.me.dto.TrackingDTO;

/**
 *
 * @author tuandom
 */
public class DetailAllController extends HttpServlet {

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
        String keySearchId = "";
        int type = -1;
        int QTime = 0;
        SolrDocumentList docs_MoreLikeThis = new SolrDocumentList();
        try {
            server = SolrJConnection.getSolrServer("all");

            //</get-parameter>
            if (request.getParameter("KeySearch") != null) {
                QueryResponse rsp;
                Map<String, Map<String, List<String>>> highLight;

                keySearch = request.getParameter("KeySearch");
                keySearchId = request.getParameter("id");

                //Phan tracking
                if (request.getParameter("KeySearch") != null) {
                    ParameterBUS par = new ParameterBUS();
                    int timeRange = Integer.parseInt(par.GetParameter("time_range", "visearch").toString());
                    String sTime = String.format("%d:00:00", timeRange);
                    TrackingDTO tracking = new TrackingDTO();
                    String keysearch = request.getParameter("KeySearch").toString();
                    request.setAttribute("KeySearch", keysearch);
                    tracking.setKeySearch(keysearch);
                    tracking.setDocId(keySearchId);
                    tracking.setIp(request.getRemoteAddr());
                    HttpSession session = request.getSession();
                    if (session.getAttribute("Member") != null) {
                        MemberDTO mem = (MemberDTO) session.getAttribute("Member");
                        tracking.setMemberId(mem.getId());
                    } else {
                        tracking.setMemberId(-1);
                    }
                    tracking.setTimeRange(sTime);
                    tracking.setTimeSearch(Calendar.getInstance());
                    tracking.setSearchType(6);
                    TrackingBUS tbus = new TrackingBUS();
                    if (tbus.InsertTracking(tracking, "visearch")) {
                        tbus.UpdateKeysearch(Integer.parseInt(keySearchId), keysearch, "all", "visearch");
                    }
                }
                // end tracking

                rsp = OnSearchSubmit(keySearchId);
                docs = rsp.getResults();
                highLight = rsp.getHighlighting();
                request.setAttribute("HighLight", highLight);
                QTime = rsp.getQTime();

                request.setAttribute("QTime", String.valueOf(1.0 * QTime / 1000));
                request.setAttribute("KeySearch", keySearch);
                highLight = rsp.getHighlighting();

                if (docs != null) {
                    String title = (docs.get(0).getFirstValue("title")).toString();
                    rsp = OnMoreLikeThis(title);
                    docs_MoreLikeThis = rsp.getResults();
                    request.setAttribute("Docs", docs);
                    request.setAttribute("Docs_MoreLikeThis", docs_MoreLikeThis);
                    request.setAttribute("HighLight", highLight);
                }
                String url = "/all_detail.jsp";
                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(url);
                rd.forward(request, response);
            }
        } catch (Exception e) {
            out.print(e.getMessage());
        } finally {
            out.close();
        }
    }

    QueryResponse OnMoreLikeThis(String strquery) throws SolrServerException, MalformedURLException, UnsupportedEncodingException {
        SolrQuery query = new SolrQuery();
        query.setQueryType("/" + MoreLikeThisParams.MLT);
        query.set(MoreLikeThisParams.MATCH_INCLUDE, false);
        query.set(MoreLikeThisParams.MIN_DOC_FREQ, 1);
        query.set(MoreLikeThisParams.MIN_TERM_FREQ, 1);
        query.set(MoreLikeThisParams.SIMILARITY_FIELDS, "title");
        //query.setQuery("title:" + ClientUtils.escapeQueryChars(q));
        query.setQuery(ClientUtils.escapeQueryChars(strquery));
        query.setStart(0);
        query.setRows(10);
        query.setHighlight(true);
        query.addHighlightField("title");
        query.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        query.setHighlightSimplePost("</em>");
        query.setHighlightRequireFieldMatch(true);
        QueryResponse rsp = server.query(query);
        return rsp;
    }

    QueryResponse OnSearchSubmit(String keySearchId) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();

        String query = "id:" + keySearchId;


        solrQuery.setQuery(query);
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("title");
        solrQuery.addHighlightField("body");
        solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.setHighlightRequireFieldMatch(true);
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
        try {
            processRequest(request, response);
        } catch (SolrServerException ex) {
            Logger.getLogger(SearchAllController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SearchAllController.class.getName()).log(Level.SEVERE, null, ex);
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
