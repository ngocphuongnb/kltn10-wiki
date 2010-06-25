<%-- 
    Document   : video
    Created on : May 28, 2010, 9:22:53 PM
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
<%@page import="java.util.*, java.net.*,java.util.Map, org.apache.commons.httpclient.util.*"%>
<%@page import="org.apache.solr.client.solrj.response.FacetField"%>
<%@page import="org.me.dto.FacetDateDTO"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Video - Wikipedia</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <script src="Scripts/AC_RunActiveContent.js" type="text/javascript"></script>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <link type="text/css" href="css/visearchStyle.css" rel="stylesheet"/>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#tbTopSearch").load("TopSearch?SearchType=4");
            });
        </script>
        <script type="" language="javascript">
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
                    var url = "SearchVideoController?type=0&sp=1&KeySearch=";
                    //url += keysearch.value;
                    url += encodeURIComponent(keysearch);
                    //alert(url);
                    window.location = url;
                }
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
            $.ajax({
                type: "POST",
                url: "TopSearch",
                cache: false,
                data: "SearchType=5",
                success: function(html){
                    $("#tbTopSearch").append(html);
                }
            });
        </script>
    </head>

    <body onLoad="setText();">
        <%
                    String currentPage = "/video.jsp";
                    if (request.getQueryString() != null) {
                        currentPage = "/SearchVideoController?";
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
                    String addBM="";
                    String QTime;
                    if (request.getAttribute("QTime") != null) {
                        QTime = request.getAttribute("QTime").toString();
                        if (request.getAttribute("Docs") != null) {
                            listdocs = (SolrDocumentList) request.getAttribute("Docs");

                            search_stats = String.format("Có %d kết quả (%s giây)", listdocs.getNumFound(), QTime);
                            if (request.getAttribute("Collation") != null) {
                                String sCollation = (String) request.getAttribute("Collation");
                                result += "<p><font color=\"#CC3333\" size=\"+2\">Có phải bạn muốn tìm: <b><a href=\"SearchVideoController?type=0&KeySearch=" + sCollation + "\">" + sCollation + "</a></b></font></p>";
                            }

                            for (int i = 0; i < listdocs.size(); i++) {
                                result += "<table style=\"font-size:13px\">";

                                // Lay noi dung cua moi field
                                String title = (listdocs.get(i).getFirstValue("title")).toString();
                                String url = (listdocs.get(i).getFieldValue("url")).toString();
                                String id = (listdocs.get(i).getFieldValue("id")).toString();
                                String category = (listdocs.get(i).getFieldValue("category")).toString();
                                String duration = (listdocs.get(i).getFieldValue("duration")).toString();


                                String title_hl = title;

                                if (request.getAttribute("HighLight") != null) {
                                    highLight = (Map<String, Map<String, List<String>>>) request.getAttribute("HighLight");
                                    List<String> highlightTitle = highLight.get(id).get("title");
                                    if (highlightTitle != null && !highlightTitle.isEmpty()) {
                                        title_hl = highlightTitle.get(0);
                                    }
                                }

                                result += "<tr>";
                                result += "<td><b>" + title_hl + "</b></td>";
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td>Thể Loại: " + "<a href = 'SearchVideoController?type=2&KeySearch=category:\"" + category + "\"'>" + category + "</a></td>";
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td>Thời gian: " + duration + "</td>";
                                result += "</tr>";


                                String mediaId = "MediaPlayer" + i;
                                String BTViewMediaId = "BTViewMediaId" + i;
                                String BTCloseMediaId = "BTCloseMediaId" + i;
                                 String btBookmark = "btBookmark" + i;
                                String spanBookmark = "spanBookmark" + i;
                                String addBookmark = "addBookmark" + i;
                                String nameBookmark = "nameBookmark" + i;
                                String hdIdValue ="hdIdValue"+i;

                                // START Tracking

                                result += "<tr><td>";
                                result += "<input type=\"button\" ID=\"" + BTViewMediaId + "\" value=\"Xem video\" onclick=\"showVideo('" + i + "');\" />";
                                result += "<input type=\"button\" ID=\"" + BTCloseMediaId + "\" class=\"hidden\" value=\"Đóng video\" onclick=\"hideVideo('" + i + "');\" /></td>";
                                result += "</tr>";

                                result += "<tr><td>";

                                result += "<span id='Tracking'></span>";

                                result += "<object class=\"hidden\" ID=\"" + mediaId + "\" classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0\" width=\"608\" height=\"432\" id=\"FLVPlayer\">";
                                result += "<param name=\"movie\" value=\"FLVPlayer_Progressive.swf\" />";
                                result += "<param name=\"salign\" value=\"lt\" />";
                                result += "<param name=\"quality\" value=\"high\" />";
                                result += "<param name=\"scale\" value=\"noscale\" />";
                                result += "<param name=\"FlashVars\" value=\"&MM_ComponentVersion=1&skinName=Clear_Skin_3&streamName=Circus_Britney&autoPlay=false&autoRewind=false\" />";
                                result += "<embed src=\"FLVPlayer_Progressive.swf\" flashvars=\"&MM_ComponentVersion=1&skinName=Clear_Skin_3&streamName=Circus_Britney&autoPlay=false&autoRewind=false\" quality=\"high\" scale=\"noscale\" width=\"608\" height=\"432\" name=\"FLVPlayer\" salign=\"LT\" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash\" />";
                                result += "</object>";

                                result += "</td></tr>";

        %>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#<%=BTViewMediaId%>").click(function(){
                    var docID = <%=id%>
                    var keySearch = $("#hfKeySearch").attr("value");
                    var Url = "TrackingController?KeySearch=" + keySearch;
                    Url += "&DocID=" + docID;
                    Url += "&searchType=5";
                    $("#Tracking").load(encodeURI(Url));
                });
            });
        </script>
        <%
