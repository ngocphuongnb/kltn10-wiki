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

        <title>Đăng kí tài khoản - ViSearch</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <link type="text/css" href="css/visearchStyle.css" rel="stylesheet"/>

        <script type="text/javascript">
            function CheckUserName()
            {
                var username = $("#txtusername").attr("value");
                if(username.toString().length == 0)
                {
                    $("#rowerror").html("Bạn chưa chọn tên đăng nhập");
                    return false;
                }
                else{
                    var regex = /^[a-z]([0-9a-z_])+$/i;
                    if(!regex.test(username))
                    {
                        $("#rowerror").html("Tên đăng nhập chỉ gồm các ký tự a-z, 0-9");
                        return false;
                    }
                }
                var Url = "CheckValidUsername?username=" + username;
                $("#checkValid").load(encodeURI(Url));
            }
            
            function CheckInput()
            {
                var error="";
                var username = $("#txtusername").val();
                if(username.toString().length == 0)
                {
                    error += "<li>Bạn chưa chọn tên đăng nhập</li>";
                }
                else{
                    var regex = /^[a-z]([0-9a-z_])+$/i;
                    if(!regex.test(username))
                        error += "<li>Tên đăng nhập chỉ gồm các ký tự a-z, 0-9</li>";
                }

                if($("#idPassword").val().length==0)
                    error += "<li>Bạn chưa nhập mật khẩu</li>";
                else
                    if($("#idPassword").val().length<4)
                        error += "<li>Mật khẩu phải ít nhất gồm 4 ký tự</li>";

                if($("#idPassword").val()!=$("#idRePassword").val())
                    error += "<li>Mật khẩu nhập 2 lần không giống nhau</li>";

                if($("#jcaptchar").val().toString().length == 0)
                {
                    error += "<li>Bạn chưa nhập mã xác nhận</li>";
                }

                if(error.toString().length!=0)
                {
                    $("#rowerror").html(error);
                    return false;
                }
                var Url = "CheckValidUsername?username=" + username;
                $("#checkValid").load(encodeURI(Url));

                if($("#checkValid").html()!="<font color=\"red\">Đã tồn tại username này</font>")
                {
                    frmRegister.action = "RegisterMemberController";
                    frmRegister.submit();
                }

            }
        </script>
        <style type="text/css">
            .required {
                color: #F00;
            }
        </style>
        <script language="javascript">

        </script>

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
                            <form class="frmRegister" name="frmRegister" method="post" action="javascript:CheckInput();">
                                <h3 class="subblockhead"> Thông tin đăng kí</h3>
                                <table width="500" border="0" cellspacing="0" cellpadding="0" >
                                    <tr><td><ul align="left" style="color: tomato" id="rowerror"></ul></td></tr>
                                    <tr><td height="15"></td></tr>
                                    <tr><td  align="left">Họ và Tên</td></tr>
                                    <tr><td align="left" width="144"><input type="text" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" name="idFullName" id="idFullName" size="45"/></td></tr>

                                    <tr><td height="15"></td></tr>
                                    <tr><td align="left">Tên đăng nhập</td></tr>
                                    <tr><td align="left" class="required"><input id="txtusername" type="text" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm'; CheckUserName();" name="idUsername" id="idUsername" />(*)</td>
                                    </tr>
                                    <tr><td align="left"><span id="checkValid"/></td></tr>

                                    <tr><td height="15"></td></tr>

                                    <tr><td align="left" >Mật khẩu</td></tr>
                                    <tr><td  align="left" class="required"><input type="password" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" name="idPassword" id="idPassword" />(*)</td></tr>
                                    <tr><td height="15"></td></tr>

                                    <tr><td align="left" >Nhập lại mật khẩu</td></tr>
                                    <tr><td  align="left" class="required"><input type="password" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" name="idRePassword" id="idRePassword" />(*)</td></tr>
                                    <tr><td height="15"></td></tr>

                                    <tr><td align="left" >Ngày sinh (dd/mm/yyyy)</td></tr>
                                    <tr><td align="left" >
                                            <select name="idDay" id="idDay">
                                                <%
                                                            for (int i = 1; i < 32; i++) {
                                                                out.println("<option  value='" + i + "'>" + i + "</option >");
                                                            }
                                                %>
                                            </select>
                                            /
                                            <select name="idMonth" id="idMonth">
                                                <%
                                                            for (int i = 1; i < 13; i++) {
                                                                out.println("<option  value='" + i + "'>" + i + "</option >");
                                                            }
                                                %>
                                            </select>
                                            /
                                            <select name="idYear" id="idYear">
                                                <%
                                                            Calendar cl = Calendar.getInstance();
                                                            for (int i = 1900; i < cl.get(Calendar.YEAR) - 7; i++) {
                                                                out.println("<option  value='" + i + "'>" + i + "</option >");
                                                            }
                                                %>
                                            </select></td></tr>
                                    <tr><td height="15"></td></tr>

                                    <tr><td align="left" >Giới tính</td></tr>
                                    <tr><td align="left" ><input type="radio" name="radio" id="idNam" value="1" />
                                            <label for="idNam">Nam</label>
                                            <input type="radio" name="radio" id="idNu" value="0" />
                                            <label for="idNu">Nữ</label>
                                            <input name="radio" type="radio" id="idKhongTietLo" value="2" checked="checked" />
                                            <label for="idKhongTietLo">Không tiết lộ</label></td></tr>
                                    <tr><td height="15"></td></tr>

                                    <tr><td align="left" >Mã xác nhận</td></tr>
                                    <tr><td  align="left" class="required">
                                            <span id="idCaptcha"></span>
                                            <img id="captchaImage" src="jcaptcha.jpg" align="middle"/>
                                            <br/><input  align="left" type="text" name="jcaptcha" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" id="jcaptchar"/>(*)
                                        </td>
                                    </tr>

                                    <tr><td height="15"></td></tr>
                                    <tr>
                                        <td align="left" >
                                            <input name="btnSubmit" type="submit" value="Đăng ký"/>
                                            <input type="reset" name="btnReset" id="btnReset" value="Làm lại" /></td>
                                    </tr>
                                    <tr>
                                        <td align="center" ><a href="index.jsp">Về trang chủ</a></td>
                                    </tr>
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

