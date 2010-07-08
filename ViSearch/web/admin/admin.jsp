<%-- 
    Document   : admin
    Created on : Jul 6, 2010, 9:24:25 AM
    Author     : VinhPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" errorPage="" %>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Trang chủ admin</title>
        <link href="../style.css"rel="stylesheet" type="text/css" />
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
                                            <tr><td><img src="../images/BannerAdmin.png" /></td></tr>
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
                            <h1>các chức năng quản lý của admin</h1>
                            <a href="/ViSearch/LoadConfigController">Cấu hình hệ thống</a>
                            <a href="/ViSearch/LoadAllMemberController">Quản lý thành viên</a>
                        </td>
                    </tr>

                    <tr height="50"><td></td><td width="743"></td>
                    </tr>
                    <!-- -->
                    <tr><td height="20" colspan="2" align="center" valign="bottom"><div align="center" class="nav"></div></td></tr>
                    <tr>
                        <td width="200"></td>
                        <td colspan="2" valign="top">
                            <%@include file="../template/footer.jsp"%>
                        </td>
                    </tr>
                </table>

            </div>
        </div>

    </body>
</html>


