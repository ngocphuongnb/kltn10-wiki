/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import BUS.BookmarkBUS;
import BUS.RaoVatBUS;
import BUS.ImageBUS;
import BUS.MusicBUS;
import BUS.NewsBUS;
import BUS.VideoBUS;
import BUS.ViwikiPageBUS;
import DTO.BookmarkDTO;
import DTO.RaoVatDTO;
import DTO.ImageDTO;
import DTO.LocationDTO;
import DTO.MusicDTO;
import DTO.NewsDTO;
import DTO.VideoDTO;
import DTO.ViwikiPageDTO;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
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

    public static int ierror;

    private SolrServer getSolrServer(String core) throws MalformedURLException {
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
        UnicodeHelper helper = new UnicodeHelper();
        return helper.removeUnicodeSign(src);
    }

    public void IndexViwiki() throws SQLException, ParseException, SolrServerException, MalformedURLException, IOException {
        //EmptyData("wikipedia");
        ViwikiPageBUS bus = new ViwikiPageBUS();
        int numOfRecords = bus.CountRecord();
        //System.out.print("Num records found: " + numOfRecords);
        int start = 0;
        //ierror = 0;
        ArrayList<Integer> lResult = new ArrayList<Integer>();
        while (start < numOfRecords) {
            ArrayList<ViwikiPageDTO> list = new ArrayList<ViwikiPageDTO>();
            list = bus.getDataList(0, 50);
            lResult = ImportViwiki2Solr(list, "wikipedia");
            try {
                ImportViwiki2SolrAll(list, "all");
            } catch (Exception ex) {
            }
            bus.UpdateAfterIndex(lResult);
            start += 100;
        }

//        FileWriter writer = new FileWriter(new File("error.txt"));
//        writer.write("Num of records error:" + ierror);
//        writer.close();
    }

    public void IndexRaoVat() throws SQLException, ParseException, SolrServerException, MalformedURLException, IOException {

        //EmptyData("raovat");
        ArrayList<Integer> lResult = new ArrayList<Integer>();
        RaoVatBUS bus = new RaoVatBUS();
        int numOfRecords = bus.CountRecord();
        int start = 0;
        while (start < numOfRecords) {
            ArrayList<RaoVatDTO> list = new ArrayList<RaoVatDTO>();
            list = bus.getDataList(0, 100);
            lResult = ImportRaoVat2Solr(list, "raovat");
            ImportRaoVat2SolrAll(list, "all");
            bus.UpdateAfterIndex(lResult);
            start += 100;
        }
    }

    public void IndexMusic() throws SQLException, ParseException, SolrServerException, MalformedURLException, IOException {

        //EmptyData("music");
        ArrayList<Integer> lResult = new ArrayList<Integer>();
        MusicBUS bus = new MusicBUS();
        int numOfRecords = bus.CountRecord();
        int start = 0;
        while (start < numOfRecords) {
            ArrayList<MusicDTO> list = new ArrayList<MusicDTO>();
            list = bus.getDataList(0, 100);
            lResult = ImportMusic2Solr(list, "music");
            ImportMusic2SolrAll(list, "all");
            bus.UpdateAfterIndex(lResult);
            start += 100;
        }
    }

    public void IndexImage() throws SQLException, ParseException, SolrServerException, MalformedURLException, IOException {

        //EmptyData("image");
        ArrayList<Integer> lResult = new ArrayList<Integer>();
        ImageBUS bus = new ImageBUS();
        int numOfRecords = bus.CountRecord();
        int start = 0;
        while (start < numOfRecords) {
            ArrayList<ImageDTO> list = new ArrayList<ImageDTO>();
            list = bus.getDataList(0, 100);
            lResult = ImportImage2Solr(list, "image");
            ImportImage2SolrAll(list, "all");
            bus.UpdateAfterIndex(lResult);
            start += 100;
        }
    }

    public void IndexBookmark() throws SQLException, ParseException, SolrServerException, MalformedURLException, IOException {

        //EmptyData("bookmark");
        ArrayList<Integer> lResult = new ArrayList<Integer>();
        BookmarkBUS bus = new BookmarkBUS();
        int numOfRecords = bus.CountRecord();
        int start = 0;
        while (start < numOfRecords) {
            ArrayList<BookmarkDTO> list = new ArrayList<BookmarkDTO>();
            list = bus.getDataList(0, 100);
            lResult = ImportBookmark2Solr(list, "bookmark");
            bus.UpdateAfterIndex(lResult);
            start += 100;
        }
    }

    public void IndexVideo() throws SQLException, ParseException, SolrServerException, MalformedURLException, IOException {

        //EmptyData("video");
        ArrayList<Integer> lResult = new ArrayList<Integer>();
        VideoBUS bus = new VideoBUS();
        int numOfRecords = bus.CountRecord();
        int start = 0;
        while (start < numOfRecords) {
            ArrayList<VideoDTO> list = new ArrayList<VideoDTO>();
            list = bus.getDataList(0, 100);
            lResult = ImportVideo2Solr(list, "video");
            ImportVideo2SolrAll(list, "all");
            bus.UpdateAfterIndex(lResult);
            start += 100;
        }
    }

    public void IndexNews() throws SQLException, ParseException, SolrServerException, MalformedURLException, IOException {

        //EmptyData("news");
        ArrayList<Integer> lResult = new ArrayList<Integer>();
        NewsBUS bus = new NewsBUS();
        int numOfRecords = bus.CountRecord();
        int start = 0;
        while (start < numOfRecords) {
            ArrayList<NewsDTO> list = new ArrayList<NewsDTO>();
            list = bus.getDataList(0, 100);
            lResult = ImportNews2Solr(list, "news");
            ImportNews2SolrAll(list, "all");
            bus.UpdateAfterIndex(lResult);
            start += 100;
        }

    }

