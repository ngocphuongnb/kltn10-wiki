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
                    String strQuery=null;
                    if(request.getParameter("q")!=null)
                        strQuery = (String) request.getParameter("q");
        %>
        <form  accept-charset="utf-8" method="get">
            <label for="q">Search:</label>
            <input id="q" name="q" type="text" value="<% if(strQuery!=null) out.print(strQuery);%>"/>
            <input type="submit"/>
        </form>
        <%
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

                        //query.addSortField("price", SolrQuery.ORDER.asc);
                        QueryResponse rsp = server.query(query);
                        SolrDocumentList docs = rsp.getResults();
                        long re = docs.getNumFound();
                        String kq = "Có " + re + " kết quả tìm được:<br>";

                        int count = (int)re;
                        if(count>10) count =10;
                        for (int i = 0; i < count; i++) {
                            kq += "<b>" + i + "</b>   ";
                            kq += docs.get(i).toString();
                            kq += "<br>";
                        }
                        out.println(kq);
                    }
        %>
    </body>
</html>
