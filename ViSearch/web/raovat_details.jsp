<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.apache.solr.client.solrj.SolrQuery"%>
<%@page import="org.apache.solr.client.solrj.SolrServer"%>
<%@page import="org.apache.solr.client.solrj.impl.CommonsHttpSolrServer"%>
<%@page import="org.apache.solr.common.SolrDocument"%>
<%@page import="org.apache.solr.common.SolrDocumentList"%>
<%@page import="org.apache.solr.common.SolrInputDocument"%>
<%@page import="org.apache.solr.client.solrj.response.QueryResponse"%>
<%@page import="java.util.*, java.net.*,java.util.Map, org.apache.commons.httpclient.util.*, java.text.*"%>
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ViSearch - Rao vặt</title>
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
                                //alert(priority);
                                //alert(keySearch);
                                var Url = "BookmarkController?NameBookmark=" + nameBookmark;
                                Url += "&DocID=" + docID;
                                Url += "&SearchType=2";
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
                $("#tbTopSearch").load("TopSearch?SearchType=2");
            });
        </script>
        <script language="javascript">
            function CheckInput()
            {
                var keysearch = document.getElementById('txtSearch').value;
                if(keysearch == "")
                    return;
                else
                {
                    var url = "SearchRaoVatController?type=0&KeySearch=";
                    //url += keysearch.value;
                    url += encodeURIComponent(keysearch);
                    //alert(url);
                    window.location = url;
                }
            }
        </script>
    </head>

    <body>

        <%
        String id = "";
                    if (request.getQueryString() != null) {
                        String currentPage = "/DetailRaoVatController?";
                        currentPage += request.getQueryString().toString();
                        session.setAttribute("CurrentPage", currentPage);
                    }
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

                    StringBuffer result = new StringBuffer();
                    if (request.getAttribute("Docs") != null) {
                        listdocs = (SolrDocumentList) request.getAttribute("Docs");
                        for (int i = 0; i < listdocs.size(); i++) {
                            // Lay noi dung cua moi field
                            String title = "";
                            if (listdocs.get(i).getFirstValue("rv_title") != null) {
                                title = (listdocs.get(i).getFirstValue("rv_title")).toString();
                            }
                            String body = "";
                            if ((listdocs.get(i).getFirstValue("rv_body")) != null) {
                                body = (listdocs.get(i).getFirstValue("rv_body")).toString();
                            }
                            
                            if (listdocs.get(i).getFieldValue("id") != null) {
                                id = (listdocs.get(i).getFieldValue("id")).toString();
                            }
                            String link = "";
                            if (listdocs.get(i).getFirstValue("url") != null) {
                                link = (listdocs.get(i).getFirstValue("url")).toString();
                            }
                            String price = "";
                            String category = (listdocs.get(i).getFieldValue("category")).toString();
                            String location = "";
                            String contact = "";
                            Date last_update = (Date) (listdocs.get(i).getFieldValue("last_update"));
                            Calendar cl = Calendar.getInstance();
                            cl.setTime(last_update);
                            SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

                            String url = title;
                            String photo = "images/Noimage.jpg";

                            if (listdocs.get(i).getFieldValue("contact") != null) {
                                contact = (listdocs.get(i).getFieldValue("contact")).toString();
                            }

                            if (listdocs.get(i).getFieldValue("photo") != null && !listdocs.get(i).getFieldValue("photo").toString().equals("")) {
                                photo = (listdocs.get(i).getFieldValue("photo")).toString();
                            }
                            if (listdocs.get(i).getFieldValue("price") != null) {
                                price = (listdocs.get(i).getFieldValue("price")).toString();
                                if (price.equals("")) {
                                    price = "Call";
                                }
                            }
                            if (listdocs.get(i).getFieldValue("location") != null) {
                                location = (listdocs.get(i).getFieldValue("location")).toString();
                            }

                            //  url = "<div class=\"title_content\" id='divtop'>" + title + "</div>";
                            //result.append(url);
                            //result.append("<div id='divleft'>");

                            result.append("<div class=\"mycode\"><table class=\"html4strict\">");

                            result.append("<tr><td>");
                            result.append("<div class=\"head\">" + title + "</div>");
                            result.append("</td></tr>");

                            if (contact != null && contact.trim() != "") {
                                result.append("<tr><td>" + "Thể loại: " + "<a href = 'SearchRaoVatController?type=2&KeySearch=category:\"" + URIUtil.encodePath(category) + "\"'>" + category + "</a></td></tr>");
                            }
                            if (location != null && location.trim() != "") {
                                result.append("<tr><td style=\"font-size:11px\">" + "Nơi rao: " + "<a href = 'SearchRaoVatController?type=2&KeySearch=location:" + URIUtil.encodePath(location) + "'>" + location + "</a></td></tr>");
                            }

                            result.append("<tr>");
                            result.append("<td style=\"font-size:11px\">Link bài viết: <a href='" + link + "' target='_blank'>" + link + "</a></td>");
                            result.append("</tr>");
                            result.append("<tr><td style=\"font-size:11px\">" + "Giá: " + price + "</td></tr>");
                            result.append("<tr><td style=\"font-size:11px\">" + "Ngày cập nhật : " + sf.format(last_update) + "</td></tr>");
                            result.append("<tr><td>&nbsp;</td></tr>");


                            photo = "<div id='divright'><img src='" + photo + "' alt='No image' width='200'/><br/>";
                            //   if (session.getAttribute("Member") != null) {
                            //       photo += "<span id='Bookmark'>"
                            ///               + "<input id='hdIdValue' type='hidden' value='" + id + "'/>"
                            //               + "<input id='btBookmark' type='button' value='Thêm vào bookmark'/></span>";
                            //    }
                            photo += "</div>";
                            //   result.append(photo);
                            //result.append("<div id='divbottom'>" + body + "</div>");

                            result.append("<tr><td>");
                            result.append("<span class=\"kw2\">" + photo + "</span>");
                            result.append("</td></tr>");


                            result.append("<tr><td>");
                            result.append("<span class=\"kw2\">" + body + "</span>");
                            result.append("</td></tr>");

                            result.append("<tr><td><div class=\"foot\">Bài viết này được tự động quét trên mạng. Chúng tôi không chịu trách nhiệm về nội dung bài viết này.</div></td></tr>");
                            result.append("</table></div>");
                        }
                    }

                    //get SolrDocumentList
