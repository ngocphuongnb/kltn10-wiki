<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.apache.solr.client.solrj.SolrQuery"%>
<%@page import="org.apache.solr.client.solrj.SolrServer"%>
<%@page import="org.apache.solr.client.solrj.impl.CommonsHttpSolrServer"%>
<%@page import="org.apache.solr.common.SolrDocument"%>
<%@page import="java.util.Calendar"%>
<%@page import="org.apache.solr.common.SolrDocumentList"%>
<%@page import="org.apache.solr.common.SolrInputDocument"%>
<%@page import="org.apache.solr.client.solrj.response.QueryResponse"%>
<%@page import="java.util.*, java.net.*,java.util.Map, org.apache.commons.httpclient.util.*"%>
<%@page import="org.apache.solr.client.solrj.response.FacetField"%>
<%@page import="org.me.dto.FacetDateDTO"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ViSearch - Wikipedia</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="script/jquery-1.4.2.min.js"/>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#tbTopSearch").load("TopSearch?SearchType=1");
            });
        </script>
        <script language="javascript">
            function setText()
            {
                var keysearch = document.getElementById('txtSearch').value;
                if(keysearch=="")
                    document.getElementById('txtSearch').focus();
            }
            function CheckInput()
            {
                var keysearch = document.getElementById('txtSearch').value;
                if(keysearch == "")
                    return;
                else
                {
                    var url = "SearchWikiController?type=0&sp=1&KeySearch=";
                    //url += keysearch.value;
                    url += encodeURIComponent(keysearch);
                    //alert(url);
                    window.location = url;
                }
            }
            function ClickDetail(link)
            {
                var keysearch = document.getElementById('hfKeySearch').value;
                var url = "DetailWikiController?url=" + link;
                url += "&KeySearch=" + keysearch;
                window.location = url;
            }

            
            function SeachPVDC(strQuery){
                var batdau = document.getElementById("divPVTC_BD").value;
                var  kethuc = document.getElementById("divPVTC_KT").value;
                strQuery =  encodeURIComponent(strQuery);
                var url = "SearchWikiController?type=4&KeySearch=" + strQuery + "&FaceName=timestamp&sd="+batdau+"&ed="+kethuc;
                window.location = url;
            }
            function showPVTC(){
                document.getElementById("divPVTC").className="display";
            }
            
            function ClickDetail(link)
            {
                var keysearch = document.getElementById('hfKeySearch').value;
                var url = "DetailWikiController?url=" + link;
                url += "&KeySearch=" + keysearch;
                window.location = url;
            }
        </script>
    </head>

    <body onLoad="setText();">
        <%
                    String currentPage = "/index.jsp";
                    if (request.getQueryString() != null) {
                        currentPage = "/SearchWikiController?";
                        currentPage += request.getQueryString().toString();
                    }
                    session.setAttribute("CurrentPage", currentPage);

                    // Get strQuery
                    String strQuery = "";
                    if (request.getAttribute("KeySearch") != null) {
                        strQuery = (String) request.getAttribute("KeySearch");
                        //strQuery = URLDecoder.decode(strQuery, "UTF-8");
                        strQuery = strQuery.replaceAll("\"", "&quot;");
                    }
                    // End Get strQuery
