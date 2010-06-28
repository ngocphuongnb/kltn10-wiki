<%-- 
    Document   : login
    Created on : Jun 17, 2010, 9:34:48 AM
    Author     : VinhPham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.me.dto.*" session="true" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link type="text/css" href="../css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="../js/jquery-ui-1.8.2.custom.min.js"></script>
        <link type="text/css" href="css/visearchStyle.css" rel="stylesheet"/>
    </head>
    <body>
        <script type="text/javascript">
            $(function() {
                var username = $("#username"),
                password = $("#password"),
                allFields = $([]).add(username).add(password),
                tips = $(".validateTips");
                $("#dialog").dialog("destroy");

                function requestLogin()
                {
                    var bValid = true;

                    allFields.removeClass('ui-state-error');

                    bValid = bValid && checkLength(username,"Tên đăng nhập");
                    bValid = bValid && checkLength(password,"mật khẩu");

                    bValid = bValid && checkRegexp(username,/^[a-z]([0-9a-z_])+$/i,"Tên đăng nhập gồm các ký tự a-z, 0-9");

                    if (bValid) {
                        $.ajax({
                            type: 'POST',
                            url: "/ViSearch/MemberLoginController",
                            data: 'username=' + username.val() + '&password=' + password.val(),
                            success: function(html){
                                window.location = html;
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

                function checkRegexp(o,regexp,n) {

                    if ( !( regexp.test( o.val() ) ) ) {
                        o.addClass('ui-state-error');
                        updateTips(n);
                        return false;
                    } else {
                        return true;
                    }

                }

                $("#login-form").dialog({
                    autoOpen: false,
                    height: 350,
                    width: 350,
                    modal: true,
                    buttons: {
                        'Thôi': function() {
                            $(this).dialog('close');
                        },
                        'Đăng nhập': function() {
                            requestLogin();
                        }
				
                    },
                    close: function() {
                        allFields.val('').removeClass('ui-state-error');
                    }
                });



                $('#linkLogin')
                .click(function() {
                    $('#login-form').dialog('open');
                });

                $('form#frmLogin').submit(function(){
                    requestLogin();
                    return false;
                });

            });
        </script>

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
                    out.print("<div style=\"float:right; margin-bottom:8px; font-size:11px\">");
                    if (member == null) {

                        out.print("<a name=\"top\" href=\"register.jsp\">Đăng kí</a>&nbsp;&nbsp;");
                        out.print("<a href=\"#\" id=\"linkLogin\">Đăng nhập</a>&nbsp;&nbsp;");
                    } else {
                        out.print("Xin chào <a href=\"member_info.jsp\">" + member.getFullName() + "</a>&nbsp;&nbsp;|&nbsp;&nbsp;");
                        out.print("<a href=\"SearchBookmarkController\">Hiển thị Bookmark</a>&nbsp;&nbsp;|&nbsp;&nbsp;");
                        out.print("<a href=\"MemberLogoutController\">Đăng xuất</a>&nbsp;&nbsp;");
                    }
                    out.print("</div>");

        %>

        <div id="result"/>

        <div id="login-form" title="Đăng nhập">
            <p class="validateTips">Vui lòng nhập thông tin đăng nhập.</p>
            <form id="frmLogin">
                <fieldset>
                    <label for="username">Tên đăng nhập</label><br/>
                    <input type="text" id="username" class="text ui-widget-content ui-corner-all" /><br/>
                    <label for="password">Password</label><br/>
                    <input type="password" name="password" id="password" value="" class="text ui-widget-content ui-corner-all" />
                </fieldset>
            </form>
        </div>
    </body>
</html>
