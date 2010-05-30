/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viwiki;

import DTO.MusicDTO;
import DTO.RaoVatDTO;
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

    public SolrServer getSolrServer(String core) throws MalformedURLException {
        String url = "http://localhost:8983/solr/" + core;
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

    public void EmptyData(String core) throws MalformedURLException, SolrServerException, IOException
    {
        SolrServer server = getSolrServer(core);
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

    public void ImportWiki2Solr(ArrayList<ViwikiPageDTO> listpage, int start) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        ViwikiPageDTO pagedto = new ViwikiPageDTO();
        Iterator<ViwikiPageDTO> iter = listpage.iterator();
        while (iter.hasNext()) {
            start++;
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("id", String.valueOf(start));
            doc.addField("wk_title", pagedto.getTitle());
            doc.addField("wk_title_unsigned", RemoveSignVN(pagedto.getTitle()));
            doc.addField("comment", pagedto.getComment());
            doc.addField("comment_unsigned", RemoveSignVN(pagedto.getComment()));
            doc.addField("ip", pagedto.getIp());
            doc.addField("minor", pagedto.getMinor());
            doc.addField("redirect", pagedto.getRedirect());
            doc.addField("restrictions", pagedto.getRestrictions());
            doc.addField("wk_text", pagedto.getText());
            doc.addField("wk_text_unsigned", RemoveSignVN(pagedto.getText()));
            doc.addField("timestamp", pagedto.getTimestamp().getTime());
            doc.addField("username", pagedto.getUsername());
            doc.addField("username_unsigned", RemoveSignVN(pagedto.getUsername()));
            docs.add(doc);
        }


        SolrServer server = getSolrServer("wikipedia");
        //server.add(docs);
       // server.commit();

        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        req.add(docs);
        UpdateResponse rsp = req.process(server);
    }

     public void ImportMusic2Solr(ArrayList<MusicDTO> listpage, int start) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        MusicDTO pagedto = new MusicDTO();
        Iterator<MusicDTO> iter = listpage.iterator();
        while (iter.hasNext()) {
            start++;
            pagedto = iter.next();
            doc = new SolrInputDocument();

//            doc.addField("id",String.valueOf(start));
//            doc.addField("ms_title", pagedto.getTitle());
//            doc.addField("ms_title_unsigned", RemoveSignVN(pagedto.getTitle()));
//
//            doc.addField("album", pagedto.getAlbum());
//            doc.addField("album_index", pagedto.getAlbum());
//            doc.addField("album_index_unsigned", RemoveSignVN(pagedto.getAlbum()));
//
//            doc.addField("artist", pagedto.getSinger());
//            doc.addField("artist_index", pagedto.getSinger());
//            doc.addField("artist_index_unsigned", RemoveSignVN(pagedto.getSinger()));
//
//            doc.addField("singer", pagedto.getSinger());
//            doc.addField("singer_index", pagedto.getSinger());
//            doc.addField("singer_index_unsigned", RemoveSignVN(pagedto.getSinger()));
//
//            doc.addField("category", pagedto.getCategory());
//            doc.addField("category_index", pagedto.getCategory());
//            doc.addField("categoryr_index_unsigned", RemoveSignVN(pagedto.getCategory()));
//
//            doc.addField("url", pagedto.getUrl());
//            doc.addField("lyric", pagedto.getLyric());
//            doc.addField("lyric_unsigned", RemoveSignVN(pagedto.getLyric()));
//            doc.addField("dayUpload", pagedto.getDayUpload().getTime());

            doc.addField("id","1");
            doc.addField("ms_title","2");
            doc.addField("ms_title_unsigned", "3");

            doc.addField("album", "1");
            doc.addField("album_index","1");
            doc.addField("album_index_unsigned", "1");

            doc.addField("artist", "1");
            doc.addField("artist_index", "1");
            doc.addField("artist_index_unsigned", "1");

            doc.addField("singer", "1");
            doc.addField("singer_index", "1");
            doc.addField("singer_index_unsigned", "1");

            doc.addField("category", "1");
            doc.addField("category_index", "1");
            doc.addField("categoryr_index_unsigned", "1");

            doc.addField("url", "1");
            doc.addField("lyric", "1");
            doc.addField("lyric_unsigned", "1");
            //doc.addField("dayUpload", "1");

            docs.add(doc);
        }

        SolrServer server = getSolrServer("music");

        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        req.add(docs);
        UpdateResponse rsp = req.process(server);
    }

    public void ImportRaoVat2Solr(ArrayList<RaoVatDTO> listpage, int start) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        RaoVatDTO pagedto = new RaoVatDTO();
        Iterator<RaoVatDTO> iter = listpage.iterator();
        while (iter.hasNext()) {
            start++;
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("id", String.valueOf(start));
            String strBody = pagedto.getBody().replaceAll("\\<.*?\\>", "");
            doc.addField("body", pagedto.getBody());
            doc.addField("rv_body", strBody);
            doc.addField("rv_body_unsigned", RemoveSignVN(strBody));
            doc.addField("category", pagedto.getCategory().trim());
            doc.addField("category_index", pagedto.getCategory());
            doc.addField("category_index_unsigned", RemoveSignVN(pagedto.getCategory()));
            doc.addField("contact", pagedto.getContact());
            doc.addField("last_update", pagedto.getLastUpdate().getTime());
            doc.addField("link_id", pagedto.getLinkId());
            doc.addField("location", pagedto.getLocation());
            doc.addField("photo", pagedto.getPhoto());
            doc.addField("price", pagedto.getPrice());
            doc.addField("score", pagedto.getScore());
            doc.addField("site", pagedto.getSite().trim());
            doc.addField("rv_title", pagedto.getTitle());
            doc.addField("rv_title_unsigned", RemoveSignVN(pagedto.getTitle()));
            doc.addField("url", pagedto.getUrl());
            docs.add(doc);
        }


        SolrServer server = getSolrServer("raovat");
        //server.add(docs);
       // server.commit();

        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        req.add(docs);
        UpdateResponse rsp = req.process(server);
    }
    
}