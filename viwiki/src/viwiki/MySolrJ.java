/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viwiki;
import com.lowagie.text.pdf.codec.postscript.ParseException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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

    private SolrServer getSolrServer() throws MalformedURLException {
        String url = "http://localhost:8983/solr";
        CommonsHttpSolrServer server = new CommonsHttpSolrServer(url);
        return server;
    }

    private SolrServer getSolrServer(String core) throws MalformedURLException {
        String url = "http://localhost:8983/solr/" + core;
        CommonsHttpSolrServer server = new CommonsHttpSolrServer(url);
        return server;
    }

    private void EmptyData(String core) throws MalformedURLException, SolrServerException, IOException {
        SolrServer server = getSolrServer(core);
        server.deleteByQuery("*:*");
        //server.commit();
        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        UpdateResponse rsp = req.process(server);
    }

    String RemoveSignVN(String src) {
        if (src == null) {
            return null;
        }
        UnicodeHelper helper = new UnicodeHelper();
        return helper.removeUnicodeSign(src);
    }

    public void IndexLocation(String filename) throws SQLException, ParseException, SolrServerException, MalformedURLException, IOException {
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        String s = "";
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        int id = 0;
        while ((s = br.readLine()) != null) {
            id++;
            doc = new SolrInputDocument();
            doc.addField("id", String.valueOf(id));
            doc.addField("location", s);
            doc.addField("location_unsigned", RemoveSignVN(s));
            docs.add(doc);
        }

        SolrServer server = getSolrServer("location"); // solrServer = music
        server.add(docs);
        server.commit();

        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        req.add(docs);
        UpdateResponse rsp = req.process(server);
    }
