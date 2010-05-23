/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.SolrConnection;

import java.net.MalformedURLException;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

/**
 *
 * @author VinhPham
 */
public class SolrJConnection {

    public static SolrServer getSolrServer() throws MalformedURLException {
        String url = "http://localhost:8983/solr";
        CommonsHttpSolrServer server = new CommonsHttpSolrServer(url);
//        server.setSoTimeout(1000);  // socket read timeout
//        server.setConnectionTimeout(1000);
//        server.setDefaultMaxConnectionsPerHost(1000);
//        server.setMaxTotalConnections(100);
//        server.setFollowRedirects(false);  // defaults to false
//        // allowCompression defaults to false.
//        // Server side must support gzip or deflate for this to have any effect.
//        server.setAllowCompression(true);
//        server.setMaxRetries(1); // defaults to 0.  > 1 not recommended.
        return server;
    }
}