//    public void IndexWiki2All() {
//        try {
//            ViwikiPageBUS Wbus = new ViwikiPageBUS();
//            int numOfRecords = Wbus.CountRecord();
//            int start = 0;
//            //ArrayList<Integer> lResult = new ArrayList<Integer>();
//            while (start < numOfRecords) {
//                ArrayList<ViwikiPageDTO> list = new ArrayList<ViwikiPageDTO>();
//                list = Wbus.getDataList(start, 100);
//                ImportViwiki2SolrAll(list, "all");
//                start += 100;
//            }
//        } catch (Exception ex) {
//        }
//    }
//    public void IndexRaoVat2All() {
//        try {
//            //ArrayList<Integer> lResult = new ArrayList<Integer>();
//            RaoVatBUS Rbus = new RaoVatBUS();
//            int numOfRecords = Rbus.CountRecord();
//            int start = 0;
//            while (start < numOfRecords) {
//                ArrayList<RaoVatDTO> list = new ArrayList<RaoVatDTO>();
//                list = Rbus.getDataList(start, 100);
//                ImportRaoVat2SolrAll(list, "all");
//                //Rbus.UpdateAfterIndex(lResult);
//                start += 100;
//            }
//        } catch (Exception ex) {
//        }
//    }
//    public void IndexMusic2All() {
//        try {
//            //ArrayList<Integer> lResult = new ArrayList<Integer>();
//            MusicBUS Mbus = new MusicBUS();
//            int numOfRecords = Mbus.CountRecord();
//            int start = 0;
//            while (start < numOfRecords) {
//                ArrayList<MusicDTO> list = new ArrayList<MusicDTO>();
//                list = Mbus.getDataList(start, 100);
//                ImportMusic2SolrAll(list, "all");
//                //Mbus.UpdateAfterIndex(lResult);
//                start += 100;
//            }
//        } catch (Exception ex) {
//        }
//    }
//    public void IndexVideo2All() {
//        try {
//            //ArrayList<Integer> lResult = new ArrayList<Integer>();
//            VideoBUS Vbus = new VideoBUS();
//            int numOfRecords = Vbus.CountRecord();
//            int start = 0;
//            while (start < numOfRecords) {
//                ArrayList<VideoDTO> list = new ArrayList<VideoDTO>();
//                list = Vbus.getDataList(start, 100);
//                ImportVideo2SolrAll(list, "all");
//                //Vbus.UpdateAfterIndex(lResult);
//                start += 100;
//            }
//        } catch (Exception ex) {
//        }
//    }
//    public void IndexImage2All() {
//        try {
//            //ArrayList<Integer> lResult = new ArrayList<Integer>();
//            ImageBUS Ibus = new ImageBUS();
//            int numOfRecords = Ibus.CountRecord();
//            int start = 0;
//            while (start < numOfRecords) {
//                ArrayList<ImageDTO> list = new ArrayList<ImageDTO>();
//                list = Ibus.getDataList(start, 100);
//                ImportImage2SolrAll(list, "all");
//                //Ibus.UpdateAfterIndex(lResult);
//                start += 100;
//            }
//        } catch (Exception ex) {
//        }
//    }
//
//    public void IndexNews2All() {
//        try {
//            //ArrayList<Integer> lResult = new ArrayList<Integer>();
//            NewsBUS Nbus = new NewsBUS();
//            int numOfRecords = Nbus.CountRecord();
//            int start = 0;
//            while (start < numOfRecords) {
//                ArrayList<NewsDTO> list = new ArrayList<NewsDTO>();
//                list = Nbus.getDataList(start, 100);
//                ImportNews2SolrAll(list, "all");
//                //Nbus.UpdateAfterIndex(lResult);
//                start += 100;
//            }
//        } catch (Exception ex) {
//        }
//    }
//    public void IndexAll(int i) {
//        switch (i) {
//            case 1:
//                IndexWiki2All();
//                break;
//            case 2:
//                IndexRaoVat2All();
//                break;
//            case 3:
//                IndexMusic2All();
//                break;
//            case 4:
//                IndexImage2All();
//                break;
//            case 5:
//                IndexVideo2All();
//                break;
//            case 6:
//                IndexNews2All();
//                break;
//            case 7:
//                IndexImage2All();
//                IndexMusic2All();
//                IndexNews2All();
//                IndexRaoVat2All();
//                IndexVideo2All();
//                IndexWiki2All();
//                break;
//            default:
//                break;
//        }
//    }
    private ArrayList<Integer> ImportMusic2Solr(ArrayList<MusicDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        MusicDTO pagedto = new MusicDTO();
        Iterator<MusicDTO> iter = listpage.iterator();
        ArrayList<Integer> listint = new ArrayList<Integer>();
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
            listint.add(pagedto.getId());
        }

        SolrServer server = getSolrServer(solrServer); // solrServer = music
        //server.add(docs);
        // server.commit();

        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        req.add(docs);
        UpdateResponse rsp = req.process(server);
        return listint;
    }

    private ArrayList<Integer> ImportMusic2SolrAll(ArrayList<MusicDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        MusicDTO pagedto = new MusicDTO();
        Iterator<MusicDTO> iter = listpage.iterator();
        ArrayList<Integer> listint = new ArrayList<Integer>();
        while (iter.hasNext()) {

            pagedto = iter.next();
            doc = new SolrInputDocument();

            doc.addField("id", "ms" + pagedto.getId());
            doc.addField("title", pagedto.getTitle());
            doc.addField("title_unsigned", RemoveSignVN(pagedto.getTitle()));

            doc.addField("category", "Nhạc");
            doc.addField("body", pagedto.getUrl());

            docs.add(doc);
            listint.add(pagedto.getId());
        }

        SolrServer server = getSolrServer(solrServer); // solrServer = music
        server.add(docs);
        server.commit();

//        UpdateRequest req = new UpdateRequest();
//        req.setAction(ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
        return listint;
    }

    private ArrayList<Integer> ImportViwiki2Solr(ArrayList<ViwikiPageDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        ViwikiPageDTO pagedto = new ViwikiPageDTO();
        Iterator<ViwikiPageDTO> iter = listpage.iterator();
        ArrayList<Integer> listint = new ArrayList<Integer>();
        try {
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
                doc.addField("wk_text_raw", pagedto.getText());
                doc.addField("wk_text", pagedto.getText().replaceAll("\\<.*?\\>", ""));
                doc.addField("wk_text_unsigned", RemoveSignVN(pagedto.getText().replaceAll("\\<.*?\\>", "")));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                doc.addField("timestamp", sdf.format(pagedto.getTimestamp().getTime()));
                doc.addField("username", pagedto.getUsername());
                doc.addField("username_unsigned", RemoveSignVN(pagedto.getUsername()));
                doc.addField("keysearch", pagedto.getKeySearch());
                doc.addField("keysearch_unsigned", RemoveSignVN(pagedto.getKeySearch()));
                docs.add(doc);
                listint.add(pagedto.getId());
            }


            SolrServer server = getSolrServer(solrServer); // solrServer =wikipedia
            server.add(docs);
            server.commit();
//            UpdateRequest req = new UpdateRequest();
//            req.setAction(ACTION.COMMIT, false, false);
//            req.add(docs);
//            UpdateResponse rsp = req.process(server);
            return listint;
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            return null;
        }
    }

    private ArrayList<Integer> ImportViwiki2SolrAll(ArrayList<ViwikiPageDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        ViwikiPageDTO pagedto = new ViwikiPageDTO();
        Iterator<ViwikiPageDTO> iter = listpage.iterator();
        ArrayList<Integer> listint = new ArrayList<Integer>();
        while (iter.hasNext()) {
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("id", "wiki" + pagedto.getId());
            doc.addField("title", pagedto.getTitle());
            doc.addField("title_unsigned", RemoveSignVN(pagedto.getTitle()));

            doc.addField("category", "Wikipedia");

            doc.addField("body", pagedto.getText().replaceAll("\\<.*?\\>", ""));
            doc.addField("body_unsigned", RemoveSignVN(pagedto.getText().replaceAll("\\<.*?\\>", "")));
            docs.add(doc);
            listint.add(pagedto.getId());
        }


        SolrServer server = getSolrServer(solrServer); // solrServer =wikipedia
        server.add(docs);
        server.commit();
//        UpdateRequest req = new UpdateRequest();
//        req.setAction(ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
        return listint;
    }

    private ArrayList<Integer> ImportRaoVat2Solr(ArrayList<RaoVatDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        RaoVatDTO pagedto = new RaoVatDTO();
        Iterator<RaoVatDTO> iter = listpage.iterator();
        ArrayList<Integer> listint = new ArrayList<Integer>();
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            doc.addField("last_update", sdf.format(pagedto.getLastUpdate().getTime()));
            doc.addField("location", pagedto.getLocation());
            doc.addField("photo", pagedto.getPhoto());
            doc.addField("price", pagedto.getPrice());
            doc.addField("score", pagedto.getScore());
            doc.addField("site", pagedto.getSite().trim());
            doc.addField("rv_title", pagedto.getTitle());
            doc.addField("rv_title_unsigned", RemoveSignVN(pagedto.getTitle()));
            doc.addField("url", pagedto.getUrl());
            docs.add(doc);
            listint.add(pagedto.getId());
        }
        SolrServer server = getSolrServer(solrServer); // solrServer = raovat
        server.add(docs);
        server.commit();
