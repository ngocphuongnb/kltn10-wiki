/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.ViSearchController;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.me.Utils.MyHashEncryption;
import org.me.bus.MemberBUS;
import org.me.dto.MemberDTO;

/**
 *
 * @author VinhPham
 */
public class RegisterMemberController extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchAlgorithmException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String userCaptchaResponse = request.getParameter("jcaptcha");
            boolean captchaPassed = SimpleImageCaptchaServlet.validateResponse(request, userCaptchaResponse);
            if (captchaPassed) {
                MemberDTO mem = new MemberDTO();
                mem.setFullName(request.getParameter("idFullName").toString());
                mem.setUserName(request.getParameter("idUsername").toString());
                mem.setPass(MyHashEncryption.hashPassword(request.getParameter("idPassword").toString()));
                int day = Integer.parseInt(request.getParameter("idDay").toString());
                int month = Integer.parseInt(request.getParameter("idMonth").toString());
                int year = Integer.parseInt(request.getParameter("idYear").toString());
                Calendar cl = Calendar.getInstance();
                cl.set(year, month, day);
                mem.setBirthDay(cl);
                int i = Integer.parseInt(request.getParameter("radio").toString());
                mem.setSex(i);
                MemberBUS membus = new MemberBUS();
                if (membus.AddNewMember(mem, "visearch")) {
                    out.print("successful");
                } else {
                    out.print("error");
                }
            } else {
               out.println("fail");
            }
        } finally {
            out.print("error");
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
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegisterMemberController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegisterMemberController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