%>
        <%
                    // Get SolrDocumentList
                    SolrDocumentList listdocs = new SolrDocumentList();
                    Map<String, Map<String, List<String>>> highLight = null;
                    int numrow = 0;
                    int numpage = 0;
                    String strpaging = "";
                    String search_stats = "";
                    String result = "";
                    String QTime;
                    if (request.getAttribute("QTime") != null) {
                        QTime = request.getAttribute("QTime").toString();
                        if (request.getAttribute("Docs") != null) {
                            listdocs = (SolrDocumentList) request.getAttribute("Docs");

                            search_stats = String.format("Có %d kết quả (%s giây)", listdocs.getNumFound(), QTime);
                            if (request.getAttribute("Collation") != null) {
                                String sCollation = (String) request.getAttribute("Collation");
                                result += "<p><font color=\"#CC3333\" size=\"+2\">Có phải bạn muốn tìm: <b><a href=\"SearchWikiController?type=0&KeySearch=" + sCollation + "\">" + sCollation + "</a></b></font></p>";
                            }

                            for (int i = 0; i < listdocs.size(); i++) {
                                result += "<table style=\"font-size:13px\">";

                                // Lay noi dung cua moi field
                                String title = (listdocs.get(i).getFirstValue("wk_title")).toString();
                                String text = (listdocs.get(i).getFieldValue("wk_text")).toString();
                                String id = (listdocs.get(i).getFieldValue("id")).toString();
                                String url = title.replace(' ', '_');
                                String title_hl = title;
                                String timestamp = (listdocs.get(i).getFirstValue("timestamp")).toString();

                                if (request.getAttribute("HighLight") != null) {
                                    highLight = (Map<String, Map<String, List<String>>>) request.getAttribute("HighLight");
                                    List<String> highlightText = highLight.get(id).get("wk_text");
                                    List<String> highlightTitle = highLight.get(id).get("wk_title");
                                    if (highlightText != null && !highlightText.isEmpty()) {
                                        text = highlightText.get(0) + "...";
                                    } else {
                                        if (text.length() > 300) {
                                            text = text.substring(0, 300) + "...";
                                        }
                                    }
                                    if (highlightTitle != null && !highlightTitle.isEmpty()) {
                                        title_hl = highlightTitle.get(0);
                                    }
                                } else {
                                    if (text.length() > 300) {
                                        text = text.substring(0, 300) + "...";
                                    }
                                }


                                url = "<td><b><a href=\"javascript:ClickDetail('" + URLEncoder.encode(url, "UTF-8") + "&id=" + id + "')\">" + title_hl + "</a><b></td>";
                                result += "<tr>";
                                result += url;
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td>" + text + "</td>";
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td>Timestamp: " + timestamp + "</td>";
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td colspan='2'>";
                                result += "<a href=\"SearchWikiController?type=1&KeySearch=" + URIUtil.encodeAll(title) + "\">Trang tương tự...</a>";
                                result += "</td>";
                                result += "</tr>";
                                result += "<tr><td>&nbsp;</td></tr>";
                                result += "</table>";
                            }


                            // Phan trang
                            numrow = Integer.parseInt(request.getAttribute("NumRow").toString());
                            numpage = Integer.parseInt(request.getAttribute("NumPage").toString());
                            strpaging = (String) request.getAttribute("Pagging");
                        } else {
                            result += strpaging + "<b>Không tìm ra dữ liệu</b><br/>";
                        }
                        // result += "Số kết quả tìm được là: " + numrow + "<br/>";
                        result += "Tổng số trang là: " + numpage + "<br/>";
                        if (numpage > 1) {
                            result += strpaging + "<br/><br/>";
                        }
                    }
                    // End get SolrDocumentList
%>

        <%
// Get Facet
                    String facet = "";


                    List<FacetField> listFacet = (List<FacetField>) request.getAttribute("ListFacet");
                    if (listFacet != null) {
                        facet += "<div class=\"title_content\" align=\"left\"><b>Facet</b></div>";
                        for (int i = 0; i < listFacet.size(); i++) {
                            facet += "<table id=\"table_left\" width=\"100%\" border=\"0\">";
                            facet += "<tr>";
                            facet += "<td>";
                            facet += "<td>";
                            String fieldName = listFacet.get(i).getName();
                            facet += "<b>Facet: " + fieldName + "</b>";
                            facet += "<br>";
                            List<FacetField.Count> listCount = listFacet.get(i).getValues();
                            if (listCount != null) {
                                for (int j = 0; j < listCount.size(); j++) {
                                    String fieldText = listCount.get(j).getName();
                                    facet += "<a href = 'SearchWikiController?type=2&KeySearch=" + strQuery + "&FaceName=" + fieldName + "&FaceValue=" + fieldText + "'>" + fieldText + "</a>";
                                    facet += " (" + listCount.get(j).getCount() + ")";
                                    facet += "<br>";
                                }
                            } else {
                                facet += "Không tìm ra Facet<br>";
                            }
                            facet += "</td></tr>";
                            facet += "</table>";
                        }
                    }

                    // End Get Facet
