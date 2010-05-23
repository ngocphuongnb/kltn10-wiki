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
        <title>Trang chủ - ViSearch</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <script language="javascript">
            function setText()
            {
                document.getElementById('txtSearch').focus();
            }
            function CheckInput()
            {
                var keysearch = document.getElementById('txtSearch').value;
                if(keysearch == "")
                    return;
                else
                {
                    var url = "SearchController?type=0&sp=1&KeySearch=";
                    //url += keysearch.value;
                    url += encodeURIComponent(keysearch);
                    //alert(url);
                    window.location = url;
                }
            }
        </script>
    </head>

    <body onload="setText();">
        <%
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
                                result += "<p><font color=\"#CC3333\" size=\"+2\">Có phải bạn muốn tìm: <b><a href=\"SearchController?type=0&KeySearch=" + sCollation + "\">" + sCollation + "</a></b></font></p>";
                            }

                            for (int i = 0; i < listdocs.size(); i++) {
                                result += "<table style=\"font-size:13px\">";

                                // Lay noi dung cua moi field
                                String title = (listdocs.get(i).getFirstValue("wk_title")).toString();
                                String text = (listdocs.get(i).getFieldValue("wk_text")).toString();
                                String id = (listdocs.get(i).getFieldValue("id")).toString();
                                String url = title.replace(' ', '_');
                                String title_hl = title;

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


                                url = "<td><font size=\"+2\"><a href=\"http://vi.wikipedia.org/wiki/" + URLEncoder.encode(url, "UTF-8") + "\">" + title_hl + "</a></font></td>";
                                result += "<tr>";
                                result += url;
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td>" + text + "</td>";
                                result += "</tr>";

                                result += "<tr>";
                                result += "<td colspan='2'>";
                                result += "<a href=\"SearchController?type=1&KeySearch=" + URIUtil.encodeAll(title) + "\">Trang tương tự...</a>";
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
                        result += "Số kết quả tìm được là: " + numrow + "<br/>";
                        result += "Tổng số trang là: " + numpage + "<br/>";
                        if (numpage > 1) {
                            result += strpaging + "<br/><br/>";
                        }
                    }

                    // End get SolrDocumentList
%>

        <%
// Get Facet
                    String facet = "<table id=\"table_left\" width=\"100%\" border=\"0\">";
                    facet += "<tr><th><div class=\"title_content\" align=\"left\">Facet</div></th></tr>";
                    facet += "<tr>";

                    List<FacetField> listFacet = (List<FacetField>) request.getAttribute("ListFacet");
                    if (listFacet != null) {
                        for (int i = 0; i < listFacet.size(); i++) {
                            facet += "<td>";
                            String fieldName = listFacet.get(i).getName();
                            facet += "Facet: " + fieldName;
                            facet += "<br>";
                            List<FacetField.Count> listCount = listFacet.get(i).getValues();
                            if (listCount != null) {
                                for (int j = 0; j < listCount.size(); j++) {
                                    String fieldText = listCount.get(j).getName();
                                    String newStrQuery = strQuery + " and " + fieldName + ":";
                                    newStrQuery += "\"";
                                    newStrQuery += fieldText;
                                    newStrQuery += "\"";

                                    facet += "Name: " + "<a href = 'SearchController?type=2&KeySearch=" + newStrQuery + "'>" + fieldText + "</a>";
                                    facet += "(Count: " + listCount.get(j).getCount() + ")";
                                    facet += "<br>";
                                }
                            } else {
                                facet += "Không tìm ra Facet<br>";
                            }
                            facet += "</td>";
                        }
                    }
                    facet += "</table>";
                    // End Get Facet
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
                    <tr><td height="20" colspan="2" align="center" valign="bottom"><div align="center" class="nav"></div></td></tr>
                    <tr>
                        <td width="200" height="33" valign="top">

                            <% out.print(facet);%>

                            <table>
                                <tr><th><div class="title_content" align="left">Từ khóa được tìm kiếm nhiều nhất</div></th></tr>
                                <tr><td><a href="">aaa</a></td></tr>
                                <tr><td><a href="">bbb</a></td></tr>
                                <tr><td><a href="">ccc</a></td></tr>
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


