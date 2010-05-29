<%--
    Document   : banner_RaoVat
    Created on : May 22, 2010, 3:51:19 PM
    Author     : vinhpham, tuandom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.me.dto.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <script language="javascript" type="text/javascript">
            function checkInputLogin(){
                if(document.getElementById("username").value == ""){
                    alert("Xin vui lòng nhập vào tên đăng nhập");
                    document.getElementById("username").focus();
                    return false;
                }
                if(document.getElementById("password").value==""){
                    alert("Xin vui lòng nhập vào mật khẩu");
                    document.getElementById("password").focus();
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>
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
        <table style="font-size: 13px">
            <form name="frmLogin" method="post" action="/ViSearch/MemberLoginController" style="text-align:center" onsubmit="return checkInputLogin();">
                <tr><td><label for="username">Tên đăng nhập:</label></td> </tr>
                <tr><td><input type="text" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" name="username" id="username"></td> </tr>


                <tr><td> <label for="password">Mật khẩu: </label></td> </tr>
                <tr><td><input type="password" name="password" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" id="password"></td> </tr>

                <tr><td>
                        <input type="submit" name="btnLogin" id="btnLogin" value="Đăng nhập">
                        <input type="reset" value="Làm lại" />
                    </td> </tr>
                <tr>
                    <td style="font-size: 10pt">
                        <span>Quên mật khẩu</span>|<span>Đăng ký</span>
                    </td>
                </tr>
            </form>
        </table>
        <%                    }
        %>
    </body>
</html>