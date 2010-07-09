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
        <title>ViSearch - Rao vặt</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/clock.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>

        <script type="" language="javascript">
            $(function(){
                $.ajax({
                    type: "POST",
                    url: "TopSearch",
                    cache: false,
                    data: "SearchType=2",
                    success: function(html){
                        $("#tbTopSearch").append(html);
                    }
                });
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
                    var url = "SearchRaoVatController?type=0&sp=1&KeySearch=";
                    url += encodeURIComponent(keysearch);
                    url += "&SortedType=" + sortedtype;
                    window.location = url;
                }
            }
            function SeachPVDC(strQuery){
                var batdau = document.getElementById("divPVTC_BD").value;
                var  kethuc = document.getElementById("divPVTC_KT").value;
                strQuery =  encodeURIComponent(strQuery);
                var url = "SearchRaoVatController?type=3&KeySearch=" + encodeURIComponent(strQuery) + "&FacetName=last_update&sd="+batdau+"&ed="+kethuc;
                window.location = url;
            }
            function showPVTC(){
                document.getElementById("divPVTC").className="display";
            }
        </script>
        <script type="" language="javascript">
            function Sort(type){
                var sortedtype = document.getElementById('slSortedType').value;
                //alert(sortedtype);
                var keysearch = document.getElementById('hfKeySearch').value;
                //alert(keysearch);
                if(keysearch == "")
                    return;
                else
                {
                    var url = "SearchRaoVatController?sp=1&KeySearch=";
                    url += encodeURIComponent(keysearch);
                    url += "&SortedType=" + sortedtype;
                    url += "&type=" + type;
                    window.location = url;
                }
            }
        </script>
    </head>

    <body onload="setText();">

        <%
                    String currentPage = "/raovat.jsp";
                    if (request.getQueryString() != null) {
                        currentPage = "/SearchRaoVatController?";
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
                                result += "<p><font color=\"#CC3333\" size=\"+2\">Có phải bạn muốn tìm: <b><a href=\"SearchRaoVatController?type=0&KeySearch=" + sCollation + "\">" + sCollation + "</a></b></font></p>";
                            }

                            for (int i = 0; i < listdocs.size(); i++) {
                                result += "<table style=\"font-size:13px\">";

                                // Lay noi dung cua moi field
                                String title = (listdocs.get(i).getFirstValue("rv_title")).toString();
                                String body = (listdocs.get(i).getFirstValue("rv_body")).toString();
                                String id = (listdocs.get(i).getFieldValue("id")).toString();
                                Date last_update = (Date) (listdocs.get(i).getFieldValue("last_update"));
                                String url;
                                String title_hl = title;
                                String photo = "images/Noimage.jpg";
                                if (listdocs.get(i).getFieldValue("photo") != null && !listdocs.get(i).getFieldValue("photo").toString().equals("")) {
                                    photo = (listdocs.get(i).getFieldValue("photo")).toString();
                                }

                                if (request.getAttribute("HighLight") != null) {
                                    highLight = (Map<String, Map<String, List<String>>>) request.getAttribute("HighLight");
                                    List<String> highlightBody = highLight.get(id).get("rv_body");
                                    List<String> highlightTitle = highLight.get(id).get("rv_title");
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

                                url = "<td><b><a href=\"DetailRaoVatController?id=" + id + "&KeySearch=" + strQuery + "\">" + title_hl + "</a></b></td>";
                                result += "<tr>";
                                result += "<td rowspan=\"3\" width=\"150\"><img src=\"" + photo + "\" width=\"150\" align=\"left\" /></td>";
                                result += url;
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td>" + body + "</td>";
                                result += "</tr>";

                                result += "<tr>";
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                                result += "<td><b>Ngày cập nhật:</b> " + sdf.format(last_update) + "</td>";
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td>";
                                result += "<a href=\"SearchRaoVatController?type=1&KeySearch=" + title.replaceAll("\\<.*?\\>", "") + "\">Trang tương tự...</a>";
                                result += "</td>";
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
                            if (fieldName.equals("site")) {
                                facet += "<b>Nguồn</b>";
                            }
                            facet += "<br>";
                            List<FacetField.Count> listCount = listFacet.get(i).getValues();
                            if (listCount != null) {
                                for (int j = 0; j < listCount.size(); j++) {
                                    String fieldText = listCount.get(j).getName();
                                    facet += "<a href = 'SearchRaoVatController?type=2&KeySearch=" + strQuery + "&FacetName=" + fieldName + "&FacetValue=" + fieldText + "'>" + fieldText + "</a>";
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
        <%
                    // Get query date
                    String facetD = "";
                    facetD += "<table id=\"table_left\" width=\"100%\" border=\"0\">";

                    Calendar cl = Calendar.getInstance();

                    String str24hqua = cl.get(Calendar.YEAR) + "-" + (cl.get(Calendar.MONTH) + 1) + "-" + (cl.get(Calendar.DAY_OF_MONTH) - 1)
                            + "T" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND) + "." + cl.get(Calendar.MILLISECOND) + "Z";

                    cl.add(Calendar.DATE, -7);
                    String str1tuanqua = cl.get(Calendar.YEAR) + "-" + (cl.get(Calendar.MONTH) + 1) + "-" + cl.get(Calendar.DAY_OF_MONTH)
                            + "T" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND) + "." + cl.get(Calendar.MILLISECOND) + "Z";

                    cl.add(Calendar.DATE, +7);
                    String str1thangqua = cl.get(Calendar.YEAR) + "-" + cl.get(Calendar.MONTH) + "-" + cl.get(Calendar.DAY_OF_MONTH)
                            + "T" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND) + "." + cl.get(Calendar.MILLISECOND) + "Z";


                    // 1976-03-06T23:59:59.999Z
                    facetD += "<tr><td>";
                    facetD += "<a href = 'SearchRaoVatController?type=4&KeySearch=" + strQuery + "&FacetName=last_update&FacetValue=" + URLEncoder.encode("[" + str24hqua + " TO NOW]", "UTF-8") + "'>" + "24 giờ qua" + "</a>";
                    facetD += "</td></tr>";

                    facetD += "<tr><td>";
                    facetD += "<a href = 'SearchRaoVatController?type=4&KeySearch=" + strQuery + "&FacetName=last_update&FacetValue=" + URLEncoder.encode("[" + str1tuanqua + " TO NOW]", "UTF-8") + "'>" + "1 tuần trước" + "</a>";
                    facetD += "</td></tr>";

                    facetD += "<tr><td>";
                    facetD += "<a href = 'SearchRaoVatController?type=4&KeySearch=" + strQuery + "&FacetName=last_update&FacetValue=" + URLEncoder.encode("[" + str1thangqua + " TO NOW]", "UTF-8") + "'>" + "1 tháng trước" + "</a>";
                    facetD += "</td></tr>";

                    facetD += "<tr><td><a style=\"cursor:pointer\" onclick=\"showPVTC();\" />Phạm vi tùy chỉnh</a></td></tr>";

                    facetD += "<tr><td>";
                    facetD += "<div id=\"divPVTC\" class=\"hidden\">";
                    facetD += "<div style=\"float:left\"> Bắt dầu: </div><div style=\"float:right\"><input type=\"text\" class=\"textForm\" onfocus=\"this.className='textForm_Hover';\" onblur=\"this.className='textForm';\" id=\"divPVTC_BD\" /></div>";
                    facetD += "<div style=\"float:left\"> Kết thúc: </div><div style=\"float:right\"><input type=\"text\"  class=\"textForm\" onfocus=\"this.className='textForm_Hover';\" onblur=\"this.className='textForm';\" id=\"divPVTC_KT\" /></div>";
                    facetD += "<div style=\"float:left\">&nbsp;&nbsp;</div><div style=\"float:right\">(dd-mm-yyyy)&nbsp;&nbsp;<input type=\"button\" name=\"btSearch\" value=\"Tìm kiếm\" onclick=\"SeachPVDC('" + strQuery + "');\" /></div>";
                    facetD += "</div>";

                    facetD += "</td></tr>";
                    // }
                    facetD += "</table>";
                    // End get Query Date
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
                                        <%@include file="template/banner_RaoVat.jsp"%>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td style="font-size:12px;" width="30%" align="middle">
                            <script type="" language="javascript">goforit();</script>
                            <span id="clock"/></td>
                        <td width="70%" align="middle"><%@include file="template/sortedtype.jsp"%></td>
                    </tr>
                    <tr><td height="20" colspan="2" align="center" valign="bottom"><div align="center" class="nav"></div></td></tr>
                    <tr>
                        <td width="200" height="33" valign="top">
                            <div class="subtable">

                                <% if (request.getAttribute("Docs") != null) {
                                                out.print(facet);
                                                out.print("<div  class=\"mnu\">Ngày cập nhật</div>" + facetD);
                                            }%>

                                <div class="mnu">Tìm kiếm nhiều</div>
                                <table  id="tbTopSearch">

                                </table>
                            </div>
                        </td>
                        <script type="text/javascript">
                                $(function(){
                                    $("#divPVTC_KT").datepicker({dateFormat: 'dd-mm-yy'});
                                    $("#divPVTC_BD").datepicker({dateFormat: 'dd-mm-yy'});
                                });
                            </script>
                        <td width="627" rowspan="2" valign="top">
                            <table>

                                <tr><td id="result_search"><% out.print(search_stats);%></td></tr><tr></tr>
                                <%  if (request.getParameter(
                                                    "FacetValue") != null) {
                                                out.print("<tr><td id=\"top-header\">");
                                                out.print(">> " + request.getParameter("FacetValue"));
                                                out.print("</td></tr>");
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