//    public void ImportWiki2Solr(ArrayList<ViwikiPageDTO> listpage, int start) throws MalformedURLException, SolrServerException, IOException {
//        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
//        SolrInputDocument doc;
//        ViwikiPageDTO pagedto = new ViwikiPageDTO();
//        Iterator<ViwikiPageDTO> iter = listpage.iterator();
//        while (iter.hasNext()) {
//            start++;
//            pagedto = iter.next();
//            doc = new SolrInputDocument();
//            doc.addField("id", String.valueOf(start));
//            doc.addField("wk_title", pagedto.getTitle());
//            doc.addField("wk_title_unsigned", RemoveSignVN(pagedto.getTitle()));
//            doc.addField("comment", pagedto.getComment());
//            doc.addField("comment_unsigned", RemoveSignVN(pagedto.getComment()));
//            doc.addField("ip", pagedto.getIp());
////            doc.addField("redirect", pagedto.getRedirect());
//            doc.addField("restrictions", pagedto.getRestrictions());
//            doc.addField("wk_text", pagedto.getText());
//            doc.addField("wk_text_unsigned", RemoveSignVN(pagedto.getText()));
//            //String timeTest = pagedto.getTimestamp().getTime().toString();
//            //String time = format2SolrTime(pagedto.getTimestamp());
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//            doc.addField("timestamp",sdf.format(pagedto.getTimestamp().getTime()));
//            doc.addField("username", pagedto.getUsername());
//            doc.addField("username_unsigned", RemoveSignVN(pagedto.getUsername()));
//            doc.addField("keysearch", pagedto.getKeySearch());
//            doc.addField("keysearch_unsigned", RemoveSignVN(pagedto.getKeySearch()));
//
//            docs.add(doc);
//        }
//
//
//        SolrServer server = getSolrServer("wikipedia");
//        //server.add(docs);
//       // server.commit();
//
//        UpdateRequest req = new UpdateRequest();
//        req.setAction(ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
//    }
//     public void ImportMusic2Solr(ArrayList<MusicDTO> listpage, int start) throws MalformedURLException, SolrServerException, IOException {
//        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
//        SolrInputDocument doc;
//        MusicDTO pagedto = new MusicDTO();
//        Iterator<MusicDTO> iter = listpage.iterator();
//        while (iter.hasNext()) {
//            start++;
//            pagedto = iter.next();
//            doc = new SolrInputDocument();
//
//            doc.addField("id",String.valueOf(start));
//            doc.addField("title", pagedto.getTitle());
//            doc.addField("title_unsigned", RemoveSignVN(pagedto.getTitle()));
//
//            doc.addField("album", pagedto.getAlbum());
//            doc.addField("album_index", pagedto.getAlbum());
//            doc.addField("album_index_unsigned", RemoveSignVN(pagedto.getAlbum()));
//
//            doc.addField("artist", pagedto.getArtist());
//            doc.addField("artist_index", pagedto.getArtist());
//            doc.addField("artist_index_unsigned", RemoveSignVN(pagedto.getArtist()));
//
//            doc.addField("singer", pagedto.getSinger());
//            doc.addField("singer_index", pagedto.getSinger());
//            doc.addField("singer_index_unsigned", RemoveSignVN(pagedto.getSinger()));
//
//            doc.addField("category", pagedto.getCategory());
//            doc.addField("category_index", pagedto.getCategory());
//            doc.addField("category_index_unsigned", RemoveSignVN(pagedto.getCategory()));
//
//            doc.addField("url", pagedto.getUrl());
//            doc.addField("lyric", pagedto.getLyric());
//            doc.addField("lyric_unsigned", RemoveSignVN(pagedto.getLyric()));
//            doc.addField("dateUpload", pagedto.getDayUpload().getTime());
//            docs.add(doc);
//        }
//
//       SolrServer server = getSolrServer("music");
//        //server.add(docs);
//       // server.commit();
//
//        UpdateRequest req = new UpdateRequest();
//        req.setAction(ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
//
//    }
//
//    public void ImportRaoVat2Solr(ArrayList<RaoVatDTO> listpage, int start) throws MalformedURLException, SolrServerException, IOException {
//        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
//        SolrInputDocument doc;
//        RaoVatDTO pagedto = new RaoVatDTO();
//        Iterator<RaoVatDTO> iter = listpage.iterator();
//        while (iter.hasNext()) {
//            start++;
//            pagedto = iter.next();
//            doc = new SolrInputDocument();
//            doc.addField("id", String.valueOf(start));
//            String strBody = pagedto.getBody().replaceAll("\\<.*?\\>", "");
//            doc.addField("body", pagedto.getBody());
//            doc.addField("rv_body", strBody);
//            doc.addField("rv_body_unsigned", RemoveSignVN(strBody));
//            doc.addField("category", pagedto.getCategory().trim());
//            doc.addField("category_index", pagedto.getCategory());
//            doc.addField("category_index_unsigned", RemoveSignVN(pagedto.getCategory()));
//            doc.addField("contact", pagedto.getContact());
//            doc.addField("last_update", pagedto.getLastUpdate().getTime());
//            doc.addField("link_id", pagedto.getLinkId());
//            doc.addField("location", pagedto.getLocation());
//            doc.addField("photo", pagedto.getPhoto());
//            doc.addField("price", pagedto.getPrice());
//            doc.addField("score", pagedto.getScore());
//            doc.addField("site", pagedto.getSite().trim());
//            doc.addField("rv_title", pagedto.getTitle());
//            doc.addField("rv_title_unsigned", RemoveSignVN(pagedto.getTitle()));
//            doc.addField("url", pagedto.getUrl());
//            docs.add(doc);
//        }
//
//
//        SolrServer server = getSolrServer("raovat");
//        //server.add(docs);
//       // server.commit();
//
//        UpdateRequest req = new UpdateRequest();
//        req.setAction(ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
//    }
//
//    public String format2SolrTime(Calendar cl){
//        try {
//            // Src: Fri May 14 00:00:00 ICT 2010
//            // des: 1995-12-31T23:59:59Z
//             String result = "";
//            String dateString = cl.getTime().toString();
//            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
//            Date date = new Date();
//            date = format.parse(dateString);
//
//            SimpleDateFormat ns1 = new SimpleDateFormat("yyyy");
//            String yyyy = ns1.format(date);
//            result+=yyyy;
//            result+="-";
//
//            SimpleDateFormat ns2 = new SimpleDateFormat("MM");
//            String MM = ns2.format(date);
//            result+=MM;
//            result+="-";
//
//             SimpleDateFormat ns3 = new SimpleDateFormat("dd");
//            String dd = ns3.format(date);
//            result+=dd;
//            result+="T";
//
//             SimpleDateFormat ns4 = new SimpleDateFormat("HH");
//            String HH = ns4.format(date);
//            result+=HH;
//            result+=":";
//
//            SimpleDateFormat ns5 = new SimpleDateFormat("mm");
//            String mm = ns5.format(date);
//            result+=mm;
//            result+=":";
//
//            SimpleDateFormat ns6 = new SimpleDateFormat("ss");
//            String ss = ns6.format(date);
//            result+=ss;
//            result+="Z";
//
//            return result;
//        } catch (ParseException ex) {
//            Logger.getLogger(MySolrJ.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return "";
//    }
//    public void ImportVideo2Solr(ArrayList<VideoDTO> listpage, int start) throws MalformedURLException, SolrServerException, IOException {
//        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
//        SolrInputDocument doc;
//        VideoDTO pagedto = new VideoDTO();
//        Iterator<VideoDTO> iter = listpage.iterator();
//        while (iter.hasNext()) {
//            start++;
//            pagedto = iter.next();
//            doc = new SolrInputDocument();
//            doc.addField("id", String.valueOf(start));
//
//            doc.addField("title", pagedto.getTitle());
//            doc.addField("title_unsigned", RemoveSignVN(pagedto.getTitle()));
//
//            doc.addField("category", pagedto.getCategory());
//            doc.addField("category_index", pagedto.getCategory());
//            doc.addField("category_index_unsigned", RemoveSignVN(pagedto.getCategory()));
//
//            doc.addField("url", pagedto.getUrl());
//            doc.addField("duration", pagedto.getDuration());
//
//
//            docs.add(doc);
//        }
//
//
//        SolrServer server = getSolrServer("video");
//        //server.add(docs);
//       // server.commit();
//
//        UpdateRequest req = new UpdateRequest();
//        req.setAction(ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
//    }
//
//     public void ImportImage2Solr(ArrayList<ImageDTO> listpage, int start) throws MalformedURLException, SolrServerException, IOException {
//        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
//        SolrInputDocument doc;
//        ImageDTO pagedto = new ImageDTO();
//        Iterator<ImageDTO> iter = listpage.iterator();
//        while (iter.hasNext()) {
//            start++;
//            pagedto = iter.next();
//            doc = new SolrInputDocument();
//            doc.addField("id",String.valueOf(start));
//            doc.addField("category", pagedto.getCategory().trim());
//            doc.addField("category_index", pagedto.getCategory());
//            doc.addField("category_index_unsigned", RemoveSignVN(pagedto.getCategory()));
//            doc.addField("url", pagedto.getUrl());
//            doc.addField("website", pagedto.getWebsite());
//            doc.addField("site_title", pagedto.getSite_title());
//            doc.addField("site_title_unsigned", RemoveSignVN(pagedto.getSite_title()));
//            doc.addField("site_body", pagedto.getSite_body());
//            doc.addField("site_body_unsigned", RemoveSignVN(pagedto.getSite_body()));
//            doc.addField("fileType", pagedto.getFileType());
//            doc.addField("width", Integer.toString(pagedto.getWidth()));
//            doc.addField("height", Integer.toString(pagedto.getHeight()));
//            doc.addField("size", Integer.toString(pagedto.getSize()));
//
//            docs.add(doc);
//        }
//
//
//        SolrServer server = getSolrServer("image");
//        //server.add(docs);
//       // server.commit();
//
//        UpdateRequest req = new UpdateRequest();
//        req.setAction(ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
//    }
//
}

