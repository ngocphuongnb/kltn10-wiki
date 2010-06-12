/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Index;

import DTO.ImageDTO;
import DTO.MusicDTO;
import DTO.RaoVatDTO;
import DTO.VideoDTO;
import DTO.ViwikiPageDTO;
import index.UnicodeHelper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        return server;
    }

    public SolrServer getSolrServer(String core) throws MalformedURLException {
        String url = "http://localhost:8983/solr/" + core;
        CommonsHttpSolrServer server = new CommonsHttpSolrServer(url);
        return server;
    }

    public void EmptyData(String core) throws MalformedURLException, SolrServerException, IOException {
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

    public void ImportWiki2Solr(String title, Calendar timestamp, String ip, String text, String restrictions, String username, String comment, int start) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
   
            start++;

            doc = new SolrInputDocument();
            doc.addField("id", String.valueOf(start));
            doc.addField("wk_title", title);
            //doc.addField("wk_title_unsigned", RemoveSignVN(title));
            doc.addField("comment", comment);
            //doc.addField("comment_unsigned", RemoveSignVN(comment));
            doc.addField("ip", ip);
//            doc.addField("minor", pagedto.getMinor());
//            doc.addField("redirect", pagedto.getRedirect());
            doc.addField("restrictions", restrictions);
            doc.addField("wk_text", text);
            //doc.addField("wk_text_unsigned", RemoveSignVN(text));
           // String time = format2SolrTime(timestime);

            //doc.addField("timestamp", timestamp); // Test lai
            doc.addField("username", username);
            //doc.addField("username_unsigned", RemoveSignVN(username));
            docs.add(doc);


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

            doc.addField("id", String.valueOf(start));
            doc.addField("title", pagedto.getTitle());
            doc.addField("title_unsigned", RemoveSignVN(pagedto.getTitle()));

            doc.addField("album", pagedto.getAlbum());
            doc.addField("album_index", pagedto.getAlbum());
            doc.addField("album_index_unsigned", RemoveSignVN(pagedto.getAlbum()));

            doc.addField("artist", pagedto.getArtist());
            doc.addField("artist_index", pagedto.getArtist());
            doc.addField("artist_index_unsigned", RemoveSignVN(pagedto.getArtist()));

            doc.addField("singer", pagedto.getSinger());
            doc.addField("singer_index", pagedto.getSinger());
            doc.addField("singer_index_unsigned", RemoveSignVN(pagedto.getSinger()));

            doc.addField("category", pagedto.getCategory());
            doc.addField("category_index", pagedto.getCategory());
            doc.addField("category_index_unsigned", RemoveSignVN(pagedto.getCategory()));

            doc.addField("url", pagedto.getUrl());
            doc.addField("lyric", pagedto.getLyric());
            doc.addField("lyric_unsigned", RemoveSignVN(pagedto.getLyric()));
            doc.addField("dateUpload", pagedto.getDayUpload().getTime());

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
        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        req.add(docs);
        UpdateResponse rsp = req.process(server);
    }

    public String format2SolrTime(Calendar cl) {
        try {
            // Src: Fri May 14 00:00:00 ICT 2010
            // des: 1995-12-31T23:59:59Z
            String result = "";
            String dateString = cl.getTime().toString();
            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Date date = new Date();
            date = format.parse(dateString);

            SimpleDateFormat ns1 = new SimpleDateFormat("yyyy");
            String yyyy = ns1.format(date);
            result += yyyy;
            result += "-";

            SimpleDateFormat ns2 = new SimpleDateFormat("MM");
            String MM = ns2.format(date);
            result += MM;
            result += "-";

            SimpleDateFormat ns3 = new SimpleDateFormat("dd");
            String dd = ns3.format(date);
            result += dd;
            result += "T";

            SimpleDateFormat ns4 = new SimpleDateFormat("HH");
            String HH = ns4.format(date);
            result += HH;
            result += ":";

            SimpleDateFormat ns5 = new SimpleDateFormat("mm");
            String mm = ns5.format(date);
            result += mm;
            result += ":";

            SimpleDateFormat ns6 = new SimpleDateFormat("ss");
            String ss = ns6.format(date);
            result += ss;
            result += "Z";

            return result;
        } catch (ParseException ex) {
            Logger.getLogger(MySolrJ.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public void ImportVideo2Solr(ArrayList<VideoDTO> listpage, int start) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        VideoDTO pagedto = new VideoDTO();
        Iterator<VideoDTO> iter = listpage.iterator();
        while (iter.hasNext()) {
            start++;
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("id", String.valueOf(start));

            doc.addField("title", pagedto.getTitle());
            doc.addField("title_unsigned", RemoveSignVN(pagedto.getTitle()));

            doc.addField("category", pagedto.getCategory());
            doc.addField("category_index", pagedto.getCategory());
            doc.addField("category_index_unsigned", RemoveSignVN(pagedto.getCategory()));

            doc.addField("url", pagedto.getUrl());
            doc.addField("duration", pagedto.getDuration());


            docs.add(doc);
        }


        SolrServer server = getSolrServer("video");
        //server.add(docs);
        // server.commit();

        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        req.add(docs);
        UpdateResponse rsp = req.process(server);
    }

    public void ImportALL2Solr(Object list, String type, int start) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;

        ////////////////////////////////////////////////////////////////////////////////
        // For Wiki
        ////////////////////////////////////////////////////////////////////////////////
        if (type.equals("wiki")) {
            ViwikiPageDTO pagedto = new ViwikiPageDTO();
            ArrayList<ViwikiPageDTO> listpage = new ArrayList<ViwikiPageDTO>();
            listpage = (ArrayList<ViwikiPageDTO>) list;
            Iterator<ViwikiPageDTO> iter = listpage.iterator();
            while (iter.hasNext()) {
                start++;
                pagedto = iter.next();
                doc = new SolrInputDocument();
                doc.addField("id", String.valueOf(start));

                doc.addField("title", pagedto.getTitle());
                doc.addField("title_unsigned", RemoveSignVN(pagedto.getTitle()));
                doc.addField("body", pagedto.getText());
                doc.addField("body_unsigned", RemoveSignVN(pagedto.getText()));
                docs.add(doc);
            }
        }
        ////////////////////////////////////////////////////////////////////////////////
        // For Rao vat
        ////////////////////////////////////////////////////////////////////////////////
        if (type.equals("raovat")) {
            RaoVatDTO pagedto = new RaoVatDTO();
            ArrayList<RaoVatDTO> listpage = new ArrayList<RaoVatDTO>();
            listpage = (ArrayList<RaoVatDTO>) list;
            Iterator<RaoVatDTO> iter = listpage.iterator();
            while (iter.hasNext()) {
                start++;
                pagedto = iter.next();
                doc = new SolrInputDocument();
                doc.addField("id", String.valueOf(start));

                doc.addField("title", pagedto.getTitle());
                doc.addField("title_unsigned", RemoveSignVN(pagedto.getTitle()));
                doc.addField("body", pagedto.getBody());
                doc.addField("body_unsigned", RemoveSignVN(pagedto.getBody()));
                docs.add(doc);
            }
        }
        ////////////////////////////////////////////////////////////////////////////////
        // For Nhac
        ////////////////////////////////////////////////////////////////////////////////
        if (type.equals("music")) {
            MusicDTO pagedto = new MusicDTO();
            ArrayList<MusicDTO> listpage = new ArrayList<MusicDTO>();
            listpage = (ArrayList<MusicDTO>) list;
            Iterator<MusicDTO> iter = listpage.iterator();
            while (iter.hasNext()) {
                start++;
                pagedto = iter.next();
                doc = new SolrInputDocument();
                doc.addField("id", String.valueOf(start));

                doc.addField("title", pagedto.getTitle());
                doc.addField("title_unsigned", RemoveSignVN(pagedto.getTitle()));
                doc.addField("body", pagedto.getUrl());
                doc.addField("body_unsigned", RemoveSignVN(pagedto.getUrl()));
                docs.add(doc);
            }
        }

        ////////////////////////////////////////////////////////////////////////////////
        // For Image
        ////////////////////////////////////////////////////////////////////////////////
        if (type.equals("image")) {
            ImageDTO pagedto = new ImageDTO();
            ArrayList<ImageDTO> listpage = new ArrayList<ImageDTO>();
            listpage = (ArrayList<ImageDTO>) list;
            Iterator<ImageDTO> iter = listpage.iterator();
            while (iter.hasNext()) {
                start++;
                pagedto = iter.next();
                doc = new SolrInputDocument();
                doc.addField("id", String.valueOf(start));

                doc.addField("title", pagedto.getSite_title());
                doc.addField("title_unsigned", RemoveSignVN(pagedto.getSite_title()));
                doc.addField("body", pagedto.getUrl());
                doc.addField("body_unsigned", RemoveSignVN(pagedto.getUrl()));
                docs.add(doc);
            }
        }
        ////////////////////////////////////////////////////////////////////////////////
        // For Video
        ////////////////////////////////////////////////////////////////////////////////
        if (type.equals("video")) {
            VideoDTO pagedto = new VideoDTO();
            ArrayList<VideoDTO> listpage = new ArrayList<VideoDTO>();
            listpage = (ArrayList<VideoDTO>) list;
            Iterator<VideoDTO> iter = listpage.iterator();
            while (iter.hasNext()) {
                start++;
                pagedto = iter.next();
                doc = new SolrInputDocument();
                doc.addField("id", String.valueOf(start));

                doc.addField("title", pagedto.getTitle());
                doc.addField("title_unsigned", RemoveSignVN(pagedto.getTitle()));
                doc.addField("body", pagedto.getUrl());
                doc.addField("body_unsigned", RemoveSignVN(pagedto.getUrl()));
                docs.add(doc);
            }
        }

        SolrServer server = getSolrServer("all");

        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        req.add(docs);
        UpdateResponse rsp = req.process(server);
    }

    public void ImportImage2Solr(ArrayList<ImageDTO> listpage, int start) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        ImageDTO pagedto = new ImageDTO();
        Iterator<ImageDTO> iter = listpage.iterator();
        while (iter.hasNext()) {
            start++;
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("id", String.valueOf(start));
            doc.addField("category", pagedto.getCategory().trim());
            doc.addField("category_index", pagedto.getCategory());
            doc.addField("category_index_unsigned", RemoveSignVN(pagedto.getCategory()));
            doc.addField("url", pagedto.getUrl());
            doc.addField("website", pagedto.getWebsite());
            doc.addField("site_title", pagedto.getSite_title());
            doc.addField("site_title_unsigned", RemoveSignVN(pagedto.getSite_title()));
            doc.addField("site_body", pagedto.getSite_body());
            doc.addField("site_body_unsigned", RemoveSignVN(pagedto.getSite_body()));
            doc.addField("fileType", pagedto.getFileType());
            doc.addField("width", Integer.toString(pagedto.getWidth()));
            doc.addField("height", Integer.toString(pagedto.getHeight()));
            doc.addField("size", Integer.toString(pagedto.getSize()));

            docs.add(doc);
        }


        SolrServer server = getSolrServer("image");
        //server.add(docs);
        // server.commit();

        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        req.add(docs);
        UpdateResponse rsp = req.process(server);
    }
}
