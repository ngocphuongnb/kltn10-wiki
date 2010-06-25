/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.Utils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.me.SolrConnection.SolrJConnection;

/**
 *
 * @author VinhPhamXP
 */
public class SearchLocation {

    public SolrDocumentList CheckLocation(String query) throws SolrServerException, MalformedURLException {
        SolrQuery solrQuery = new SolrQuery();
        String q = "";
        if(MyString.CheckSigned(query))
           q = String.format("location:(\"%s\")", query);
        else
         q = String.format("location:(\"%s\")^5 || location_unsigned:(\"%s\")^2", query, query);
        solrQuery.setQuery(q);
        solrQuery.setStart(0);
        solrQuery.setRows(3);
        SolrServer server = SolrJConnection.getSolrServer("location");
        QueryResponse rsp = server.query(solrQuery);
        return rsp.getResults();
    }
}