// END Tracking


        // START Bookmark
        %>
        <script type="text/javascript">
            $(function() {
                $("#datepicker").datepicker({dateFormat: 'dd-mm-yy'});

                $("#dialog").dialog("destroy");
                var tips = $(".validateTips");
                var name = $("#<%=nameBookmark%>");

                function updateTips(t) {
                    tips
                    .text(t)
                    .addClass('ui-state-highlight');
                    setTimeout(function() {
                        tips.removeClass('ui-state-highlight', 1500);
                    }, 500);
                }

                function checkLength(o) {

                    if ( o.val().length == 0) {
                        o.addClass('ui-state-error');
                        updateTips("Bạn chưa nhập tên bookmark");
                        return false;
                    } else {
                        return true;
                    }
                }

                $('#<%=addBookmark%>').dialog({
                    autoOpen: false,
                    height: 300,
                    width: 350,
                    modal: true,
                    buttons: {
                        'Đóng': function() {
                            $(this).dialog('close');
                        },
                        'Thêm': function() {
                            var bValid = true;
                            tips.removeClass('ui-state-error');
                            bValid = checkLength(name);
                            if(bValid)
                            {
                                var docID = $("#<%=hdIdValue%>").attr("value");
                                var keySearch = $("#hfKeySearch").attr("value");
                                var nameBookmark = $("#<%=nameBookmark%>").attr("value");
                                var Url = "BookmarkController?NameBookmark=" + nameBookmark;
                                Url += "&DocID=" + docID;
                                Url += "&SearchType=5";
                                alert("Đã thêm vào Bookmark");
                                $("#<%=spanBookmark%>").load(encodeURI(Url));
                                $(this).dialog('close');
                            }
                        }
                    },close: function() {
                        name.val('').removeClass('ui-state-error');
                    }
                });
                $('#<%=btBookmark%>')
                .button()
                .click(function() {
                    $('#<%=addBookmark%>').dialog('open');
                });
            });

            $(document).ready(function(){
                $("#tbTopSearch").load("TopSearch?SearchType=5");
            });
        </script>
        <%



                                if (session.getAttribute("Member") != null) {
                                    result += "<tr><td><span id=\"" + spanBookmark + "\"><input id=\"" + hdIdValue + "\" type='hidden' value='" + id + "'/>"
                                            + "<input id=\"" + btBookmark + "\" type='button' value='Thêm vào bookmark'/></span></td></tr>";
                                }

                                addBM += "<div id=\"" + addBookmark + "\" title=\"Thêm bookmark\">";
                                addBM += "<p class=\"validateTips\"/>";
                                addBM += "<form name=\"frmBm"+i+"\">";
                                addBM += " <fieldset>";
                                addBM += "  <label for=\"name\">Tên bookmark</label>";
                                addBM += "  <input type=\"text\" name=\"name\"  ID=\"" + nameBookmark + "\" class=\"text ui-widget-content ui-corner-all\" />";
                                addBM += " </fieldset>";
                                addBM += " </form>";
                                addBM += "</div>";
        // END Bookmark

                                result += "</td></tr>";
                                result += "<tr>";
                                result += "<td colspan='2'>";
                                result += "<a href=\"SearchVideoController?type=1&KeySearch=" + URIUtil.encodeAll(title) + "\">Trang tương tự...</a>";
                                result += "</td>";

                                result += "</tr>";
                                result += "<tr><td>&nbsp;</td></tr>";
                                result += "</table>";
                            }


                            // Phan trang
                            numrow = Integer.parseInt(request.getAttribute("NumRow").toString());
                            numpage = Integer.parseInt(request.getAttribute("NumPage").toString());
                            strpaging = (String) request.getAttribute("Pagging");
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
                        facet += "<div class=\"mnu\">Facet</div>";
                        for (int i = 0; i < listFacet.size(); i++) {
                            facet += "<table id=\"table_left\" width=\"100%\" border=\"0\">";
                            facet += "<tr>";
                            facet += "<td>";
                            facet += "<td>";
                            String fieldName = listFacet.get(i).getName();
                            facet += "<b>Thể loại</b>"; // vi chi facet 1 field category
                            facet += "<br>";
                            List<FacetField.Count> listCount = listFacet.get(i).getValues();
                            if (listCount != null) {
                                for (int j = 0; j < listCount.size(); j++) {
                                    String fieldText = listCount.get(j).getName();
                                    facet += "<a href = 'SearchVideoController?type=2&KeySearch=" + strQuery + "&FacetName=" + fieldName + "&FacetValue=" + fieldText + "'>" + fieldText + "</a>";
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
                                        <%@ include file="template/banner_Video.jsp"%>
                                        <!-- end banner      !-->
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr><td height="20" colspan="2" align="center" valign="bottom"><div align="center" class="nav"></div></td></tr>
                    <tr>
                        <td width="200" height="33" valign="top">

                            <div class="subtable">

                                <% out.print(facet);%>
                                <div class="mnu">Tìm kiếm nhiều</div>
                                <table id="tbTopSearch">

                                </table>

                            </div>
                        </td>
                        <td width="627" rowspan="2" valign="top">
<% out.print(addBM); %>
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



