/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Index;

import Utils.MySolrJ;
import BUS.AdminBUS;
import BUS.ViwikiPageBUS;
import DTO.ViwikiPageDTO;
import BUS.MusicBUS;
import DTO.MusicDTO;
import BUS.ImageBUS;
import BUS.VideoBUS;
import DTO.ImageDTO;
import DTO.VideoDTO;
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
    /**
     * Web service operation
     */
    @WebMethod(operationName = "SyncDataWiki")
    public void SyncDataViwiki(@WebParam(name = "listWikiPage")
    ArrayList<ViwikiPageDTO> listWikiPage) {
        try {
            ViwikiPageBUS bus = new ViwikiPageBUS();
            bus.SyncDataViwiki(listWikiPage);
            //TODO write your implementation code here:
        } catch (SQLException ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "IndexDataViwiki")
    public boolean IndexDataViwiki(@WebParam(name = "code")
    String code, @WebParam(name = "dateRequest")
    Calendar dateRequest){
        AdminBUS bus = new AdminBUS();
        if(bus.CheckSecurity(code, dateRequest.getTime()))
        {
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
    @WebMethod(operationName = "SyncDataMusic")
    public void SyncDataMusic(@WebParam(name = "listMusic")
    ArrayList<MusicDTO> listMusic) {
        try {
            MusicBUS bus = new MusicBUS();
            bus.SyncDataMusic(listMusic);
            //TODO write your implementation code here:
        } catch (SQLException ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Web service operation
     */
    @WebMethod(operationName = "IndexDataMusic")
    public boolean IndexDataMusic(@WebParam(name = "code")
    String code, @WebParam(name = "dateRequest")
    Calendar dateRequest){
        AdminBUS bus = new AdminBUS();
        if(bus.CheckSecurity(code, dateRequest.getTime()))
        {
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
    public void SyncDataImage(@WebParam(name = "listImage")
    ArrayList<ImageDTO> listImage) {
        try {
            ImageBUS bus = new ImageBUS();
            bus.SyncDataImage(listImage);
            //TODO write your implementation code here:
        } catch (SQLException ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Web service operation
     */
    @WebMethod(operationName = "IndexDataImage")
    public boolean IndexDataImage(@WebParam(name = "code")
    String code, @WebParam(name = "dateRequest")
    Calendar dateRequest){
        AdminBUS bus = new AdminBUS();
        if(bus.CheckSecurity(code, dateRequest.getTime()))
        {
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
    public void SyncDataVideo(@WebParam(name = "listVideo")
    ArrayList<VideoDTO> listVideo) {
        try {
            VideoBUS bus = new VideoBUS();
            bus.SyncDataVideo(listVideo);
            //TODO write your implementation code here:
        } catch (SQLException ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Web service operation
     */
    @WebMethod(operationName = "IndexDataVideo")
    public boolean IndexDataVideo(@WebParam(name = "code")
    String code, @WebParam(name = "dateRequest")
    Calendar dateRequest){
        AdminBUS bus = new AdminBUS();
        if(bus.CheckSecurity(code, dateRequest.getTime()))
        {
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
}
