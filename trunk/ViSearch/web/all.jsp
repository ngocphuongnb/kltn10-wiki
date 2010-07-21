<%--
    Document   : news
    Created on : Jun 12, 2010, 11:37:53 AM
    Author     : tuandom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.apache.solr.client.solrj.SolrQuery"%>
<%@page import="org.apache.solr.client.solrj.SolrServer"%>
<%@page import="org.apache.solr.client.solrj.impl.CommonsHttpSolrServer"%>
<%@page import="org.apache.solr.common.SolrDocument"%>
<%@page import="org.apache.solr.common.SolrDocumentList"%>
<%@page import="org.apache.solr.common.SolrInputDocument"%>
<%@page import="org.apache.solr.client.solrj.response.QueryResponse"%>
<%@page import="java.util.*, java.net.*,java.util.Map, org.apache.commons.httpclient.util.*, java.text.SimpleDateFormat"%>
<%@page import="org.apache.solr.client.solrj.response.FacetField"%>
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ViSearch - Tổng hợp</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/clock.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#tbTopSearch").load("TopSearch?SearchType=7");
            });

            function Tracking(docid){
                var keySearch = document.getElementById('hfKeySearch').value;
                var Url = "TrackingController?KeySearch=" + encodeURIComponent(keySearch);
                Url += "&DocID=" + docid;
                Url += "&searchType=7";
                //alert(Url);
                window.location = Url;
            }
        </script>
        <script language="javascript">
            $.ajax({
                type: "POST",
                url: "TopSearch",
                cache: false,
                data: "SearchType=7",
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
                    var url = "SearchAllController?type=0&sp=1&KeySearch=";
                    url += encodeURIComponent(keysearch);
                    url += "&SortedType=" + sortedtype;
                    window.location = url;
                }
            }
        </script>
        <script language="javascript">
            function Sort(type){
                var sortedtype = document.getElementById('slSortedType').value;
                //alert(sortedtype);
                var keysearch = document.getElementById('hfKeySearch').value;

                if(keysearch == "")
                    return;
                else
                {
                    var url = "SearchAllController?sp=1&KeySearch=";
                    url += encodeURIComponent(keysearch);

                    url += "&SortedType=" + sortedtype;
                    if(document.getElementById('hdqf')!=null)
                    {
                        url+="&qf="+document.getElementById('hdqf').value;
                        type = 2;
                    }
                    if(document.getElementById('hdqv')!=null)
                        url+="&qv="+encodeURIComponent(document.getElementById('hdqv').value);
                    if(document.getElementById('hdsorttype')!=null)
                    {
                        //url+="&SortedType="+document.getElementById('hdsorttype').value;
                    }
                    url += "&type=" + type;
                    window.location = url;
                }
            }
        </script>
    </head>

    <body onload="setText();">

        <%
                    String currentPage = "/all.jsp";
                    if (request.getQueryString() != null) {
                        currentPage = "/SearchAllController?";
                        currentPage += request.getQueryString().toString();
                    }
                    session.setAttribute("CurrentPage", currentPage);
                    // get String query
                    String strQuery = "";
                    if (request.getAttribute("KeySearch") != null) {
                        strQuery = (String) request.getAttribute("KeySearch");
                        //strQuery = URLDecoder.decode(strQuery, "UTF-8");
                        strQuery = strQuery.replaceAll("\"", "&quot;");
                    }
                    // end get String query
                    int sortedType = 0;
                    if (request.getAttribute("SortedType") != null) {
                        sortedType = Integer.parseInt(request.getAttribute("SortedType").toString());
                    }
        %>
        <%
                    //get SolrDocumentList
                    SolrDocumentList listdocs = new SolrDocumentList();
                    Map<String, Map<String, List<String>>> highLight = null;
                    int numrow = 0;
                    int numpage = 0;
                    String strpaging = "";
                    StringBuffer result = new StringBuffer();
                    String search_stats = "";
                    String QTime;
                    if (request.getAttribute("QTime") != null) {
                        QTime = request.getAttribute("QTime").toString();

                        if (request.getAttribute("Docs") != null) {
                            listdocs = (SolrDocumentList) request.getAttribute("Docs");

                            search_stats = String.format("Có %d kết quả (%s giây)", listdocs.getNumFound(), QTime);
                            if (request.getAttribute("Collation") != null) {
                                String sCollation = (String) request.getAttribute("Collation");
                                result.append("<p><font color=\"#CC3333\" size=\"+2\">Có phải bạn muốn tìm: <b><a href=\"SearchAllController?type=0&KeySearch=" + sCollation + "\">" + sCollation + "</a></b></font></p>");
                            }

                            result.append("<table style=\"font-size:13px\">");
                            for (int i = 0; i < listdocs.size(); i++) {

                                // Lay noi dung cua moi field
                                String title = "";
                                if (listdocs.get(i).getFirstValue("title") != null) {
                                    title = listdocs.get(i).getFirstValue("title").toString();
                                }
                                String body = "";
                                if (listdocs.get(i).getFirstValue("body") != null) {
                                    body = (listdocs.get(i).getFirstValue("body")).toString();
                                }
                                String url = "";
                                if (listdocs.get(i).getFieldValues("url") != null) {
                                    url = (listdocs.get(i).getFieldValues("url")).toString();
                                }
                                String id = "";
                                if (listdocs.get(i).getFieldValue("id") != null) {
                                    id = (listdocs.get(i).getFieldValue("id")).toString();
                                }

                                String title_hl = title;

                                if (request.getAttribute("HighLight") != null) {
                                    List<String> highlightBody = null;
                                    List<String> highlightTitle = null;
                                    highLight = (Map<String, Map<String, List<String>>>) request.getAttribute("HighLight");
                                    if (highLight.get(id).get("body") != null) {
                                        highlightBody = highLight.get(id).get("body");
                                    }
                                    if (highLight.get(id).get("title") != null) {
                                        highlightTitle = highLight.get(id).get("title");
                                    }
                                    if (highlightBody != null && !highlightBody.isEmpty()) {
                                        body = highlightBody.get(0) + "...";
                                    } else {
                                        if (body.length() > 100) {
                                            body = body.substring(0, 100) + "...";
                                        }
                                    }
                                    if (highlightTitle != null && !highlightTitle.isEmpty()) {
                                        title_hl = highlightTitle.get(0);
                                    }
                                } else {
                                    if (title.length() > 100) {
                                        title_hl = title.substring(0, 100) + "...";
                                    }
                                    if (body.length() > 100) {
                                        body = body.substring(0, 100) + "...";
                                    }
                                }
                                String linkDetail = url;
                                if (id.contains("wiki")) {
                                    linkDetail = "http://vi.wikipedia.org/wiki/" + title.replace(' ', '_');
                                } else {
                                    linkDetail = linkDetail.substring(1, linkDetail.length() - 1);
                                }
                                result.append("<tr>");
                                result.append("<td><b><a href=\"" + linkDetail + "\" onclick=\"Tracking('" + id + "');\">" + title_hl + "</a><b></td>");
                                result.append("</tr>");

                                result.append("<tr>");
                                result.append("<td>" + body + "</td>");
                                result.append("</tr>");

                                result.append("<tr>");
                                result.append("<td><font style=\"font-size:11px; color:#00CC00\"><b>Link bài viết: </b>" + linkDetail + "</font></td>");
                                result.append("</tr>");

                                result.append("<tr><td>");
                                result.append("<a href=\"SearchAllController?type=1&KeySearch=" + title.replaceAll("\\<.*?\\>", "") + "\">Trang tương tự...</a>");
                                result.append("</td></tr>");
                                result.append("<tr><td>&nbsp;</td></tr>");

                            }
                            result.append("</table>");

                            // Phan trang
                            numrow = Integer.parseInt(request.getAttribute("NumRow").toString());
                            if (request.getAttribute("NumPage") != null) {
                                numpage = Integer.parseInt(request.getAttribute("NumPage").toString());
                            }
                            strpaging = (String) request.getAttribute("Pagging");
                        }
                        //result += "Số kết quả tìm được là: " + numrow + "<br/>";
                        if (numpage > 1) {
                            result.append("Tổng số trang là: " + numpage + "<br/>");
                        }
                        result.append("<p><font color=\"#CC3333\" size=\"+1\">" + strpaging + "</font></p>");
                    }
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
                                        <%@include file="template/banner_TongHop.jsp"%>
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
                    <script type="text/javascript">

                    </script>
                    <tr><td height="20" colspan="2" align="center" valign="bottom"><div align="center" class="nav"></div></td></tr>
                    <tr>
                        <td width="200" height="33" valign="top">
                            <div class="subtable">


                                <div class="mnu">Tìm kiếm nhiều</div>
                                <table  id="tbTopSearch">
                                </table>
                            </div>
                        </td>
                        <td width="627" rowspan="2" valign="top">
                            <table>

                                <tr><td id="result_search"><% out.print(search_stats);%></td></tr><tr></tr>

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




