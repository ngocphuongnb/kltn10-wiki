<%-- 
    Document   : showBookmark
    Created on : Jun 13, 2010, 6:16:24 PM
    Author     : tuandom
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" errorPage="" %>
<%@ page import="java.util.*"%> 
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%
            Object obj = request.getAttribute("forMark");
            if (obj == null) {
                String url = "/GetBookmarkController?";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <title>Hiển thị Bookmark - ViSearch</title>
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <link href="style.css"rel="stylesheet" type="text/css" />


    </head>
    <%
                String currentPage = "/showBookmark.jsp";
                if (request.getQueryString() != null) {
                    currentPage = "/GetBookmarkController?";
                    currentPage += request.getQueryString().toString();
                }
                session.setAttribute("CurrentPage", currentPage);



                List<Object[]> lstBmWiki = null;
                List<Object[]> lstBmRaoVat = null;
                List<Object[]> lstBmMusic = null;
                List<Object[]> lstBmNews = null;
                if (request.getAttribute("lstBmWiki") != null) {
                    lstBmWiki = (List<Object[]>) request.getAttribute("lstBmWiki");
                }
                if (request.getAttribute("lstBmRaoVat") != null) {
                    lstBmRaoVat = (List<Object[]>) request.getAttribute("lstBmRaoVat");
                }
                if (request.getAttribute("lstBmMusic") != null) {
                    lstBmMusic = (List<Object[]>) request.getAttribute("lstBmMusic");
                }
                if (request.getAttribute("lstBmNews") != null) {
                    lstBmMusic = (List<Object[]>) request.getAttribute("lstBmNews");
                }
    %>
    <body>

        <div id="wrap_left" align="center">
            <div id="wrap_right">
                <table id="wrap" width="974" border="0" cellpadding="0" cellspacing="0">

                    <tr><td height="8" colspan="2" align="center" valign="middle"></td></tr>
                    <tr>
                        <td height="130" colspan="2" valign="top">
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td style="text-align:right; margin-bottom:8px; font-size:11px">
                                        <%@include file="template/frm_login.jsp" %>
                                    </td></tr>
                                <tr>
                                    <td width="974" valign="top">
                                        <!-- banner here !-->
                                        <table id="Table_01" width="975" height="130" border="0" cellpadding="0" cellspacing="0">
                                            <tr><td><img src="images/banner-register.png" /></td></tr>
                                        </table>
                                        <!-- end banner      !-->
                                    </td>
                                </tr>
                            </table>
                        </td></tr>
                    <tr><td height="20" colspan="2" align="center" valign="bottom"><div align="center" class="nav"></div></td></tr>
                    <!-- !-->
                    <tr>
                        <td colspan="2" height="33" valign="top">

                            <!-- register !-->
                            <form class="frmRegister" name="" method="post" action="RegisterMemberController">
                                <h3 class="subblockhead"> Thông tin Bookmark đã lưu</h3>
                                <table width="500" border="0" cellspacing="0" cellpadding="0" >
                                    
                                   
                                    
                                    
                                    <%
                                                if (lstBmRaoVat != null) {
                                                    out.print("<tr><td align=\"left\" ><b>Trang Rao vặt<b></td></tr>");
                                                    for (int i = 0; i < lstBmRaoVat.size(); i++) {
                                                        Object[] objBm = new Object[2];
                                                        objBm = lstBmRaoVat.get(i);
                                                       String KeySearch = objBm[0].toString();
                                                        out.print("<tr><td align=\"left\" >Từ khóa: " + KeySearch + "</td></tr>");

                                                        ArrayList<String> lstDocId = new ArrayList<String>();
                                                        lstDocId = (ArrayList<String>) objBm[1];

                                                        for (int j = 0; j < lstDocId.size(); j++) {
                                                            out.print("<tr><td align=\"left\" ><a href=\"DetailRaoVatController?id=" + lstDocId.get(j) +"\">Link "+(j+1)+" </a></td></tr>");
                                                        }
                                                    }
                                                }
                                    %>

                                   
  <%
                                                if (lstBmNews != null) {
                                                    out.print("<tr><td align=\"left\" ><b>Trang tin tức<b></td></tr>");
                                                    for (int i = 0; i < lstBmNews.size(); i++) {
                                                        Object[] objBm = new Object[2];
                                                        objBm = lstBmNews.get(i);
                                                       String KeySearch = objBm[0].toString();
                                                        out.print("<tr><td align=\"left\" >Từ khóa: " + KeySearch + "</td></tr>");

                                                        ArrayList<String> lstDocId = new ArrayList<String>();
                                                        lstDocId = (ArrayList<String>) objBm[1];

                                                        for (int j = 0; j < lstDocId.size(); j++) {
                                                            out.print("<tr><td align=\"left\" ><a href=\"DetailRaoVatController?id=" + lstDocId.get(j) +"\">Link "+(j+1)+" </a></td></tr>");
                                                        }
                                                    }
                                                }
                                    %>


                                   
                                    <tr><td height="15"></td></tr>
                                    <tr><td align="left" ><a href="index.jsp">Về trang chủ</a></td></tr>
                                </table>
                            </form>
                            <!-- end register -->
                        </td>

                    </tr>



                    <tr height="50"><td></td><td width="743"></td>
                    </tr>
                    <!-- -->
                    <tr><td height="20" colspan="2" align="center" valign="bottom"><div align="center" class="nav"></div></td></tr>
                    <tr>
                        <td width="200"></td>
                        <td colspan="2" valign="top">
                            <%@include file="template/footer.jsp"%>
                        </td>
                    </tr>
                </table>

            </div>
        </div>

    </body>
</html>


