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

<title>Trang chủ - ViSearch</title>
<link href="style.css"rel="stylesheet" type="text/css" />
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
      <form id="frmRegister" name="frmRegister" method="post" action="RegisterMemberController">
      <h3 class="subblockhead"> Thông tin đăng kí</h3>
<table width="500" border="0" cellspacing="0" cellpadding="0" >
                <tr><td>Họ và Tên</td></tr>
                <tr><td width="144"><input type="text" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" name="idFullName" id="idFullName" size="45"/></td></tr>
                <tr><td height="15"></td></tr>

                <tr><td>Tên đăng nhập</td></tr>
                <tr><td class="required"><input type="text" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" name="idUsername" id="idUsername" />(*)</td></tr>
                <tr><td height="15"></td></tr>

                <tr><td>Mật khẩu</td></tr>
                <tr><td class="required"><input type="password" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" name="idPassword" id="idPassword" />(*)</td></tr>
                <tr><td height="15"></td></tr>

                <tr><td>Nhập lại mật khẩu</td></tr>
                <tr><td class="required"><input type="password" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" name="idRePassword" id="idRePassword" />(*)</td></tr>
                <tr><td height="15"></td></tr>

                <tr><td>Ngày sinh (dd/mm/yyyy)</td></tr>
                 <tr><td>
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

               <tr><td>Giới tính</td></tr>
                <tr><td><input type="radio" name="radio" id="idSex" value="1" />
                        <label for="idSex">Nam</label>
                        <input type="radio" name="radio" id="idSex" value="0" />
                        <label for="idSex">Nữ
                            <input name="radio" type="radio" id="idSex" value="2" checked="checked" />
                  Không tiết lộ</label></td></tr>
                  <tr><td height="15"></td></tr>

                  <tr><td>Mã xác nhận</td></tr>
                    <tr><td class="required">
                        <span id="idCaptcha"></span>
                        <img id="captchaImage" src="jcaptcha.jpg" align="middle"/>
                        <br/><input type="text" name="jcaptcha" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" id="jcaptchar"/>
                        (*)
                  </td></tr>
                  <tr><td height="15"></td></tr>

                <tr>
                  <td>
                  <input name="btnSubmit" type="submit" value="Đăng ký" />
                  <input type="reset" name="btnReset" id="btnReset" value="Làm lại" /></td>
                </tr>
                  <tr><td height="15"></td></tr>
                  <tr><td><a href="index.jsp">Về trang chủ</a></td></tr>
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

