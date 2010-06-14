/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.ViSearchController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.me.dao.BookMarkDAO;
import org.me.dto.MemberDTO;


/**
 *
 * @author tuandom
 */
public class GetBookmarkController extends HttpServlet {
   
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
            HttpSession session = request.getSession();
            MemberDTO memdto = (MemberDTO) session.getAttribute("Member");

            // Data wikiBookMark
            List<Object[]> lstBmWiki = getBookmart(memdto.getId(), 1);
            // Data wikiRaoVat
            List<Object[]> lstBmRaoVat = getBookmart(memdto.getId(), 2);

           // Data wikiMusic
            List<Object[]> lstBmMusic = getBookmart(memdto.getId(), 3);

                       // Data News
            List<Object[]> lstBmNews = getBookmart(memdto.getId(), 6);


            request.setAttribute("lstBmWiki", lstBmWiki);
            request.setAttribute("lstBmRaoVat", lstBmRaoVat);
            request.setAttribute("lstBmMusic", lstBmMusic);
            request.setAttribute("lstBmNews", lstBmNews);
             request.setAttribute("forMark", 1);
        } finally {
            String url = "/showBookmark.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    } 

     private List<Object[]> getBookmart(int memberId, int searchType) {
        BookMarkDAO myDAO = new BookMarkDAO();
        return myDAO.GetBookmark(memberId, searchType, "visearch");
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
