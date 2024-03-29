<%-- 
    Document   : image_detail
    Created on : Jun 6, 2010, 4:56:11 PM
    Author     : tuandom
--%>

<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.apache.solr.client.solrj.SolrQuery"%>
<%@page import="org.apache.solr.client.solrj.SolrServer"%>
<%@page import="org.apache.solr.client.solrj.impl.CommonsHttpSolrServer"%>
<%@page import="org.apache.solr.common.SolrDocument"%>
<%@page import="org.apache.solr.common.SolrDocumentList"%>
<%@page import="org.apache.solr.common.SolrInputDocument"%>
<%@page import="org.apache.solr.client.solrj.response.QueryResponse"%>
<%@page import="java.util.*, java.net.*,java.util.Map, org.apache.commons.httpclient.util.*"%>
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ViSearch - Hình ảnh</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/clock.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <link type="text/css" href="css/visearchStyle.css" rel="stylesheet"/>
         <script type="text/javascript">
            $(function() {
                $("#datepicker").datepicker({dateFormat: 'dd-mm-yy'});

                $("#dialog").dialog("destroy");

                var tips = $(".validateTips");
                var name = $("#nameBookmark");

                //alert("priority.val()");
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
                                var Url = "BookmarkController?NameBookmark=" + nameBookmark;
                                Url += "&DocID=" + docID;
                                Url += "&SearchType=4";
                                Url += "&Priority=" + priority;
                                alert("Đã thêm vào Bookmark");
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
                $("#tbTopSearch").load("TopSearch?SearchType=4");
            });
        </script>
        <script  language="javascript">
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
                    var url = "SearchImageController?type=0&sp=1&KeySearch=";
                    //url += keysearch.value;
                    url += encodeURIComponent(keysearch);
                    //alert(url);
                    window.location = url;
                }
            }
            function showPVTC(){
                document.getElementById("divPVTC").className="display";
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
                                result += "<table style=\"font-size:13px\">";

                                // Lay noi dung cua moi field
                                String title = (listdocs.get(i).getFirstValue("site_title")).toString();
                                String body = (listdocs.get(i).getFirstValue("site_body")).toString();
                                String id = (listdocs.get(i).getFieldValue("id")).toString();
                                String url = "";
                           //     if (!listdocs.get(i).getFieldValue("url_local").equals("")&&listdocs.get(i).getFieldValue("url_local") != null) {
                             //       File file = new File("webapps\\ViSearch\\" + listdocs.get(i).getFieldValue("url_local").toString());
                             //       if (file.exists()) {
                             //           url = (listdocs.get(i).getFieldValue("url_local")).toString();
				//						url = url.replace('\\', '/');
                               //     }
                              //  } else {
                                    url = (listdocs.get(i).getFieldValue("url")).toString();
                             //   }
                                String website = (listdocs.get(i).getFieldValue("website")).toString();
                                String width = (listdocs.get(i).getFieldValue("width")).toString();
                                String height = (listdocs.get(i).getFieldValue("height")).toString();
                                String size = (listdocs.get(i).getFieldValue("size")).toString();
                                String fileType = (listdocs.get(i).getFieldValue("fileType")).toString();
                                String title_hl = title;

                                if (request.getAttribute("HighLight") != null) {
                                    highLight = (Map<String, Map<String, List<String>>>) request.getAttribute("HighLight");
                                    List<String> highlightTitle = highLight.get(id).get("site_title");
                                    List<String> highlightBody = highLight.get(id).get("site_body");
                                    if (highlightTitle != null && !highlightTitle.isEmpty()) {
                                        title_hl = highlightTitle.get(0);
                                    }
                                    if (highlightBody != null && !highlightBody.isEmpty()) {
                                        body = highlightBody.get(0);
                                    }
                                }

                                result += "<tr>";
                                result += "<td><a href='" + url + "' target=\"_blank\"><img src=\"" + url + "\" width=\"400\" align=\"left\" /></a></td>";
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td>Thông tin hình ảnh:</b>";
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td>Kích thước: " + width + " x " + height;
                                result += "</tr>";

                                if (session.getAttribute("Member") != null) {
                                    result += "<input id='hdIdValue' type='hidden' value='" + id + "'>";
                                    result += "<tr><td><span id='Bookmark'><input id='btBookmark' type='button' value='Thêm vào bookmark'></span></td></tr>";
                                }
                                result += "<tr>";
                                result += "<td>Loại: " + size + "Kb - " + fileType + "</td>";
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td><b><a href='" + website + "' target=\"_blank\">" + title_hl + "</a></b></td>";
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td>" + body + "</td>";
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td><a href='http://" + website + "' target=\"_blank\">Tới trang web</a>&nbsp;|&nbsp;";
                                result += "<a href='" + url + "' target=\"_blank\">Hình ảnh đầy đủ</a>&nbsp;|&nbsp;";
                                result += "<a href=\"SearchImageController?type=1&KeySearch=" + title.replaceAll("\\<.*?\\>", "") + "\">Trang tương tự...</a>";
                                result += "</td>";
                                result += "</tr>";


                                result += "<tr><td>&nbsp;</td></tr>";
                                result += "</table>";
                            }

                        }

                    }
                    //get SolrDocumentList
        %>
        
        <%
                    //get Cùng chuyên mục Category
                    SolrDocumentList listdocs2 = new SolrDocumentList();
                    String result2 = "";

                    if (request.getAttribute("Docs_MoreLikeThis") != null) {
                        listdocs2 = (SolrDocumentList) request.getAttribute("Docs_MoreLikeThis");

                        result2 += "<div style=\"font-size:13px\">";
                        result2 += "Một số hình ảnh tương tự: <br>";
                        for (int i = 0; i < listdocs2.size(); i++) {

                            // Lay noi dung cua moi field
                            String id = (listdocs2.get(i).getFieldValue("id")).toString();
                            String url = (listdocs2.get(i).getFieldValue("url")).toString();

                            result2 += "<b><a href=\"DetailImageController?id=" + id + "&KeySearch=" + strQuery + "\"><img src=\"" + url + "\" width=\"150\" align=\"left\" /></a></li>";
                        }
                        result2 += "</div>";
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





