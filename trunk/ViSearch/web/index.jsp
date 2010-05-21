<%-- 
    Document   : index
    Created on : Apr 18, 2010, 11:15:41 AM
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link href="styles.css" rel="stylesheet" type="text/css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Demo Search with SolrJ</title>
        <script language="javascript" type="text/javascript">
            function CheckInput()
            {
                var keysearch = document.getElementsByName("KeySearch")[0];
                if(keysearch.value == "")
                    return;
                else
                {
                    var url = "SearchController?type=0&KeySearch=";
                    //url += keysearch.value;
                    url += encodeURIComponent(keysearch.value);
                    url += "&sp=1";
                    window.location = url;
                }
            }
        </script>
    </head>
    <body>
        <img src="logo_ViSearch.png" >
        <%

                    String strQuery = "";
                    if (request.getAttribute("KeySearch") != null) {
                        strQuery = (String) request.getAttribute("KeySearch");
                        //strQuery = URLDecoder.decode(strQuery, "UTF-8");
                        strQuery = strQuery.replaceAll("\"", "&quot;");
                    }
        %>
        <form action="javascript:CheckInput()" method="GET">
            <input id="searchbox" name="KeySearch" type="text" maxlength="2048" size="41" class="lst" value="<% if (strQuery != null) {
                            out.print(strQuery);
                        }%>"/>
            <input type="submit" value="Tìm kiếm" class="lsb">
            <br>
        </form>
        <%
                    SolrDocumentList listdocs = new SolrDocumentList();
                    Map<String, Map<String, List<String>>> highLight = null;
                    int numrow = 0;
                    int numpage = 0;
                    String strpaging = "";
                    String QTime;
                    if (request.getAttribute("QTime") != null) {
                        QTime = request.getAttribute("QTime").toString();
                        if (request.getAttribute("Docs") != null) {
                            listdocs = (SolrDocumentList) request.getAttribute("Docs");

                            String result = String.format("<div style=\"font-size:11pt\">Số kết quả tìm được: %d - Thời gian tìm kiếm: %s giây</div>", listdocs.getNumFound(), QTime);

                            if (request.getAttribute("Collation") != null) {
                                String sCollation = (String) request.getAttribute("Collation");
                                result += "<p><font color=\"#CC3333\" size=\"+2\">Có phải bạn muốn tìm: <b><a href=\"SearchController?type=0&KeySearch=" + sCollation + "\">" + sCollation + "</a></b></font></p>";
                            }

                            for (int i = 0; i < listdocs.size(); i++) {
                                result += "<ul><table style='border: none; text-align: left' width='100%'>";

                                // Lay noi dung cua moi field
                                String title = (listdocs.get(i).getFieldValue("title")).toString();
                                String text = (listdocs.get(i).getFieldValue("text")).toString();
                                String id = (listdocs.get(i).getFieldValue("id")).toString();
                                String url = title.replace(' ', '_');
                                String title_hl = title;

                                if (request.getAttribute("HighLight") != null) {
                                    highLight = (Map<String, Map<String, List<String>>>) request.getAttribute("HighLight");
                                    List<String> highlightText = highLight.get(id).get("text");
                                    List<String> highlightTitle = highLight.get(id).get("title");
                                    if (highlightText != null && !highlightText.isEmpty()) {
                                        text = highlightText.get(0) + "...";
                                    } else {
                                        if (text.length() > 100) {
                                            text = text.substring(0, 100) + "...";
                                        }
                                    }
                                    if (highlightTitle != null && !highlightTitle.isEmpty()) {
                                        title_hl = highlightTitle.get(0);
                                    }
                                } else {
                                    if (text.length() > 100) {
                                        text = text.substring(0, 100) + "...";
                                    }
                                }


                                url = "<td><font size=\"+2\"><a href=\"http://vi.wikipedia.org/wiki/" + URLEncoder.encode(url, "UTF-8") + "\">" + title_hl + "</a></font></td>";
                                result += "<tr>";
                                result += "<th width='70px'>Tiêu đề: </th>";
                                result += url;
                                result += "</tr>";
                                result += "<tr>";
                                result += "<th>Nội dung: </th>";
                                result += "<td>" + text + "</td>";
                                result += "</tr>";
                                result += "<tr>";
                                result += "<td colspan='2'>";
                                result += "<a href=\"SearchController?type=1&KeySearch=" + URIUtil.encodeAll(title) + "\">Trang tương tự...</a>";
                                result += "</td>";
                                result += "</tr>";
                                result += "</table></ul>";
                            }
                            out.println(result);

                            // Phan trang
                            numrow = Integer.parseInt(request.getAttribute("NumRow").toString());
                            numpage = Integer.parseInt(request.getAttribute("NumPage").toString());
                            strpaging = (String) request.getAttribute("Pagging");
                        }
                        out.println("Số kết quả tìm được là: " + numrow + "<br/>");
                        out.println("Tổng số trang là: " + numpage + "<br/>");
                        out.print(strpaging + "<br/><br/>");
                    }
        %>
    </body>
</html>