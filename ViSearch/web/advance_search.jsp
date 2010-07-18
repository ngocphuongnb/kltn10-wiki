<%-- 
    Document   : advance_search
    Created on : Jul 17, 2010, 8:59:26 AM
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
        <title>ViSearch - Tìm kiếm nâng cao</title>
                <link href="style.css"rel="stylesheet" type="text/css" />
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/clock.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>
        <link type="text/css" href="css/visearchStyle.css" rel="stylesheet"/>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#tbTopSearch").load("TopSearch?SearchType=7");
            });
        </script>

        <script type="text/javascript">
            function AdvanceSearch()
            {
                var ChuDe = document.getElementById('slChuDe').value;
                var TextAll = encodeURIComponent(document.getElementById('txtTextAll').value);
                var TextExact = encodeURIComponent(document.getElementById('txtTextExact').value);
                var TextOneOf = encodeURIComponent(document.getElementById('txtTextOneOf').value);
                var TextNone = encodeURIComponent(document.getElementById('txtTextNone').value);
                var query = "&ta="+TextAll+"&te="+TextExact+"&to="+TextOneOf+"&tn="+TextNone;
                if(ChuDe==1){
                    var url = "SearchWikiController?type=4&sp=1"+query;
                    url += "&SortedType=1";
                    window.location = url;
                }
                if(ChuDe==2){ // rao vat
                    var url = "SearchRaoVatController?type=5&sp=1"+query;
                    url += "&SortedType=1";
                    window.location = url;
                }
                if(ChuDe==3){
                    var url = "SearchMusicController?type=3&sp=1"+query;
                    url += "&SortedType=1";
                    window.location = url;
                }
                if(ChuDe==4){
                    var url = "SearchImageController?type=4&sp=1"+query
                    url += "&SortedType=1";
                    window.location = url;
                }
                if(ChuDe==5){ // video
                    var url = "SearchVideoController?type=4&sp=1"+query;
                    url += "&SortedType=1";
                    window.location = url;
                }
                if(ChuDe==6){
                    var url = "SearchNewsController?type=5&sp=1"+query;
                    url += "&SortedType=1";
                    window.location = url;
                }
                if(ChuDe==7){ // tong hop
                    var url = "SearchAllController?type=3"+query;
                    url += "&SortedType=1";
                    window.location = url;
                }
            }
        </script>
    </head>
    <body>


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
                                        <!-- end banner !-->
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
                                 <div class="mnu">Tìm kiếm nhiều</div>
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
                                    <td><div class="title_content">&nbsp;Tìm kiếm nâng cao</div>
                                        <form id="frmSearch" action="javascript:AdvanceSearch()">
                                            <table style="font-size:13px">
                                                <tr>
                                                    <td><b>Chủ đề</b>: </td>
                                                    <td>
                                                        <select id="slChuDe">
                                                            <option value="1">Wikipedia</option>
                                                            <option value="2">Rao vặt</option>
                                                            <option value="3">Nhạc</option>
                                                            <option value="4">Hình ảnh</option>
                                                            <option value="5">Video</option>
                                                            <option value="6">Tin tức</option>
                                                            <option value="7">Tổng hợp</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Có <b>tất cả</b> các từ: </td>
                                                    <td><input class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" id="txtTextAll" size="30px" type="text"/></td>
                                                </tr>

                                                <tr>
                                                    <td>Có <b>cụm từ chính xác</b> </td>
                                                    <td><input class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" id="txtTextExact" size="30px" type="text"/></td>
                                                </tr>
                                                <tr>
                                                    <td>Có <b>ít nhất một</b> trong các từ: </td>
                                                    <td><input class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" id="txtTextOneOf" size="30px" type="text"/></td>
                                                </tr>
                                                <tr>
                                                    <td><b>Không có</b> các từ: </td>
                                                    <td><input class="textForm" onfocus="this.className='textForm_Hover';" onblur="this.className='textForm';" id="txtTextNone" size="30px" type="text"/></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" value="Tìm kiếm"></td>
                                                </tr>
                                            </table>
                                        </form>
                                    </td>   
                    </tr>
                    <tr>
                        <td valign="top" id="content">
                            <% %>
                        </td>
                    <tr>
                        <td>
                            <table><tr><td align="left" ><a href="index.jsp">Về trang chủ</a></td></tr></table>
                        </td>
                    </tr>
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
