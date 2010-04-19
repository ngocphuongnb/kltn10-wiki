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
<%@page import="java.util.Collection"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Demo Search with SolrJ</title>
    </head>
    <body>
        <img src="logo_ViSearch.png" >
        <%
                    String strQuery = null;
                    int currentpage = 1;
                    int limit = 10;

                    if (request.getParameter("q") != null) {
                        strQuery = (String) request.getParameter("q");
                    }
                    if (request.getParameter("page") != null) {
                        currentpage = Integer.parseInt((String) request.getParameter("page"));
                    }

                    int start = (currentpage - 1) * limit;
        %>
        <form  accept-charset="utf-8" method="get">
            <label for="q">Search:</label>
            <input id="q" name="q" type="text" value="<% if (strQuery != null) {
                            out.print(strQuery);
                        }%>"/>
            <input type="submit"/>
        </form>
        <%
                    // Tao server
                    String url = "http://localhost:8983/solr";
                    CommonsHttpSolrServer server = null;
                    server = new CommonsHttpSolrServer(url);
                    server.setSoTimeout(1000);  // socket read timeout
                    server.setConnectionTimeout(100);
                    server.setDefaultMaxConnectionsPerHost(100);
                    server.setMaxTotalConnections(100);
                    server.setFollowRedirects(false);  // defaults to false
                    // allowCompression defaults to false.
                    // Server side must support gzip or deflate for this to have any effect.
                    server.setAllowCompression(true);
                    server.setMaxRetries(1); // defaults to 0.  > 1 not recommended.

                    //Query
                    if (strQuery != null) {
                        SolrQuery query = new SolrQuery();
                        query.setQuery(strQuery);
                        // Them vao thu
                        query.setFacet(true);
                        query.setHighlight(true);
                        query.setStart(start);
                        // query.set

                        //query.addSortField("price", SolrQuery.ORDER.asc);
                        QueryResponse rsp = server.query(query);

                        // Result
                        SolrDocumentList docs = rsp.getResults();
                        long total = docs.getNumFound();
                        String kq = "Có " + total + " kết quả tìm được:<br>";

                        long end = start + limit;
                        if (end > total) {
                            end = total;
                        }


                        kq += "<div>Results " + start + 1 + " - " + end + " of " + total + ":</div>";
                        for (int i = 0; i < limit; i++) {
                            kq += "<li><table style='border: 1px solid black; text-align: left'>";

                            // Lay danh sach tat ca cac field
                            Collection<String> fieldNames = docs.get(i).getFieldNames();
                            Object[] strFields = (Object[]) fieldNames.toArray();

                            // Lay noi dung cua moi field
                            for (int j = 0; j < strFields.length; j++) {
                                kq += "<tr>";
                                kq += "<th>" + strFields[j].toString() + "</th>";
                                kq += "<td>" + docs.get(i).getFieldValue(strFields[j].toString()) + "</td>";
                                kq += "</tr>";
                            }
                            kq += "</table></li>";
                        }
                        out.println(kq);

                        // Phan trang
                        double numpage = Math.ceil((double) total / (limit * 1.0));
                        if (strQuery != null) // Neu da co query
                        {
                            out.println("Number of page is: " + (int)numpage + "<br/>");
                        }

                        String nav = "";

                        for (int i = 1; i <= numpage; i++) {
                            if (i == currentpage) {
                                nav += i;
                            } else {
                                nav+="<a href='index.jsp?q="+strQuery+"&page="+i+"'>"+i+"</a>";
                            }
                        }

                        if (numpage > 1) // Neu co nhieu hon 1 trang
                        {
                            out.println(nav);
                        }
                    }
        %>
    </body>
</html>