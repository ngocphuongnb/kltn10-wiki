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
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.HighlightParams;
import org.apache.solr.common.params.MoreLikeThisParams;
import org.me.SolrConnection.SolrJConnection;
import org.me.Utils.MySegmenter;
import org.me.Utils.MyString;
import org.me.Utils.Paging;

/**
 *
 * @author tuandom
 */
public class SearchAllController extends HttpServlet {

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
        String sPaging = "/ViSearch/SearchAllController?";

        try {
            if (request.getParameter("currentpage") != null) {
                currentpage = Integer.parseInt(request.getParameter("currentpage"));
            }

            server = SolrJConnection.getSolrServer("all");
            int start = (currentpage - 1) * pagesize;

            if (request.getParameter("type") != null) {
                type = Integer.parseInt(request.getParameter("type"));
                sPaging += "type=" + type;
            }

            if (request.getParameter("SortedType") != null) {
                sortedType = Integer.parseInt(request.getParameter("SortedType"));
                sPaging += "&SortedType=" + sortedType;
            }

            if (request.getParameter("KeySearch") != null) {
                keySearch = request.getParameter("KeySearch");
                sPaging += "&KeySearch=" + keySearch;
            }
            QueryResponse rsp;
            Map<String, Map<String, List<String>>> highLight;

            switch (type) {
                case 0:
                    if (request.getParameter("sp") != null) {
                        String sCollation = OnCheckSpelling(keySearch);
                        if (!sCollation.equals("")) {
                            request.setAttribute("Collation", sCollation);
                        }
                        sPaging += "&sp=" + request.getParameter("sp").toString();
                    }

                    rsp = OnSearchSubmit(keySearch, start, pagesize, sortedType);
                    docs = rsp.getResults();
                    highLight = rsp.getHighlighting();
                    request.setAttribute("HighLight", highLight);
                    QTime = rsp.getQTime();
                    // Get Facet
                    break;
                case 1:
                    rsp = OnMLT(keySearch, pagesize, sortedType);
                    docs = rsp.getResults();
                    highLight = rsp.getHighlighting();
                    if (highLight != null) {
                        request.setAttribute("HighLight", highLight);
                    }
                    QTime = rsp.getQTime();

//                    for (int i = 0; i < docs.size() - 1; i++) {
//                        for (int j = i + 1; j < docs.size(); j++) {
//                            String title1 = docs.get(i).getFirstValue("title").toString();
//                            String title2 = docs.get(j).getFirstValue("title").toString();
//                            if (title1.trim().equals(title2.trim())) {
//                                Date date1 = (Date) docs.get(i).getFieldValue("last_update");
//                                Date date2 = (Date) docs.get(j).getFieldValue("last_update");
//                                if (date1.compareTo(date2) >= 0) {
//                                    docs.remove(j);
//                                    j--;
//                                } else {
//                                    docs.remove(i);
//                                    i--;
//                                    break;
//                                }
//                            }
//                        }
//                    }

                    int idem = Math.min(20, docs.size());
                    while (docs.size() > idem) {
                        docs.remove(idem);
                    }
                    // Get Facet
                    //listFacet = rsp.getFacetFields();
                    break;
                case 2: // facet
                case 4: // query date

                    break;
                case 3:

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
                if (type != 1) {
                    sPaging = Paging.getPaging(numpage, pagesize, currentpage, sPaging);
                    request.setAttribute("NumPage", numpage);
                } else {
                    sPaging = "20 kết quả tốt nhất trong " + numRow + " kết quả tìm được";
                }
                request.setAttribute("Pagging", sPaging);
                request.setAttribute("NumRow", numRow);
            }
            String url = "/all.jsp";
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



    QueryResponse OnSearchSubmit(String keySearch, int start, int pagesize, int sortedType) throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        keySearch = keySearch.replaceAll("\"", "\\\"");
        String query = "";
        switch (sortedType) {
            case 0:
                query = "";
                break;
            case 1:
               // ko su dung
                break;
            case 2:
                if (MyString.CheckSigned(keySearch)) {
                    query = "keysearch:(\"" + keySearch + "\")^100 || ";
                } else {
                    query = "keysearch:(\"" + keySearch + "\")^100 || keysearch_unsigned:(\"" + keySearch + "\")^100 || ";
                }
                break;

            case 3: // chi ap dung cho có dấu
                MySegmenter myseg = new MySegmenter();
                String seg = myseg.getwordBoundaryMark(keySearch);
                seg = seg.replaceAll("[\\[\\]]", "\"");
                query = "title:(\"" + keySearch + "\")^20 || body:(\"" + keySearch + "\")^15"; //  tuyet doi
                query += String.format(" || title:(%s)^10 || body:(%s)^5 ", seg, seg); // tach tu tieng viet
                break;
            default:
                break;
        }

        if (sortedType != 3) {
            if (MyString.CheckSigned(keySearch)) {
                query += "title:(\"" + keySearch + "\")^5 || title:(" + keySearch + ")^3 || body:(\"" + keySearch + "\")^2.5 || body:(" + keySearch + ")^2";
            } else {
                query += "title:(\"" + keySearch + "\")^5 || title:(" + keySearch + ")^3 || "
                        + "title_unsigned:(\"" + keySearch + "\")^4 || title_unsigned:(" + keySearch + ")^2 || "
                        + "body:(\"" + keySearch + "\")^2.5 || body:(" + keySearch + ")^1.6 || "
                        + "body_unsigned:(\"" + keySearch + "\")^2 || body_unsigned:(" + keySearch + ")^1.4";
            }
        }

        solrQuery.setQuery(query);
        // Facet
        // End Facet

        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("title");
        solrQuery.addHighlightField("body");
        solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.setHighlightRequireFieldMatch(true);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }

