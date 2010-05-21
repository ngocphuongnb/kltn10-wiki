<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@page import="org.apache.solr.client.solrj.SolrQuery"%>
<%@page import="org.apache.solr.client.solrj.SolrServer"%>
<%@page import="org.apache.solr.client.solrj.impl.CommonsHttpSolrServer"%>
<%@page import="org.apache.solr.common.SolrDocument"%>
<%@page import="org.apache.solr.common.SolrDocumentList"%>
<%@page import="org.apache.solr.common.SolrInputDocument"%>
<%@page import="org.apache.solr.client.solrj.response.QueryResponse"%>
<%@page import="java.util.*, java.net.*,java.util.Map, org.apache.commons.httpclient.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Rao vat</title>
        <script language="javascript" type="text/javascript">
            function CheckInput()
            {
                var keysearch = document.getElementsByName("KeySearch")[0];
                if(keysearch.value == "")
                    return;
                else
                {
                    var url = "RaoVatController?type=0&KeySearch=";
                    //url += keysearch.value;
                    url += encodeURIComponent(keysearch.value);
                    //alert(url);
                    window.location = url;
                }
            }
        </script>
    </head>

    <body>
        <%

                    String strQuery = "";
                    if (request.getAttribute("KeySearch") != null) {
                        strQuery = (String) request.getAttribute("KeySearch");
                        //strQuery = URLDecoder.decode(strQuery, "UTF-8");
                        strQuery = strQuery.replaceAll("\"", "&quot;");
                    }
        %>
        <table width="800" border="1px" align="center">
            <tr>
                <td height="150" colspan="3"><table width="800" border="0" cellpadding="0px" cellspacing="0">
                        <tr>
                            <td width="225" rowspan="2"><img src="images/ViSearchDesign_01.gif" width="225" height="124" /></td>
                            <td width="33" height="48">&nbsp;</td>
                            <td width="99" style="vertical-align:bottom"><input type="image" src="images/ViSearchDesign_06.gif" width="99" height="24" /></td>
                            <td width="99" style="vertical-align:bottom"><input type="image" src="images/ViSearchDesign_07.gif" width="99" height="24" /></td>
                            <td width="236">&nbsp;</td>
                            <td height="48">&nbsp;</td>
                        </tr>
                        <tr>
                            <td height="75" colspan="4" background="images/ViSearchDesign_09.gif" style="text-align:center; vertical-align:middle" valign="middle">
                                <form action="javascript:CheckInput()" method="GET"><input type="text" name="KeySearch" id="KeySearch" size="55" style="padding:5px; border:none" value="<%
                                            out.print(strQuery);
                                                                                           %>"/>
                                </form>
                            </td>
                            <td width="107" style="text-align:center"><input type="image" src="images/ViSearchDesign_12.gif" width="88" height="62" onclick="javascript:CheckInput()"/></td>
                        </tr>
                    </table></td>
            </tr>
            <tr>
                <td width="200">&nbsp;</td>
                <td width="600" >
                    <p>
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
                                                result += "<ul style='padding:0px'><table border=\"0\" align=\"center\" width=\"100%\" cellpadding='0'>";

                                                // Lay noi dung cua moi field
                                                String title = (listdocs.get(i).getFieldValue("title")).toString();
                                                String body = (listdocs.get(i).getFieldValue("body")).toString();
                                                String id = (listdocs.get(i).getFieldValue("id")).toString();
                                                String url = title.replace(' ', '_');
                                                String title_hl = title.replaceAll("\\<.*?\\>", "");
                                                String photo = "";
                                                if (listdocs.get(i).getFieldValue("photo") != null) {

                                                    photo = (listdocs.get(i).getFieldValue("photo")).toString();
                                                }

                                                if (request.getAttribute("HighLight") != null) {
                                                    highLight = (Map<String, Map<String, List<String>>>) request.getAttribute("HighLight");
                                                    List<String> highlightText = highLight.get(id).get("body");
                                                    List<String> highlightTitle = highLight.get(id).get("title");
                                                    if (highlightText != null && !highlightText.isEmpty()) {
                                                        body = highlightText.get(0) + "...";
                                                    } else {
                                                        if (body.length() > 100) {
                                                            body = body.substring(0, 100) + "...";
                                                        }
                                                    }
                                                    if (highlightTitle != null && !highlightTitle.isEmpty()) {
                                                        title_hl = highlightTitle.get(0);
                                                    }
                                                } else {
                                                    if (body.length() > 100) {
                                                        body = body.substring(0, 100) + "...";
                                                    }
                                                }


                                                url = "<td><font size=\"+2\"><a href=\"DetailRaoVatController?id=" + id + "\">" + title_hl + "</a></font></td>";
                                                result += "<tr>";
                                                result += "<td rowspan=\"3\" width=\"150\"><img src=\"" + photo + "\" alt=\"No image\" width=\"150\" align=\"left\" /></td>";
                                                result += url;
                                                result += "</tr>";
                                                result += "<tr>";
                                                result += "<td>" + body.replaceAll("\\<.*?\\>", "") + "</td>";
                                                result += "</tr>";
                                                result += "<tr>";
                                                result += "<td>";
                                                result += "<a href=\"RaoVatController?type=1&KeySearch=" + title_hl.replaceAll("\\<.*?\\>", "") + "\">Trang tương tự...</a>";
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
                    </p>
                </td>
            </tr>
            <tr>
                <td colspan="3" align="center">Copyright: visearch</td>
            </tr>
        </table>
    </body>
</html>