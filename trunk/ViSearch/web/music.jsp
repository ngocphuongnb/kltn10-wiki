<%-- 
    Document   : music
    Created on : May 28, 2010, 11:57:45 PM
    Author     : tuandom
--%>

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
        <script type="text/javascript" src="script/jquery-1.4.2.min.js"/>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#tbTopSearch").load("TopSearch?SearchType=3");
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
                    var url = "SearchMusicController?type=0&sp=1&KeySearch=";
                    //url += keysearch.value;
                    url += encodeURIComponent(keysearch);
                    //alert(url);
                    window.location = url;
                }
            }
            function showMediaWindow(id)
            {
                MDid = 'MediaPlayer'+id;
                document.getElementById(MDid).className="display";

                // Close all other MediaPlayers
                count = document.getElementsByTagName('OBJECT').length;
                for(var i=0; i < count; i++){
                    if(i!=id)
                    {
                        MDid2 = 'MediaPlayer'+i;
                        document.getElementById(MDid2).className="hidden";
                    }
                }
            }
            function showLyric(id)
            {
                // Show lyric div
                MDid = 'Lyric'+id;
                document.getElementById(MDid).className="display";

                // Button XemLoiNhac hide, button DongLoiNhac show
                var btxem = 'BTViewlyricId'+id;
                document.getElementById(btxem).className="hidden";

                var  btDong = "BTCloselyricId" + id;
                document.getElementById(btDong).className="display";
            }
            function hideLyric(id)
            {
                // Show lyric div
                MDid = 'Lyric'+id;
                document.getElementById(MDid).className="hidden";

                // Button XemLoiNhac hide, button DongLoiNhac show
                var btxem = 'BTViewlyricId'+id;
                document.getElementById(btxem).className="display";

                var  btDong = "BTCloselyricId" + id;
                document.getElementById(btDong).className="hidden";
            }
        </script>
    </head>

    <body onLoad="setText();">
        <%

                    String currentPage = "/music.jsp";
                    if (request.getQueryString() != null) {
                        currentPage = "/SearchMusicController?";
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
                                result += "<p><font color=\"#CC3333\" size=\"+2\">Có phải bạn muốn tìm: <b><a href=\"SearchMusicController?type=0&KeySearch=" + sCollation + "\">" + sCollation + "</a></b></font></p>";
                            }

                            for (int i = 0; i < listdocs.size(); i++) {
                                result += "<table style=\"font-size:13px\">";

                                // Lay noi dung cua moi field
                                String title = (listdocs.get(i).getFirstValue("title")).toString();
                                String url = (listdocs.get(i).getFieldValue("url")).toString();
                                String id = (listdocs.get(i).getFieldValue("id")).toString();
                                String CaSi = (listdocs.get(i).getFieldValue("singer")).toString();
                                String category = (listdocs.get(i).getFieldValue("category")).toString();
                                String album = (listdocs.get(i).getFieldValue("album")).toString();
                                String lyric = (listdocs.get(i).getFieldValue("lyric")).toString();
                                String artist = (listdocs.get(i).getFieldValue("artist")).toString();

                                String title_hl = title;

                                if (request.getAttribute("HighLight") != null) {
                                    highLight = (Map<String, Map<String, List<String>>>) request.getAttribute("HighLight");
                                    List<String> highlightTitle = highLight.get(id).get("title");
                                    if (highlightTitle != null && !highlightTitle.isEmpty()) {
                                        title_hl = highlightTitle.get(0);
                                    }
                                }

                                result += "<tr>";
                                result += "<td><b><a href=\"DetailMusicController?id=" + id + "\">" + title_hl + "</a></b></td>";
                                result += "</tr>";


                                // result += "<tr>";
                                // result += "<td>Link nhạc: "+url+"</td>";
                                // result += "</tr>";



                                result += "<tr>";
                                result += "<td>Ca sĩ: " + "<a href = 'SearchMusicController?type=2&KeySearch=singer:\"" + CaSi + "\"'>" + CaSi + "</a></td>";
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td>Nhạc sĩ: " + "<a href = 'SearchMusicController?type=2&KeySearch=artist:\"" + artist + "\"'>" + artist + "</a></td>";
                                result += "</tr>";


                                result += "<tr>";
                                result += "<td>Thể Loại: " + "<a href = 'SearchMusicController?type=2&KeySearch=category:\"" + category + "\"'>" + category + "</a></td>";
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td>Album: " + "<a href = 'SearchMusicController?type=2&KeySearch=album:\"" + album + "\"'>" + album + "</a></td>";
                                result += "</tr>";

                                String mediaId = "MediaPlayer" + i;
                                String lyricId = "Lyric" + i;
                                String BTViewlyricId = "BTViewlyricId" + i;
                                String BTCloselyricId = "BTCloselyricId" + i;

                                result += "<tr>";
                                result += "<td><input type=\"button\" value=\"Play\" onclick=\"showMediaWindow('" + i + "');\" />";
                                result += "<input type=\"button\" ID=\"" + BTViewlyricId + "\" value=\"Xem lời nhạc\" onclick=\"showLyric('" + i + "');\" />";
                                result += "<input type=\"button\" ID=\"" + BTCloselyricId + "\" class=\"hidden\" value=\"Đóng lời nhạc\" onclick=\"hideLyric('" + i + "');\" /></td>";
                                result += "</tr>";


                                result += "<tr><td>";
                                result += "<OBJECT class=\"hidden\" ID=\"" + mediaId + "\" CLASSID=\"CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95\" CODEBASE=\"http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab# Version=5,1,52,70\" STANDBY=\"Loading Microsoft Windows® Media Player components...\" TYPE=\"application/x-oleobject\" width=\"280\" height=\"46\">";
                                result += "<param name=\"fileName\" value=\"\">";
                                result += "<param name=\"animationatStart\" value=\"false\">";
                                result += "<param name=\"transparentatStart\" value=\"true\">";
                                result += "<param name=\"autoStart\" value=\"true\">";
                                result += "<param name=\"showControls\" value=\"true\">";
                                result += "<param name=\"Volume\" value=\"-300\">";
                                result += "<embed type=\"application/x-mplayer2\" pluginspage=\"http://www.microsoft.com/Windows/MediaPlayer/\" src=\"E:\\Relax\\Music\\Nhac Viet Nam\\Tinh yeu lung linh.mp3\" name=\"MediaPlayer1\" width=280 height=46 autostart=1 showcontrols=1 volume=-300>";
                                result += "</OBJECT>";
                                result += "</td></tr>";

                                result += "<tr><td>";
                                result += "<div class=\"hidden\" ID=\"" + lyricId + "\" style=\"border:thin inset; padding:6px; height:175px; overflow:auto\">";
                                result += lyric;
                                result += "</div>";

                                result += "</td></tr>";

                                result += "<tr>";
                                result += "<td colspan='2'>";
                                result += "<a href=\"SearchMusicController?type=1&KeySearch=" + URIUtil.encodeAll(title) + "\">Trang tương tự...</a>";
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
                        facet += "<div class=\"title_content\" align=\"left\"><b>Facet</b></div>";
                        for (int i = 0; i < listFacet.size(); i++) {
                            facet += "<table id=\"table_left\" width=\"100%\" border=\"0\">";
                            facet += "<tr>";
                            facet += "<td>";
                            facet += "<td>";
                            String fieldName = listFacet.get(i).getName();
                            if (fieldName.equals("category")) {
                                facet += "<b>Thể loại:</b>";
                            }
                            if (fieldName.equals("singer")) {
                                facet += "<b>Ca sĩ:</b>";
                            }
                            if (fieldName.equals("artist")) {
                                facet += "<b>Nhạc sĩ:</b>";
                            }
                            facet += "<br>";
                            List<FacetField.Count> listCount = listFacet.get(i).getValues();
                            if (listCount != null) {
                                for (int j = 0; j < listCount.size(); j++) {
                                    String fieldText = listCount.get(j).getName();
                                    facet += "<a href = 'SearchMusicController?type=2&KeySearch=" + strQuery + "&FacetName=" + fieldName + "&FacetValue=" + fieldText + "'>" + fieldText + "</a>";
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

                    ArrayList<FacetDateDTO> listFacetDate = (ArrayList<FacetDateDTO>) request.getAttribute("ListFacetDate");
                    if (listFacetDate != null) {
                        facetD += "<tr><td><b>Facet: last_update</b>";
                        facetD += "<br>";
                        if (listFacetDate.size() > 0) {
                            for (int i = 0; i < listFacetDate.size(); i++) {

                                String fieldText = listFacetDate.get(i).getDateTime();
                                facetD += "<a href = 'SearchMusicController?type=2&KeySearch=" + strQuery + "&FacetName=" + "last_update" + "&FacetValue=" + fieldText + "'>" + fieldText + "</a>";
                                facetD += " (" + listFacetDate.get(i).getCount() + ")";
                                facetD += "<br>";
                            }
                        } else {
                            facetD += "Không tìm ra Facet<br>";
                        }
                        facetD += "</td></tr>";
                    }
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
                                        <%@ include file="template/banner_Nhac.jsp"%>
                                        <!-- end banner      !-->
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr><td height="20" colspan="2" align="center" valign="bottom"><div align="center" class="nav"></div></td></tr>
                    <tr>
                        <td width="200" height="33" valign="top">

                            <%@include file="template/login.jsp" %>
                            <% out.print(facet);%>
                            <% out.print(facetD);%>
                            <table id="tbTopSearch">
                            </table>
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
                    <tr><td height="50"></td></tr>
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




