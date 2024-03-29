<%-- 
    Document   : image
    Created on : Jun 5, 2010, 1:09:22 AM
    Author     : tuandom
--%>

<%@page import="java.io.File"%>
<%@page import="org.apache.tomcat.jni.FileInfo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.apache.solr.client.solrj.SolrQuery"%>
<%@page import="org.apache.solr.client.solrj.SolrServer"%>
<%@page import="org.apache.solr.client.solrj.impl.CommonsHttpSolrServer"%>
<%@page import="org.apache.solr.common.SolrDocument"%>
<%@page import="org.apache.solr.common.SolrDocumentList"%>
<%@page import="org.apache.solr.common.SolrInputDocument"%>
<%@page import="org.apache.solr.client.solrj.response.QueryResponse"%>
<%@page import="java.util.*, java.net.*,java.util.Map, org.apache.commons.httpclient.util.*"%>
<%@page import="org.apache.solr.client.solrj.response.FacetField"%>
<%@page import="org.me.dto.FacetDateDTO"%>
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ViSearch - Hình ảnh</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/clock.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#tbTopSearch").load("TopSearch?SearchType=4");
            });
        </script>
        <script language="javascript">
            $.ajax({
                type: "POST",
                url: "TopSearch",
                cache: false,
                data: "SearchType=4",
                success: function(html){
                    $("#tbTopSearch").append(html);
                }
            });
            function setText()
            {
                var keysearch = document.getElementById('txtSearch').value;
                if(keysearch=="")
                    document.getElementById('txtSearch').focus();
            }
            function CheckInput()
            {
                var keysearch = document.getElementById('txtSearch').value;
                var sortedtype = document.getElementById('slSortedType').value;
                if(keysearch == "")
                    return;
                else
                {
                    var url = "SearchImageController?type=0&sp=1&KeySearch=";
                    url += encodeURIComponent(keysearch);
                    url += "&SortedType=" + sortedtype;
                    window.location = url;
                }
            }
            function SeachPVDC(strQuery){
                var R = document.getElementById("divPVTC_R").value;
                var  C = document.getElementById("divPVTC_C").value;
                strQuery =  encodeURIComponent(strQuery);
                var url = "SearchImageController?type=3&KeySearch=" + strQuery + "&w="+R+"&h="+C;
                window.location = url;
            }
            function showPVTC(){
                document.getElementById("divPVTC").className="display";
            }
            function Sort(type){
                var sortedtype = document.getElementById('slSortedType').value;
                var keysearch = document.getElementById('hfKeySearch').value;
                if(keysearch == "")
                    return;
                else
                {
                    var url = "SearchImageController?sp=1&KeySearch=";
                    url += encodeURIComponent(keysearch);
                    url += "&SortedType=" + sortedtype;

                    if(document.getElementById('hdqf')!=null)
                    {
                        url+="&qf="+document.getElementById('hdqf').value;
                        type = 2; //facet
                    }
                    if(document.getElementById('hdqv')!=null)
                        url+="&qv="+encodeURIComponent(document.getElementById('hdqv').value);
                    //if(document.getElementById('hdsorttype')!=null)
                    //    url+="&SortedType="+document.getElementById('hdsorttype').value;

                    url += "&type=" + type;
                    window.location = url;
                }
            }
        </script>
    </head>

    <body onload="setText();">

        <%
                    session.setAttribute("CurrentPage", request.getRequestURI().replaceFirst("/ViSearch", ""));
                    // get String query
                    String strQuery = "";
                    if (request.getAttribute("KeySearch") != null) {
                        strQuery = (String) request.getAttribute("KeySearch");
                        //strQuery = URLDecoder.decode(strQuery, "UTF-8");
                        strQuery = strQuery.replaceAll("\"", "&quot;");
                    }
                    int sortedType = 0;
                    if (request.getAttribute("SortedType") != null) {
                        sortedType = Integer.parseInt(request.getAttribute("SortedType").toString());
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
                    String search_stats = "";
                    String QTime;
                    if (request.getAttribute("QTime") != null) {
                        QTime = request.getAttribute("QTime").toString();

                        if (request.getAttribute("Docs") != null) {
                            listdocs = (SolrDocumentList) request.getAttribute("Docs");

                            search_stats = String.format("Có %d kết quả (%s giây)", listdocs.getNumFound(), QTime);
                            if (request.getAttribute("Collation") != null) {
                                String sCollation = (String) request.getAttribute("Collation");
                                result += "<p><font color=\"#CC3333\" size=\"+2\">Có phải bạn muốn tìm: <b><a href=\"SearchImageController?type=0&KeySearch=" + sCollation + "\">" + sCollation + "</a></b></font></p>";
                            }

                            for (int i = 0; i < listdocs.size(); i++) {
                                result += "<div class=\"cellImage\">  <table style=\"font-size:13px; vertical-align:bottom\">";

                                // Lay noi dung cua moi field
                                String title = (listdocs.get(i).getFirstValue("site_title")).toString();
                                String id = (listdocs.get(i).getFieldValue("id")).toString();
                                String url = "";
                                //  if (!listdocs.get(i).getFieldValue("url_local").equals("") && listdocs.get(i).getFieldValue("url_local") != null) {
                                //     File file = new File("webapps\\ViSearch\\" + listdocs.get(i).getFieldValue("url_local").toString());
                                //     if (file.exists()) {
                                //        url = (listdocs.get(i).getFieldValue("url_local")).toString();
                                //        url = url.replace('\\', '/');
                                //out.print(url);
                                //    }
                                //  } else {
                                url = (listdocs.get(i).getFieldValue("url")).toString();
                                //   }
                                String width = "";
                                if ((listdocs.get(i).getFieldValue("width")) != null) {
                                    width = listdocs.get(i).getFieldValue("width").toString();
                                }
                                String height = "";
                                if ((listdocs.get(i).getFieldValue("height")) != null) {
                                    height = listdocs.get(i).getFieldValue("height").toString();
                                }
                                String size = "";
                                 if ((listdocs.get(i).getFieldValue("size")) != null) {
                                     size = listdocs.get(i).getFieldValue("size").toString();
                                 }
                                String fileType = "";
                                if ((listdocs.get(i).getFieldValue("fileType")) != null) {
                                    fileType = listdocs.get(i).getFieldValue("fileType").toString();
                                }
                                String title_hl = title;

                                if (request.getAttribute("HighLight") != null) {
                                    highLight = (Map<String, Map<String, List<String>>>) request.getAttribute("HighLight");
                                    List<String> highlightTitle = highLight.get(id).get("site_title");
                                    if (highlightTitle != null && !highlightTitle.isEmpty()) {
                                        title_hl = highlightTitle.get(0);
                                    }
                                }
                                if (title.length() > 30) {
                                    title_hl = title.substring(0, 20) + "...";
                                }



                                result += "<tr>";
                                result += "<td width=\"150\" height=\"200\" valign=\"bottom\"><a href=\"DetailImageController?id=" + id + "&KeySearch=" + strQuery + "\"><img src=\"" + url + "\" width=\"150\" align=\"left\" /></a></td>";
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td>" + width + " x " + height + " - " + size + "Kb - " + fileType + "</td>";
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td>";
                                result += "<a href=\"SearchImageController?type=1&KeySearch=" + title.replaceAll("\\<.*?\\>", "") + "\">Trang tương tự...</a>";
                                result += "</td>";
                                result += "</tr>";
                                result += "<tr><td>&nbsp;</td></tr>";
                                result += "</table></div>";
                            }
                            // Phan trang
                            numrow = Integer.parseInt(request.getAttribute("NumRow").toString());
                            numpage = Integer.parseInt(request.getAttribute("NumPage").toString());
                            strpaging = (String) request.getAttribute("Pagging");
                        }
                        //result += "Số kết quả tìm được là: " + numrow + "<br/>";
                        result += "<div style=\"float:left; clear:both\"> Tổng số trang là: " + numpage + "<br/>";
                        if (numpage > 1) {
                            result += strpaging + "<br/><br/></div>";
                        }
                    }
                    //get SolrDocumentList
        %>
        <%
                    // Get Facet
                    String facet = "";

                    List<FacetField> listFacet = (List<FacetField>) request.getAttribute("ListFacet");
                    if (listFacet != null) {
                        facet += "<div class=\"mnu\">Bộ lọc</div>";
                        for (int i = 0; i < listFacet.size(); i++) {
                            facet += "<table id=\"table_left\" width=\"100%\" border=\"0\">";
                            facet += "<tr>";
                            facet += "<td>";
                            String fieldName = listFacet.get(i).getName();
                            if (fieldName.equals("category")) {
                                facet += "<b>Chuyên mục</b>";
                            }
                            facet += "<br>";
                            List<FacetField.Count> listCount = listFacet.get(i).getValues();
                            if (listCount != null) {
                                for (int j = 0; j < listCount.size(); j++) {
                                    String fieldText = listCount.get(j).getName();
                                    facet += "<a href = 'SearchImageController?type=2&KeySearch=" + strQuery + "&qf=" + fieldName + "&qv=" + URIUtil.encodePath(fieldText) + "&SortedType=" + sortedType + "'>" + fieldText + "</a>";
                                    facet += " (" + listCount.get(j).getCount() + ")";
                                    facet += "<br>";
                                }
                            } else {
                                facet += "Không tìm ra dữ liệu<br>";
                            }
                            facet += "</td></tr>";
                            facet += "</table>";
                        }
                    }


                    // End get Facet
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
                                        <%@include file="template/banner_Image.jsp"%>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    <tr>
                        <td style="font-size:12px;" width="30%" align="middle">
                            <script type="" language="javascript">goforit();</script>
                            <span id="clock"/></td>
                        <td width="70%" align="middle"><%@include file="template/sortedtype1.jsp"%></td>
                    </tr>
                    </tr>
                    <tr><td height="20" colspan="2" align="center" valign="bottom"><div align="center" class="nav"></div></td></tr>
                    <tr>
                        <td width="200" height="33" valign="top">
                            <div class="subtable">

                                <% if (request.getAttribute("Docs") != null) {
                                                out.print(facet);
                                                out.print("<div class=\"mnu\">Kích thước</div>");

                                                out.print("<table id=\"table_left\" width=\"100%\" border=\"0\">");
                                                out.print("<tr><td>");
                                                out.print("<a href = 'SearchImageController?type=2&KeySearch=" + strQuery + "&qf=width&qv=" + URIUtil.encodePath("[1001 TO *]") + "'>" + "Lớn" + "</a>");
                                                out.print("</td></tr>");

                                                out.print("<table id=\"table_left\" width=\"100%\" border=\"0\">");
                                                out.print("<tr><td>");
                                                out.print("<a href = 'SearchImageController?type=2&KeySearch=" + strQuery + "&qf=width&qv=" + URIUtil.encodePath("[501 TO 1000]") + "'>" + "Trung bình" + "</a>");
                                                out.print("</td></tr>");

                                                out.print("<table id=\"table_left\" width=\"100%\" border=\"0\">");
                                                out.print("<tr><td>");
                                                out.print("<a href = 'SearchImageController?type=2&KeySearch=" + strQuery + "&qf=width&qv=" + URIUtil.encodePath("[* TO 500]") + "'>" + "Nhỏ" + "</a>");
                                                out.print("</td></tr>");

                                                out.print("<tr><td><a style=\"cursor:pointer\" onclick=\"showPVTC();\" />Phạm vi tùy chỉnh</a></td></tr>");
                                                out.print("<tr><td>");
                                                out.print("<div id=\"divPVTC\" class=\"hidden\">");
                                                out.print("<div style=\"float:left\"> Khoảng chiều rộng: </div><div style=\"float:right\"><input type=\"text\" size=12 class=\"textForm\" onfocus=\"this.className='textForm_Hover';\" onblur=\"this.className='textForm';\" id=\"divPVTC_R\" /></div>");
                                                out.print("<div style=\"float:left\"> Khoảng chiều cao: </div><div style=\"float:right\"><input type=\"text\" size=12 class=\"textForm\" onfocus=\"this.className='textForm_Hover';\" onblur=\"this.className='textForm';\" id=\"divPVTC_C\" /></div>");
                                                out.print("<div style=\"float:left\"><i>(ví dụ: 50-100)</i></div><div style=\"float:right\">&nbsp;&nbsp;<input type=\"button\" name=\"btSearch\" value=\"Tìm kiếm\" onclick=\"SeachPVDC('" + strQuery + "');\" /></div>");
                                                out.print("</div>");

                                                out.print("</td></tr>");

                                                out.print("</table>");
                                            }%>
                                <div class="mnu">Tìm kiếm nhiều</div>
                                <table id="tbTopSearch">

                                </table>
                            </div>
                        </td>
                        <td width="627" rowspan="2" valign="top">

                            <table>

                                <tr><td id="result_search"><% out.print(search_stats);%></td></tr><tr></tr>
                                <%  if (request.getParameter("qf") != null) {
                                                out.print("<tr><td id=\"top-header\">");
                                                if (request.getParameter("qf").toString().equals("category")) {
                                                    out.print(">> Chuyên mục: " + request.getParameter("qv"));
                                                } else if (request.getParameter("qf").toString().equals("width")) {
                                                    if (request.getParameter("qv").equals("[1001 TO *]")) {
                                                        out.print(">> Kích thước: Lớn");
                                                    }
                                                    else if (request.getParameter("qv").equals("[* TO 500]")) {
                                                        out.print(">> Kích thước: Nhỏ");
                                                    }
                                                    else out.print(">> Kích thước: Trung bình");
                                                } else {
                                                    out.print(">> " + request.getParameter("qf") + ": " + request.getParameter("qv"));
                                                }
                                                out.print("</td></tr>");
                                                out.print("<input type='hidden' id='hdqf' value='" + request.getParameter("qf") + "'>");
                                                out.print("<input type='hidden' id='hdqv' value='" + request.getParameter("qv") + "'>");
                                                out.print("<input type='hidden' id='hdsorttype' value='" + request.getAttribute("SortedType") + "'>");
                                            }
                                %>
                            </table>
                            <table id="table_right" width="100%" cellpadding="0" cellspacing="0">


                                <tr>
                                    <td  valign="top" id="content">
                                        <% out.print(result);%>

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
                            <%@include file="template/footer.jsp" %>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </body>
</html>




