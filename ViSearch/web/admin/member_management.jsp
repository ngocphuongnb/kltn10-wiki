<%-- 
    Document   : admin
    Created on : Jul 6, 2010, 9:24:25 AM
    Author     : VinhPham
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.me.dto.MemberDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
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

        <style type="text/css" title="currentStyle">
            @import "css/demo_page.css";
            @import "css/demo_table.css";
        </style>

        <script type="text/javascript" charset="utf-8">
            $(document).ready(function() {
                $('#tbManagement').dataTable();
            } );
        </script>

        <script language="javascript" type="text/javascript">
            function CheckAdmin(id)
            {
                var bCheck = "#cbAdmin" + id;
                var sCheck = "#chk" + id;
                var btDel = "#btDel" + id;
                var val;
                if($(bCheck).attr("checked") == 1)
                {
                    val = 1;
                }
                else
                    val = 0;
                $.ajax({
                    type: 'POST',
                    url: "/ViSearch/UpdateRoleController",
                    data: 'id='+ id + '&vl=' + val,
                    success: function(html){
                        if(html=="1")
                        {
                            alert("Đã cập nhật");
                            if(val==1)
                            {
                                $(sCheck).attr("disabled", "disabled");
                                $(btDel).attr("disabled", "disabled");
                            }
                            else
                            {
                                $(sCheck).attr("disabled", "");
                                $(btDel).attr("disabled", "");
                            }
                        }
                    else
                        alert("Lỗi");
                }
            });
    }

function DeleteMember(id)
{
    var kq = confirm("Bạn thật sự muốn xóa");
    if(kq)
    {
        var url="DeleteMemberController?";
        //var value  = "&value=" + arrcheck.join('-');
        url += "arrID=" + id;
        url += "&t=" + Math.random();
        window.location = url;
    }
}

function DeleteSelect()
{
    var arrcheck = new Array;
    var field = document.getElementsByName('chbox[]');
    var idem = 0;
    for (i = 0; i < field.length; i++)
        if( field[i].checked == true){
            arrcheck[idem] = field[i].value;
            idem ++;
        }
    if(idem > 0)
    {
        var kq = confirm("Bạn thật sự muốn xóa");
        if(kq)
        {
            var url="DeleteMemberController?";
            var arrID  = "arrID=" + arrcheck.join('-');
            url += arrID;
            url += "&t=" + Math.random();
            window.location = url;
        }
    }
    else
    {
        alert("Không có dòng nào được chọn");
    }
}
        </script>

        <style type="text/css">
            button.delele{
                background-image:url(images/delete.png);
                background-repeat: no-repeat;
                background-position: left;
                padding-left:25px;
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
                        <td colspan="2" height="33" valign="top" style="font-size: small">
                            <%
                                        int admin = -1;
                                        if (session.getAttribute("admin") != null) {
                                            admin = Integer.parseInt(session.getAttribute("admin").toString());
                                        }

                                        if (request.getAttribute("ListMember") != null) {
                                            ArrayList<MemberDTO> list = (ArrayList<MemberDTO>) request.getAttribute("ListMember");
                            %>
                            <h1>Quản lý thành viên</h1>
                            <table id="tbManagement" cellpadding="0" cellspacing="0" border="0" class="display">
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
                                        <td><input id="chk<%=mem.getId()%>" name="chbox[]" type="checkbox" value = "<%=mem.getId()%>" <%if (mem.getRole() == 1) {
                                                                                                                                out.print("disabled");
                                                                                                                            }%>/></td>
                                        <td><%=mem.getFullName()%></td>
                                        <td><%=mem.getUserName()%></td>
                                        <td><%=sdf.format(mem.getBirthDay().getTime())%></td>
                                        <td>
                                            <%
                                                                                                                                if (mem.getSex() == 0) {
                                                                                                                                    out.print("Nữ");
                                                                                                                                } else {
                                                                                                                                    if (mem.getSex() == 1) {
                                                                                                                                        out.print("Nam");
                                                                                                                                    } else {
                                                                                                                                        out.print("Không tiết lộ");
                                                                                                                                    }
                                                                                                                                }


                                            %>
                                        </td>

                                        <%
                                                                                                                            if (mem.getRole() == 0) {%>
                                        <td><input id="cbAdmin<%=mem.getId()%>" type="checkbox" onclick="CheckAdmin(<%=mem.getId()%>)"/></td>
                                        <%
                                                                                                                                                                    } else {
                                        %>
                                        <td><input id="cbAdmin<%=mem.getId()%>" type="checkbox" checked onclick="CheckAdmin(<%=mem.getId()%>)" <%if (mem.getId() == admin) {
                                                                                                                                                                            out.print("disabled");
                                                                                                                                                                        }%>/></td>
                                            <%
                                                                                                                                }
                                            %>
                                        <td>
                                            <%
                                                                                                                                if (mem.getRole() == 1) {
                                            %>
                                            <button id="btDel<%=mem.getId()%>" class="delele" onclick="DeleteMember(<%out.print(mem.getId());%>);" disabled>
                                                Xóa
                                            </button>
                                            <%} else {%>
                                            <button id="btDel<%=mem.getId()%>" class="delele" onclick="DeleteMember(<%out.print(mem.getId());%>);">
                                                Xóa
                                            </button>
                                            <%}%>
                                        </td>
                                    </tr>
                                    <%
                                                                                }
                                    %>
                                </tbody>
                            </table>
                            <input id="btXoaChon" type="button" value="Xoá dòng được chọn" onclick="DeleteSelect();"/>
                            <%
                                        }
                            %>
                            <p>
                                <a href="admin/admin.jsp">Trang chủ admin</a>
                            </p>
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


