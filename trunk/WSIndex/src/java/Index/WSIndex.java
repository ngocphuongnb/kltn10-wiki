/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Index;

import Utils.MySolrJ;
import BUS.AdminBUS;
import BUS.RaoVatBUS;
import BUS.ViwikiPageBUS;
import DTO.RaoVatDTO;
import DTO.ViwikiPageDTO;
import BUS.MusicBUS;
import DTO.MusicDTO;
import BUS.ImageBUS;
import BUS.NewsBUS;
import BUS.VideoBUS;
import DTO.ImageDTO;
import DTO.NewsDTO;
import DTO.VideoDTO;
import Utils.SaveImageFromURL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author tuandom
 */
@WebService()
public class WSIndex {

    static final int WIKI = 1;
    static final int RAOVAT = 2;
    static final int MUSIC = 3;
    static final int IMAGE = 4;
    static final int VIDEO = 5;
    static final int NEWS = 6;
    static final int ALL = 7;

    /**
     * Web service operation
     */
    @WebMethod(operationName = "SyncDataWiki")
    public void SyncDataViwiki(@WebParam(name = "listWikiPage") ArrayList<ViwikiPageDTO> listWikiPage) {
        try {
            ViwikiPageBUS bus = new ViwikiPageBUS();
            bus.SyncDataViwiki(listWikiPage);
            //TODO write your implementation code here:
        } catch (SQLException ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
        MySolrJ mSolr = new MySolrJ();
        try {
            mSolr.IndexAll(WIKI);
            mSolr.IndexViwiki();
        } catch (Exception ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "IndexDataViwiki")
    public boolean IndexDataViwiki(@WebParam(name = "code") String code, @WebParam(name = "dateRequest") Calendar dateRequest) {
        AdminBUS bus = new AdminBUS();
        if (bus.CheckSecurity(code, dateRequest.getTime())) {
            MySolrJ ms = new MySolrJ();
            try {
                ms.IndexViwiki();
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "SyncDataRaovat")
    public void SyncDataRaovat(@WebParam(name = "listRaovat") ArrayList<RaoVatDTO> listRaovat) {
        try {
            RaoVatBUS bus = new RaoVatBUS();
            bus.SyncDataRaovat(listRaovat);
        } catch (SQLException ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
        MySolrJ mSolr = new MySolrJ();
        try {
            mSolr.IndexAll(RAOVAT);
            mSolr.IndexRaoVat();
        } catch (Exception ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "IndexDataRaovat")
    public boolean IndexDataRaovat(@WebParam(name = "code") String code, @WebParam(name = "dateRequest") Calendar dateRequest) {
        AdminBUS bus = new AdminBUS();
        if (bus.CheckSecurity(code, dateRequest.getTime())) {
            MySolrJ ms = new MySolrJ();
            try {
                ms.IndexRaoVat();
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "SyncDataMusic")
    public void SyncDataMusic(@WebParam(name = "listMusic") ArrayList<MusicDTO> listMusic) {
        try {
            MusicBUS bus = new MusicBUS();
            bus.SyncDataMusic(listMusic);
            //TODO write your implementation code here:
        } catch (SQLException ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
        MySolrJ mSolr = new MySolrJ();
        try {
            mSolr.IndexAll(MUSIC);
            mSolr.IndexMusic();
        } catch (Exception ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "IndexDataMusic")
    public boolean IndexDataMusic(@WebParam(name = "code") String code, @WebParam(name = "dateRequest") Calendar dateRequest) {
        AdminBUS bus = new AdminBUS();
        if (bus.CheckSecurity(code, dateRequest.getTime())) {
            MySolrJ ms = new MySolrJ();
            try {
                ms.IndexMusic();
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "SyncDataImage")
    public void SyncDataImage(@WebParam(name = "listImage") ArrayList<ImageDTO> listImage) {
        try {
            ImageBUS bus = new ImageBUS();
            bus.SyncDataImage(listImage);
            //TODO write your implementation code here:
        } catch (SQLException ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
        }

        MySolrJ mSolr = new MySolrJ();
        try {
            mSolr.IndexAll(IMAGE);
            mSolr.IndexImage();
        } catch (Exception ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "IndexDataImage")
    public boolean IndexDataImage(@WebParam(name = "code") String code, @WebParam(name = "dateRequest") Calendar dateRequest) {
        AdminBUS bus = new AdminBUS();
        if (bus.CheckSecurity(code, dateRequest.getTime())) {
            MySolrJ ms = new MySolrJ();
            try {
                ms.IndexImage();
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "SyncDataVideo")
    public void SyncDataVideo(@WebParam(name = "listVideo") ArrayList<VideoDTO> listVideo) {
        try {
            VideoBUS bus = new VideoBUS();
            bus.SyncDataVideo(listVideo);
            //TODO write your implementation code here:
        } catch (SQLException ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
        }

        MySolrJ mSolr = new MySolrJ();
        try {
            mSolr.IndexAll(VIDEO);
            mSolr.IndexVideo();
        } catch (Exception ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "IndexDataVideo")
    public boolean IndexDataVideo(@WebParam(name = "code") String code, @WebParam(name = "dateRequest") Calendar dateRequest) {
        AdminBUS bus = new AdminBUS();
        if (bus.CheckSecurity(code, dateRequest.getTime())) {
            MySolrJ ms = new MySolrJ();
            try {
                ms.IndexVideo();
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "SyncDataNews")
    public void SyncDataNews(@WebParam(name = "listNews") ArrayList<NewsDTO> listNews) {
        try {
            NewsBUS bus = new NewsBUS();
            bus.SyncDataNews(listNews);
            //TODO write your implementation code here:
        } catch (SQLException ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
        }

        MySolrJ mSolr = new MySolrJ();
        try {
            mSolr.IndexAll(NEWS);
            mSolr.IndexNews();
        } catch (Exception ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "IndexDataNews")
    public boolean IndexDataNews(@WebParam(name = "code") String code, @WebParam(name = "dateRequest") Calendar dateRequest) {
        AdminBUS bus = new AdminBUS();
        if (bus.CheckSecurity(code, dateRequest.getTime())) {
            MySolrJ ms = new MySolrJ();
            try {
                ms.IndexNews();
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "IndexDataAll")
    public boolean IndexDataAll(@WebParam(name = "code") String code, @WebParam(name = "dateRequest") Calendar dateRequest) {
        AdminBUS bus = new AdminBUS();
        if (bus.CheckSecurity(code, dateRequest.getTime())) {
            MySolrJ ms = new MySolrJ();
            try {
                ms.IndexAll(ALL);
                ms.IndexImage();
                ms.IndexMusic();
                ms.IndexNews();
                ms.IndexRaoVat();
                ms.IndexVideo();
                ms.IndexViwiki();
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }
    /**
     * Web service operation
     */
    @WebMethod(operationName = "SaveImage")
    public boolean SaveImage(@WebParam(name = "code")
    String code, @WebParam(name = "dateRequest")
    Calendar dateRequest){
        AdminBUS bus = new AdminBUS();
        if(bus.CheckSecurity(code, dateRequest.getTime()))
        {
            SaveImageFromURL ms = new SaveImageFromURL();
            try {
                // save img ve local va update trong mysql
                ms.loadImage();
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }
}
