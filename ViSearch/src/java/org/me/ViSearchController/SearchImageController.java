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
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.MoreLikeThisParams;
import org.me.SolrConnection.SolrJConnection;
import org.me.Utils.MyString;
import org.me.Utils.Paging;
import org.me.dto.FacetDateDTO;

/**
 *
 * @author tuandom
 */
public class SearchImageController extends HttpServlet {
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
        String sPaging = "";
        int type = -1;
        int QTime = 0;
        try {

            if (request.getParameter("currentpage") != null) {
                currentpage = Integer.parseInt(request.getParameter("currentpage"));
            }

            server = SolrJConnection.getSolrServer("image");
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
                            if (sCollation.equals("")==false) {
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
                        //listFacetDate = NewestUpdateDocument(keySearch, "25");
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
                       // listFacetDate = NewestUpdateDocument(keySearch, "25");
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
                        //listFacetDate = NewestUpdateDocument(keySearch, "25");

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
                sPaging = Paging.getPaging(numpage, pagesize, currentpage, keySearch, "/ViSearch/SearchImageController", type);
                request.setAttribute("Docs", docs);
                request.setAttribute("ListFacetDate", listFacetDate);
                request.setAttribute("Pagging", sPaging);
                request.setAttribute("NumRow", numRow);
                request.setAttribute("NumPage", numpage);
                request.setAttribute("ListFacet", listFacet);
            }
            String url = "/image.jsp";
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

    QueryResponse OnSearchSubmit(String keySearch, int start, int pagesize) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        //solrQuery.setQueryType("dismax");

        solrQuery.setQuery(keySearch);

        // Facet
        solrQuery.setFacet(true);
        solrQuery.addFacetField("category");
        solrQuery.setFacetLimit(10);
        solrQuery.setFacetMinCount(1);
        // End Facet


        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("site_title");
        solrQuery.addHighlightField("site_body");
        solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.setHighlightRequireFieldMatch(true);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    void NewestDocument22(String keySearch, String numDays) throws SolrServerException {

        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", keySearch);
        params.set("facet", true);
        params.set("field", "last_update");
        params.set("field", "site");
        params.set("facet.date.start", "NOW/DAY-5DAYS");
        params.set("facet.date.end", "NOW/DAY%2B1DAY");
        params.set("facet.date.gap", "%2B1DAY");


        QueryResponse rsp = server.query(params);

        Map<String, Integer> sdl = rsp.getFacetQuery();
        List<FacetField> lfc1 = rsp.getFacetFields();
        List<FacetField> lfc = rsp.getFacetDates();
        FacetField fc = rsp.getFacetDate("last_update");
        SolrDocumentList dl = rsp.getResults();
        int a = 8;
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

    QueryResponse OnSearchSubmitStandard(String keySearch, String faceName, String faceValue, int start, int pagesize) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        if (!faceName.equals("") && faceName != null) {
            keySearch = "+(site_title:(" + keySearch + ") site_body:(" + keySearch + ") category_index:(" + keySearch + ")) + " + faceName + ":\"" + faceValue + "\"";
        }
        solrQuery.setQuery(keySearch);

       // Facet
        solrQuery.setFacet(true);
        solrQuery.addFacetField("category");
        solrQuery.setFacetLimit(10);
        solrQuery.setFacetMinCount(1);
        // End Facet

        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("site_title");
        //solrQuery.addHighlightField("body");
        solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.setHighlightRequireFieldMatch(true);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    QueryResponse OnMLT(String q, int start, int pagesize) throws SolrServerException, MalformedURLException, UnsupportedEncodingException {
        //q = URLDecoder.decode(q, "UTF-8");
        SolrQuery query = new SolrQuery();

        // Facet
        query.setFacet(true);
        query.addFacetField("category");
        query.setFacetLimit(10);
        query.setFacetMinCount(1);
        // End Facet

        query.setQueryType("/" + MoreLikeThisParams.MLT);
        query.set(MoreLikeThisParams.MATCH_INCLUDE, false);
        query.set(MoreLikeThisParams.MIN_DOC_FREQ, 1);
        query.set(MoreLikeThisParams.MIN_TERM_FREQ, 1);
        query.set(MoreLikeThisParams.SIMILARITY_FIELDS, "site_title");

        query.setQuery("site_title:" + MyString.cleanQueryTerm(q));
        //query.setQuery(ClientUtils.escapeQueryChars(q));
        query.setStart(start);
        query.setRows(pagesize);
        query.setHighlight(true);
        query.addHighlightField("site_title");
        query.addHighlightField("site_body");
        query.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        query.setHighlightSimplePost("</em>");
        query.setHighlightRequireFieldMatch(true);
        QueryResponse rsp = server.query(query);
        return rsp;
    }

    String OnCheckSpelling(String q) throws org.apache.commons.httpclient.URIException, IOException {
        String result = "";
        HttpClient client = new HttpClient();
        //&spellcheck.build=true
        String url = "http://localhost:8983/solr/image/spell?q=" + q + "&spellcheck=true&spellcheck.collate=true&spellcheck.dictionary=jarowinkler&wt=json";
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
