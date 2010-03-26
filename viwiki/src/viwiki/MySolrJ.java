/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viwiki;

import DTO.ViwikiPageDTO;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest.ACTION;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author VinhPham
 */
public class MySolrJ {

    public SolrServer getSolrServer() throws MalformedURLException {
        String url = "http://localhost:8983/solr";
        CommonsHttpSolrServer server = new CommonsHttpSolrServer(url);
//        server.setSoTimeout(1000);  // socket read timeout
//        server.setConnectionTimeout(100);
//        server.setDefaultMaxConnectionsPerHost(100);
//        server.setMaxTotalConnections(100);
//        server.setFollowRedirects(false);  // defaults to false
//        // allowCompression defaults to false.
//        // Server side must support gzip or deflate for this to have any effect.
//        server.setAllowCompression(true);
//        server.setMaxRetries(1); // defaults to 0.  > 1 not recommended.
        return server;
    }

    public void EmptyData() throws MalformedURLException, SolrServerException, IOException
    {
        SolrServer server = getSolrServer();
        server.deleteByQuery( "*:*" );
        //server.commit();
        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        UpdateResponse rsp = req.process(server);
    }

    String RemoveSignVN(String src){
        if(src == null)
            return null;

//        if (VIQRHelper.checkVIQR(src) == true) {
//            return VIQRHelper.removeVIQRSign(src);
//        } else if (VNIWindowsHelper.checkVNIWindow(src) == true) {
//            return VNIWindowsHelper.removeVNIWindowsSign(src);
//        }else if (UnicodeHelper.checkUnicode(src) == true) {
//            return UnicodeHelper.removeUnicodeSign(src);
//        } else if (TCVNHelper.checkTCVN(src) == true) {
//            return TCVNHelper.removeTCVNSign(src);
//        } else {
//            return src;
//        }
        return UnicodeHelper.removeUnicodeSign(src);
    }

    public void Import2Solr(ArrayList<ViwikiPageDTO> listpage) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        ViwikiPageDTO pagedto = new ViwikiPageDTO();
        Iterator<ViwikiPageDTO> iter = listpage.iterator();
        while (iter.hasNext()) {
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("title", pagedto.getTitle());
            doc.addField("title_unsigned", RemoveSignVN(pagedto.getTitle()));
            doc.addField("comment", pagedto.getComment());
            doc.addField("comment_unsigned", RemoveSignVN(pagedto.getComment()));
            doc.addField("ip", pagedto.getIp());
            doc.addField("minor", pagedto.getMinor());
            doc.addField("redirect", pagedto.getRedirect());
            doc.addField("restrictions", pagedto.getRestrictions());
            doc.addField("text", pagedto.getText());
            doc.addField("text_unsigned", RemoveSignVN(pagedto.getText()));
            doc.addField("timestamp", pagedto.getTimestamp().getTime());
            doc.addField("username", pagedto.getUsername());
            doc.addField("username_unsigned", RemoveSignVN(pagedto.getUsername()));
            docs.add(doc);
        }


        SolrServer server = getSolrServer();
        //server.add(docs);
       // server.commit();

        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        req.add(docs);
        UpdateResponse rsp = req.process(server);
    }
}
