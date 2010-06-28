/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.ViSearchController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.me.Utils.MyHashEncryption;
import org.me.bus.MemberBUS;
import org.me.dto.MemberDTO;

/**
 *
 * @author VinhPham
 */
public class MemberInfoController extends HttpServlet {

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
            int type;
            HttpSession session = request.getSession();
            MemberDTO member = (MemberDTO) session.getAttribute("Member");
            MemberBUS membus = new MemberBUS();
            if (request.getParameter("type") != null) {
                type = Integer.parseInt(request.getParameter("type").toString());
                switch (type) {
                    case 1: //change pass
                        String oldpass = "";
                        String newpass = "";
                        oldpass = request.getParameter("oldpass").toString();
                        oldpass = MyHashEncryption.hashPassword(oldpass);
                        newpass = request.getParameter("newpass").toString();
                        newpass = MyHashEncryption.hashPassword(newpass);
                        if (membus.ChangePass(member.getId(), oldpass, newpass, "visearch")) {
                            out.print("Cập nhật thành công");
                        } else {
                            out.print("Mật khẩu không đúng! Vui lòng thử lại");
                        }
                        break;
                    case 2:
                        member.setFullName(request.getParameter("FullName").toString());
                        int day = Integer.parseInt(request.getParameter("Day").toString());
                        int month = Integer.parseInt(request.getParameter("Month").toString());
                        int year = Integer.parseInt(request.getParameter("Year").toString());
                        Calendar cl = Calendar.getInstance();
                        cl.set(year, month, day);
                        member.setBirthDay(cl);
                        int i = Integer.parseInt(request.getParameter("Sex").toString());
                        member.setSex(i);
                        if (membus.UpdateInfo(member, "visearch")) {
                            out.print("Cập nhật thành công");
                        } else {
                            out.print("Lỗi cập nhật");
                        }
                        break;
                    default:
                        break;
                }
            }

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
