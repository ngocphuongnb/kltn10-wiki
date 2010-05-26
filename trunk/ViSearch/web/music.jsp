<%-- 
    Document   : music
    Created on : May 26, 2010, 11:47:07 PM
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
<%@page import="org.apache.solr.client.solrj.response.FacetField"%>
<%@page import="org.me.dto.FacetDateDTO"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>ViSearch - Wikipedia</title>
        <link href="style.css"rel="stylesheet" type="text/css" />
        <script language="javascript">
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
                    var url = "SearchWikiController?type=0&sp=1&KeySearch=";
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
                   
                    // End Get strQuery
%>
        <%
                    // Get SolrDocumentList
                   

                    // End get SolrDocumentList
%>

        <%
// Get Facet
                     

                    // End Get Facet
%>

 <%
                    // Get Facet date
                   
                    // End get Facet Date
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

                                        <% String strQuery ="aaaaaaaa";%>
                                        <%@include file="template/banner_Wiki.jsp"%>
                                        <!-- end banner      !-->
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr><td height="20" colspan="2" align="center" valign="bottom"><div align="center" class="nav"></div></td></tr>
                    <tr>
                        <td width="200" height="33" valign="top">

                            <% //out.print(facet);%>
                            <% //out.print(facetD);%>
                            <table>
                                <tr><th><div class="title_content" align="left">Từ khóa được tìm kiếm nhiều nhất</div></th></tr>
                                <tr><td><a href="">aaa</a></td></tr>
                                <tr><td><a href="">bbb</a></td></tr>
                                <tr><td><a href="">ccc</a></td></tr>
                            </table>
                        </td>
                        <td width="627" rowspan="2" valign="top">

                            <table>

                                <tr><td id="result_search"><% //out.print(search_stats);%></td></tr><tr></tr>
                            </table>
                            <table id="table_right" width="100%" cellpadding="0" cellspacing="0">

                                <tr>

                                    <td valign="top" id="content">
                                        <% //out.print(result);%>
                                        <OBJECT ID="MediaPlayer1" CLASSID="CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95" CODEBASE="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab# Version=5,1,52,701" STANDBY="Loading Microsoft Windows® Media Player components..." TYPE="application/x-oleobject" width="280" height="46">
<param name="fileName" value="http://stream4.nhaccuatui.com/bad8413f2c21aadeb4f3cbc360292d6e/4bfd4684/NhacCuaTui82/22-lady_gaga_ft_beyonce-telephone%20NCT%206230957034%20%5BNCT%2001633954496341250000%5D.mp3">
<param name="animationatStart" value="true">
<param name="transparentatStart" value="true">
<param name="autoStart" value="true">
<param name="showControls" value="true">
<param name="Volume" value="-300">
<embed type="application/x-mplayer2" pluginspage="http://www.microsoft.com/Windows/MediaPlayer/" src="http://stream4.nhaccuatui.com/bad8413f2c21aadeb4f3cbc360292d6e/4bfd4684/NhacCuaTui82/22-lady_gaga_ft_beyonce-telephone%20NCT%206230957034%20%5BNCT%2001633954496341250000%5D.mp3" name="MediaPlayer1" width=280 height=46 autostart=1 showcontrols=1 volume=-300>
</OBJECT>

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