%>
        <%
                    StringBuffer result2 = new StringBuffer();
                    result2.append("</div><table>");
                    result2.append("<tr><td>");
                    if (session.getAttribute("Member") != null) {
                        String bm = "<span id='Bookmark'>"
                                + "<input id='hdIdValue' type='hidden' value='" + id + "'/>"
                                + "<input id='btBookmark' type='button' value='Thêm vào bookmark'/></span>";
                    result2.append(bm);
                    }
                    
                    result2.append("</td></tr>");
                    result2.append("</table></div>");

                    //get Cùng chuyên mục Category
                    SolrDocumentList listdocs2 = new SolrDocumentList();


                    
                    if (request.getAttribute("Docs_MoreLikeThis") != null) {
                        listdocs2 = (SolrDocumentList) request.getAttribute("Docs_MoreLikeThis");

                        result2.append("<div style=\"font-size:13px\">");
                        result2.append("<div class=\"title_content\">Một số bài viết liên quan</div>");
                        for (int i = 0; i < listdocs2.size(); i++) {
                            // Lay noi dung cua moi field
                            String title = "";
                            if ((listdocs2.get(i).getFieldValue("rv_title")).toString() != null) {
                                title = listdocs2.get(i).getFirstValue("rv_title").toString();
                            }

                            id = (listdocs2.get(i).getFieldValue("id")).toString();
                            String url;

                            url = "<li><b><a href=\"DetailRaoVatController?id=" + id + "&KeySearch=" + strQuery + "\">" + title + "</a></b></li>";
                            result2.append(url);
                        }
                        result2.append("</div>");
                    }
                    //end Cùng chuyên mục Category
%>
        <%
                    // Get Facet

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
                                    </td></tr>
                                <tr>
                                    <td width="974" valign="top">
                                        <%@include file="template/banner_RaoVat.jsp" %>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr><td height="20" colspan="2" align="center" valign="bottom"><div align="center" class="nav"></div></td></tr>
                    <tr>
                        <td width="200" height="33" valign="top">

                            <%  //out.print(facet);%>
                            <div class="mnu">Tìm kiếm nhiều</div>
                            <table id="tbTopSearch">
                            </table>
                        </td>
                        <td width="627" rowspan="2" valign="top">

                            <!--
                            <table>

                                <tr><td id="result_search">thong ke</td></tr><tr></tr>
                            </table>
                            -->
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

                            <div  valign="top" id="content">
                                <%
                                            out.print(result);
                                            if (result2 != null) {
                                                out.print("<hr style=\"color:#f00; height:5px; background-color: #f00; margin-top:40px\" />");
                                                
                                                out.print(result2.toString());
                                            }
                                %>
                            </div>
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



