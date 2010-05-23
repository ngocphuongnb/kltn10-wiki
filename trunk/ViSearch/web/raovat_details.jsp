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
        <title>Rao vặt - ViSearch</title>
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
                    var url = "RaoVatController?type=0&KeySearch=";
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

                    String result = "";
                    if (request.getAttribute("Docs") != null) {
                        listdocs = (SolrDocumentList) request.getAttribute("Docs");
                        for (int i = 0; i < listdocs.size(); i++) {
                            result += "<table style=\"font-size:13px\">";

                            // Lay noi dung cua moi field
                            String id = (listdocs.get(i).getFieldValue("id")).toString();
                            String title = (listdocs.get(i).getFieldValue("rv_title")).toString();
                            String body = (listdocs.get(i).getFieldValue("rv_body")).toString();
                            String price = "";
                            String category = (listdocs.get(i).getFieldValue("category")).toString();
                            String score = (listdocs.get(i).getFieldValue("score")).toString();
                            String site = (listdocs.get(i).getFieldValue("site")).toString();
                            String location = "";
                            String contact = "";
                            String last_update = (listdocs.get(i).getFieldValue("last_update")).toString();
                            String url = title;
                            String photo = "";

                             if (listdocs.get(i).getFieldValue("contact") != null) {
                                contact = (listdocs.get(i).getFieldValue("contact")).toString();
                            }

                            if (listdocs.get(i).getFieldValue("photo") != null) {
                                photo = (listdocs.get(i).getFieldValue("photo")).toString();
                            }
                            if (listdocs.get(i).getFieldValue("price") != null) {
                                price = (listdocs.get(i).getFieldValue("price")).toString();
                            }
                            if (listdocs.get(i).getFieldValue("location") != null) {
                                location = (listdocs.get(i).getFieldValue("location")).toString();
                            }

                            url = "<tr><td><div class=\"title_content\">" + title + "</div></td></tr>";
                            result += url;
                            photo = "<td rowspan='7' width='200'><img src='" + photo + "' alt='No image' width='200'/></td>";

                            result += "<tr><td width='auto'>" + "Contact: "+ "<a href = 'RaoVatController?type=2&KeySearch=contact:"+contact+"'>" + contact+ "</a></td>" + photo +"</tr>";
                            result += "<tr><td>" + "Category: "+ "<a href = 'RaoVatController?type=2&KeySearch=category:"+category+"'>" + category+ "</a></td></tr>";
                            result += "<tr><td>" + "Location: "+ "<a href = 'RaoVatController?type=2&KeySearch=location:"+location+"'>" + location+ "</a></td></tr>";
                            result += "<tr><td>" + "Score: "+score + "</td></tr>";
                            result += "<tr><td>" + "Site: "+ "<a href = 'RaoVatController?type=2&KeySearch=site:"+site+"'>" + site+ "</a></td></tr>";
                            result += "<tr><td>" + "Price: "+price + "</td></tr>";
                            result += "<tr><td>" + "Last update: "+ last_update + "</td></tr>";

                            result += "<tr>";
                            result += "<td>" + body + "</td>";
                            result += "</tr>";

                            result += "</table>";
                        }
                    }

                     //get SolrDocumentList
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
                                    String newStrQuery = fieldName + ":";
                                    newStrQuery += "\"";
                                    newStrQuery += fieldText;
                                    newStrQuery += "\""  + " and " +  strQuery;

                                    facet += "Name: " + "<a href = 'RaoVatController?type=2&KeySearch=" + newStrQuery + "'>" + fieldText + "</a>";
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

                    // End get Facet
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
                                        <%@include file="template/banner_RaoVat.jsp" %>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr><td height="20" colspan="2" align="center" valign="bottom"><div align="center" class="nav"></div></td></tr>
                    <tr>
                        <td width="200" height="33" valign="top">
                            <%  out.print(facet);%>
                             <table>
                                <tr><th><div class="title_content" align="left">Từ khóa được tìm kiếm nhiều nhất</div></th></tr>
                                <tr><td><a href="">aaa</a></td></tr>
                                <tr><td><a href="">bbb</a></td></tr>
                                <tr><td><a href="">ccc</a></td></tr>
                            </table>
                        </td>
                        <td width="627" rowspan="2" valign="top">

                            <table>

                                <tr><td id="result_search">thong ke</td></tr><tr></tr>
                            </table>
                            <table id="table_right" width="100%" cellpadding="0" cellspacing="0">


                                <tr>
                                    <td  valign="top" id="content">
                                        <% out.print(result);%>

                                        <table>
                                            <tr><td><div class="title_content">Bài viết cùng chuyên mục</div></td></tr>
                                        </table>

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
                            <%@include file="template/footer.jsp" %>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

    </body>
</html>