//        UpdateRequest req = new UpdateRequest();
//        req.setAction(ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
        return listint;
    }

    private ArrayList<Integer> ImportRaoVat2SolrAll(ArrayList<RaoVatDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        RaoVatDTO pagedto = new RaoVatDTO();
        Iterator<RaoVatDTO> iter = listpage.iterator();
        ArrayList<Integer> listint = new ArrayList<Integer>();
        while (iter.hasNext()) {
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("id", "rv" + pagedto.getId());
            String strBody = pagedto.getBody().replaceAll("\\<.*?\\>", "");
            doc.addField("body", strBody);
            doc.addField("body_unsigned", RemoveSignVN(strBody));

            doc.addField("category", "Rao vặt");

            doc.addField("title", pagedto.getTitle());
            doc.addField("title_unsigned", RemoveSignVN(pagedto.getTitle()));
            docs.add(doc);
            listint.add(pagedto.getId());
        }


        SolrServer server = getSolrServer(solrServer); // solrServer = raovat
        server.add(docs);
        server.commit();

//        UpdateRequest req = new UpdateRequest();
//        req.setAction(ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
        return listint;
    }

    private ArrayList<Integer> ImportVideo2Solr(ArrayList<VideoDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        VideoDTO pagedto = new VideoDTO();
        Iterator<VideoDTO> iter = listpage.iterator();
        ArrayList<Integer> listint = new ArrayList<Integer>();
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
            listint.add(pagedto.getId());
        }
        SolrServer server = getSolrServer(solrServer); // solrServer = video
        server.add(docs);
        server.commit();
