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
import java.util.Date;
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
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String keySearchId = "";
        SolrDocumentList docs = new SolrDocumentList();
        //SolrDocumentList docs_Category = new SolrDocumentList();
        SolrDocumentList docs_MoreLikeThis = new SolrDocumentList();
        try {

            server = SolrJConnection.getSolrServer("raovat");

            if (request.getParameter("id") != null) {
                keySearchId = request.getParameter("id");

                //Phan tracking
                if (request.getParameter("KeySearch") != null) {
                    ParameterBUS par = new ParameterBUS();
                    TrackingDTO tracking = new TrackingDTO();
                    try {
                        int timeRange = Integer.parseInt(par.GetParameter("time_range", "visearch").toString());
                        String sTime = String.format("%d:00:00", timeRange);
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
                        tracking.setSearchType(2);
                        TrackingBUS tbus = new TrackingBUS();
                        if (tbus.InsertTracking(tracking, "visearch")) {
                            tbus.UpdateKeysearch(Integer.parseInt(keySearchId), keysearch, "raovat", "visearch");
                        }
                    } catch (Exception ex) {
                    }
                }
                // end tracking

                QueryResponse rsp;
                try {
                    rsp = OnSearchSubmit(keySearchId);
                    docs = rsp.getResults();
                    if (docs != null) {
                        String title = (docs.get(0).getFirstValue("rv_title")).toString();
                        rsp = OnMoreLikeThis(title);
                        docs_MoreLikeThis = rsp.getResults();
                        for (int i = 0; i < docs_MoreLikeThis.size() - 1; i++) {
                            for (int j = i + 1; j < docs_MoreLikeThis.size(); j++) {
                                String title1 = docs_MoreLikeThis.get(i).getFirstValue("rv_title").toString();
                                String title2 = docs_MoreLikeThis.get(j).getFirstValue("rv_title").toString();
                                if (title1.trim().equals(title2.trim())) {
                                    Date date1 = (Date) docs_MoreLikeThis.get(i).getFieldValue("last_update");
                                    Date date2 = (Date) docs_MoreLikeThis.get(j).getFieldValue("last_update");
                                    if (date1.compareTo(date2) >= 0) {
                                        docs_MoreLikeThis.remove(j);
                                        j--;
                                    } else {
                                        docs_MoreLikeThis.remove(i);
                                        i--;
                                        break;
                                    }
                                }
                            }
                        }

                        int idem = Math.min(10, docs_MoreLikeThis.size());
                        while (docs_MoreLikeThis.size() > idem) {
                            docs_MoreLikeThis.remove(idem);
                        }
                    }
                } catch (SolrServerException ex) {
                    Logger.getLogger(DetailRaoVatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String url = "/raovat_details.jsp";
            request.setAttribute("Docs", docs);
            request.setAttribute("Docs_MoreLikeThis", docs_MoreLikeThis);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (Exception ex) {
            out.print(ex.getMessage());
        } finally {
            out.close();
        }
    }

    QueryResponse OnSearchSubmit(String id) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();

        solrQuery.setQuery("id:" + id);

        // Facet
        solrQuery.setFacet(true);
        solrQuery.addFacetField("category");
        solrQuery.setFacetLimit(10);
        solrQuery.setFacetMinCount(1);
        // End Facet
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    QueryResponse Category(String query) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(query);
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    QueryResponse OnMoreLikeThis(String strquery) throws SolrServerException, MalformedURLException, UnsupportedEncodingException {
        SolrQuery query = new SolrQuery();
        query.setQueryType("/" + MoreLikeThisParams.MLT);
        query.set(MoreLikeThisParams.MATCH_INCLUDE, false);
        query.set(MoreLikeThisParams.MIN_DOC_FREQ, 1);
        query.set(MoreLikeThisParams.MIN_TERM_FREQ, 1);
        query.set(MoreLikeThisParams.SIMILARITY_FIELDS, "rv_title");

        query.setQuery("rv_title:" + ClientUtils.escapeQueryChars(strquery));
        //query.setQuery(ClientUtils.escapeQueryChars(strquery));
        query.setStart(0);
        query.setRows(100);
        query.setHighlight(true);
        query.addHighlightField("rv_title");
        query.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        query.setHighlightSimplePost("</em>");
        query.setHighlightRequireFieldMatch(true);
        QueryResponse rsp = server.query(query);
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