    QueryResponse OnSearchAdvance(String TextAll, String TextExact, String TextOneOf, String TextNone, int start, int pagesize, int sortedType) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();

        String query = "";
        String keySearch = genKeySearch(TextAll, TextExact, TextOneOf, TextNone);

        query += "title:(\"" + keySearch + "\")^5 || title:(" + keySearch + ")^3 || "
                + "title_unsigned:(\"" + keySearch + "\")^4 || title_unsigned:(" + keySearch + ")^2 || "
                + "body:(\"" + keySearch + "\")^2.5 || body:(" + keySearch + ")^1.6 || "
                + "body_unsigned:(\"" + keySearch + "\")^2 || body_unsigned:(" + keySearch + ")^1.4";
        solrQuery.setQuery(query);

        // Facet
        // End Facet

        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("title");
        solrQuery.addHighlightField("body");
        solrQuery.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.setHighlightRequireFieldMatch(true);
        solrQuery.setStart(start);
        solrQuery.setRows(pagesize);
        QueryResponse rsp = server.query(solrQuery);
        return rsp;
    }


    QueryResponse OnMLT(String q, int pagesize, int sortedType) throws SolrServerException, MalformedURLException, UnsupportedEncodingException {
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
        query.set(MoreLikeThisParams.SIMILARITY_FIELDS, "title");
        if (sortedType == 1) {
            query.set(MoreLikeThisParams.BOOST, true);
          // query.set(MoreLikeThisParams.QF, "{!boost b= recip(rord(timestamp),1,1000,1000)}");
        }

        query.setQuery("title:" + MyString.cleanQueryTerm(q));
        //query.setQuery(ClientUtils.escapeQueryChars(q));
        query.setStart(0);
        query.setRows(100);
        query.setHighlight(true);
        query.addHighlightField("title");
        query.addHighlightField("body");
        query.setHighlightSimplePre("<em style=\"background-color:#FF0\">");
        query.setHighlightSimplePost("</em>");
        //query.set(HighlightParams.ALTERNATE_FIELD, "wk_title");
        query.set(HighlightParams.FRAGMENTER, "regex");
        query.setHighlightFragsize(70);
        query.set(HighlightParams.SLOP, "0.5");
        query.set(HighlightParams.REGEX, "[-,/\n\"']{20,200}");
        QueryResponse rsp = server.query(query);
        return rsp;
    }

    String OnCheckSpelling(String q) throws org.apache.commons.httpclient.URIException, IOException {
        String result = "";
        HttpClient client = new HttpClient();
        //&spellcheck.build=true
        String url = "http://localhost:8983/solr/all/spell?q=" + q + "&spellcheck=true&spellcheck.collate=true&spellcheck.dictionary=jarowinkler&wt=json";
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
