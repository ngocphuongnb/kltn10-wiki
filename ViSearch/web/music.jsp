
<%--
    Document   : music
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
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ViSearch - Music</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/clock.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <link type="text/css" href="css/visearchStyle.css" rel="stylesheet"/>

        <script language="javascript">
            $(document).ready(function(){
                $("#tbTopSearch").load("TopSearch?SearchType=3");
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
                f = document.getElementById('field').value;
                var sortedtype = document.getElementById('slSortedType').value;
                if(keysearch == "")
                    return;
                else
                {
                    var url = "SearchMusicController?type=0&sp=1&f="+f+"&KeySearch=";
                    url += encodeURIComponent(keysearch);
                    url += "&SortedType=" + sortedtype;
                    window.location = url;
                }
            }
            function Sort(type){
                var sortedtype = document.getElementById('slSortedType').value;
                var keysearch = document.getElementById('hfKeySearch').value;
                if(keysearch == "")
                    return;
                else
                {
                    var url = "SearchMusicController?sp=1&KeySearch=";
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

                    f = document.getElementById('field').value;
                    url += "&f=" + f;
                    url += "&type=" + type;
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
                    String FieldId = "";
                    if (request.getAttribute("KeySearch") != null) {
                        strQuery = (String) request.getAttribute("KeySearch");
                        //strQuery = URLDecoder.decode(strQuery, "UTF-8");
                        strQuery = strQuery.replaceAll("\"", "&quot;");
                    }
                    if (request.getAttribute("FieldId") != null) {
                        FieldId = (String) request.getAttribute("FieldId");
                    }
                    int sortedType = 0;
                    if (request.getAttribute("SortedType") != null) {
                        sortedType = Integer.parseInt(request.getAttribute("SortedType").toString());
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
                    StringBuffer result = new StringBuffer();
                    StringBuffer addBM = new StringBuffer();
                    String QTime;
                    if (request.getAttribute("QTime") != null) {
                        QTime = request.getAttribute("QTime").toString();
                        if (request.getAttribute("Docs") != null) {
                            listdocs = (SolrDocumentList) request.getAttribute("Docs");

                            search_stats = String.format("Có %d kết quả (%s giây)", listdocs.getNumFound(), QTime);
                            if (request.getAttribute("Collation") != null) {
                                String sCollation = (String) request.getAttribute("Collation");
                                result.append("<p><font color=\"#CC3333\" size=\"+2\">Có phải bạn muốn tìm: <b><a href=\"SearchMusicController?type=0&sp=1&f=6&KeySearch=" + sCollation + "\">" + sCollation + "</a></b></font></p>");
                            }

                            for (int i = 0; i < listdocs.size(); i++) {
                                result.append("<table style=\"font-size:13px\">");

                                // Lay noi dung cua moi field
                                String title = (listdocs.get(i).getFirstValue("title")).toString();
                                String url = (listdocs.get(i).getFieldValue("url")).toString();
                                String id = (listdocs.get(i).getFieldValue("id")).toString();
                                String CaSi = (listdocs.get(i).getFieldValue("artist")).toString();
                                String category = (listdocs.get(i).getFieldValue("category")).toString();
                                String album = (listdocs.get(i).getFieldValue("album")).toString();
                                String lyric = (listdocs.get(i).getFieldValue("lyric")).toString();
                                if (lyric == null || lyric.trim().length() == 0) {
                                    lyric = "Đang cập nhật";
                                }
                                String author = (listdocs.get(i).getFieldValue("author")).toString();

                                String title_hl = title;

                                if (request.getAttribute("HighLight") != null) {
                                    highLight = (Map<String, Map<String, List<String>>>) request.getAttribute("HighLight");
                                    List<String> highlightTitle = highLight.get(id).get("title");
                                    if (highlightTitle != null && !highlightTitle.isEmpty()) {
                                        title_hl = highlightTitle.get(0);
                                    }
                                }

                                result.append("<tr>");
                                result.append("<td><b>" + title_hl + "</b></td>");
                                result.append("</tr>");


                                result.append("<tr>");
                                result.append("<td>Ca sĩ: " + "<a href = 'SearchMusicController?type=0&f=3&KeySearch=\"" + URIUtil.encodePath(CaSi) + "\"'>" + CaSi + "</a></td>");
                                result.append("</tr>");

                                result.append("<tr>");
                                result.append("<td>Nhạc sĩ: " + "<a href = 'SearchMusicController?type=0&f=4&KeySearch=\"" + URIUtil.encodePath(author) + "\"'>" + author + "</a></td>");
                                result.append("</tr>");

                                result.append("<tr>");
                                result.append("<td>Thể Loại: " + "<a href = 'SearchMusicController?type=0&f=7&KeySearch=\"" + URIUtil.encodePath(category) + "\"'>" + category + "</a></td>");
                                result.append("</tr>");


                                result.append("<tr>");
                                result.append("<td>Album: " + "<a href = 'SearchMusicController?type=0&f=2&KeySearch=\"" + URIUtil.encodePath(album) + "\"'>" + album + "</a></td>");
                                result.append("</tr>");

                                String mediaId = "MediaPlayer" + i;
                                String lyricId = "Lyric" + i;
                                String BTViewlyricId = "BTViewlyricId" + i;
                                String BTCloselyricId = "BTCloselyricId" + i;
                                String btPlay = "btPlay" + i;
                                String btBookmark = "btBookmark" + i;
                                String spanBookmark = "spanBookmark" + i;
                                String addBookmark = "addBookmark" + i;
                                String nameBookmark = "nameBookmark" + i;
                                String hdIdValue = "hdIdValue" + i;

                                // Start Phan tracking
                                result.append("<span id='Tracking'>");
                                result.append("</span>");
                                result.append("<tr>");
                                result.append("<td><input type=\"button\" id=\"" + btPlay + "\" value=\"Play\" onclick=\"showMediaWindow('" + i + "');\" />");
                                result.append("<input type=\"button\" ID=\"" + BTViewlyricId + "\" value=\"Xem lời nhạc\" onclick=\"showLyric('" + i + "');\" />");
                                result.append("<input type=\"button\" ID=\"" + BTCloselyricId + "\" class=\"hidden\" value=\"Đóng lời nhạc\" onclick=\"hideLyric('" + i + "');\" /></td>");

        %>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#<%=btPlay%>").click(function(){
                    var docID = <%=id%>
                    var keySearch = $("#hfKeySearch").attr("value");
                    var Url = "TrackingController?KeySearch=" + keySearch;
                    Url += "&DocID=" + docID;
                    Url += "&searchType=3";
                    $("#Tracking").load(encodeURI(Url));
                });
            });
        </script>

        <%
                                        //  END Phan tracking

                                        //  START Bookmark
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
                                var priority = $("#<%=addBookmark%> input:radio:checked").val();
                                var Url = "BookmarkController?NameBookmark=" + nameBookmark;
                                Url += "&DocID=" + docID;
                                Url += "&SearchType=3";
                                Url += "&Priority=" + priority;
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
                $("#tbTopSearch").load("TopSearch?SearchType=3");
            });
        </script>
        <%



                                if (session.getAttribute("Member") != null) {
                                    result.append("<tr><td><span id=\"" + spanBookmark + "\"><input  id=\"" + hdIdValue + "\"  type='hidden' value='" + id + "'/>");
                                    result.append("<input id=\"" + btBookmark + "\" type='button' value='Thêm vào bookmark'/></span></td></tr>");
                                }


                                addBM.append("<div id=\"" + addBookmark + "\" title=\"Thêm bookmark\">");
                                addBM.append("<p class=\"validateTips\"/>");
                                addBM.append("<form name=\"frmBm" + i + "\">");
                                addBM.append(" <fieldset>");
                                addBM.append("  <label for=\"name\">Tên bookmark</label>");
                                addBM.append("  <input type=\"text\" name=\"name\"  ID=\"" + nameBookmark + "\" class=\"text ui-widget-content ui-corner-all\" />");
                                addBM.append("<input type=\"radio\" name=\"priority\" id=\"private\" value=\"0\"/>");
                                addBM.append("<label for=\"private\">Riêng tư</label>");
                                addBM.append("<input type=\"radio\" name=\"priority\" id=\"public\" value=\"1\" checked/>");
                                addBM.append("<label for=\"public\">Chia sẻ</label>");
                                addBM.append(" </fieldset>");
                                addBM.append(" </form>");
                                addBM.append("</div>");

                                // END Bookmark

                                result.append("<tr><td><div class=\"hidden\" ID=\"" + mediaId + "\">" + url + "</div></td></tr>");

                                result.append("<tr><td>");
                                result.append("<div class=\"hidden\" ID=\"" + lyricId + "\" style=\"border:thin inset; padding:6px; height:175px; overflow:auto\">");
                                result.append(lyric);
                                result.append("</div>");
                                result.append("</td></tr>");

                                result.append("<tr>");
                                result.append("<td colspan='2'>");
                                result.append("<a href=\"SearchMusicController?type=1&KeySearch=" + URIUtil.encodeAll(title.trim()) + "\">Trang tương tự...</a>");
                                result.append("</td>");

                                result.append("</tr>");
                                result.append("<tr><td>&nbsp;</td></tr>");
                                result.append("</table>");
                            }

                            // Phan trang
                            numrow = Integer.parseInt(request.getAttribute("NumRow").toString());
                            numpage = Integer.parseInt(request.getAttribute("NumPage").toString());
                            strpaging = (String) request.getAttribute("Pagging");
                        }
                        // result.append("Số kết quả tìm được là: " + numrow + "<br/>";
                        result.append("Tổng số trang là: " + numpage + "<br/>");
                        if (numpage > 1) {
                            result.append(strpaging + "<br/>");
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
                            facet += "<td>";
                            String fieldName = listFacet.get(i).getName();
                            if (fieldName.equals("category")) {
                                facet += "<b>Thể loại:</b>";
                            }
                            if (fieldName.equals("author")) {
                                facet += "<b>Nhạc sĩ:</b>";
                            }
                            if (fieldName.equals("artist")) {
                                facet += "<b>Ca sĩ:</b>";
                            }
                            facet += "<br>";
                            List<FacetField.Count> listCount = listFacet.get(i).getValues();
                            if (listCount != null) {
                                for (int j = 0; j < listCount.size(); j++) {
                                    String fieldText = listCount.get(j).getName();

                                    if (fieldText != null && fieldText.equals("") == false && fieldText.equals("\n") == false) {
                                        facet += "<a href = 'SearchMusicController?type=2&KeySearch=" + strQuery + "&f=" + FieldId + "&qf=" + fieldName + "&qv=" + URIUtil.encodePath(fieldText) + "&SortedType=" + sortedType + "'>" + fieldText + "</a>";
                                        facet += " (" + listCount.get(j).getCount() + ")";
                                        facet += "<br>";
                                    }
                                }
                            } else {
                                facet += "Không tìm ra dữ liệu<br>";
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
                                    </td>
                                </tr>
                                <tr>
                                    <td width="974" valign="top">
                                        <!-- banner here !-->
                                        <%@ include file="template/banner_Nhac.jsp"%>
                                        <!-- end banner      !-->
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
                                            }%>
                                <div class="mnu">Tìm kiếm nhiều</div>
                                <table id="tbTopSearch">
                                </table>
                            </div>
                        </td>
                        <td width="627" rowspan="2" valign="top">
                            <% out.print(addBM);%>

                            <table>

                                <tr><td id="result_search"><% out.print(search_stats);%></td></tr><tr></tr>
                                <%  if (request.getParameter("qf") != null) {
                                                out.print("<tr><td id=\"top-header\">");
                                                if (request.getParameter("qf").toString().equals("category")) {
                                                    out.print(">> Thể loại: " + request.getParameter("qv"));
                                                } else if (request.getParameter("qf").toString().equals("artist")) {
                                                    out.print(">> Ca sĩ: " + request.getParameter("qv"));
                                                } else if (request.getParameter("qf").toString().equals("author")) {
                                                    out.print(">> Tác giả: " + request.getParameter("qv"));
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