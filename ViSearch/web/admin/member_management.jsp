<%-- 
    Document   : admin
    Created on : Jul 6, 2010, 9:24:25 AM
    Author     : VinhPham
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.me.dto.MemberDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" errorPage="" %>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Trang quản lý thành viên</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf-8">
            $(document).ready(function() {
                $('#tbManagement').dataTable();
            } );
        </script>
        <script language="javascript" type="text/javascript">
            function CallUpdate(id) {
                $('#update-form').dialog('open');
            }
            function checkSex(id)
            {
                if ($("#hfSex"+id).val() == "0")
                    $("#idNu" + id).attr("checked","checked");
                else if ($("#hfSex" + id).val() == "1")
                    $("#idNam" + id).attr("checked","checked");
                else
                    $("#idKhongTietLo" + id).attr("checked","checked");
            }
            $(function() {
                $("#update-form").dialog({
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
            });
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
                                            <tr><td><img src="images/BannerAdmin.png" /></td></tr>
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
                            <%
                                        if (request.getAttribute("ListMember") != null) {
                                            ArrayList<MemberDTO> list = (ArrayList<MemberDTO>) request.getAttribute("ListMember");
                            %>
                            <h1>Quản lý thông tin thành viên</h1>
                            <table id="tbManagement">
                                <thead>
                                    <tr>
                                        <th>Chọn</th>
                                        <th>Họ tên</th>
                                        <th>Tên đăng nhập</th>
                                        <th>Ngày sinh</th>
                                        <th>Giới tính</th>
                                        <th>Quyền admin</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                                                                for (MemberDTO mem : list) {
                                    %>
                                    <tr>
                                        <td><input name="chbox[]" type="checkbox" value = "<%=mem.getId()%>"/></td>
                                        <td><input id="" type="text" onchange="" value = "<%=mem.getFullName()%>" disabled/></td>
                                        <td><input id="" type="text" onchange="" value = "<%=mem.getUserName()%>" disabled/></td>
                                        <td><input id="" type="text" onchange="" value = "<%=sdf.format(mem.getBirthDay().getTime())%>" disabled/></td>
                                        <td><input type="radio" name="radio<%=mem.getId()%>" id="idNam<%=mem.getId()%>" value="1" disabled/>
                                            <label for="idNam">Nam</label>
                                            <input type="radio" name="radio<%=mem.getId()%>" id="idNu<%=mem.getId()%>" value="0" disabled/>
                                            <label for="idNu">Nữ</label>
                                            <input type="radio" name="radio<%=mem.getId()%>" id="idKhongTietLo<%=mem.getId()%>" value="2" disabled/>
                                            <label for="idKhongTietLo">Không tiết lộ</label>
                                            <input type="hidden" id="hfSex<%=mem.getId()%>" value="<%=mem.getSex()%>" disabled/>
                                        </td>
                                        <script type="text/javascript">
                                            checkSex("<%=mem.getId()%>");
                                        </script>

                                        <%
                                                                                                                            if (mem.getRole() == 0) {%>
                                        <td><input id="cbAdmin<%=mem.getId()%>" type="checkbox" disabled/></td>
                                        <%
                                                                                                                                                                    } else {
                                        %>
                                        <td><input id="cbAdmin<%=mem.getId()%>" type="checkbox" checked disabled/></td>
                                        <%
                                                                                                                            }
                                        %>
                                        <td>
                                            <input id="btCN<%=mem.getId()%>" type="button" value="Cập nhật" onclick="CallUpdate(<%=mem.getId()%>);"/>
                                            <input id="btXoa<%=mem.getId()%>" type="button" value="Xoá"/>
                                        </td>
                                    </tr>
                                    <%
                                                                                }
                                    %>
                                </tbody>
                            </table>
                            <input id="btXoaChon" type="button" value="Xoá dòng được chọn"/>
                            <%
                                            if (request.getAttribute("Paging") != null) {
                                                String paging = request.getAttribute("Paging").toString();
                                                out.println(paging);
                                            }
                                        }
                            %>

                            <div id="update-form" title="Cập nhật thông tin">
                                <p class="validateTips">Vui lòng nhập thông tin đăng nhập.</p>
                                <form id="frmLogin">
                                    <fieldset>
                                        <label for="username">Tên đăng nhập</label><br/>
                                        <input type="text" id="username" class="text ui-widget-content ui-corner-all" /><br/>
                                        <label for="password">Mật khẩu</label><br/>
                                        <input type="password" name="password" id="password" value="" class="text ui-widget-content ui-corner-all" />
                                    </fieldset>
                                </form>
                            </div>
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


