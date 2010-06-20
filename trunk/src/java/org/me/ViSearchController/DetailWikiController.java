/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.ViSearchController;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.me.bus.ParameterBUS;
import org.me.bus.TrackingBUS;
import org.me.dto.MemberDTO;
import org.me.dto.TrackingDTO;

/**
 *
 * @author VinhPham
 */
public class DetailWikiController extends HttpServlet {

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
        boolean b;
        try {
            if (request.getParameter("id") != null) {
                int id = Integer.parseInt(request.getParameter("id").toString());
                //Phan update tracking boosting
                //Phan tracking
                TrackingDTO tracking = new TrackingDTO();
                if (request.getParameter("KeySearch") != null) {

                    ParameterBUS par = new ParameterBUS();
                    int timeRange = Integer.parseInt(par.GetParameter("time_range", "visearch").toString());
                    String sTime = String.format("%d:00:00", timeRange);
                    String keysearch = URLDecoder.decode(request.getParameter("KeySearch").toString(), "UTF-8");
                    tracking.setKeySearch(keysearch);
                    tracking.setDocId(String.valueOf(id));
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
                    tracking.setSearchType(1);
                    try {
                        TrackingBUS tbus = new TrackingBUS();
                        b = tbus.InsertTracking(tracking, "visearch");
                        b = tbus.UpdateKeysearch(id, keysearch, "viwiki", "visearch");
                    } catch (Exception ex) {
                    }
                }
                // end tracking
                String link = URLDecoder.decode(request.getParameter("url").toString(), "UTF-8");
                String url = "http://vi.wikipedia.org/wiki/" + URLEncoder.encode(link, "UTF-8");
                response.sendRedirect(url);
            }
        } catch (Exception ex) {
            out.println(ex.getMessage());
        } finally {
            out.close();

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
