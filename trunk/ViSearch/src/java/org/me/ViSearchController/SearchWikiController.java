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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MoreLikeThisParams;
import org.apache.solr.common.params.StatsParams;
import org.me.SolrConnection.SolrJConnection;
import org.me.Utils.MyString;
import org.me.Utils.Paging;
import org.me.Utils.SearchLocation;
import org.me.dto.FacetDateDTO;

/**
 *
 * @author VinhPham
 */
public class SearchWikiController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    SolrServer server;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SolrServerException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        SolrDocumentList docs = new SolrDocumentList();
        String keySearch = "";
        int pagesize = 10;
        int currentpage = 1;
        int type = -1;
        int QTime = 0;
        long numRow = 0;
        String sPaging = "/ViSearch/SearchWikiController?";
        List<FacetField> listFacet = null;
        ArrayList<FacetDateDTO> listFacetDate = null;
        int sortedType = 0;
        try {
            //<get-parameter defaultstate="collapsed">
            if (request.getParameter("currentpage") != null) {
                currentpage = Integer.parseInt(request.getParameter("currentpage"));
            }

            server = SolrJConnection.getSolrServer("wikipedia");
            int start = (currentpage - 1) * pagesize;

            if (request.getParameter("type") != null) {
                type = Integer.parseInt(request.getParameter("type"));
            }

            if (request.getParameter("SortedType") != null) {
                sortedType = Integer.parseInt(request.getParameter("SortedType"));
                sPaging += "SortedType=" + sortedType;
            }

            //</get-parameter>
            if (request.getParameter("KeySearch") != null) {

                QueryResponse rsp;
                Map<String, Map<String, List<String>>> highLight;

                keySearch = URLDecoder.decode(request.getParameter("KeySearch"), "UTF-8");
//                out.print(keySearch);
//                out.print(URLEncoder.encode(keySearch, "UTF-8"));
//                out.print(URLDecoder.decode(keySearch, "UTF-8"));
                sPaging += "&KeySearch=" + URLEncoder.encode(keySearch, "UTF-8");

                switch (type) {
                    case 0:
                        if (request.getParameter("sp") != null) {
                            String sCollation = OnCheckSpelling(keySearch);

                            if (sCollation != null && sCollation.equals("") == false) {
                                request.setAttribute("Collation", sCollation);
                            }
                        }

                        rsp = OnSearchSubmit(ClientUtils.escapeQueryChars(keySearch), start, pagesize, sortedType);
                        docs = rsp.getResults();
                        highLight = rsp.getHighlighting();
                        request.setAttribute("HighLight", highLight);
                        QTime = rsp.getQTime();
                        sPaging += "&type=0";
                        break;
                    case 1:
                        rsp = OnMLT(keySearch, start, pagesize, sortedType);
                        docs = rsp.getResults();
                        highLight = rsp.getHighlighting();
                        if (highLight != null) {
                            request.setAttribute("HighLight", highLight);
                        }
                        QTime = rsp.getQTime();
                        sPaging += "&type=1";
                        break;
                    case 2:
                        String facetName = "";
                        String facetValue = "";
                        sPaging += "&type=2";
                        if (request.getParameter("FacetName") != null) {
                            facetName = request.getParameter("FacetName");
                            facetValue = request.getParameter("FacetValue");
                            sPaging += "&FacetName=" + facetName;
                            sPaging += "&FacetValue=" + facetValue;
                        }
                        rsp = OnSearchSubmitStandard(keySearch, facetName, facetValue, start, pagesize, sortedType);
                        docs = rsp.getResults();
                        highLight = rsp.getHighlighting();
                        request.setAttribute("HighLight", highLight);
                        QTime = rsp.getQTime();
                        break;
                    case 3:
                        facetName = "";
                        facetValue = "";
                        sPaging += "&type=3";
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
                        rsp = OnSearchSubmitStandard(keySearch, facetName, facetValue, start, pagesize, sortedType);

                        highLight = rsp.getHighlighting();
                        request.setAttribute("HighLight", highLight);
                        docs = rsp.getResults();
                        QTime = rsp.getQTime();

                        break;
                    default:
                        break;
                }
            }

            request.setAttribute("QTime", String.valueOf(1.0 * QTime / 1000));
            request.setAttribute("KeySearch", keySearch);
            request.setAttribute("SortedType", sortedType);
            request.setAttribute("Type", type);
            if (docs != null) {
                numRow = docs.getNumFound();
                //numRow = docs.size();
                int numpage = (int) (numRow / pagesize);

                if (numRow % pagesize > 0) {
                    numpage++;
                }

                sPaging = Paging.getPaging(numpage, pagesize, currentpage, sPaging);
                request.setAttribute("Docs", docs);
                request.setAttribute("ListFacetDate", listFacetDate);
                request.setAttribute("Pagging", sPaging);
                request.setAttribute("NumRow", numRow);
                request.setAttribute("NumPage", numpage);
                request.setAttribute("ListFacet", listFacet);
            }
            String url = "/index.jsp";
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

    QueryResponse OnSearchSubmit(String keySearch, int start, int pagesize, int sortedType) throws SolrServerException, MalformedURLException {
        SolrQuery solrQuery = new SolrQuery();
        keySearch = MyString.cleanQueryTerm(keySearch);
        String query = "";
        switch(sortedType)
        {
            case 0:
                query = "";
                break;
            case 1:
                //query = "{!boost b=recip(ms(NOW,timestamp),3.16e-11,1,1)}";
                query = "{!boost b=recip(rord(timestamp),1,1000,1000)}";
                break;
            case 2:
                 if (MyString.CheckSigned(keySearch))
                     query = "keysearch:(\"" + keySearch + "\")^100 || ";
                 else
                     query = "keysearch_unsigned:(\"" + keySearch + "\")^100 || ";
                break;

            default:
                break;
        }

        //Gop chung co dau va ko dau
        //query += "wk_title:(\"" + keySearch + "\")^3 || (\"" + keySearch + "\")^2 || wk_title:(" + keySearch + ")^1.5 ||(" + keySearch + ")";

        if (MyString.CheckSigned(keySearch)) {
            query += "wk_title:(\"" + keySearch + "\")^10 || wk_text:(\"" + keySearch + "\")^5 || wk_title:(" + keySearch + ")^1.5 || wk_text:(" + keySearch + ")";
        } else {
            query += "wk_title:(\"" + keySearch + "\")^20 || wk_text:(\"" + keySearch + "\")^8 || "
                    + "wk_title:(" + keySearch + ")^7 || wk_text:(" + keySearch + ")^6 || "
                    + "wk_title_unsigned:(\"" + keySearch + "\")^10 || wk_text_unsigned:(\"" + keySearch + "\")^2 || wk_title_unsigned:(" + keySearch + ")^5 || wk_text_unsigned:(" + keySearch + ")";
        }

        SearchLocation searchLocation = new SearchLocation();
        SolrDocumentList list = searchLocation.CheckLocation(keySearch);
        for (SolrDocument doc : list) {
            String slocation = doc.getFirstValue("location").toString();
            query += String.format(" || wk_title:(\"%s\")^30 || wk_text:(\"%s\")^5 ", slocation, slocation);
        }

        solrQuery.setQuery(query);
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("wk_title");
        solrQuery.addHighlightField("wk_text");
        solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.setHighlightRequireFieldMatch(true);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
        solrQuery.set("fl", "id, wk_title, wk_text, timestamp");
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    QueryResponse OnSearchSubmitStandard(String keySearch, String facetName, String facetValue, int start, int pagesize, int sortedType) throws SolrServerException {
        String str = "";
        String trackingboost = "";
         switch(sortedType)
        {
            case 0:
                str = "";
                trackingboost = "";
                break;
            case 1:
                str = "{!boost b= recip(rord(timestamp),1,1000,1000)}";
                trackingboost = "";
                break;
            case 2:
                 if (MyString.CheckSigned(keySearch))
                     trackingboost = "keysearch:(\"" + keySearch + "\")^100 || ";
                 else
                     trackingboost = "keysearch_unsigned:(\"" + keySearch + "\")^100 || ";
                break;

            default:
                break;
        }
        SolrQuery solrQuery = new SolrQuery();
        String query = keySearch;
        if (facetName.equals("") == false && facetName != null) {
            if (MyString.CheckSigned(keySearch)) {
                query += "+(" + trackingboost + "wk_title:(\"" + keySearch + "\")^3 || wk_text:(\"" + keySearch + "\")^2 || wk_title:(" + keySearch + ")^1.5 || wk_text:(" + keySearch + "))";
            } else {
                query += "+(" + trackingboost + "wk_title:(\"" + keySearch + "\")^10 || wk_text:(\"" + keySearch + "\")^8 || "
                        + "wk_title:(" + keySearch + ")^7 || wk_text:(" + keySearch + ")^6 || "
                        + "wk_title_unsigned:(\"" + keySearch + "\")^3 || wk_text_unsigned:(\"" + keySearch + "\")^2 || wk_title_unsigned:(" + keySearch + ")^2 || wk_text_unsigned:(" + keySearch + "))";
            }
            query += " +(" + facetName + ":" + facetValue + ")";
        }

        str += query;
        solrQuery.setQuery(str);
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("wk_title");
        solrQuery.addHighlightField("wk_text");
        solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.setHighlightRequireFieldMatch(true);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
        solrQuery.set("fl", "id, wk_title, wk_text, timestamp");
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    ArrayList<FacetDateDTO> NewestUpdateDocument(String keySearch, String startDay, String endDay) throws SolrServerException, org.apache.commons.httpclient.URIException, IOException {
        HttpClient client = new HttpClient();

        //String url = "http://localhost:8983/solr/wikipedia/select/?q=" + keySearch + "&facet=true&facet.date=timestamp&facet.date.start=NOW/DAY-" + numDays + "DAYS&facet.date.end=NOW/DAY%2B1DAY&facet.field=timestamp&facet.limit=10&wt=json";
        String url = "";
        keySearch = URIUtil.encodeQuery(keySearch);
        if (endDay != null && "".equals(endDay.trim()) == false) {
            url = "http://localhost:8983/solr/wikipedia/select/?q=" + keySearch + "&facet=true&facet.date=timestamp&facet.date.start=NOW/DAY-" + startDay + "DAYS&facet.date.end=NOW/DAY-" + endDay + "DAYS&facet.date.gap=%2B1DAY&wt=json";
        } else {
            url = "http://localhost:8983/solr/wikipedia/select/?q=" + keySearch + "&facet=true&facet.date=timestamp&facet.date.start=NOW/DAY-" + startDay + "DAYS&facet.date.end=NOW/DAY&facet.date.gap=%2B1DAY&wt=json";
        }

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
            JSONObject location = ob.getJSONObject("facet_dates");


            // Vi moi chi xai 1 field Location nen lay luon
            JSONObject last_update = location.getJSONObject("timestamp");
            JSONArray jsonarr = null;
            last_update.toJSONArray(jsonarr);
            //JSONArray arrTime = location.getJSONArray("timestamp");
            ArrayList<FacetDateDTO> myArr = new ArrayList<FacetDateDTO>();

//            if (last_update.size() > 0) {
//                FacetDateDTO fD = null;
//                for (int i = 0; i < last_update.size(); i++) {
//                    if (i % 2 == 0)// Phan tu chan la Ngay (Value)
//                    {
//                        fD = new FacetDateDTO();
//                        fD.setDateTime(last_update.get(i).toString());
//                    } else //  Phan tu le là số (Count)
//                    {
//                        fD.setCount(last_update.get(i).toString());
//                        myArr.add(fD);
//                    }
//                }
//            }
            //  ArrayList arrL = (ArrayList) last_update.values();
            String test = last_update.toString();
            test = test.substring(1, test.length() - 1);
            String[] strArr = test.split(",");

            for (int i = 0; i < strArr.length - 2; i++) {
                String[] value = strArr[i].split("\":");
                if (value[1].toString().equals("0") == false) {
                    String val0 = value[0].substring(1);
                    FacetDateDTO fD = new FacetDateDTO();
                    fD.setDateTime(val0.toString());
                    fD.setCount(value[1]);
                    myArr.add(fD);
                }
            }
            // if (last_update.size() > 0) {
            //     FacetDateDTO fD = null;
            //     for (int i = 0; i < last_update.size()-2; i++) {
            //       fD = new FacetDateDTO();
            //last_update.g
            // last_update.
            //       String label = last_update.getJSONObject(i).getString("labels");
            //String docs = last_update.getJSONObject(i).getString("docs");
            // last_update.get
            //.get
            //fD.setCount(last_update.get(i).toString());
            // myArr.add(fD);
            //    }
            //  }
            get.releaseConnection();
            return myArr;
        } catch (Exception x) {
            return null;
        }

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

    public List<ClusterRecord> cluster(String query, int rows) throws SolrServerException, IOException {

        List<ClusterRecord> result = new ArrayList<ClusterRecord>();

        HttpClient client = new HttpClient();
        String url = "http://localhost:8983/solr/wikipedia/clustering?q=" + query + "&rows=" + rows + "&wt=json";

        url = URIUtil.encodeQuery(url);
        GetMethod get = new GetMethod(url);

        get.setRequestHeader(new Header("User-Agent", "localhost bot admin@localhost.com"));
        //RequestResult result=new RequestResult();
        int status = client.executeMethod(get);
        String charSet = get.getResponseCharSet();
        if (charSet == null) {
            charSet = "UTF-8";
        }
        String body = convertStreamToString(get.getResponseBodyAsStream(), charSet);

        JSONObject json = (JSONObject) JSONSerializer.toJSON(body);

        JSONArray cluster = json.getJSONArray("clusters");
        for (int i = 0; i < cluster.size(); i++) {
            String label = cluster.getJSONObject(i).getString("labels");
            String docs = cluster.getJSONObject(i).getString("docs");

            ClusterRecord cr = new ClusterRecord();
            cr.setLabel(label);
            cr.setDocs(docs);

            result.add(cr);
        }
        get.releaseConnection();
        return result;

    }

    String OnCheckSpelling(String q) throws SolrServerException, URIException, HttpException, IOException {
        q = MyString.cleanQueryTerm(q);
        String result = "";
        HttpClient client = new HttpClient();
        //&spellcheck.build=true
        String url = "http://localhost:8983/solr/wikipedia/spell?q=" + q + "&spellcheck=true&spellcheck.collate=true&spellcheck.dictionary=jarowinkler&wt=json";
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

    QueryResponse OnMLT(String q, int start, int pagesize, int sortedType) throws SolrServerException, MalformedURLException, UnsupportedEncodingException {
        //boost b= }";
        SolrQuery query = new SolrQuery();
        query.setQueryType("/" + MoreLikeThisParams.MLT);
        query.set(MoreLikeThisParams.MATCH_INCLUDE, false);
        query.set(MoreLikeThisParams.MIN_DOC_FREQ, 1);
        query.set(MoreLikeThisParams.MIN_TERM_FREQ, 1);
        if (sortedType == 1) {
            query.set(MoreLikeThisParams.BOOST, true);
            query.set(MoreLikeThisParams.QF, "{!boost b= recip(rord(timestamp),1,1000,1000)}");
        }
        query.set(MoreLikeThisParams.SIMILARITY_FIELDS, "wk_title");
        query.setQuery("wk_title:" + MyString.cleanQueryTerm(q));
        //query.setQuery(ClientUtils.escapeQueryChars(q));
        query.setStart(start);
        query.setRows(pagesize);
        query.setHighlight(true);
        query.addHighlightField("wk_title");
        query.addHighlightField("wk_text");
        query.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        query.setHighlightSimplePost("</em>");
        query.setHighlightRequireFieldMatch(true);
        query.set("fl", "id, wk_title, wk_text, timestamp");
        QueryResponse rsp = server.query(query);
        return rsp;
    }

    public void OnStats(ArrayList<String> fields, String facet) throws SolrServerException, IOException, Exception {

        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setRows(0);
        query.set(StatsParams.STATS, true);
        query.set(StatsParams.STATS_FIELD, "id");
        query.set(StatsParams.STATS_FACET, "timestamp");
        query.setParam("indent", true);

        QueryResponse rsp = server.query(query);

        Map<String, FieldStatsInfo> map = rsp.getFieldStatsInfo();
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
        try {
            processRequest(request, response);






        } catch (SolrServerException ex) {
            Logger.getLogger(SearchWikiController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        try {
            processRequest(request, response);
        } catch (SolrServerException ex) {
            Logger.getLogger(SearchWikiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";


    }// </editor-fold>

    private String CountNumDay(String strDate) {
        // Lay ngay  hien tai
        Date now = new Date();
        Calendar cl1 = Calendar.getInstance();
        cl1.setTime(now);

        // Lay ngay nhap vao
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = new Date();

        try {
            date1 = format.parse(strDate);
            Calendar cl2 = Calendar.getInstance();
            cl2.setTime(date1);

            // Tinh khoang cach so voi ngay hien tai
            int count = cl1.get(Calendar.DAY_OF_YEAR) - cl2.get(Calendar.DAY_OF_YEAR);
            return Integer.toString(count);
        } catch (ParseException ex) {
            Logger.getLogger(SearchWikiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

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
}
