/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.SolrConnection;

import java.net.MalformedURLException;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.me.bus.ParameterBUS;

/**
 *
 * @author VinhPham
 */
public class SolrJConnection {
    private static String _url = "";

    public static SolrServer getSolrServer() throws MalformedURLException {
        String url = "http://localhost:8983/solr";
        CommonsHttpSolrServer server = new CommonsHttpSolrServer(url);
        return server;
    }

    private void init()
    {
        ParameterBUS parbus = new ParameterBUS();
        _url = parbus.GetParameter("SolrAddress", "visearch");
    }

    public static SolrServer getSolrServer(String core) throws MalformedURLException {
        String url = _url + core;
        CommonsHttpSolrServer server = new CommonsHttpSolrServer(url);
        return server;
    }
}
