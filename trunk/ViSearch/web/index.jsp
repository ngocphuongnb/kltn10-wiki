<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
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
<%@page import="org.me.dto.FacetDateDTO" session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ViSearch - Wikipedia</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <link type="text/css" href="css/visearchStyle.css" rel="stylesheet"/>

        <script type="text/javascript">
            $(function() {
                $("#accordion").accordion({
                    collapsible: true,
                    header: 'div.Quickview',
                    autoHeight: false,
                    active: false
                });

                $("#accordion").accordion({
                    change: function() {
                        var active = $("#accordion" ).accordion( "option", "active" );
                        var divi = "#div" + active;
                        var hfID = "#hdIdValue" + active;
                        var id = $(hfID).val();
                        $.ajax({
                            type: "POST",
                            url: "QuickViewController",
                            data: "id=" + id,
                            success: function(html){
                                $(divi).html(html);
                            }
                        });
                    }
                });
                //getter
                //$( "#accordion" ).accordion( "option", "active", 0 );
                //$("div", "#accordion").click(function() {
                //});
            });
        </script>

        <script language="javascript">
            $(function(){
                setInterval(
                $.ajax({
                    type: "POST",
                    url: "TopSearch",
                    cache: false,
                    data: "SearchType=1",
                    success: function(html){
                        $("#tbTopSearch").append(html);
                    }
                }), 10000);

            });

            function CheckInput()
            {
                var keysearch = $("#txtSearch");
                var sortedtype = $("#slSortedType");
                if(keysearch.val() == "")
                    return;
                else
                {
                    var url = "SearchWikiController?type=0&sp=1&KeySearch=";
                    url += encodeURIComponent(keysearch.val());
                    url += "&SortedType=" + sortedtype.val();
                    window.location = url;
                }
            }

            function post_to_url(path, params) {
                var form = document.createElement("form");
                form.setAttribute("method", "post");
                form.setAttribute("action", path);

                for(var key in params) {
                    var hiddenField = document.createElement("input");
                    hiddenField.setAttribute("type", "hidden");
                    hiddenField.setAttribute("name", key);
                    hiddenField.setAttribute("value", params[key]);

                    form.appendChild(hiddenField);
                }

                document.body.appendChild(form);    // Not entirely sure if this is necessary
                form.submit();
            }
            function setText()
            {
                var keysearch = document.getElementById('txtSearch').value;
                if(keysearch=="")
                    document.getElementById('txtSearch').focus();
            }
            function ClickDetail(link, id)
            {
                var keysearch = $('#hfKeySearch');
                var url = "DetailWikiController?";
                url += "&url=" + encodeURIComponent(link);
                url += "&KeySearch=" + encodeURIComponent(keysearch.val()),
                url += "&id=" + id;
                url += "&t=" + Math.random();
                window.open(url, "ViWiKi", "toolbar=yes, location=yes,directories=yes,status=yes,menubar=yes,scrollbars=yes,copyhistory=yes, resizable=yes");
            }

            function MoreLikeThis(keysearch)
            {
                var url = "SearchWikiController?";
                url += "&type=1";
                url += "&KeySearch=" + keysearch;
                url += "&t="+Math.random();
                window.location = url;
            }

            
            function SeachPVDC(strQuery){
                var batdau = document.getElementById("divPVTC_BD").value;
                var  kethuc = document.getElementById("divPVTC_KT").value;
                strQuery =  encodeURIComponent(strQuery);
                var url = "SearchWikiController?type=3&KeySearch=" + strQuery + "&FacetName=timestamp&sd="+batdau+"&ed="+kethuc;
                window.location = url;
            }
            function showPVTC(){
                document.getElementById("divPVTC").className="display";
            }
        </script>

        <script language="javascript">
            function Sort(type){
                var sortedtype = document.getElementById('slSortedType').value;
                //alert(sortedtype);
                var keysearch = document.getElementById('hfKeySearch').value;
                //alert(keysearch);
                if(keysearch == "")
                    return;
                else
                {
                    var url = "SearchWikiController?sp=1&KeySearch=";
                    url += encodeURIComponent(keysearch);
                    url += "&SortedType=" + sortedtype;
                    url += "&type=" + type;
                    window.location = url;
                }
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
                    int sortedType = 0;
                    if (request.getAttribute("SortedType") != null) {
                        sortedType = Integer.parseInt(request.getAttribute("SortedType").toString());
                    }
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
                    String addBM = "";
                    if (request.getAttribute("QTime") != null) {
                        QTime = request.getAttribute("QTime").toString();
                        if (request.getAttribute("Docs") != null) {
                            listdocs = (SolrDocumentList) request.getAttribute("Docs");

                            search_stats = String.format("Có %d kết quả (%s giây)", listdocs.getNumFound(), QTime);
                            // Phan trang
                            numrow = Integer.parseInt(request.getAttribute("NumRow").toString());
                            numpage = Integer.parseInt(request.getAttribute("NumPage").toString());
                            strpaging = (String) request.getAttribute("Pagging");
                        } else {
                            result += strpaging + "<b>Không có kết quả nào</b><br/>";
                        }
                        // result += "Số kết quả tìm được là: " + numrow + "<br/>";
                        if (numpage > 0) {
                            result += "Tổng số trang là: " + numpage + "<br/>";
                        }
                        result += strpaging + "<br/><br/>";
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
                            String fieldName = listFacet.get(i).getName();
                            facet += "<b>Facet: " + fieldName + "</b>";
                            facet += "<br>";
                            List<FacetField.Count> listCount = listFacet.get(i).getValues();
                            if (listCount != null) {
                                for (int j = 0; j < listCount.size(); j++) {
                                    String fieldText = listCount.get(j).getName();
                                    facet += "<a href = 'SearchWikiController?type=2&KeySearch=" + strQuery + "&FacetName=" + fieldName + "&FacetValue=" + fieldText + "'>" + fieldText + "</a>";
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

                    Calendar cl = Calendar.getInstance();

                    String str24hqua = cl.get(Calendar.YEAR) + "-" + (cl.get(Calendar.MONTH) + 1) + "-" + (cl.get(Calendar.DAY_OF_MONTH) - 1)
                            + "T" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND) + "." + cl.get(Calendar.MILLISECOND) + "Z";

                    cl.add(Calendar.DATE, -7);
                    String str1tuanqua = cl.get(Calendar.YEAR) + "-" + (cl.get(Calendar.MONTH) + 1) + "-" + cl.get(Calendar.DAY_OF_MONTH)
                            + "T" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND) + "." + cl.get(Calendar.MILLISECOND) + "Z";

                    cl.add(Calendar.DATE, +7);
                    String str1thangqua = cl.get(Calendar.YEAR) + "-" + cl.get(Calendar.MONTH) + "-" + cl.get(Calendar.DAY_OF_MONTH)
                            + "T" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND) + "." + cl.get(Calendar.MILLISECOND) + "Z";


                    facetD += "<tr><td>";
                    facetD += "<a href = 'SearchWikiController?type=2&KeySearch=" + strQuery + "&FacetName=timestamp&FacetValue=" + URLEncoder.encode("[" + str24hqua + " TO NOW]", "UTF-8") + "'>" + "24 giờ qua" + "</a>";
                    facetD += "</td></tr>";

                    facetD += "<tr><td>";
                    facetD += "<a href = 'SearchWikiController?type=2&KeySearch=" + strQuery + "&FacetName=timestamp&FacetValue=" + URLEncoder.encode("[" + str1tuanqua + " TO NOW]", "UTF-8") + "'>" + "1 tuần trước" + "</a>";
                    facetD += "</td></tr>";

                    facetD += "<tr><td>";
                    facetD += "<a href = 'SearchWikiController?type=2&KeySearch=" + strQuery + "&FacetName=timestamp&FacetValue=" + URLEncoder.encode("[" + str1thangqua + " TO NOW]", "UTF-8") + "'>" + "1 tháng trước" + "</a>";
                    facetD += "</td></tr>";

                    facetD += "<tr><td><a style=\"cursor:pointer\" onclick=\"showPVTC();\" />Phạm vi tùy chỉnh</a></td></tr>";

                    facetD += "<tr><td>";
                    facetD += "<div id=\"divPVTC\" class=\"hidden\">";
                    facetD += "<div style=\"float:left\"> Bắt đầu: </div><div style=\"float:right\"><input type=\"text\" class=\"textForm\" onfocus=\"this.className='textForm_Hover';\" onblur=\"this.className='textForm';\" id=\"divPVTC_BD\" /></div>";
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
                            <span><%@include file="template/sortedtype.jsp"%></span>
                        </td>
                    </tr>
                    <tr>
                        <td height="20" colspan="2" align="center" valign="bottom">
                            <div align="center" class="nav"></div>
                        </td>
                    </tr>
                    <tr>
                        <td width="200" height="33" valign="top">
                            <script type="text/javascript">
                                $(function(){
                                    $("#divPVTC_KT").datepicker({dateFormat: 'dd-mm-yy'});
                                    $("#divPVTC_BD").datepicker({dateFormat: 'dd-mm-yy'});
                                });
                            </script>
                            <div class="subtable">
                                <% if (request.getAttribute("Docs") != null) {
                                                out.print(facet);
                                                out.print("<div  class=\"mnu\">Ngày cập nhật</div>" + facetD);
                                            }
                                %>

                                <div class="mnu">Tìm kiếm nhiều nhất trong tuần</div>
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
                                        <%
                                                    if (request.getAttribute("Collation") != null) {
                                                        String sCollation = (String) request.getAttribute("Collation");
                                                        out.print("<p><font color=\"#CC3333\" size=\"+2\">Có phải bạn muốn tìm: <b><a href=\"SearchWikiController?type=0&KeySearch=" + sCollation + "\">" + sCollation + "</a></b></font></p>");
                                                    }

                                                    out.print("<div id=\"accordion\">");

                                                    for (int i = 0; i < listdocs.size(); i++) {
                                                        out.print("<table style=\"font-size:13px\" width=\"100%\">");

                                                        // Lay noi dung cua moi field
                                                        String title = (listdocs.get(i).getFirstValue("wk_title")).toString();
                                                        String text = (listdocs.get(i).getFieldValue("wk_text")).toString();
                                                        //String text_raw = (listdocs.get(i).getFieldValue("wk_text_raw")).toString();
                                                        String id = (listdocs.get(i).getFieldValue("id")).toString();
                                                        //int id_link = Integer.parseInt(listdocs.get(i).getFieldValue("id_link").toString());
                                                        String url = title.replace(' ', '_');
                                                        String title_hl = title;
                                                        Date timestamp = (Date) (listdocs.get(i).getFieldValue("timestamp"));

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

                                                        url = "<td><h2><a href=\"javascript:ClickDetail('" + url + "','" + id + "')\">" + title_hl + "</a></h2></td>";
                                                        out.print("<tr>");
                                                        out.print(url);
                                                        out.print("</tr>");

                                                        out.print("<tr>");
                                                        out.print("<td>" + text + "</td>");
                                                        out.print("</tr>");

                                                        out.print("<tr>");
                                                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                                                        out.print("<td><b>Ngày cập nhật:</b> " + sdf.format(timestamp) + "</td>");
                                                        out.print("</tr>");

                                                        out.print("<tr>");
                                                        out.print("<td>");
                                                        out.print("<a href=\"javascript:MoreLikeThis('" + URLEncoder.encode(title, "UTF-8") + "');\">Trang tương tự...</a>");
                                                        out.print("</td>");

                                                        //  START Bookmark
                                                        String btBookmark = "btBookmark" + i;
                                                        String spanBookmark = "spanBookmark" + i;
                                                        String addBookmark = "addBookmark" + i;
                                                        String nameBookmark = "nameBookmark" + i;
                                                        String hdIdValue = "hdIdValue" + i;

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
                                                                Url += "&SearchType=1";
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
                                                $("#tbTopSearch").load("TopSearch?SearchType=1");
                                            });
                                        </script>
                                        <%


                                                        String spBM = "";
                                                        if (session.getAttribute("Member") != null) {
                                                            spBM += "<tr><td><span id=\"" + spanBookmark + "\">"
                                                                    + "<input id=\"" + btBookmark + "\" type='button' value='Thêm vào bookmark'/></span></td></tr>";
                                                        }
                                                        out.print(spBM);

                                                        addBM += "<div id=\"" + addBookmark + "\" title=\"Thêm bookmark\">";
                                                        addBM += "<p class=\"validateTips\"/>";
                                                        addBM += "<form name=\"frmBm" + i + "\">";
                                                        addBM += " <fieldset>";
                                                        addBM += "  <label for=\"name\">Tên bookmark</label>";
                                                        addBM += "  <input type=\"text\" name=\"name\"  ID=\"" + nameBookmark + "\" class=\"text ui-widget-content ui-corner-all\" />";
                                                        addBM += "<input type=\"radio\" name=\"priority\" id=\"private\" value=\"0\"/>";
                                                        addBM += "<label for=\"private\">Riêng tư</label>";
                                                        addBM += "<input type=\"radio\" name=\"priority\" id=\"public\" value=\"1\" checked/>";
                                                        addBM += "<label for=\"public\">Chia sẻ</label>";
                                                        addBM += " </fieldset>";
                                                        addBM += " </form>";
                                                        addBM += "</div>";

                                                        // END Bookmark
                                                        out.print("<input  id=\"" + hdIdValue + "\"  type='hidden' value='" + id + "'/>");
                                                        out.print("</tr>");
                                                        out.print("<tr><td><div class='Quickview' style=\"width:100%\">Xem nhanh</div><div id='div" + i + "'>Đang đọc dữ liệu. Vui lòng chờ...</div></td></tr>");
                                                        out.print("</table><hr/>");
                                                    }

                                                    out.print("</div>");
                                                    out.print(result);%>
                                    </td>
                                </tr>
                            </table>
                            <% out.print(addBM);%>
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