%>

        <%
                    // Get Facet date
                    String facetD = "";
                    facetD += "<table id=\"table_left\" width=\"100%\" border=\"0\">";

                    /*       ArrayList<FacetDateDTO> listFacetDate = (ArrayList<FacetDateDTO>) request.getAttribute("ListFacetDate");
                    if (listFacetDate != null) {
                    facetD += "<tr><td><b>Facet: timestamp</b>";
                    facetD += "<br>";
                    if (listFacetDate.size() > 0) {
                    for (int i = 0; i < listFacetDate.size(); i++) {

                    String fieldText = listFacetDate.get(i).getDateTime();
                    facetD += "<a href = 'SearchWikiController?type=2&KeySearch=" + strQuery + "&FaceName=" + "timestamp" + "&FaceValue=" + fieldText + "'>" + fieldText + "</a>";
                    facetD += " (" + listFacetDate.get(i).getCount() + ")";
                    facetD += "<br>";
                    }
                    } else {
                    facetD += "Không tìm ra Facet<br>";
                    }
                    facetD += "</td></tr>";
                     */


                    Calendar cl = Calendar.getInstance();
                    String homnay = cl.get(Calendar.YEAR) + "-" + (cl.get(Calendar.MONTH)+1) + "-" + cl.get(Calendar.DAY_OF_MONTH) + "T00:00:00.000Z";

                    cl.set(Calendar.DAY_OF_MONTH, cl.get(Calendar.DAY_OF_MONTH) - 1);
                    String homqua = cl.get(Calendar.YEAR) + "-" + (cl.get(Calendar.MONTH)+1) + "-" + cl.get(Calendar.DAY_OF_MONTH) + "T00:00:00.000Z";
                    String homquaEnd = cl.get(Calendar.YEAR) + "-" + (cl.get(Calendar.MONTH)+1) + "-" + cl.get(Calendar.DAY_OF_MONTH) + "T23:59:59.999Z";


                    cl.set(Calendar.DAY_OF_MONTH, cl.get(Calendar.DAY_OF_MONTH) - 1);
                    String homkia = cl.get(Calendar.YEAR) + "-" + (cl.get(Calendar.MONTH)+1) + "-" + cl.get(Calendar.DAY_OF_MONTH) + "T00:00:00.000Z";
                    String homkiaEnd = cl.get(Calendar.YEAR) + "-" + (cl.get(Calendar.MONTH)+1) + "-" + cl.get(Calendar.DAY_OF_MONTH) + "T23:59:59.999Z";

                    // 1976-03-06T23:59:59.999Z

                    facetD += "<tr><td>";
                    facetD += "<a href = 'SearchWikiController?type=2&KeySearch=" + strQuery + "&FaceName=timestamp&FaceValue=" + URLEncoder.encode("[" + homnay + " TO NOW]", "UTF-8") + "'>" + "Hôm nay" + "</a>";
                    facetD += "</td></tr>";

                    facetD += "<tr><td>";
                    facetD += "<a href = 'SearchWikiController?type=2&KeySearch=" + strQuery + "&FaceName=timestamp&FaceValue=" + URLEncoder.encode("[" + homqua + " TO "+homquaEnd+"]", "UTF-8") + "'>" + "Hôm qua" + "</a>";
                    facetD += "</td></tr>";

                    facetD += "<tr><td>";
                    facetD += "<a href = 'SearchWikiController?type=2&KeySearch=" + strQuery + "&FaceName=timestamp&FaceValue=" + URLEncoder.encode("[" + homkia + " TO "+homkiaEnd+"]", "UTF-8") + "'>" + "Hôm kia" + "</a>";
                    facetD += "</td></tr>";

                    facetD += "<tr><td><input type=\"button\" name=\"btShowPVTC\" value=\"Phạm vi tùy chỉnh\" onclick=\"showPVTC();\" /></td></tr>";

                    facetD += "<tr><td>";
                    facetD += "<div id=\"divPVTC\" class=\"hidden\">";
                    facetD += "<div style=\"float:left\"> Bắt dầu: </div><div style=\"float:right\"><input type=\"text\" class=\"textForm\" onfocus=\"this.className='textForm_Hover';\" onblur=\"this.className='textForm';\" id=\"divPVTC_BD\" /></div>";
                    facetD += "<div style=\"float:left\"> Kết thúc: </div><div style=\"float:right\"><input type=\"text\"  class=\"textForm\" onfocus=\"this.className='textForm_Hover';\" onblur=\"this.className='textForm';\" id=\"divPVTC_KT\" /></div>";
                    facetD += "<div style=\"float:left\">&nbsp;&nbsp;</div><div style=\"float:right\">(dd-mm-yyyy)&nbsp;&nbsp;<input type=\"button\" name=\"btSearch\" value=\"Tìm kiếm\" onclick=\"SeachPVDC('" + strQuery + "');\" /></div>";
                    facetD += "</div>";

                    facetD += "</td></tr>";
                    // }
                    facetD += "</table>";
                    // End get Facet Date
%>
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
                                        <%@ include file="template/banner_Wiki.jsp"%>
                                        <!-- end banner      !-->
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td height="20" colspan="2" align="center" valign="bottom">
                            <div align="center" class="nav"></div>
                        </td>
                    </tr>
                    <tr>
                        <td width="200" height="33" valign="top">
                            <div class="subtable">
                                <div class="mnu">Đăng nhập</div>
                                <%@include file="template/login.jsp" %>
                                <% if (request.getAttribute("Docs") != null) {
                                                out.print(facet);
                                            }%>
                                <% if (request.getAttribute("Docs") != null) {
                                                 out.print("<div  class=\"mnu\">Ngày cập nhật</div>" + facetD);
                                             }%>

                                <div class="mnu">Tìm kiếm nhiều</div>
                                <table id="tbTopSearch">

                                </table>
                            </div>
                        </td>
                        <td width="627" rowspan="2" valign="top">

                            <table>

                                <tr><td id="result_search"><% out.print(search_stats);%></td></tr><tr></tr>
                            </table>
                            <table id="table_right" width="100%" cellpadding="0" cellspacing="0">

                                <tr>

                                    <td valign="top" id="content">
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
                            <%@include file="template/footer.jsp"%>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

    </body>
</html>


