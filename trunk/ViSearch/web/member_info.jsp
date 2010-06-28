<%-- 
    Document   : register
    Created on : May 30, 2010, 3:52:28 PM
    Author     : tuandom
--%>
<%@page import="java.lang.reflect.Member"%>
<%@page import="org.me.dto.MemberDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" errorPage="" %>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <title>Thông tin cá nhân - ViSearch</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <link type="text/css" href="css/visearchStyle.css" rel="stylesheet"/>

        <script type="text/javascript">
            $(function() {
                $('#btCheckValid')
                .button()
                .click(function() {
                    var username = $("#txtusername").attr("value");
                    var Url = "CheckValidUsername?username=" + username;
                    $("#Bookmark").load(encodeURI(Url));
                    $("#checkValid").load(encodeURI(Url));
                });

                $("#idDay").val($("#hfDay").val());
                $("#idMonth").val($("#hfMonth").val());
                $("#idYear").val($("#hfYear").val());

                if ($("#hfSex").val() == "0")
                    $("#idNu").attr("checked","checked");
                else if ($("#hfSex").val() == "1")
                    $("#idNam").attr("checked","checked");
                else
                    $("#idKhongTietLo").attr("checked","checked");
            });



            $(function() {
                var oldpass = $("#oldpass"),
                newpass = $("#newpass"),
                renewpass = $("#renewpass"),
                allFields = $([]).add(oldpass).add(newpass).add(renewpass),
                tips = $(".validateTips");
                $("#dialog").dialog("destroy");

                function requestChangePass()
                {
                    var bValid = true;

                    allFields.removeClass('ui-state-error');

                    bValid = bValid && checkLength(oldpass,"mật khẩu hiện tại");
                    bValid = bValid && checkLength(newpass,"mật khẩu mới");
                    bValid = bValid && checkMinLength(newpass,"Mật khẩu mới");
                    bValid = bValid && compareNewPass(newpass,renewpass,"Vui lòng xác nhận lại mật khẩu mới");

                    if (bValid) {
                        $.ajax({
                            type: 'POST',
                            url: "/ViSearch/MemberInfoController",
                            data: 'type=1' + '&oldpass=' + oldpass.val() + '&newpass=' + newpass.val(),
                            success: function(html){
                                updateTips(html);
                            }
                        });
                        $("#login-form").dialog('close');
                    }
                }


                function updateTips(t) {
                    tips
                    .text(t)
                    .addClass('ui-state-highlight');
                    setTimeout(function() {
                        tips.removeClass('ui-state-highlight', 1500);
                    }, 500);
                }

                function checkLength(o,n) {

                    if ( o.val().length ==0 ) {
                        o.addClass('ui-state-error');
                        updateTips("Bạn chưa nhập " + n);
                        return false;
                    } else {
                        return true;
                    }

                }

                function checkMinLength(o,n) {

                    if ( o.val().length > 0 &&  o.val().length < 4) {
                        o.addClass('ui-state-error');
                        updateTips(n + " phải ít nhất 4 ký tự");
                        return false;
                    } else {
                        return true;
                    }

                }

                function compareNewPass(o1,o2,n) {

                    if ( o1.val() != o2.val() ) {
                        o1.addClass('ui-state-error');
                        o2.addClass('ui-state-error');
                        updateTips(n);
                        return false;
                    } else {
                        return true;
                    }

                }

                $("#changepass-form").dialog({
                    autoOpen: false,
                    height: 350,
                    width: 350,
                    modal: true,
                    buttons: {
                        'Đóng': function() {
                            $(this).dialog('close');
                        },
                        'Cập nhật': function() {
                            requestChangePass();
                        }

                    },
                    close: function() {
                        allFields.val('').removeClass('ui-state-error');
                    }
                });



                $('#linkChangePass')
                .click(function() {
                    $('#changepass-form').dialog('open');
                });

                $('form#frmChangePass').submit(function(){
                    requestChangePass();
                    return false;
                });

                $('form#frmInfo').submit(function(){
                    //requestChangePass();
                    var sex = $("input[@name='radio']:checked").val();
                    var name = $("#idFullName").val();
                    var d = $("#idDay").val();
                    var m = $("#idMonth").val();
                    var y = $("#idYear").val();
                    $.ajax({
                        type: 'POST',
                        url: "/ViSearch/MemberInfoController",
                        data: 'type=2' + '&FullName=' + name + '&Day=' + d + '&Month=' + m + '&Year=' + y + '&Sex=' + sex,
                        success: function(html){
                            $("#updateResult").html(html);
                        }
                    });
                    return false;
                });
            });
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
                                            <tr><td><img src="images/Slogan.png" /></td></tr>
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
                            <%
                                        MemberDTO member = null;
                                        if (session.getAttribute("Member") != null) {
                                            member = (MemberDTO) session.getAttribute("Member");
                            %>
                            <h2 class="subblockhead"> Thông tin thành viên</h2>
                            <div align="center">
                                <h3 class="ui-widget-header">Thông tin đăng nhập</h3>
                                <table class="ui-widget-content" border="0" cellspacing="5" cellpadding="10" width="90%">
                                    <tbody>
                                        <tr>
                                            <th>Tên đăng nhập:</th>
                                            <td><%=member.getUserName()%></td>
                                        </tr>
                                        <tr>
                                            <th>Mật khẩu:</th>
                                            <td>
                                                *****
                                            </td>
                                            <td><a id="linkChangePass" href="#">Đổi mật khẩu</a></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>

                            <form class="frmInfo" id="frmInfo" method="post">
                                <div align="center">
                                    <h3 class="ui-widget-header">Thông tin cá nhân</h3>
                                    <table class="ui-widget-content" border="0" cellspacing="5" cellpadding="10" width="90%">
                                        <tr>
                                            <th>Họ và Tên</th>
                                            <td align="left" width="144"><input type="text" class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" name="idFullName" id="idFullName" size="45" value="<%=member.getFullName()%>"/></td>
                                        </tr>
                                        <tr><th>Ngày sinh<br/> (dd/mm/yyyy)</th>
                                            <td>
                                                <select name="idDay" id="idDay">
                                                    <%
                                                                                                for (int i = 1; i < 32; i++) {
                                                                                                    out.println("<option  value='" + i + "'>" + i + "</option >");
                                                                                                }
                                                    %>
                                                    <input type="hidden" id="hfDay" value="<%=member.getBirthDay().get(Calendar.DAY_OF_MONTH)%>"/>
                                                </select>
                                                /
                                                <select name="idMonth" id="idMonth">
                                                    <%
                                                                                                for (int i = 1; i < 13; i++) {
                                                                                                    out.println("<option  value='" + i + "'>" + i + "</option >");
                                                                                                }
                                                    %>
                                                </select>
                                                <input type="hidden" id="hfMonth" value="<%=member.getBirthDay().get(Calendar.MONTH)%>"/>
                                                /
                                                <select name="idYear" id="idYear">
                                                    <%
                                                                                                Calendar cl = Calendar.getInstance();
                                                                                                for (int i = 1900; i < cl.get(Calendar.YEAR) - 7; i++) {
                                                                                                    out.println("<option  value='" + i + "'>" + i + "</option >");
                                                                                                }
                                                    %>
                                                </select>
                                                <input type="hidden" id="hfYear" value="<%=member.getBirthDay().get(Calendar.YEAR)%>"/>
                                            </td></tr>

                                        <tr><th>Giới tính</th>
                                            <td align="left" ><input type="radio" name="radio" id="idNam" value="1" />
                                                <label for="idNam">Nam</label>
                                                <input type="radio" name="radio" id="idNu" value="0" />
                                                <label for="idNu">Nữ</label>
                                                <input name="radio" type="radio" id="idKhongTietLo" value="2"/>
                                                <label for="idKhongTietLo">Không tiết lộ</label></td>
                                            <input type="hidden" id="hfSex" value="<%=member.getSex()%>"/>
                                        </tr>
                                        <tr>
                                            <td colspan="2" align="center">
                                                <input name="btnSubmit" type="submit" value="Lưu thông tin" />
                                                <input type="reset" name="btnReset" id="btnReset" value="Làm lại" />
                                            </td>
                                        </tr>
                                    </table>
                                    <span id="updateResult"/>
                                </div>
                            </form>
                                        <p><a href="index.jsp">Về trang chủ</a></p>                            <!-- end register -->
                            <%
                                        } else
                                            response.sendRedirect("index.jsp");
                            %>
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

        <div id="changepass-form" title="Đổi mật khẩu">
            <p class="validateTips">Vui lòng điền đủ thông tin.</p>
            <form id="frmChangePass">
                <fieldset>
                    <label for="oldpass">Mật khẩu hiện tại</label><br/>
                    <input type="password" id="oldpass" class="text ui-widget-content ui-corner-all" /><br/>
                    <label for="newpass">Mật khẩu mới</label><br/>
                    <input type="password" id="newpass" class="text ui-widget-content ui-corner-all" /><br/>
                    <label for="renewpass">Nhập lại mật khẩu mới</label><br/>
                    <input type="password" id="renewpass" class="text ui-widget-content ui-corner-all" /><br/>
                </fieldset>
            </form>
        </div>
    </body>
</html>

