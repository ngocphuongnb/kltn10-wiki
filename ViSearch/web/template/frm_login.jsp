<%-- 
    Document   : login2.jsp
    Created on : Jun 13, 2010, 4:59:13 PM
    Author     : tuandom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.me.dto.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div style="float:right; margin-bottom:8px; font-size:11px">
            <%
                        MemberDTO member = null;
                        if (session.getAttribute("Member") != null) {
                            member = new MemberDTO();
                            member = (MemberDTO) session.getAttribute("Member");
                        }

                        if (request.getAttribute("Member") != null) {
                            member = new MemberDTO();
                            member = (MemberDTO) request.getAttribute("Member");
                            session.setAttribute("Member", member);
                        }
                        if (member == null) {
            %>
            <a name="top" href="register.jsp">Đăng kí</a>&nbsp;&nbsp;
            <a href="login.jsp">Đăng nhập</a>&nbsp;&nbsp;
            <%                       } else {
                            out.print("Xin chào <a href=\"#\">" + member.getFullName() + "</a>&nbsp;&nbsp;|&nbsp;&nbsp;");
                            out.print("<a href=\"showBookmark.jsp\">Hiển thị Bookmark</a>&nbsp;&nbsp;|&nbsp;&nbsp;");
                            out.print("<a href=\"MemberLogoutController\">Đăng xuất</a>&nbsp;&nbsp;");
                        }%>
        </div>
    </body>
</html>

