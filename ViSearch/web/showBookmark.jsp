<%-- 
    Document   : showBookmark
    Created on : Jun 13, 2010, 6:16:24 PM
    Author     : tuandom
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@page import="org.apache.solr.client.solrj.SolrQuery"%>
<%@page import="org.apache.solr.client.solrj.SolrServer"%>
<%@page import="org.apache.solr.client.solrj.impl.CommonsHttpSolrServer"%>
<%@page import="org.apache.solr.common.SolrDocument"%>
<%@page import="org.apache.solr.common.SolrDocumentList"%>
<%@page import="org.apache.solr.common.SolrInputDocument"%>
<%@page import="org.apache.solr.client.solrj.response.QueryResponse"%>
<%@page import="java.util.*, java.net.*,java.util.Map, org.apache.commons.httpclient.util.*, java.text.SimpleDateFormat"%>

<%
            Object obj = request.getAttribute("lstBm");
            if (obj == null) {
                String url = "/GetBookmarkController?";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <title>Hiển thị Bookmark - ViSearch</title>
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <link href="style.css"rel="stylesheet" type="text/css" />

        <script type="text/javascript">
            function DeleteBM(id){
                var url = "DeleteBookmark?id="+id;
                window.location = url;
            }
            function onmouseover(nama){
                nama.className='tableBM_on';
                return true;
            }
            function CheckInput()
            {
                var keysearch = document.getElementById('txtSearchBM').value;
                if(keysearch == "")
                    return;
                else
                {
                    var url = "SearchBookmarkController?type=0&sp=1&KeySearch=";
                    url += encodeURIComponent(keysearch);
                    window.location = url;
                }
            }
        </script>

    </head>
    <body>
    <%
                List<Object[]> lstBm = null;
                if (request.getAttribute("lstBm") != null) {
                    lstBm = (List<Object[]>) request.getAttribute("lstBm");
                }
    %>

         <%
                    String currentPage = "/showBookmark.jsp";
                    if (request.getQueryString() != null) {
                        currentPage = "/SearchBookmarkController?";
                        currentPage += request.getQueryString().toString();
                    }
                    session.setAttribute("CurrentPage", currentPage);
                    // get String query
                    String strQuery = "";
                    if (request.getAttribute("KeySearch") != null) {
                        strQuery = (String) request.getAttribute("KeySearch");
                        strQuery = strQuery.replaceAll("\"", "&quot;");
                    }
                    // end get String query
                    
        %>
 <%
                    //get SolrDocumentList
                    SolrDocumentList listdocs = new SolrDocumentList();
                    Map<String, Map<String, List<String>>> highLight = null;
                    int numrow = 0;
                    int numpage = 0;
                    String strpaging = "";
                    String result = "";
  
                    
                        if (request.getAttribute("Docs") != null) {
                            listdocs = (SolrDocumentList) request.getAttribute("Docs");
                            
                            if (request.getAttribute("Collation") != null) {
                                String sCollation = (String) request.getAttribute("Collation");
                                result += "<p><font color=\"#CC3333\" size=\"+2\">Có phải bạn muốn tìm: <b><a href=\"SearchRaoVatController?type=0&KeySearch=" + sCollation + "\">" + sCollation + "</a></b></font></p>";
                            }

                            for (int i = 0; i < listdocs.size(); i++) {
                                result += "<table style=\"font-size:13px\">";

                                // Lay noi dung cua moi field
                                String id = (listdocs.get(i).getFirstValue("id")).toString();
                                String memberId = (listdocs.get(i).getFirstValue("member_ID")).toString();
                                String docid = (listdocs.get(i).getFieldValue("docid")).toString();
                                String searchType = (listdocs.get(i).getFieldValue("searchtype")).toString();
                                String bookmarkname = (listdocs.get(i).getFieldValue("bookmarkname")).toString();
                                Date date_created = (Date) (listdocs.get(i).getFieldValue("date_created"));
                                String url="";
                                
                                if (request.getAttribute("HighLight") != null) {
                                    highLight = (Map<String, Map<String, List<String>>>) request.getAttribute("HighLight");
                                    List<String> HLbookmarkname = highLight.get(id).get("bookmarkname");
                                    if (HLbookmarkname != null && !HLbookmarkname.isEmpty()) {
                                        bookmarkname = HLbookmarkname.get(0) + "...";
                                    } 
                                } 

                                result += "<tr>";
                                result += "<td>" + bookmarkname + "</td>";
                                result += "</tr>";

                                result += "<tr>";
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                                result += "<td><b>Ngày tạo:</b> " + sdf.format(date_created) + "</td>";
                                result += "</tr>";

                                result += "<tr><td>&nbsp;</td></tr>";
                                result += "</table>";
                            }

                            // Phan trang
                            numrow = Integer.parseInt(request.getAttribute("NumRow").toString());
                            if (request.getAttribute("NumPage") != null) {
                                numpage = Integer.parseInt(request.getAttribute("NumPage").toString());
                            }
                            strpaging = (String) request.getAttribute("Pagging");
                        }
                        //result += "Số kết quả tìm được là: " + numrow + "<br/>";
                        if (numpage > 1) {
                            result += "Tổng số trang là: " + numpage + "<br/>";
                        }
                        result += "<p><font color=\"#CC3333\" size=\"+1\">" + strpaging + "</font></p><br/><br/>";
                    //get SolrDocumentList
        %>
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
                                    </td></tr>
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
                            <form class="frmRegister" name="" method="post" action="RegisterMemberController">
                                <h3 class="subblockhead"> Thông tin Bookmark của bạn</h3>
                                <table width="500" border="0" cellspacing="0" cellpadding="0" >

                                    <tr>
                                        <td>
                                            <input type="text" id="txtSearchBM" size="30px"/>
                                            <input type="button" value="Tìm kiếm" name="btSearchBM" onclick="CheckInput();"/>
                                        </td>
                                    </tr>

                                    <% out.print(result);%>

                                    <%
                                                if (lstBm != null) {
                                                    for (int i = 0; i < lstBm.size(); i++) {
                                                        Object[] objBm = new Object[2];
                                                        objBm = lstBm.get(i);
                                                        String id = objBm[0].toString();
                                                        String link = objBm[1].toString();
                                                        String BmName = objBm[2].toString();

                                                        out.print("<tr class=\"trBM\" onmouseover=\"onmouseover(this)\"  onmouseout=\"onmouseout(this)\"  \">");
                                                        out.print("<td align=\"left\" ><a href=\"" + link + "\">" + BmName + "</a></td>");
                                                        out.print("<td align=\"right\" ><input type=\"button\" value=\"Xóa\" onclick=\"DeleteBM('" + id + "')\"/></td>");
                                                        out.print("</tr>");
                                                    }
                                                }
                                    %>


                                    <tr><td height="15"></td></tr>
                                    <tr><td align="left" ><a href="index.jsp">Về trang chủ</a></td></tr>
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


