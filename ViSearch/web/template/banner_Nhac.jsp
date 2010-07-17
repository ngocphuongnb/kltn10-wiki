<%-- 
    Document   : banner_Nhac
    Created on : May 30, 2010, 6:32:01 PM
    Author     : tuandom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">



<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>footer</title>
        <script type="text/javascript">
            function MM_preloadImages() { //v3.0
                var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
                        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
                }

                function MM_swapImgRestore() { //v3.0
                    var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
                }

                function MM_findObj(n, d) { //v4.01
                    var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
                        d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                    if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
                    for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                    if(!x && d.getElementById) x=d.getElementById(n); return x;
                }

                function MM_swapImage() { //v3.0
                    var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                        if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
                }
        </script>
    </head>
    <body onLoad="MM_preloadImages('../images/button-over.gif')">
        <form action="javascript:CheckInput()" method="GET">
            <table id="Table_01" width="975" height="130" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td rowspan="7">
                        <img src="images/banner_01_nhac.gif" width="231" height="130" alt=""></td>
                    <td>
                        <img src="images/banner_02.gif" width="532" height="44" alt=""></td>
                    <td rowspan="7">
                        <img src="images/banner_03.gif" width="26" height="130" alt=""></td>
                    <td rowspan="3">
                        <img src="images/banner_04.gif" width="110" height="66" alt=""></td>
                    <td rowspan="7">
                        <img src="images/banner_05.gif" width="75" height="130" alt=""></td>
                    <td>
                        <img src="images/spacer.gif" width="1" height="44" alt=""></td>
                </tr>
                <tr>
                    <td align="center">
                        <div id="menu" style="border:0; margin:0; padding:0" width="532" height="20">
                            <%@ include file="menu.jsp"%>
                        </div>

                    </td>
                    <td>
                        <img src="images/spacer.gif" width="1" height="20" alt=""></td>
                </tr>
                <tr>
                    <td rowspan="2">
                        <img src="images/banner_07.gif" width="532" height="7" alt=""></td>
                    <td>
                        <img src="images/spacer.gif" width="1" height="2" alt=""></td>
                </tr>
                <tr>
                    <td rowspan="3">
                        <a  id="btSearch" style="cursor:pointer" onclick="javascript:CheckInput();"><img src="images/banner_08.gif" alt="" width="110" height="42" id="Image1" onMouseOver="MM_swapImage('Image1','','images/button-over.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
                    <td>
                        <img src="images/spacer.gif" width="1" height="5" alt=""></td>
                </tr>
                <tr>

                    <td>
                        <div style="margin:0; border:0; padding:0; float:left"><img src="images/magnifying_glass.gif" width="26" /></div>
                        <div id="" style="float:left">
                            <input id="txtSearch" style="width:384px" type="text" value="<% if (strQuery != null && FieldId.equals("8")==false) {
                                            out.print(strQuery);
                                        }%>"/>
                           <select id="field" style="width:96px">
                               <% if(FieldId.equals("1")==true)
                                    out.print("<option value=\"1\" selected=\"selected\">Tên bài hát</option>");
                                    else out.print("<option value=\"1\">Tên bài hát</option>");

                            if(FieldId.equals("2")==true)
                                    out.print("<option value=\"2\" selected=\"selected\">Album</option>");
                                    else out.print("<option value=\"2\">Album</option>");

                            if(FieldId.equals("3")==true)
                                    out.print("<option value=\"3\" selected=\"selected\">Ca sĩ</option>");
                                    else out.print("<option value=\"3\">Ca sĩ</option>");

                            if(FieldId.equals("4")==true)
                                    out.print("<option value=\"4\" selected=\"selected\">Tác giả</option>");
                                    else out.print("<option value=\"4\">Tác giả</option>");

                            if(FieldId.equals("5")==true)
                                    out.print("<option value=\"5\" selected=\"selected\">Lời nhạc</option>");
                                    else out.print("<option value=\"5\">Lời nhạc</option>");
                            if(FieldId.equals("6")==true)
                                    out.print("<option value=\"6\" selected=\"selected\">Tất cả</option>");
                                    else out.print("<option value=\"6\">Tất cả</option>");
                                   %>
      
        </select>
                            <input id="hfKeySearch" type="hidden" value="<% if (strQuery != null) {
                                            out.print(strQuery);
                                        }%>"/>
                        </div>
                    </td>
                    <td>
                        <img src="images/spacer.gif" width="1" height="33" alt=""></td>
                </tr>
                <tr>
                    <td rowspan="2">
                        <img src="images/banner_10.gif" width="532" height="26" alt=""></td>
                    <td>
                        <img src="images/spacer.gif" width="1" height="4" alt=""></td>
                </tr>
                <tr>
                    <td style="background:url(images/banner_11.gif);">
                        <font size="-2"><a href="advance_search.jsp">Tìm kiếm nâng cao</a></font></td>
                    <td>
                        <img src="images/spacer.gif" width="1" height="22" alt=""></td>
                </tr>
            </table>
        </form>
    </body>
</html>
