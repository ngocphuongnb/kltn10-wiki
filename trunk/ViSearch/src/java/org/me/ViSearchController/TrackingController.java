/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.ViSearchController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
 * @author tuandom
 */
public class TrackingController extends HttpServlet {
   
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
        String docId="";
        int searchType = 0;
        searchType = Integer.parseInt(request.getParameter("searchType"));
         if (request.getParameter("DocID") != null) {
                docId = request.getParameter("DocID");
         }
        try {
            //Phan tracking
                ParameterBUS par = new ParameterBUS();
                int timeRange = Integer.parseInt(par.GetParameter("time_range", "visearch").toString());
                String sTime = String.format("%d:00:00", timeRange);

                TrackingDTO tracking = new TrackingDTO();
                String keysearch = request.getParameter("KeySearch").toString();

                request.setAttribute("KeySearch", keysearch);
                tracking.setKeySearch(keysearch);
                tracking.setDocId(docId);
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
                tracking.setSearchType(searchType);
                TrackingBUS tbus = new TrackingBUS();
                tbus.InsertTracking(tracking, "visearch");
                // end tracking


//            ServletContext sc = getServletContext();
//            RequestDispatcher rd = sc.getRequestDispatcher(urlFrom);
//            rd.forward(request, response);
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
