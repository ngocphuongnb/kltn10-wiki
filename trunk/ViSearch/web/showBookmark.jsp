<%-- 
    Document   : showBookmark
    Created on : Jun 25, 2010, 8:12:53 PM
    Author     : tuandom
--%>

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

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Thông tin Bookmark - ViSearch</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>

        <script type="text/javascript">
            function CheckInput()
            {
                var keysearch = document.getElementById('txtSearchBM').value;
                if(keysearch == "")
                    return;
                else
                {
                    var url = "SearchBookmarkController?sp=1&KeySearch=";
                    url += encodeURIComponent(keysearch);
                    window.location = url;
                }
            }
        </script>
    </head>

    <body>
        <%
                    List<Object[]> lstBm = null;
                    if (request.getAttribute("lstBm") != null) {
                        lstBm = (List<Object[]>) request.getAttribute("lstBm");
                    }
        %>

        <%
                    String currentPage = "/showBookmark.jsp";
                    if (request.getQueryString() != null) {
                        currentPage = "/SearchBookmarkController?";
                        currentPage += request.getQueryString().toString();
                    }
                    session.setAttribute("CurrentPage", currentPage);
                    // get String query
                    String strQuery = "";
                    if (request.getAttribute("KeySearch") != null) {
                        strQuery = (String) request.getAttribute("KeySearch");
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


                    if (request.getAttribute("Docs") != null) {
                        listdocs = (SolrDocumentList) request.getAttribute("Docs");

                        if (request.getAttribute("Collation") != null) {
                            String sCollation = (String) request.getAttribute("Collation");
                            result += "<p><font color=\"#CC3333\" size=\"+2\">Có phải bạn muốn tìm: <b><a href=\"SearchRaoVatController?type=0&KeySearch=" + sCollation + "\">" + sCollation + "</a></b></font></p>";
                        }

                        for (int i = 0; i < listdocs.size(); i++) {
                            result += "<table style=\"font-size:13px\">";

                            // Lay noi dung cua moi field
                            String id = (listdocs.get(i).getFirstValue("id")).toString();
                            String memberId = (listdocs.get(i).getFirstValue("memberid")).toString();
                            String docid = (listdocs.get(i).getFieldValue("docid")).toString();
                            String searchtype = (listdocs.get(i).getFieldValue("searchtype")).toString();
                            String bookmarkname = (listdocs.get(i).getFieldValue("bookmarkname")).toString();
                            Date date_created = (Date) (listdocs.get(i).getFieldValue("date_created"));
                            String url = "";

                            if (request.getAttribute("HighLight") != null) {
                                highLight = (Map<String, Map<String, List<String>>>) request.getAttribute("HighLight");
                                List<String> HLbookmarkname = highLight.get(id).get("bookmarkname");
                                if (HLbookmarkname != null && !HLbookmarkname.isEmpty()) {
                                    bookmarkname = HLbookmarkname.get(0) + "...";
                                }
                            }

                            String link = "";
                            if (searchtype.equals("1")) {
                                link = "DetailWikiController?id=" + docid + "&KeySearch=";
                            } else if (searchtype.equals("2")) {
                                link = "DetailRaoVatController?id=" + docid + "&KeySearch=";
                            } else if (searchtype.equals("3")) {
                                link = "SearchMusicController?type=0&sp=1&f=8&KeySearch=" + docid;
                            } else if (searchtype.equals("4")) {
                                link = "DetailImageController?id=" + docid + "&KeySearch=";
                            } else if (searchtype.equals("5")) {
                                link = "SearchVideoController?type=0&more=detail&KeySearch=" + docid;
                            } else if (searchtype.equals("6")) {
                                link = "DetailNewsController?id=" + docid + "&KeySearch=";
                            }

                            result += "<tr>";
                            result += "<td><a href=\"" + link + "\">" + bookmarkname + "</a></td>";
                            result += "</tr>";

                            result += "<tr>";
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                            result += "<td><b>Ngày tạo:</b> " + sdf.format(date_created) + "</td>";
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
                            if (fieldName.equals("searchtype")) {
                                facet += "<b>Chuyên mục</b>";
                            }

                            facet += "<br>";
                            List<FacetField.Count> listCount = listFacet.get(i).getValues();
                            if (listCount != null) {
                                for (int j = 0; j < listCount.size(); j++) {
                                    String fieldText = listCount.get(j).getName();
                                    String showFieldText = "";
                                    if (fieldText.equals("1")) {
                                        showFieldText = "Wikipedia";
                                    } else if (fieldText.equals("2")) {
                                        showFieldText = "Rao vặt";
                                    } else if (fieldText.equals("3")) {
                                        showFieldText = "Nhạc";
                                    } else if (fieldText.equals("4")) {
                                        showFieldText = "Hình ảnh";
                                    } else if (fieldText.equals("5")) {
                                        showFieldText = "Video";
                                    } else if (fieldText.equals("6")) {
                                        showFieldText = "Tin tức";
                                    }
                                    facet += "<a href = 'SearchRaoVatController?type=2&KeySearch=" + strQuery + "&FacetName=" + fieldName + "&FacetValue=" + fieldText + "'>" + showFieldText + "</a>";
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
                                        <table id="Table_01" width="975" border="0" cellpadding="0" cellspacing="0">
                                            <tr><td><img alt="" src="images/Slogan.png" /></td></tr>
                                        </table>
                                        <!-- end banner      !-->
                                    </td>
                                </tr>
                            </table>
                            <span></span>
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
                                            }%>
                                <table id="tbTopSearch">
                                </table>
                            </div>
                        </td>
                        <td width="627" rowspan="2" valign="top">

                            <table>
                                <tr><td id="result_search"></td></tr><tr></tr>
                            </table>
                            <table id="table_right" width="100%" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>
                                        <input type="text" id="txtSearchBM" size="30px"/>
                                        <input type="button" value="Tìm kiếm" name="btSearchBM" onclick="CheckInput();"/>
                                    </td>
                                </tr>
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



