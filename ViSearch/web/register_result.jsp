<%-- 
    Document   : loginresult
    Created on : Nov 13, 2009, 1:01:51 AM
    Author     : VinhPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.me.dto.*, java.util.*" session="true"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%
                    String url = "index.jsp";
                    MemberDTO member = (MemberDTO) request.getAttribute("Register");
                    session.setAttribute("Member", member);
                    if (session.getAttribute("CurrentPage") != null) {
                        url = session.getAttribute("CurrentPage").toString();
                    }


        %>
        <META HTTP-EQUIV="refresh" CONTENT="3;URL=<%="Visearch/"+url%>">
        <title>Kết quả đăng ký</title>
    </head>
    <body>
        <div align="center">
            <%
                        String name = "";
                        if (!member.getFullName().equals("")) {
                            name = member.getFullName();
                        } else {
                            name = member.getUserName();
                        }
            %>
            Chúc mừng <%=name%> đã đăng ký thành công<br/>
            Server sẽ tự động đăng nhập và chuyển đến trang trước trong vài giây<br/>
            <a href="<%=url%>">Click vào đây</a> nếu đợi quá lâu.
        </div>
    </body>
</html>
