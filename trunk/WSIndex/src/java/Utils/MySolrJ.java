/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import BUS.RaoVatBUS;
import BUS.ImageBUS;
import BUS.MusicBUS;
import BUS.NewsBUS;
import BUS.VideoBUS;
import BUS.ViwikiPageBUS;
import DTO.RaoVatDTO;
import DTO.ImageDTO;
import DTO.MusicDTO;
import DTO.NewsDTO;
import DTO.VideoDTO;
import DTO.ViwikiPageDTO;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public void IndexViwiki() throws SQLException, ParseException, SolrServerException, MalformedURLException, IOException {

        EmptyData("wikipedia");
        ViwikiPageBUS bus = new ViwikiPageBUS();
        int numOfRecords = bus.CountRecord();
        int start = 0;
        while (start < numOfRecords) {
            ArrayList<ViwikiPageDTO> list = new ArrayList<ViwikiPageDTO>();
            list = bus.getDataList(start, 2000);
            ImportViwiki2Solr(list, "wikipedia");
            start += 2000;
        }
    }

    public void IndexRaoVat() throws SQLException, ParseException, SolrServerException, MalformedURLException, IOException {

        EmptyData("raovat");
        RaoVatBUS bus = new RaoVatBUS();
        int numOfRecords = bus.CountRecord();
        int start = 0;
        while (start < numOfRecords) {
            ArrayList<RaoVatDTO> list = new ArrayList<RaoVatDTO>();
            list = bus.getDataList(start, 2000);
            ImportRaoVat2Solr(list,"raovat");
            start += 2000;
        }
    }

    public void IndexMusic() throws SQLException, ParseException, SolrServerException, MalformedURLException, IOException {

        EmptyData("music");
        MusicBUS bus = new MusicBUS();
        int numOfRecords = bus.CountRecord();
        int start = 0;
        while (start < numOfRecords) {
            ArrayList<MusicDTO> list = new ArrayList<MusicDTO>();
            list = bus.getDataList(start, 2000);
            ImportMusic2Solr(list, "music");
            start += 2000;
        }
    }

    public void IndexImage() throws SQLException, ParseException, SolrServerException, MalformedURLException, IOException {

        EmptyData("image");
        ImageBUS bus = new ImageBUS();
        int numOfRecords = bus.CountRecord();
        int start = 0;
        while (start < numOfRecords) {
            ArrayList<ImageDTO> list = new ArrayList<ImageDTO>();
            list = bus.getDataList(start, 2000);
            ImportImage2Solr(list, "image");
            start += 2000;
        }
    }

    public void IndexVideo() throws SQLException, ParseException, SolrServerException, MalformedURLException, IOException {

        EmptyData("video");
        VideoBUS bus = new VideoBUS();
        int numOfRecords = bus.CountRecord();
        int start = 0;
        while (start < numOfRecords) {
            ArrayList<VideoDTO> list = new ArrayList<VideoDTO>();
            list = bus.getDataList(start, 2000);
            ImportVideo2Solr(list, "video");
            start += 2000;
        }
    }

    public void IndexNews() throws SQLException, ParseException, SolrServerException, MalformedURLException, IOException {

        EmptyData("news");
        NewsBUS bus = new NewsBUS();
        int numOfRecords = bus.CountRecord();
        int start = 0;
        while (start < numOfRecords) {
            ArrayList<NewsDTO> list = new ArrayList<NewsDTO>();
            list = bus.getDataList(start, 2000);
            ImportNews2Solr(list, "news");
            start += 2000;
        }

    }

    public void IndexAll() throws SQLException, ParseException, SolrServerException, MalformedURLException, IOException {

        EmptyData("all");

        ViwikiPageBUS Wbus = new ViwikiPageBUS();
        int numOfRecords = Wbus.CountRecord();
        int start = 0;
        while (start < numOfRecords) {
            ArrayList<ViwikiPageDTO> list = new ArrayList<ViwikiPageDTO>();
            list = Wbus.getDataList(start, 2000);
            ImportViwiki2Solr(list, "all");
            start += 2000;
        }

        ///////////////////////
        // RaoVat
        //////////////////////

        RaoVatBUS Rbus = new RaoVatBUS();
        numOfRecords = Rbus.CountRecord();
        start = 0;
        while (start < numOfRecords) {
            ArrayList<RaoVatDTO> list = new ArrayList<RaoVatDTO>();
            list = Rbus.getDataList(start, 2000);
            ImportRaoVat2Solr(list, "all");
            start += 2000;
        }
        ///////////////////////
        // Music
        //////////////////////
        MusicBUS Mbus = new MusicBUS();
        numOfRecords = Mbus.CountRecord();
        start = 0;
        while (start < numOfRecords) {
            ArrayList<MusicDTO> list = new ArrayList<MusicDTO>();
            list = Mbus.getDataList(start, 2000);
            ImportMusic2Solr(list, "all");
            start += 2000;
        }

        ///////////////////////
        // Image
        //////////////////////
        ImageBUS Ibus = new ImageBUS();
        numOfRecords = Ibus.CountRecord();
        start = 0;
        while (start < numOfRecords) {
            ArrayList<ImageDTO> list = new ArrayList<ImageDTO>();
            list = Ibus.getDataList(start, 2000);
            ImportImage2Solr(list, "all");
            start += 2000;
        }
        ///////////////////////
        // Video
        //////////////////////
        VideoBUS Vbus = new VideoBUS();
        numOfRecords = Vbus.CountRecord();
        start = 0;
        while (start < numOfRecords) {
            ArrayList<VideoDTO> list = new ArrayList<VideoDTO>();
            list = Vbus.getDataList(start, 2000);
            ImportVideo2Solr(list, "all");
            start += 2000;
        }
        ///////////////////////
        // News
        //////////////////////
        NewsBUS Nbus = new NewsBUS();
        numOfRecords = Nbus.CountRecord();
        start = 0;
        while (start < numOfRecords) {
            ArrayList<NewsDTO> list = new ArrayList<NewsDTO>();
            list = Nbus.getDataList(start, 2000);
            ImportNews2Solr(list, "all");
            start += 2000;
        }
    }

    public void ImportMusic2Solr(ArrayList<MusicDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        MusicDTO pagedto = new MusicDTO();
        Iterator<MusicDTO> iter = listpage.iterator();
        while (iter.hasNext()) {

            pagedto = iter.next();
            doc = new SolrInputDocument();

            doc.addField("id", pagedto.getId());
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

        SolrServer server = getSolrServer(solrServer); // solrServer = music
        //server.add(docs);
        // server.commit();

        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        req.add(docs);
        UpdateResponse rsp = req.process(server);

    }

    private void ImportViwiki2Solr(ArrayList<ViwikiPageDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        ViwikiPageDTO pagedto = new ViwikiPageDTO();
        Iterator<ViwikiPageDTO> iter = listpage.iterator();
        while (iter.hasNext()) {
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("id", pagedto.getId());
            doc.addField("wk_title", pagedto.getTitle());
            doc.addField("wk_title_unsigned", RemoveSignVN(pagedto.getTitle()));
            doc.addField("comment", pagedto.getComment());
            doc.addField("comment_unsigned", RemoveSignVN(pagedto.getComment()));
            doc.addField("ip", pagedto.getIp());
            doc.addField("restrictions", pagedto.getRestrictions());
            doc.addField("wk_text", pagedto.getText());
            doc.addField("wk_text_unsigned", RemoveSignVN(pagedto.getText()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            doc.addField("timestamp", sdf.format(pagedto.getTimestamp().getTime()));
            doc.addField("username", pagedto.getUsername());
            doc.addField("username_unsigned", RemoveSignVN(pagedto.getUsername()));
            doc.addField("keysearch", pagedto.getKeySearch());
            doc.addField("keysearch_unsigned", RemoveSignVN(pagedto.getKeySearch()));
            docs.add(doc);
        }


        SolrServer server = getSolrServer(solrServer); // solrServer =wikipedia
        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        req.add(docs);
        UpdateResponse rsp = req.process(server);
    }

    public void ImportRaoVat2Solr(ArrayList<RaoVatDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        RaoVatDTO pagedto = new RaoVatDTO();
        Iterator<RaoVatDTO> iter = listpage.iterator();
        while (iter.hasNext()) {
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("id", pagedto.getId());
            String strBody = pagedto.getBody().replaceAll("\\<.*?\\>", "");
            doc.addField("body", pagedto.getBody());
            doc.addField("rv_body", strBody);
            doc.addField("rv_body_unsigned", RemoveSignVN(strBody));
            doc.addField("category", pagedto.getCategory().trim());
            doc.addField("category_index", pagedto.getCategory());
            doc.addField("category_index_unsigned", RemoveSignVN(pagedto.getCategory()));
            doc.addField("contact", pagedto.getContact());
            doc.addField("last_update", pagedto.getLastUpdate().getTime());
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


        SolrServer server = getSolrServer(solrServer); // solrServer = raovat
        //server.add(docs);
        // server.commit();

        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        req.add(docs);
        UpdateResponse rsp = req.process(server);
    }

    public void ImportVideo2Solr(ArrayList<VideoDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        VideoDTO pagedto = new VideoDTO();
        Iterator<VideoDTO> iter = listpage.iterator();
        while (iter.hasNext()) {
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("id", pagedto.getId());

            doc.addField("title", pagedto.getTitle());
            doc.addField("title_unsigned", RemoveSignVN(pagedto.getTitle()));

            doc.addField("category", pagedto.getCategory());
            doc.addField("category_index", pagedto.getCategory());
            doc.addField("category_index_unsigned", RemoveSignVN(pagedto.getCategory()));

            doc.addField("url", pagedto.getUrl());
            doc.addField("duration", pagedto.getDuration());


            docs.add(doc);
        }


        SolrServer server = getSolrServer(solrServer); // solrServer = video
        //server.add(docs);
        // server.commit();

        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        req.add(docs);
        UpdateResponse rsp = req.process(server);
    }

    public void ImportImage2Solr(ArrayList<ImageDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        ImageDTO pagedto = new ImageDTO();
        Iterator<ImageDTO> iter = listpage.iterator();
        while (iter.hasNext()) {
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("id", pagedto.getId());
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
            doc.addField("width", Float.toString(pagedto.getWidth()));
            doc.addField("height", Float.toString(pagedto.getHeight()));
            doc.addField("size", pagedto.getSize());

            docs.add(doc);
        }

        SolrServer server = getSolrServer(solrServer); // solrServer =image
        //server.add(docs);
        // server.commit();

        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        req.add(docs);
        UpdateResponse rsp = req.process(server);
    }

    public void ImportNews2Solr(ArrayList<NewsDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        NewsDTO pagedto = new NewsDTO();
        Iterator<NewsDTO> iter = listpage.iterator();
        while (iter.hasNext()) {
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("id", pagedto.getId());
            doc.addField("category", pagedto.getCategory().trim());
            doc.addField("category_index", pagedto.getCategory());
            doc.addField("category_index_unsigned", RemoveSignVN(pagedto.getCategory()));

            doc.addField("title", pagedto.getTitle());
            doc.addField("title_unsigned", RemoveSignVN(pagedto.getTitle()));

            doc.addField("introtext", pagedto.getIntrotext());
            doc.addField("introtext_unsigned", RemoveSignVN(pagedto.getIntrotext()));

            doc.addField("fulltext", pagedto.getFulltext());
            doc.addField("fulltext_unsigned", RemoveSignVN(pagedto.getFulltext()));

            doc.addField("created", pagedto.getCreated().getTime());
            //doc.addField("modified", pagedto.getModified().getTime());

            docs.add(doc);
        }

        SolrServer server = getSolrServer(solrServer); // solrServer = news
        //server.add(docs);
        // server.commit();

        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        req.add(docs);
        UpdateResponse rsp = req.process(server);
    }
}

