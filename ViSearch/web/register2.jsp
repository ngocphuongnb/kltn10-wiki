<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Đăng ký thành viên</title>

    <script type="text/javascript" src="script/jquery-1.4.2.min.js"/>
    <script type="text/javascript">
        $(document).ready(function(){
            //$("#idCaptcha").load("template/captcha.jsp");
            $("#frmRegister").submit(function(){
                if($("#idUsername").attr("value").trim() == "")
                {
                    alert("Chưa chọn tên đăng nhập");
                    $("#idUsername").focus();
                    return false;
                }
                if($("#idPassword").attr("value") == "")
                {
                    alert("Chưa nhập mật khẩu");
                    $("#idPassword").focus();
                    return false;
                }
                if($("#idRePassword").attr("value") == "" || $("#idRePassword").attr("value") != $("#idPassword").attr("value"))
                {
                    alert("Mật khẩu nhập 2 lần không giống nhau");
                    $("#idPassword").focus();
                    return false;
                }
                if($("#jcaptchar").attr("value").trim() == "")
                {
                    alert("Chưa nhập mã xác nhận");
                    $("#jcaptchar").focus();
                    return false;
                }
            });
        });
    </script>

    <style type="text/css">
    .required {
	color: #F00;
}
    </style>
    </head>

    <body>
    <form id="frmRegister" name="frmRegister" method="post" action="RegisterMemberController">
<table width="500" border="1" cellspacing="0" cellpadding="0">
                <tr>
                    <th width="50">Họ tên</th>
                    <td width="144">
                        <input type="text" name="idFullName" id="idFullName" size="45"/>
                    </td>
                </tr>
                <tr>
                    <th>Tên đăng nhập</th>
                    <td class="required">
                        <input type="text" name="idUsername" id="idUsername" />
                  (*)</td>
                </tr>
                <tr>
                    <th>Mật khẩu</th>
                    <td class="required">
                        <input type="password" name="idPassword" id="idPassword" />
                        (*)</td>
                </tr>
                <tr>
                    <th>Nhập lại mật khẩu</th>
                    <td class="required">
                        <input type="password" name="idRePassword" id="idRePassword" />
                        (*)</td>
                </tr>
                <tr>
                    <th>Ngày sinh (dd/mm/yyyy)</th>
                    <td>
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
                        </select></td>
                </tr>
                <tr>
                    <th>Giới tính</th>
                    <td><input type="radio" name="radio" id="idSex" value="1" />
                        <label for="idSex">Nam</label>
                        <input type="radio" name="radio" id="idSex" value="0" />
                        <label for="idSex">Nữ
                            <input name="radio" type="radio" id="idSex" value="2" checked="checked" />
                  Không tiết lộ</label></td>
                </tr>
                <tr>
                    <th>Mã xác nhận</th>
                    <td class="required">
                        <span id="idCaptcha"></span>
                        <img id="captchaImage" src="jcaptcha.jpg" align="middle"/>
                        <br/><input type="text" name="jcaptcha" id="jcaptchar"/>
                        (*)
                  </td>
                </tr>
                <tr>
                  <td colspan="2" align="center"><input name="btnSubmit" type="submit" value="Đăng ký" />
                  <input type="reset" name="btnReset" id="btnReset" value="Làm lại" /></td>
                </tr>
            </table>
      </form>
</body>
</html>