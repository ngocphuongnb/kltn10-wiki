/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Member;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.me.bus.MemberBUS;

/**
 *
 * @author VinhPhamXP
 */
public class UpdateRoleController extends HttpServlet {

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
            HttpSession session = request.getSession();
            if (session.getAttribute("admin") != null) {
                if (request.getParameter("id")!=null) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    int value = Integer.parseInt(request.getParameter("vl"));
                    MemberBUS membus = new MemberBUS();
                    if(membus.UpdateRole(id, value, "visearch"))
                        out.print("1");
                    else
                        out.print("0");
                }
            } else {
                out.print("Bạn không có quyền truy cập và trang này");
            }
        } catch(Exception ex){
            out.print(ex.getMessage());
        }finally {
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
