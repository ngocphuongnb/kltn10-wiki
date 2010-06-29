<%-- 
    Document   : help
    Created on : Jun 29, 2010, 7:59:46 PM
    Author     : tuandom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ViSearch - Hướng dẫn tìm kiếm</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>

        <script type="text/javascript">

        </script>
    </head>

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
                                    </td>
                                </tr>
                                <tr>
                                    <td width="974" valign="top">
                                        <!-- banner here !-->
                                        <table id="Table_01" width="975" border="0" cellpadding="0" cellspacing="0">
                                            <tr><td><img alt="" src="images/Slogan.png" /></td></tr>
                                        </table>
                                        <!-- end banner !-->
                                    </td>
                                </tr>
                            </table>
                            <span></span>
                        </td>
                    </tr>
                    <tr>
                        <td height="20" colspan="2" align="center" valign="bottom">
                            <div align="center" class="nav"></div>
                        </td>
                    </tr>
                    <tr>
                        <td width="200" height="33" valign="top">
                            <div class="subtable">
                                <%
                                %>
                                <table id="tbTopSearch">
                                </table>
                            </div>
                        </td>
                        <td width="627" rowspan="2" valign="top">

                            <table>
                                <tr><td id="result_search"></td></tr><tr></tr>
                            </table>
                            <table id="table_right" width="100%" cellpadding="0" cellspacing="0">
                                <tr>
                                </tr>
                                <tr>
                                    <td valign="top" id="content">
                                        Từ khóa: <b>mùa hè sôi động</b> <br>
                                        Kết quả trả về sẽ là trang có chứa các chữ: mùa, mùa hè, sôi động...<br>

                                        Từ khóa: <b>mùa hè AND sôi động</b> <br>
                                        Kết quả trả về sẽ là trang có chứa các chữ: mùa hè sôi động...<br>

                                        Từ khóa: <b>mùa hè NOT sôi động</b> <br>
                                        Kết quả trả về sẽ là trang có chứa các từ mùa hè và không có từ sôi động...<br>
                                        <%  %>
                                    </td>
                                <tr>
                                    <td>
                                        <table><tr><td align="left" ><a href="index.jsp">Về trang chủ</a></td></tr></table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>

                    <tr>

                    </tr>
                    <tr><td height="30"></td></tr>
                    <tr><td height="20" colspan="2" align="center" valign="bottom"><div align="center" class="nav"></div></td></tr>
                    <tr>
                        <td width="100"></td>
                        <td colspan="2" valign="top">
                            <%@include file="template/footer1.jsp"%>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

    </body>
</html>




