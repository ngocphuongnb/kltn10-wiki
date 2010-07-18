/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.ViSearchController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.DisMaxParams;
import org.apache.solr.common.params.HighlightParams;
import org.apache.solr.common.params.MoreLikeThisParams;
import org.me.SolrConnection.SolrJConnection;
import org.me.Utils.MySegmenter;
import org.me.Utils.MyString;
import org.me.Utils.Paging;
import org.me.dto.FacetDateDTO;

/**
 *
 * @author VinhPham
 */
public class SearchRaoVatController extends HttpServlet {

    SolrServer server;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        SolrDocumentList docs = new SolrDocumentList();
        String keySearch = "";
        int pagesize = 10;
        int currentpage = 1;
        long numRow = 0;
        int type = -1;
        int QTime = 0;
        int sortedType = 0;
        String sPaging = "/ViSearch/SearchRaoVatController?";
        List<FacetField> listFacet = null;

        try {

            if (request.getParameter("currentpage") != null) {
                currentpage = Integer.parseInt(request.getParameter("currentpage"));
            }

            server = SolrJConnection.getSolrServer("raovat");
            int start = (currentpage - 1) * pagesize;

            if (request.getParameter("type") != null) {
                type = Integer.parseInt(request.getParameter("type"));
                sPaging += "type=" + type;
            }

            if (request.getParameter("SortedType") != null) {
                sortedType = Integer.parseInt(request.getParameter("SortedType"));
                sPaging += "&SortedType=" + sortedType;
            }

            QueryResponse rsp;
            Map<String, Map<String, List<String>>> highLight;

            if (request.getParameter("KeySearch") != null) {
                keySearch = URLDecoder.decode(request.getParameter("KeySearch"), "UTF-8");
                sPaging += "&KeySearch=" + URLEncoder.encode(keySearch, "UTF-8");

            }

            switch (type) {
                case 0://submit search
                    if (request.getParameter("sp") != null) {
                        String sCollation = OnCheckSpelling(keySearch);
                        if (!sCollation.equals("")) {
                            request.setAttribute("Collation", sCollation);
                        }
                    }

                    rsp = OnSearchSubmit(keySearch, start, pagesize, sortedType);
                    docs = rsp.getResults();
                    highLight = rsp.getHighlighting();
                    request.setAttribute("HighLight", highLight);
                    QTime = rsp.getQTime();
                    // Get Facet
                    listFacet = rsp.getFacetFields();
                    break;
                case 1://morelikethis
                    rsp = OnMLT(keySearch, pagesize, sortedType);
                    docs = rsp.getResults();
                    highLight = rsp.getHighlighting();
                    if (highLight != null) {
                        request.setAttribute("HighLight", highLight);
                    }
                    QTime = rsp.getQTime();

                    for (int i = 0; i < docs.size() - 1; i++) {
                        for (int j = i + 1; j < docs.size(); j++) {
                            String title1 = docs.get(i).getFirstValue("rv_title").toString();
                            String title2 = docs.get(j).getFirstValue("rv_title").toString();
                            if (title1.trim().toLowerCase().equals(title2.trim().toLowerCase())) {
                                Date date1 = (Date) docs.get(i).getFieldValue("last_update");
                                Date date2 = (Date) docs.get(j).getFieldValue("last_update");
                                if (date1.compareTo(date2) >= 0) {
                                    docs.remove(j);
                                    j--;
                                } else {
                                    docs.remove(i);
                                    i--;
                                    break;
                                }
                            }
                        }
                    }

                    int idem = Math.min(20, docs.size());
                    while (docs.size() > idem) {
                        docs.remove(idem);
                    }
                    // Get Facet
                    listFacet = rsp.getFacetFields();
                    break;
                case 2:
                case 4:
                    String facetName = "";
                    String facetValue = "";
                    if (request.getParameter("FacetName") != null) {
                        facetName = request.getParameter("FacetName");
                        facetValue = request.getParameter("FacetValue");
                        sPaging += "&FacetName=" + facetName;
                        sPaging += "&FacetValue=" + facetValue;
                    }
                    rsp = OnSearchSubmitStandard(keySearch, facetName, facetValue, start, pagesize, type, sortedType);
                    docs = rsp.getResults();
                    highLight = rsp.getHighlighting();
                    request.setAttribute("HighLight", highLight);
                    QTime = rsp.getQTime();
                    // Get Facet
                    listFacet = rsp.getFacetFields();
                    break;
                case 3:
                    facetName = "";
                    facetValue = "";
                    sPaging += "&type=2";
                    if (request.getParameter("FacetName") != null) {
                        facetName = request.getParameter("FacetName");
                        sPaging += "&FacetName=" + facetName;
                        String startDate = "";
                        if (request.getParameter("sd") != null) {
                            startDate = request.getParameter("sd");
                        }
                        String endDate = "";
                        if (request.getParameter("ed") != null) {
                            endDate = request.getParameter("ed");
                        }
                        facetValue = createFacetValue(startDate, endDate);
                        sPaging += "&FacetValue=" + facetValue;
                    }
                    rsp = OnSearchSubmitStandard(keySearch, facetName, facetValue, start, pagesize, type, sortedType);

                    highLight = rsp.getHighlighting();
                    request.setAttribute("HighLight", highLight);
                    docs = rsp.getResults();
                    QTime = rsp.getQTime();
                    // Get Facet
                    listFacet = rsp.getFacetFields();
                    break;
                case 5:
                    String TextAll = "";
                    String TextExact = "";
                    String TextOneOf = "";
                    String TextNone = "";

                    if (request.getParameter("ta") != null) {
                        TextAll = request.getParameter("ta");
                        sPaging += "&ta=" + TextAll;
                    }
                    if (request.getParameter("te") != null) {
                        TextExact = request.getParameter("te");
                        sPaging += "&te=" + TextExact;
                    }
                    if (request.getParameter("to") != null) {
                        TextOneOf = request.getParameter("to");
                        sPaging += "&to=" + TextOneOf;
                    }
                    if (request.getParameter("tn") != null) {
                        TextNone = request.getParameter("tn");
                        sPaging += "&tn=" + TextNone;
                    }
                    rsp = OnSearchAdvance(TextAll, TextExact, TextOneOf, TextNone, start, pagesize, sortedType);
                    docs = rsp.getResults();
                    highLight = rsp.getHighlighting();
                    request.setAttribute("HighLight", highLight);
                    QTime = rsp.getQTime();
                    // Get Facet
                    listFacet = rsp.getFacetFields();
                    break;
                default:
                    break;
            }


            request.setAttribute("QTime", String.valueOf(1.0 * QTime / 1000));
            request.setAttribute("KeySearch", keySearch);
            request.setAttribute("SortedType", sortedType);
            if (docs != null) {
                numRow = docs.getNumFound();
                int numpage = (int) (numRow / pagesize);

                if (numRow % pagesize > 0) {
                    numpage++;
                }
                request.setAttribute("Docs", docs);
                //request.setAttribute("ListFacetDate", listFacetDate);
                if (type != 1) {
                    sPaging = Paging.getPaging(numpage, pagesize, currentpage, sPaging);
                    request.setAttribute("NumPage", numpage);
                } else {
                    int min = (int) Math.min(20, numRow);
                    sPaging = min + " kết quả tốt nhất trong " + numRow + " kết quả tìm được";
                }
                request.setAttribute("Pagging", sPaging);
                request.setAttribute("NumRow", numRow);
                request.setAttribute("ListFacet", listFacet);
            }
            String url = "/raovat.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (Exception e) {
            out.print(e.getMessage());
            //String url = "/ViSearch/index.jsp";
            //response.sendRedirect(url);
        } finally {
            out.close();
        }
    }

    // tuan-dm
    private String createFacetValue(String startDate, String endDate) {
        // src: dd-mm-yyyy
        // dest: 1976-03-06T23:59:59.999Z
        String result = "[";
        String[] arrStr1 = startDate.split("-");
        if (arrStr1.length >= 3) {
            result += arrStr1[2] + "-" + arrStr1[1] + "-" + arrStr1[0] + "T00:00:00.000Z";
        } else {
            result += "1990-01-01T00:00:00.000Z";
        }
        result += " TO ";
        String[] arrStr2 = endDate.split("-");
        if (arrStr2.length >= 3) {
            result += arrStr2[2] + "-" + arrStr2[1] + "-" + arrStr2[0] + "T23:59:59.999Z";
        } else {
            result += "NOW";
        }
        result += "]";
        return result;
    }

    QueryResponse OnSearchSubmit(String keySearch, int start, int pagesize, int sortedType) throws SolrServerException, IOException {
        //keySearch = MyString.cleanQueryTerm(keySearch);
        SolrQuery solrQuery = new SolrQuery();
        switch (sortedType) {
            case 0:
                if (MyString.CheckSigned(keySearch)) {
                    solrQuery.setQueryType("dismax");
                } else {
                    solrQuery.setQueryType("dismax_unsigned");
                }
                break;
            case 1:
                if (MyString.CheckSigned(keySearch)) {
                    solrQuery.setQueryType("dismax");
                } else {
                    solrQuery.setQueryType("dismax_unsigned");
                }
                solrQuery.set(DisMaxParams.BF, "recip(rord(last_update),1,1000,1000)");
                break;
            case 2:
                if (MyString.CheckSigned(keySearch)) {
                    solrQuery.setQueryType("dismax_boosting");
                } else {
                    solrQuery.setQueryType("dismax_unsigned_boosting");
                }
                break;
            case 3:
                if (MyString.CheckSigned(keySearch)) {
                    solrQuery.setQueryType("dismax");
                } else {
                    solrQuery.setQueryType("dismax_unsigned");
                }
                MySegmenter seg = new MySegmenter();
                keySearch = seg.getwordBoundaryMark(keySearch);
                break;
        }
        solrQuery.setQuery(keySearch);

        // Facet
        solrQuery.setFacet(true);
        solrQuery.set("facet.field", "category");
        //solrQuery.addFacetField("category");
        solrQuery.addFacetField("site");
        solrQuery.setFacetLimit(10);
        solrQuery.setFacetMinCount(1);
        // End Facet


        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("rv_title");
        solrQuery.addHighlightField("rv_body");
        solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.setHighlightRequireFieldMatch(true);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
        solrQuery.set("fl", "id, rv_title, rv_body, photo, url, site, category, last_update");
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    // Lay nhung bai viet moi nhat --> Test OK
    ArrayList<FacetDateDTO> NewestUpdateDocument(String keySearch, String numDays) throws SolrServerException, org.apache.commons.httpclient.URIException, IOException {
        HttpClient client = new HttpClient();

        String url = "http://localhost:8983/solr/raovat/select/?q=" + keySearch + "&facet=true&facet.date=timestamp&facet.date.start=NOW/DAY-" + numDays + "DAYS&facet.date.end=NOW/DAY%2B1DAY&facet.field=last_update&facet.limit=10&wt=json";
        url = URIUtil.encodeQuery(url);
        GetMethod get = new GetMethod(url);

        get.setRequestHeader(new Header("User-Agent", "localhost bot admin@localhost.com"));

        int status = client.executeMethod(get);
        String charSet = get.getResponseCharSet();
        if (charSet == null) {
            charSet = "UTF-8";
        }
        String body = convertStreamToString(get.getResponseBodyAsStream(), charSet);


        try {
            JSONObject json = (JSONObject) JSONSerializer.toJSON(body);
            JSONObject ob = json.getJSONObject("facet_counts");
            JSONObject location = ob.getJSONObject("facet_fields");

            // Vi moi chi xai 1 field Location nen lay luon
            JSONArray last_update = location.getJSONArray("last_update");
            ArrayList<FacetDateDTO> myArr = new ArrayList<FacetDateDTO>();

            if (last_update.size() > 0) {
                FacetDateDTO fD = null;
                for (int i = 0; i < last_update.size(); i++) {
                    if (i % 2 == 0)// Phan tu chan la Ngay (Value)
                    {
                        fD = new FacetDateDTO();
                        fD.setDateTime(last_update.get(i).toString());
                    } else //  Phan tu le là số (Count)
                    {
                        fD.setCount(last_update.get(i).toString());
                        myArr.add(fD);
                    }
                }
            }
            get.releaseConnection();
            return myArr;
        } catch (Exception x) {
            return null;
        }

    }

    QueryResponse OnSearchSubmitStandard(String keySearch, String facetName, String facetValue, int start, int pagesize, int type, int sortedType) throws SolrServerException {
        String str = "";
        String trackingboost = "";
        SolrQuery solrQuery = new SolrQuery();
        keySearch = keySearch.replaceAll("\"", "\\\"");
        switch (sortedType) {
            case 0:
                str = "";
                trackingboost = "";
                break;
            case 1:
                str = "{!boost b= recip(rord(last_update),1,1000,1000)}";
                trackingboost = "";
                break;
            case 2:
                if (MyString.CheckSigned(keySearch)) {
                    trackingboost = "keysearch:(\"" + keySearch + "\")^100 || ";
                } else {
                    trackingboost = "keysearch_unsigned:(\"" + keySearch + "\")^100 || ";
                }
                break;

            default:
                break;
        }
        if (!facetName.equals("") && facetName != null) {
            if (MyString.CheckSigned(keySearch)) {
                keySearch = "+(" + trackingboost + "rv_title:(\"" + keySearch + "\")^3 || rv_body:(\"" + keySearch + "\")^2 || category_index:(\"" + keySearch + "\")^1.5 || rv_title:(" + keySearch + ")^1.5 || rv_body:(" + keySearch + ") || category_index:(" + keySearch + ")^0.5)";
            } else {
                keySearch = "+(" + trackingboost + "rv_title:(\"" + keySearch + "\")^10 || rv_body:(\"" + keySearch + "\")^8 || category_index:(\"" + keySearch + "\")^5 || rv_title:(" + keySearch + ")^7 || rv_body:(" + keySearch + ")^6 || category_index:(" + keySearch + ")^4"
                        + " rv_title_unsigned:(\"" + keySearch + "\")^4 || rv_body_unsigned:(\"" + keySearch + "\")^3 || category_index_unsigned:(\"" + keySearch + "\")^2 || rv_title_unsigned:(" + keySearch + ")^2.5 || "
                        + "rv_body_unsigned:(" + keySearch + ")^1.5 || category_index_unsigned:(" + keySearch + "))";
            }
            if (type == 2) // seach chuoi facet, can ""
            {
                keySearch += " +(" + facetName + ":\"" + facetValue + "\")";
            } else if (type == 4)// type = 4: query ngay thang, ko can ""
            {
                keySearch += " +(" + facetName + ":" + facetValue + ")";
            }
        }

        str += keySearch;
        solrQuery.setQuery(str);

        // Facet
        solrQuery.setFacet(true);
        solrQuery.addFacetField("category");
        solrQuery.addFacetField("site");
        //solrQuery.addFacetField("location");
        solrQuery.setFacetLimit(10);
        solrQuery.setFacetMinCount(1);
        // End Facet

        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("rv_title");
        //solrQuery.addHighlightField("body");
        solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.setHighlightRequireFieldMatch(true);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
        solrQuery.set("fl", "id, rv_title, rv_body, photo, url, site, category, last_update");
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    QueryResponse OnMLT(String q, int pagesize, int sortedType) throws SolrServerException, MalformedURLException, UnsupportedEncodingException {
        SolrQuery query = new SolrQuery();

        // Facet
        query.setFacet(true);
        query.addFacetField("category");
        query.addFacetField("site");
        //query.addFacetField("location");
        query.setFacetLimit(10);
        query.setFacetMinCount(1);
        // End Facet

        query.setQueryType("/" + MoreLikeThisParams.MLT);
        query.set(MoreLikeThisParams.MATCH_INCLUDE, false);
        query.set(MoreLikeThisParams.MIN_DOC_FREQ, 1);
        query.set(MoreLikeThisParams.MIN_TERM_FREQ, 1);
        query.set(MoreLikeThisParams.SIMILARITY_FIELDS, "rv_title");
        if (sortedType == 1) {
            query.set(MoreLikeThisParams.BOOST, true);
            query.set(MoreLikeThisParams.QF, "{!boost b= recip(rord(timestamp),1,1000,1000)}");
        }

        query.setQuery("rv_title:" + ClientUtils.escapeQueryChars(q));

        //query.setQuery("rv_title:" + MyString.cleanQueryTerm(q));
        //query.setQuery(ClientUtils.escapeQueryChars(q));
        query.setStart(0);
        query.setRows(100);
        query.setHighlight(true);
        query.addHighlightField("rv_title");
        query.addHighlightField("rv_body");
        query.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        query.setHighlightSimplePost("</em>");
        query.set(HighlightParams.ALTERNATE_FIELD, "wk_title");
        query.set(HighlightParams.FRAGMENTER, "regex");
        query.setHighlightFragsize(70);
        query.set(HighlightParams.SLOP, "0.5");
        query.set(HighlightParams.REGEX, "[-,/\n\"']{20,200}");
        query.set("fl", "id, rv_title, rv_body, photo, url, site, category, last_update");
        QueryResponse rsp = server.query(query);
        return rsp;
    }

    String OnCheckSpelling(String q) throws org.apache.commons.httpclient.URIException, IOException {
        q = MyString.cleanQueryTerm(q);
        String result = "";
        HttpClient client = new HttpClient();
        //&spellcheck.build=true
        String url = "http://localhost:8983/solr/raovat/spell?q=" + q + "&spellcheck=true&spellcheck.collate=true&spellcheck.dictionary=jarowinkler&wt=json";
        url = URIUtil.encodeQuery(url);
        GetMethod get = new GetMethod(url);

        get.setRequestHeader(new Header("User-Agent", "localhost bot admin@localhost.com"));

        int status = client.executeMethod(get);
        String charSet = get.getResponseCharSet();
        if (charSet == null) {
            charSet = "UTF-8";
        }
        String body = convertStreamToString(get.getResponseBodyAsStream(), charSet);

        try {
            JSONObject json = (JSONObject) JSONSerializer.toJSON(body);
            JSONObject ob = json.getJSONObject("spellcheck");
            JSONArray cluster = ob.getJSONArray("suggestions");
            if (cluster.size() > 0) {
                result = cluster.getString(cluster.size() - 1);
            }
            get.releaseConnection();
            return result;
        } catch (Exception x) {
            return null;
        }
    }

    QueryResponse OnSearchAdvance(String TextAll, String TextExact, String TextOneOf, String TextNone, int start, int pagesize, int sortedType) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        String keySearch = "";
        keySearch = genKeySearch(TextAll, TextExact, TextOneOf, TextNone);
        solrQuery.setQueryType("dismax_unsigned");

        solrQuery.setQuery(keySearch);

        // Facet
        solrQuery.setFacet(true);
        solrQuery.addFacetField("category");
        solrQuery.addFacetField("site");
        //solrQuery.addFacetField("location");
        solrQuery.setFacetLimit(10);
        solrQuery.setFacetMinCount(1);
        // End Facet


        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("rv_title");
        solrQuery.addHighlightField("rv_body");
        solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        solrQuery.setHighlightSimplePost("</em>");
        //solrQuery.setHighlightRequireFieldMatch(true);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
        solrQuery.set("fl", "id, rv_title, rv_body, photo, url, site, category, last_update");
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    private String genKeySearch(String TextAll, String TextExact, String TextOneOf, String TextNone) {
        String keySearch = "";
        // Có tất cả các từ
        if (TextAll != null && TextAll.trim().length() > 0) {
            String[] arrTextAll = TextAll.split(" ");
            for (int i = 0; i < arrTextAll.length; i++) {
                keySearch += "+" + arrTextAll[i] + " ";
            }
        }
        // có chứa cụm từ này
        if (TextExact != null && TextExact.trim().length() > 0) {
            keySearch += "+\"" + TextExact + "\" ";
        }

        // Có ít nhất 1 trong các từ này
        if (TextOneOf != null && TextOneOf.trim().length() > 0) {
            String[] arrTextOneOf = TextOneOf.split(" ");
            for (int i = 0; i < arrTextOneOf.length; i++) {
                if (i == 0) {
                    keySearch += "+(";
                }
                if (i >= 1) {
                    keySearch += "OR ";
                }
                keySearch += arrTextOneOf[i] + " ";
                if (i == arrTextOneOf.length - 1) {
                    keySearch += ") ";
                }
            }
        }
        // Không có các từ này
        if (TextNone != null && TextNone.trim().length() > 0) {
            String[] arrTextNone = TextNone.split(" ");
            for (int i = 0; i < arrTextNone.length; i++) {
                keySearch += "NOT " + arrTextNone[i] + " ";
            }
        }
        return keySearch;
    }

    public String convertStreamToString(InputStream is, String encode) throws IOException {
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, encode));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                is.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
