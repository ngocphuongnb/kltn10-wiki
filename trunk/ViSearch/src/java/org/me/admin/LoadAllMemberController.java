/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.me.Utils.Paging;
import org.me.bus.MemberBUS;
import org.me.dto.MemberDTO;

/**
 *
 * @author VinhPhamXP
 */
public class LoadAllMemberController extends HttpServlet {

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
        //int pagesize = 2;
        //int currentpage = 1;
        String sPaging = "/ViSearch/LoadAllMemberController?";
        //int numRow = 0;
        try {
            MemberBUS membus = new MemberBUS();
//            if (request.getParameter("currentpage") != null) {
//                currentpage = Integer.parseInt(request.getParameter("currentpage"));
//            }
//            int start = (currentpage - 1) * pagesize;
//            numRow = membus.Count();
//            int numpage = (int) (numRow / pagesize);
//
//            if (numRow % pagesize > 0) {
//                numpage++;
//            }
            //sPaging = Paging.getPaging(numpage, pagesize, currentpage, sPaging);
            ArrayList<MemberDTO> ListMember = new ArrayList<MemberDTO>();
            ListMember = membus.GetListMember("visearch");
            request.setAttribute("ListMember", ListMember);
            //request.setAttribute("Paging", sPaging);
            String url = "/admin/member_management.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (Exception ex) {
            out.print(ex.getMessage());
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
