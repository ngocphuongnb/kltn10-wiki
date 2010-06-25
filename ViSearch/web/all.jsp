<%-- 
    Document   : all
    Created on : Jun 8, 2010, 7:49:58 PM
    Author     : tuandom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.apache.solr.client.solrj.SolrQuery"%>
<%@page import="org.apache.solr.client.solrj.SolrServer"%>
<%@page import="org.apache.solr.client.solrj.impl.CommonsHttpSolrServer"%>
<%@page import="org.apache.solr.common.SolrDocument"%>
<%@page import="java.util.Calendar"%>
<%@page import="org.apache.solr.common.SolrDocumentList"%>
<%@page import="org.apache.solr.common.SolrInputDocument"%>
<%@page import="org.apache.solr.client.solrj.response.QueryResponse"%>
<%@page import="java.util.*, java.net.*,java.util.Map, org.apache.commons.httpclient.util.*, java.text.*"%>
<%@page import="org.apache.solr.client.solrj.response.FacetField"%>
<%@page import="org.me.dto.FacetDateDTO"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ViSearch - Tổng hợp</title>
               <link href="style.css"rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <link type="text/css" href="css/visearchStyle.css" rel="stylesheet"/>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#tbTopSearch").load("TopSearch?SearchType=7");
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
                    var url = "SearchAllController?type=0&KeySearch=";
                    url += encodeURIComponent(keysearch);
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


            function showVideo(id)
            {
                // showVideo and Close all other Videos
                count = document.getElementsByTagName('OBJECT').length;
                for(var i=0; i < count; i++){
                    MDid = 'MediaPlayer'+i;
                    if(i!=id) // Close others
                    {
                        hideVideo(i);
                    }
                    else // and open new
                    {
                        document.getElementById(MDid).className="display";
                    }
                }
                // Show button CloseVideo and Close bt View
                var  btDong = "BTCloseMediaId" + id;
                document.getElementById(btDong).className="display";
                var btxem = 'BTViewMediaId'+id;
                document.getElementById(btxem).className="hidden";
            }
            function hideVideo(id)
            {
                // Hide media
                MDid = 'MediaPlayer'+id;
                document.getElementById(MDid).className="hidden";

                // Button XemLoiNhac hide, button DongLoiNhac show
                var btxem = 'BTViewMediaId'+id;
                document.getElementById(btxem).className="display";
                var  btDong = "BTCloseMediaId" + id;
                document.getElementById(btDong).className="hidden";
            }

            function ClickDetail(link)
            {
                var keysearch = document.getElementById('hfKeySearch').value;
                var url = "DetailAllController?url=" + link;
                url += "&KeySearch=" + keysearch;
                window.location = url;
            }
            function Tracking(docid){
                var keySearch = document.getElementById('hfKeySearch').value;
                var Url = "TrackingController?KeySearch=" + keySearch;
                Url += "&DocID=" + docid;
                Url += "&searchType=7";
                alert(Url);
                window.location = Url;
            }
        </script>


    </head>

    <body onLoad="setText();">
        <%
                    String currentPage = "/all.jsp";
                    if (request.getQueryString() != null) {
                        currentPage = "/SearchAllController?";
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
                                result += "<p><font color=\"#CC3333\" size=\"+2\">Có phải bạn muốn tìm: <b><a href=\"SearchAllController?type=0&KeySearch=" + sCollation + "\">" + sCollation + "</a></b></font></p>";
                            }

                            List<String> highlightBody = null;
                            for (int i = 0; i < listdocs.size(); i++) {
                                result += "<table style=\"font-size:13px\">";

                                // Lay noi dung cua moi field
                                String title = (listdocs.get(i).getFirstValue("title")).toString();
                                String body = (listdocs.get(i).getFieldValue("body")).toString();
                                String id = (listdocs.get(i).getFieldValue("id")).toString();
                                String title_hl = title;

                                if (request.getAttribute("HighLight") != null) {
                                    highLight = (Map<String, Map<String, List<String>>>) request.getAttribute("HighLight");
                                    highlightBody = highLight.get(id).get("body");
                                    List<String> highlightTitle = highLight.get(id).get("title");

                                    if (highlightTitle != null && !highlightTitle.isEmpty()) {
                                        title_hl = highlightTitle.get(0);
                                    }
                                }


                                String link = "";
                                String type = "";
                                String subId = "";
                                if (id.length() > 2 && id.substring(0, 2).equals("rv")) {
                                    subId = id.substring(2);
                                    link = "<a href=\"DetailRaoVatController?id=" + subId + "&KeySearch=" + strQuery + "\" onclick=\"Tracking('" + subId + "');\">" + title_hl + "</a>";
                                    type = "rv";
                                } else if (id.length() > 4 && id.substring(0, 4).equals("news")) {
                                    subId = id.substring(4);
                                    link = "<a href=\"DetailNewsController?id=" + subId + "&KeySearch=" + strQuery + "\" onclick=\"Tracking('" + subId + "');\">" + title_hl + "</a>";
                                    type = "news";
                                } else if (id.length() > 4 && id.substring(0, 4).equals("wiki")) {
                                    subId = id.substring(4);
                                    link = "<a href=\"DetailWikiController?id=" + subId + "&KeySearch=" + strQuery + "\" onclick=\"Tracking('" + subId + "');\">" + title_hl + "</a>";
                                    type = "wiki";
                                } else if (id.length() > 2 && id.substring(0, 2).equals("ms")) {
                                    subId = id.substring(2);
                                    link = title_hl;
                                    type = "ms";
                                } else if (id.length() > 5 && id.substring(0, 5).equals("video")) {
                                    subId = id.substring(5);
                                    link = title_hl;
                                    type = "video";
                                } else if (id.length() > 3 && id.substring(0, 3).equals("img")) {
                                    subId = id.substring(3);
                                    link = "<a href=\"DetailImageController?id=" + subId + "&KeySearch=" + strQuery + "\" onclick=\"Tracking('" + subId + "');\">" + title_hl + "</a>";
                                    type = "img";
                                }

                                // show title
                                result += "<tr>";
                                result += "<td><b>" + link + "</b></td>";
                                result += "</tr>";


                                // show body
                                String strBody = "";
                                // Neu la image thì body la link image --> show image lên
                                if (type.equals("img")) {
                                    strBody = "<tr><td valign=\"bottom\"><a href=\"DetailImageController?id=" + subId + "&KeySearch=" + strQuery + "\"><img src='" + body + "' width=\"150\" align=\"left\"/></a></td></tr>";
                                    // Neu la music thì show window media lên
                                } else if (type.equals("ms")) {
                                    strBody += "<tr><td>";
                                    strBody += "<OBJECT CLASSID=\"CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95\" CODEBASE=\"http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab# Version=5,1,52,70\" STANDBY=\"Loading Microsoft Windows® Media Player components...\" TYPE=\"application/x-oleobject\" width=\"280\" height=\"46\">";
                                    strBody += "<param name=\"fileName\" value=\"\">";
                                    strBody += "<param name=\"animationatStart\" value=\"false\">";
                                    strBody += "<param name=\"transparentatStart\" value=\"true\">";
                                    strBody += "<param name=\"autoStart\" value=\"false\">";
                                    strBody += "<param name=\"showControls\" value=\"true\">";
                                    strBody += "<param name=\"Volume\" value=\"-300\">";
                                    strBody += "<embed type=\"application/x-mplayer2\" pluginspage=\"http://www.microsoft.com/Windows/MediaPlayer/\" src=\"E:\\Relax\\Music\\Nhac Viet Nam\\Tinh yeu lung linh.mp3\" name=\"MediaPlayer1\" width=280 height=46 autostart=0 showcontrols=1 volume=-300>";
                                    strBody += "</OBJECT>";
                                    strBody += "</td></tr>";

                                } else if (type.equals("video")) {
                                    // Neu la video thì show window media lên
                                    String BTViewMediaId = "BTViewMediaId" + i;
                                    String BTCloseMediaId = "BTCloseMediaId" + i;
                                    String mediaId = "MediaPlayer" + i;
                                    strBody += "<tr><td><input type=\"button\" ID=\"" + BTViewMediaId + "\" value=\"Xem video\" onclick=\"showVideo('" + i + "');\" />";
                                    strBody += "<input type=\"button\" ID=\"" + BTCloseMediaId + "\" class=\"hidden\" value=\"Đóng video\" onclick=\"hideVideo('" + i + "');\" />";
                                    strBody += "</td></tr>";

                                    strBody += "<tr><td>";
                                    strBody += "<span id='Tracking'></span>";
                                    strBody += "<object class=\"hidden\" ID=\"" + mediaId + "\" classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0\" width=\"608\" height=\"432\" id=\"FLVPlayer\">";
                                    strBody += "<param name=\"movie\" value=\"FLVPlayer_Progressive.swf\" />";
                                    strBody += "<param name=\"salign\" value=\"lt\" />";
                                    strBody += "<param name=\"quality\" value=\"high\" />";
                                    strBody += "<param name=\"scale\" value=\"noscale\" />";
                                    strBody += "<param name=\"FlashVars\" value=\"&MM_ComponentVersion=1&skinName=Clear_Skin_3&streamName=Circus_Britney&autoPlay=false&autoRewind=false\" />";
                                    strBody += "<embed src=\"FLVPlayer_Progressive.swf\" flashvars=\"&MM_ComponentVersion=1&skinName=Clear_Skin_3&streamName=Circus_Britney&autoPlay=false&autoRewind=false\" quality=\"high\" scale=\"noscale\" width=\"608\" height=\"432\" name=\"FLVPlayer\" salign=\"LT\" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash\" />";
                                    strBody += "</object>";
                                    strBody += "</td></tr>";
                                    %>
                                    <script type="text/javascript">
                                        $(document).ready(function(){
                                            $("#<%=BTViewMediaId%>").click(function(){
                                                var docID = <%=subId%>
                                                var keySearch = $("#hfKeySearch").attr("value");
                                                var Url = "TrackingController?KeySearch=" + keySearch;
                                                Url += "&DocID=" + docID;
                                                Url += "&searchType=7";
                                                $("#Tracking").load(encodeURI(Url));
                                            });
                                        });
                                    </script>
                                    <%
                                } else { // còn lại là rao vặt + news
                                    if (highlightBody != null && !highlightBody.isEmpty()) {
                                        body = highlightBody.get(0);
                                    } else {
                                        if (body.length() > 500) {
                                            body = body.substring(0, 500) + "...";
                                        }
                                    }
                                    strBody = "<tr><td>" + body + "</td></tr>";
                                }

                                result += strBody;

                                result += "<tr>";
                                result += "<td colspan='2'>";
                                result += "<a href=\"SearchAllController?type=1&KeySearch=" + URIUtil.encodeAll(title) + "\">Trang tương tự...</a>";
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
                                    facet += "<a href = 'SearchAllController?type=2&KeySearch=" + strQuery + "&qf=" + fieldName + "&qv=" + fieldText + "'>" + fieldText + "</a>";
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
                                    </td>
                                </tr>
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

                                <% if (request.getAttribute("Docs") != null) {
                                                out.print(facet);
                                            }
                                %>
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

