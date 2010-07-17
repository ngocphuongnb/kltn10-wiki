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
        <script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>

        <script type="text/javascript">
            function CheckInput()
            {
                var filter = document.getElementsByName("filter[]");
                var arrcheck = new Array;
                var idem = 0;
                for (i = 0; i < filter.length; i++)
                    if( filter[i].checked == true){
                        arrcheck[idem] = filter[i].value;
                        idem ++;
                    }

                if(arrcheck.length == 0)
                {
                    alert("Vui lòng chọn bộ lọc tìm kiếm bookmark");
                    return false;
                }
                var keysearch = document.getElementById('txtSearchBM').value;
                if(keysearch == "")
                    return;
                else
                {
                    var url = "SearchBookmarkController?type=0&sp=1&KeySearch=";
                    url += encodeURIComponent(keysearch);
                    url += "&Filter=" + arrcheck.join("_");
                    window.location = url;
                }
            }
            function AdvanceSearch()
            {
                var ChuDe = document.getElementById('slChuDe').value;
                var TextAll = document.getElementById('txtTextAll').value;
                var TextExact = document.getElementById('txtTextExact').value;
                var TextOneOf = document.getElementById('txtTextOneOf').value;
                var TextNone = document.getElementById('txtTextNone').value;
                var query = "&ta="+TextAll+"&te="+TextExact+"&to="+TextOneOf+"&tn="+TextNone;
                
                if(ChuDe==3){
                    var url = "SearchMusicController?type=3&sp=1&KeySearch="+query;
                    url += "&SortedType=1";
                    window.location = url;
                }
                if(ChuDe==6){
                    var url = "SearchNewsController?type=5&sp=1"+query;
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
                                <div class="mnu">Nhà tài trợ</div>

                                <% if (request.getAttribute("NewestDocs") != null) {
                                                out.print("result3");
                                            }%>
                            </div>
                        </td>
                        <td width="627" rowspan="2" valign="top">

                            <table>
                                <tr><td id="result_search"></td></tr><tr></tr>
                            </table>
                            <table id="table_right" width="100%" cellpadding="0" cellspacing="0">
                                <tr><form action="javascript:CheckInput();" method="GET">
                                    <td>&nbsp;
                                        <form frmSearch action="javascript:CheckInput()">
                                            Tìm kết quả:<br>
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
                                                    <td><input type ="button" value="Tìm kiếm" onclick="AdvanceSearch()"></td>
                                                </tr>
                                            </table>
                                            <hr/>
                                        </form>
                                    </td>
                                </form>
                    </tr>
                    <tr>
                        <td valign="top" id="content">
                            <% out.print("result");%>
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
