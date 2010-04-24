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
<%@page import="java.util.*, java.net.*,java.util.Map"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
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
                    var url = "SearchController?KeySearch=";
                    url += encodeURIComponent(keysearch.value);
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
                        strQuery = (String) request.getParameter("KeySearch");
                        strQuery = URLDecoder.decode(strQuery, "UTF-8");
                        strQuery = strQuery.replaceAll("\"", "&quot;");
                    }
        %>
        <form action="javascript:CheckInput()" method="GET">
            <label for="searchbox">Search:</label>
            <input id="searchbox" name="KeySearch" type="text" value="<% if (strQuery != null) {
                            out.print(strQuery);
                        }%>"/>
            <input type="submit"/>
        </form>
        <%
                    SolrDocumentList listdocs = new SolrDocumentList();
                    Map<String, Map<String, List<String>>> highLight = null;
                    if (request.getAttribute("Docs") != null) {
                        listdocs = (SolrDocumentList) request.getAttribute("Docs");
                        if (request.getAttribute("HighLight") != null) {
                            highLight = (Map<String, Map<String, List<String>>>) request.getAttribute("HighLight");
                        }
                        String result = "";

                        for (int i = 0; i < listdocs.size(); i++) {
                            result += "<li><table style='border: 1px solid black; text-align: left'>";

                            // Lay danh sach tat ca cac field
                            Collection<String> fieldNames = listdocs.get(i).getFieldNames();
                            Object[] strFields = (Object[]) fieldNames.toArray();

                            // Lay noi dung cua moi field
                            String title = (listdocs.get(i).getFieldValue("title")).toString();
                            String text = (listdocs.get(i).getFieldValue("text")).toString();
                            String url = title.replace(' ', '_');
                            List<String> highlightText = highLight.get(title).get("text");
                            if (!highlightText.isEmpty()) {
                                text = highlightText.get(0) + "...";
                            } else {
                                if(text.length()>100)
                                    text = text.substring(0, 100) + "...";
                            }
                            url = "<td><a href=\"http://vi.wikipedia.org/wiki/" + URLEncoder.encode(url, "UTF-8") + "\">" + title + "</a></td>";
                            result += "<tr>";
                            result += "<th>Tiêu đề: </th>";
                            result += url;
                            result += "</tr>";
                            result += "<tr>";
                            result += "<th>Nội dung: </th>";
                            result += "<td>" + text + "</td>";
                            result += "</tr>";
                            result += "</table></li>";
                        }
                        out.println(result);

                        // Phan trang
                        int numrow = Integer.parseInt(request.getAttribute("NumRow").toString());
                        int numpage = Integer.parseInt(request.getAttribute("NumPage").toString());
                        out.println("Số kết quả tìm được là: " + numrow + "<br/>");
                        out.println("Tổng số trang là: " + numpage + "<br/>");
                        String strpaging = (String) request.getAttribute("Pagging");
                        out.print(strpaging);
                    }
        %>
    </body>
</html>