/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.ViSearchController;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.me.bus.BookMarkBUS;
import org.me.dto.BookMarkDTO;
import org.me.dto.MemberDTO;

/**
 *
 * @author VinhPham
 */
public class BookmarkController extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            MemberDTO memdto = (MemberDTO) session.getAttribute("Member");
            int searchType = Integer.parseInt(request.getParameter("SearchType").toString());
            BookMarkDTO bmdto = new BookMarkDTO();
            String docID = request.getParameter("DocID").toString();
            String nameBookmark = request.getParameter("NameBookmark").toString();
            int priority = Integer.parseInt(request.getParameter("Priority").toString());
            bmdto.setDocId(docID);
            bmdto.setMemberId(memdto.getId());
            bmdto.setSearchType(searchType);
            bmdto.setNameBookmark(nameBookmark);
            bmdto.setPriority(priority);
            BookMarkBUS bmbus = new BookMarkBUS();
            bmbus.InsertBookmark(bmdto, "visearch");
            out.print("<input type='button' disabled value='Đã thêm bookmark'/>");
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
