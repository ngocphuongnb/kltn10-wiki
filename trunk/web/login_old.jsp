
<%--
    Document   : register
    Created on : May 30, 2010, 3:52:28 PM
    Author     : tuandom
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" errorPage="" %>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <title>Đăng nhập - ViSearch</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <style type="text/css">
            .required {
                color: #F00;
            }
        </style>
       

    </head>

    <body>

        <div id="wrap_left" align="center">
            <div id="wrap_right">
                <table id="wrap" width="974" border="0" cellpadding="0" cellspacing="0">

                    <tr><td height="20" colspan="2" align="center" valign="middle"></td></tr>
                    <tr>
                        <td height="130" colspan="2" valign="top">
                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
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
                            <form class="frmRegister" name="frmRegister" method="post" action="/ViSearch/MemberLoginController">
                                <h3 class="subblockhead"> Thông tin đăng nhập</h3>
                                <table width="500" border="0" cellspacing="0" cellpadding="0" >
                                   

                                    <tr><td align="left">Tên đăng nhập</td></tr>
                                    <tr><td  align="left" class="required"><input type="text" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" name="username" id="username" />(*)</td></tr>
                                    <tr><td height="15"></td></tr>

                                    <tr><td align="left" >Mật khẩu</td></tr>
                                    <tr><td  align="left" class="required"><input type="password" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" name="password" id="password" />(*)</td></tr>
                                    <tr><td height="15"></td></tr>

                                   
                                    <tr><td height="15"></td></tr>


                                    <tr>
                                        <td align="left" >
                                            <input name="btnSubmit" type="submit" value="Đăng nhập" />
                                            <input type="reset" name="btnReset" id="btnReset" value="Làm lại" /></td>
                                    </tr>
                                    <tr><td height="15"></td></tr>
                                    <tr><td align="left" ><a href="index.jsp">Về trang chủ</a>&nbsp;&nbsp;<a href="index.jsp">Quên mật khẩu</a></td></tr>
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

