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
import java.util.ArrayList;
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
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MoreLikeThisParams;
import org.apache.solr.common.params.StatsParams;
import org.me.SolrConnection.SolrJConnection;
import org.me.Utils.Paging;
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

    QueryResponse OnSearchSubmit(String keySearch, int start, int pagesize) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        //solrQuery.setQueryType("dismax");

        solrQuery.setQuery("wk_title:(\""+keySearch + "\")^3 (\""+keySearch + "\")^2 wk_title:("+keySearch + ")^1.5 ("+keySearch + ")");

         // Facet
        solrQuery.setFacet(true);

        // Cái này chắc ko cần, nhưng cứ để cho chắc
        solrQuery.addFacetQuery("wk_title:(\""+keySearch + "\")^3 (\""+keySearch + "\")^2 wk_title:(\""+keySearch + "\")^1.5 ("+keySearch + ")");
        solrQuery.addFacetField("wk_title");
        //solrQuery.addFacetField("username");
        solrQuery.setFacetLimit(10);
        solrQuery.setFacetMinCount(1);
        // End Facet

        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("wk_title");
        solrQuery.addHighlightField("wk_text");
        solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.setHighlightRequireFieldMatch(true);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    QueryResponse OnSearchSubmitStandard(String keySearch, String faceName, String faceValue, int start, int pagesize) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        if (faceName != "" && faceName != null) {
            keySearch = "+(wk_title:(" + keySearch + ") || wk_text:(" + keySearch + ")) +(" + faceName + ":\"" + faceValue + "\")";
        }

        solrQuery.setQuery(keySearch);
         // Facet
        solrQuery.setFacet(true);
        solrQuery.addFacetField("wk_title");
        //solrQuery.addFacetField("username");
        solrQuery.setFacetLimit(10);
        solrQuery.setFacetMinCount(1);
        // End Facet

        //solrQuery.setFacet(true);
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("wk_title");
        solrQuery.addHighlightField("wk_text");
        solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.setHighlightRequireFieldMatch(true);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    ArrayList<FacetDateDTO> NewestUpdateDocument(String keySearch, String numDays) throws SolrServerException, org.apache.commons.httpclient.URIException, IOException {
        HttpClient client = new HttpClient();

        String url = "http://localhost:8983/solr/wikipedia/select/?q=" + keySearch + "&facet=true&facet.date=timestamp&facet.date.start=NOW/DAY-" + numDays + "DAYS&facet.date.end=NOW/DAY%2B1DAY&facet.field=timestamp&facet.limit=10&wt=json";
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
            JSONArray last_update = location.getJSONArray("timestamp");
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
        }
        catch(Exception x)
        {
            return null;
        }
    }

    QueryResponse OnMLT(String q, int start, int pagesize) throws SolrServerException, MalformedURLException, UnsupportedEncodingException {
        //q = URLDecoder.decode(q, "UTF-8");
        SolrQuery query = new SolrQuery();
        query.setQueryType("/" + MoreLikeThisParams.MLT);
        query.set(MoreLikeThisParams.MATCH_INCLUDE, false);
        query.set(MoreLikeThisParams.MIN_DOC_FREQ, 1);
        query.set(MoreLikeThisParams.MIN_TERM_FREQ, 1);
        query.set(MoreLikeThisParams.SIMILARITY_FIELDS, "wk_title");
        //query.setQuery("title:" + ClientUtils.escapeQueryChars(q));
        query.setQuery(ClientUtils.escapeQueryChars(q));
        query.setStart(start);
        query.setRows(pagesize);
        query.setHighlight(true);
        query.addHighlightField("wk_title");
        query.addHighlightField("wk_text");
        query.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        query.setHighlightSimplePost("</em>");
        query.setHighlightRequireFieldMatch(true);
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SolrServerException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        SolrDocumentList docs = new SolrDocumentList();
        String keySearch = "";
        int pagesize = 10;
        int currentpage = 1;
        long numRow = 0;
        String sPaging = "";
        int type = -1;
        int QTime = 0;

        try {

            if (request.getParameter("currentpage") != null) {
                currentpage = Integer.parseInt(request.getParameter("currentpage"));
            }

            server = SolrJConnection.getSolrServer("wikipedia");
            int start = (currentpage - 1) * pagesize;

            if (request.getParameter("type") != null) {
                type = Integer.parseInt(request.getParameter("type"));
            }

             List<FacetField> listFacet = null;
              ArrayList<FacetDateDTO> listFacetDate = null;
            if (request.getParameter("KeySearch") != null) {
                keySearch = request.getParameter("KeySearch");
                QueryResponse rsp;
                Map<String, Map<String, List<String>>> highLight;

                switch (type) {
                    case 0:
                        if (request.getParameter("sp") != null) {
                            String sCollation = OnCheckSpelling(keySearch);

                            if (sCollation != "") {
                                request.setAttribute("Collation", sCollation);
                            }
                        }

                        rsp = OnSearchSubmit(keySearch, start, pagesize);
                        docs = rsp.getResults();
                        highLight = rsp.getHighlighting();
                        request.setAttribute("HighLight", highLight);
                        QTime = rsp.getQTime();

                        // Get Facet
                        listFacet = rsp.getFacetFields();
                        listFacetDate = NewestUpdateDocument(keySearch, "25");
                        break;
                    case 1:
                        rsp = OnMLT(keySearch, start, pagesize);
                        docs = rsp.getResults();
                        highLight = rsp.getHighlighting();
                        if (highLight != null) {
                            request.setAttribute("HighLight", highLight);
                        }
                        QTime = rsp.getQTime();

                         // Get Facet
                        listFacet = rsp.getFacetFields();
                        listFacetDate = NewestUpdateDocument(keySearch, "25");
                        break;
                    case 2:
                        String faceName = "";
                        String faceValue = "";
                        if (request.getParameter("FaceName") != null) {
                            faceName = request.getParameter("FaceName");
                            faceValue = request.getParameter("FaceValue");
                        }
                        rsp = OnSearchSubmitStandard(keySearch, faceName, faceValue, start, pagesize);
                        docs = rsp.getResults();
                        highLight = rsp.getHighlighting();
                        request.setAttribute("HighLight", highLight);
                        QTime = rsp.getQTime();

                         // Get Facet
                        listFacet = rsp.getFacetFields();
                        listFacetDate = NewestUpdateDocument(keySearch, "25");
                        break;
                    default:
                        break;
                }
            }

            request.setAttribute("QTime", String.valueOf(1.0 * QTime / 1000));
            request.setAttribute("KeySearch", keySearch);
            if (docs != null) {
                numRow = docs.getNumFound();
                int numpage = (int) (numRow / pagesize);

                if (numRow % pagesize > 0) {
                    numpage++;
                }

                sPaging = Paging.getPaging(numpage, pagesize, currentpage, keySearch, "/ViSearch/SearchWikiController", type);
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
}
