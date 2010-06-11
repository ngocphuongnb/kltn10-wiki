/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.ViSearchController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.me.bus.TrackingBUS;

/**
 *
 * @author VinhPham
 */
public class TopSearch extends HttpServlet {

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
        try {
            if (request.getParameter("SearchType") != null) {
                int searchtype = Integer.parseInt(request.getParameter("SearchType"));
                String sController = "";
                switch (searchtype) {
                    case 1:
                        sController = "SearchWikiController";
                        break;
                    case 2:
                        sController = "SearchRaoVatController";
                        break;
                    case 3:
                        sController = "SearchMusicController";
                        break;
                    case 4:
                        sController = "SearchVideoController";
                        break;
                    default:
                        break;
                }
                ArrayList<String> arr = new ArrayList<String>();
                TrackingBUS tbus = new TrackingBUS();
                arr = tbus.GetTopSearch(searchtype, "visearch");
                String result = "";
                String url = "";
                for (String keysearch : arr) {
                    url = String.format("%s?type=0&sp=1&KeySearch=%s&SortedType=0", sController, keysearch);
                    result += String.format("<tr><td><a href='%s'>%s</a></td></tr>", url, keysearch);
                }
                out.print(result);
            }
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
