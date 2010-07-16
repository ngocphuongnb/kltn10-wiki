/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.ViSearchController;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.me.SolrConnection.SolrJConnection;
import org.me.Utils.Paging;
import org.me.bus.BookMarkBUS;
import org.me.dto.BookMarkDTO;
import org.me.dto.MemberDTO;

/**
 *
 * @author tuandom
 */
public class ShowMyBookmark extends HttpServlet {

    SolrServer server;
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
        String sPaging = "/ViSearch/ShowMyBookmark?";
        long numRow = 0;
        int pagesize = 10;
        int currentpage = 1;
        SolrDocumentList NewestDocs = new SolrDocumentList();
        QueryResponse rsp;
        server = SolrJConnection.getSolrServer("bookmark");
        if (request.getParameter("currentpage") != null) {
            currentpage = Integer.parseInt(request.getParameter("currentpage"));
        }
        int start = (currentpage - 1) * pagesize;
        try {
            HttpSession session = request.getSession();
            MemberDTO mem = new MemberDTO();
            if (session.getAttribute("Member") != null) {
                mem = (MemberDTO) session.getAttribute("Member");

                int memberId = mem.getId();
                BookMarkBUS bmBUS = new BookMarkBUS();
                ArrayList<BookMarkDTO> lstBM = new ArrayList<BookMarkDTO>();
                lstBM = bmBUS.lstBookmark("visearch", memberId, start, pagesize);

                numRow = bmBUS.getNumRow("visearch", memberId);
                int numpage = (int) (numRow / pagesize);
                if (numRow % pagesize > 0) {
                    numpage++;
                }
                sPaging = Paging.getPaging(numpage, pagesize, currentpage, sPaging);
                 rsp = OnSearchSubmitNewestDocument(0, 10);
                NewestDocs = rsp.getResults();

                request.setAttribute("NewestDocs", NewestDocs);
                request.setAttribute("lstBM", lstBM);
                request.setAttribute("Pagging", sPaging);
                request.setAttribute("NumRow", numRow);
                request.setAttribute("NumPage", numpage);

                String url = "/showBookmark.jsp";
                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                out.print("Bạn chưa đăng nhập<br>");
                out.print("<a href='./'>Về trang chủ</a>");
            }
        } catch (Exception e) {
            out.print(e.getMessage());
        } finally {
            out.close();
        }
    }

     QueryResponse OnSearchSubmitNewestDocument(int start, int pagesize) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();

        String queryValue = "";
        String query = "";
        Calendar cl = Calendar.getInstance();
        String str1thangqua = cl.get(Calendar.YEAR) + "-" + cl.get(Calendar.MONTH) + "-" + cl.get(Calendar.DAY_OF_MONTH)
                + "T" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND) + "." + cl.get(Calendar.MILLISECOND) + "Z";

        queryValue = "[" + str1thangqua + " TO NOW]";

        query += " +(date_created:" + queryValue + ")";

        solrQuery.setQuery(query);

        solrQuery.setHighlightRequireFieldMatch(true);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
       // solrQuery.set("fl", "docid, searchtype, bookmarkname");
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
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