//        UpdateRequest req = new UpdateRequest();
//        req.setAction(ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
        return listint;
    }

    private ArrayList<Integer> ImportBookmark2Solr(ArrayList<BookmarkDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        BookmarkDTO pagedto = new BookmarkDTO();
        Iterator<BookmarkDTO> iter = listpage.iterator();
        ArrayList<Integer> listint = new ArrayList<Integer>();
        while (iter.hasNext()) {
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("id", pagedto.getId());
            doc.addField("memberid", pagedto.getMemberId());
            doc.addField("docid", pagedto.getDocId());
            doc.addField("searchtype", pagedto.getSearchType());
            doc.addField("bookmarkname", pagedto.getBookmarkName());
            doc.addField("bookmarkname_unsigned", RemoveSignVN(pagedto.getBookmarkName()));
            doc.addField("date_created", pagedto.getDate_create().getTime());
            doc.addField("priority", pagedto.getPriority());

            docs.add(doc);
            listint.add(pagedto.getId());
        }
        SolrServer server = getSolrServer(solrServer); // solrServer = bookmark
        server.add(docs);
        server.commit();
//        UpdateRequest req = new UpdateRequest();
//        req.setAction(ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
        return listint;
    }

    private ArrayList<Integer> ImportVideo2SolrAll(ArrayList<VideoDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        VideoDTO pagedto = new VideoDTO();
        Iterator<VideoDTO> iter = listpage.iterator();
        ArrayList<Integer> listint = new ArrayList<Integer>();
        while (iter.hasNext()) {
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("id", "video" + pagedto.getId());

            doc.addField("title", pagedto.getTitle());
            doc.addField("title_unsigned", RemoveSignVN(pagedto.getTitle()));

            doc.addField("category", "Video");

            doc.addField("body", pagedto.getUrl());

            docs.add(doc);
            listint.add(pagedto.getId());
        }
        SolrServer server = getSolrServer(solrServer); // solrServer = video
        server.add(docs);
        server.commit();
//        UpdateRequest req = new UpdateRequest();
//        req.setAction(ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
        return listint;
    }

    private ArrayList<Integer> ImportImage2Solr(ArrayList<ImageDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        ImageDTO pagedto = new ImageDTO();
        Iterator<ImageDTO> iter = listpage.iterator();
        ArrayList<Integer> listint = new ArrayList<Integer>();
        while (iter.hasNext()) {
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("id", pagedto.getId());
            doc.addField("category", pagedto.getCategory().trim());
            doc.addField("category_index", pagedto.getCategory());
            doc.addField("category_index_unsigned", RemoveSignVN(pagedto.getCategory()));
            doc.addField("url", pagedto.getUrl());
            doc.addField("url_local", pagedto.getUrl_local());
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
            listint.add(pagedto.getId());
        }

        SolrServer server = getSolrServer(solrServer); // solrServer =image
        server.add(docs);
        server.commit();

//        UpdateRequest req = new UpdateRequest();
//        req.setAction(ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
        return listint;
    }

    private ArrayList<Integer> ImportImage2SolrAll(ArrayList<ImageDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        ImageDTO pagedto = new ImageDTO();
        Iterator<ImageDTO> iter = listpage.iterator();
        ArrayList<Integer> listint = new ArrayList<Integer>();
        while (iter.hasNext()) {
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("id", "img" + pagedto.getId());

            doc.addField("body", pagedto.getUrl());

            doc.addField("category", "Hình ảnh");
            doc.addField("title", pagedto.getSite_title());
            doc.addField("title_unsigned", RemoveSignVN(pagedto.getSite_title()));

            docs.add(doc);
            listint.add(pagedto.getId());
        }

        SolrServer server = getSolrServer(solrServer); // solrServer =image
        server.add(docs);
        server.commit();

//        UpdateRequest req = new UpdateRequest();
//        req.setAction(ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
        return listint;
    }

    private ArrayList<Integer> ImportNews2Solr(ArrayList<NewsDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        NewsDTO pagedto = new NewsDTO();
        Iterator<NewsDTO> iter = listpage.iterator();
        ArrayList<Integer> listint = new ArrayList<Integer>();
        while (iter.hasNext()) {
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("id", pagedto.getId());
            doc.addField("category", pagedto.getCategory().trim());
            doc.addField("category_index", pagedto.getCategory());
            doc.addField("category_index_unsigned", RemoveSignVN(pagedto.getCategory()));

            doc.addField("title", pagedto.getTitle());
            doc.addField("title_unsigned", RemoveSignVN(pagedto.getTitle()));

            doc.addField("body", pagedto.getBody());
            doc.addField("body_unsigned", RemoveSignVN(pagedto.getBody()));

            doc.addField("site", pagedto.getSite());
            doc.addField("url", pagedto.getSite());
            doc.addField("photo", pagedto.getSite());

            doc.addField("last_update", pagedto.getLast_update().getTime());

            docs.add(doc);
            listint.add(pagedto.getId());
        }

        SolrServer server = getSolrServer(solrServer); // solrServer = news
        server.add(docs);
        server.commit();


        UpdateRequest req = new UpdateRequest();
        req.setAction(ACTION.COMMIT, false, false);
        req.add(docs);
//        UpdateRequest req = new UpdateRequest();
//        req.setAction(ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
        return listint;
    }

    private ArrayList<Integer> ImportNews2SolrAll(ArrayList<NewsDTO> listpage, String solrServer) throws MalformedURLException, SolrServerException, IOException {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        NewsDTO pagedto = new NewsDTO();
        Iterator<NewsDTO> iter = listpage.iterator();
        ArrayList<Integer> listint = new ArrayList<Integer>();
        while (iter.hasNext()) {
            pagedto = iter.next();
            doc = new SolrInputDocument();
            doc.addField("id", "news" + pagedto.getId());

            doc.addField("category", "Tin tức");

            doc.addField("title", pagedto.getTitle());
            doc.addField("title_unsigned", RemoveSignVN(pagedto.getTitle()));

            String strBody = pagedto.getBody().replaceAll("\\<.*?\\>", "");

            doc.addField("body", strBody);
            doc.addField("body_unsigned", RemoveSignVN(strBody));
            docs.add(doc);
            listint.add(pagedto.getId());
        }
        SolrServer server = getSolrServer(solrServer); // solrServer = news
        server.add(docs);
        server.commit();

//        UpdateRequest req = new UpdateRequest();
//        req.setAction(ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
        return listint;
    }

    public void IndexLocation(ArrayList<LocationDTO> listLocation) throws SQLException, ParseException, SolrServerException, MalformedURLException, IOException {
        EmptyData("location");
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        for (LocationDTO loc : listLocation) {
            doc = new SolrInputDocument();
            doc.addField("id", loc.getId());
            doc.addField("location", loc.getLocation());
            doc.addField("location_unsigned", RemoveSignVN(loc.getLocation()));
            docs.add(doc);
        }
        SolrServer server = getSolrServer("location"); // solrServer = music
        server.add(docs);
        server.commit();
//        UpdateRequest req = new UpdateRequest();
//        req.setAction(ACTION.COMMIT, false, false);
//        req.add(docs);
//        UpdateResponse rsp = req.process(server);
    }

    public void SaveImage() throws SQLException, ParseException, MalformedURLException, SolrServerException, IOException, URISyntaxException {
        SaveImageFromURL ms = new SaveImageFromURL();
        ArrayList<Integer> lResult = new ArrayList<Integer>();
        ImageBUS Ibus = new ImageBUS();
        int numOfRecords = Ibus.CountRecordNotLocal();
        int start = 0;
        while (start < numOfRecords) {
            ArrayList<ImageDTO> list = new ArrayList<ImageDTO>();
            list = Ibus.getListNotLocal(0, 100);
            ms.loadImage(list);
            lResult = ImportImage2Solr(list, "image");
            ImportImage2SolrAll(list, "all");
            Ibus.UpdateAfterIndex(lResult);
            start += 100;
        }
    }
}
