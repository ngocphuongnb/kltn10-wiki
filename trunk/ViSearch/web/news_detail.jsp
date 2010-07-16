<%--
    Document   : image_detail
    Created on : Jun 6, 2010, 4:56:11 PM
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
        <title>ViSearch - Tin tức</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <link type="text/css" href="css/visearchStyle.css" rel="stylesheet"/>
        <script type="text/javascript">
            $(function() {
                $("#datepicker").datepicker({dateFormat: 'dd-mm-yy'});
                $("#dialog").dialog("destroy");
                var tips = $(".validateTips");
                var name = $("#nameBookmark");

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

                $("#addBookmark").dialog({
                    autoOpen: false,
                    height: 250,
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
                                var docID = $("#hdIdValue").attr("value");
                                var keySearch = $("#hfKeySearch").attr("value");
                                var nameBookmark = $("#nameBookmark").attr("value");
                                var priority = $("#addBookmark input:radio:checked").val();
                                //alert(priority);
                                //alert(keySearch);
                                var Url = "BookmarkController?NameBookmark=" + nameBookmark;
                                Url += "&DocID=" + docID;
                                Url += "&SearchType=6";
                                Url += "&Priority=" + priority;
                                $("#Bookmark").load(encodeURI(Url));
                                $(this).dialog('close');
                            }
                        }
                    },close: function() {
                        name.val('').removeClass('ui-state-error');
                    }
                });
                $('#btBookmark')
                .button()
                .click(function() {
                    $('#addBookmark').dialog('open');
                });
            });
            $(document).ready(function(){
                $("#tbTopSearch").load("TopSearch?SearchType=6");
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
                    var url = "SearchNewsController?type=0&sp=1&KeySearch=";
                    url += encodeURIComponent(keysearch);
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
                    // end get String query
%>
        <%
                    //get SolrDocumentList
                    SolrDocumentList listdocs = new SolrDocumentList();
                    Map<String, Map<String, List<String>>> highLight = null;

                    StringBuffer result = new StringBuffer();
                    String search_stats = "";
                    String QTime;
                    if (request.getAttribute("QTime") != null) {
                        QTime = request.getAttribute("QTime").toString();

                        if (request.getAttribute("Docs") != null) {
                            listdocs = (SolrDocumentList) request.getAttribute("Docs");

                            if (request.getAttribute("Collation") != null) {
                                String sCollation = (String) request.getAttribute("Collation");
                                result.append("<p><font color=\"#CC3333\" size=\"+2\">Có phải bạn muốn tìm: <b><a href=\"SearchImageController?type=0&KeySearch=" + sCollation + "\">" + sCollation + "</a></b></font></p>");
                            }

                            for (int i = 0; i < listdocs.size(); i++) {
                                result.append("<table style=\"font-size:13px\">");

                                // Lay noi dung cua moi field
                                String title = (listdocs.get(i).getFirstValue("title")).toString();
                                String body = (listdocs.get(i).getFirstValue("body")).toString();
                                String url = (listdocs.get(i).getFirstValue("url")).toString();
                                Date created = (Date) (listdocs.get(i).getFieldValue("last_update"));
                                String photo = (listdocs.get(i).getFirstValue("photo")).toString();
                                String[] arrPhoto = photo.split("\n");
                                if (arrPhoto.length > 1) {
                                    photo = arrPhoto[0];
                                }
                                String id = (listdocs.get(i).getFieldValue("id")).toString();
                                String title_hl = title;

                                if (request.getAttribute("HighLight") != null) {
                                    highLight = (Map<String, Map<String, List<String>>>) request.getAttribute("HighLight");
                                    List<String> highlightTitle = highLight.get(id).get("title");
                                    List<String> highlightBody = highLight.get(id).get("body");
                                    if (highlightTitle != null && !highlightTitle.isEmpty()) {
                                        title_hl = highlightTitle.get(0);
                                    }
                                    if (highlightBody != null && !highlightBody.isEmpty()) {
                                        body = highlightBody.get(0);
                                    }
                                }

                                result.append("<tr>");
                                result.append("<td><b><a href=\"DetailNewsController?id=" + id + "&KeySearch=" + strQuery + "\">" + title_hl + "</a><b></td>");
                                result.append("</tr>");

                                result.append("<tr>");
                                result.append("<td><b>Link bài viết: </b><a href='"+url+"' target='_blank'>" + url + "</a></td>");
                                result.append("</tr>");

                                result.append("<tr>");
                                result.append("<td><img src=\"" + photo + "\" width=\"150\" align=\"left\" /></td>");
                                result.append("</tr>");

                                result.append("<tr>");
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                                result.append("<td><b>Ngày cập nhật: </b> " + sdf.format(created) + "</td>");
                                result.append("</tr>");

                                result.append("<tr>");
                                result.append("<td>" + body + "</td>");
                                result.append("</tr>");

                                if (session.getAttribute("Member") != null) {
                                    result.append("<tr><td><span id='Bookmark'><input id='hdIdValue' type='hidden' value='" + id + "'>");
                                    result.append("<input id='btBookmark' type='button' value='Thêm vào bookmark'></span></td></tr>");
                                }
                                result.append("<tr><td>&nbsp;</td></tr>");
                                result.append("</table>");
                            }
                        }
                    }
                    //get SolrDocumentList
%>
        <%
                    // Get Facet
                    StringBuffer facet = new StringBuffer();
                    List<FacetField> listFacet = (List<FacetField>) request.getAttribute("ListFacet");
                    if (listFacet != null) {
                        facet.append("<div class=\"mnu\">Bộ lọc</div>");
                        for (int i = 0; i < listFacet.size(); i++) {
                            facet.append("<table id=\"table_left\" width=\"100%\" border=\"0\">");
                            facet.append("<tr>");
                            facet.append("<td>");
                            String fieldName = listFacet.get(i).getName();
                            if (fieldName.equals("category")) {
                                facet.append("<b>Chuyên mục</b>");
                            }
                            facet.append("<br>");
                            List<FacetField.Count> listCount = listFacet.get(i).getValues();
                            if (listCount != null) {
                                for (int j = 0; j < listCount.size(); j++) {
                                    String fieldText = listCount.get(j).getName();
                                    facet.append("<a href = 'SearchImageController?type=2&KeySearch=" + strQuery + "&FacetName=" + fieldName + "&FacetValue=" + fieldText + "'>" + fieldText + "</a>");
                                    facet.append(" (" + listCount.get(j).getCount() + ")");
                                    facet.append("<br>");
                                }
                            } else {
                                facet.append("Không tìm ra dữ liệu<br>");
                            }
                            facet.append("</td></tr>");
                            facet.append("</table>");
                        }
                    }
                    // End get Facet
        %>
        <%
                    //get Cùng chuyên mục Category
                    SolrDocumentList listdocs2 = new SolrDocumentList();
                    StringBuffer result2 = new StringBuffer();
                    if (request.getAttribute("Docs_MoreLikeThis") != null) {
                        listdocs2 = (SolrDocumentList) request.getAttribute("Docs_MoreLikeThis");

                        result2.append("<div style=\"font-size:13px\">");
                        result2.append("<hr>");
                        result2.append("Một số bài viết tương tự: <br>");
                        for (int i = 0; i < listdocs2.size(); i++) {

                            // Lay noi dung cua moi field
                            String id = (listdocs2.get(i).getFieldValue("id")).toString();
                            String title = (listdocs2.get(i).getFieldValue("title")).toString();
                            result2.append("<li><b><a href=\"DetailNewsController?id=" + id + "&KeySearch=" + strQuery + "\">" + title + "</a></li>");
                        }
                        result2.append("</div>");
                    }
                    //end Cùng chuyên mục Category
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
                    <tr><td height="20" colspan="2" align="center" valign="bottom"><div align="center" class="nav"></div></td></tr>
                    <tr>
                        <td width="200" height="33" valign="top">
                            <div class="subtable">

                                <% if (request.getAttribute("Docs") != null) {
                                                // out.print(facet);
                                            }%>
                                <div class="mnu">Tìm kiếm nhiều</div>
                                <table id="tbTopSearch">
                                </table>
                            </div>
                        </td>
                        <td width="627" rowspan="2" valign="top">

                            <div id="addBookmark" title="Thêm bookmark">
                                <p class="validateTips"/>
                                <form>
                                    <fieldset>
                                        <label for="name">Tên bookmark</label>
                                        <input type="text" name="name" id="nameBookmark" class="text ui-widget-content ui-corner-all" />
                                        <input type="radio" name="priority" id="private" value="0"/>
                                        <label for="private">Riêng tư</label>
                                        <input type="radio" name="priority" id="public" value="1" checked/>
                                        <label for="public">Chia sẻ</label>
                                    </fieldset>
                                </form>
                            </div>

                            <table>

                                <tr><td id="result_search"><% out.print(search_stats);%></td></tr><tr></tr>
                            </table>
                            <table id="table_right" width="100%" cellpadding="0" cellspacing="0">


                                <tr>
                                    <td  valign="top" id="content">
                                        <% out.print(result);%>
                                        <% out.print(result2);%>

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





