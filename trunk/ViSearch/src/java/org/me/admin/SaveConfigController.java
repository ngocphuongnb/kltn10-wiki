/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.admin;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.me.bus.ParameterBUS;

/**
 *
 * @author tuandom
 */
public class SaveConfigController extends HttpServlet {

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
        try {
            ParameterBUS bus = new ParameterBUS();

            String time_range = request.getParameter("time_range");
            String SolrAddress = request.getParameter("SolrAddress");
            String TimeIndexBM = request.getParameter("TimeIndexBookmark");
            String TimeIndexTracking = request.getParameter("TimeIndexTracking");
            String TimeTopSearch = request.getParameter("TimeTopSearch");
            String RecordPaging = request.getParameter("RecordPaging");

            bus.updateParameter("visearch", "time_range", time_range);
            bus.updateParameter("visearch", "SolrAddress", SolrAddress);
            bus.updateParameter("visearch", "TimeIndexBookmark", TimeIndexBM);
            bus.updateParameter("visearch", "TimeIndexTracking", TimeIndexTracking);
            bus.updateParameter("visearch", "TimeTopSearch", TimeTopSearch);
            bus.updateParameter("visearch", "RecordPaging", RecordPaging);

            String url = "/LoadConfigController";
            request.setAttribute("Msg", "Đã cập nhật thành công");
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);

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
