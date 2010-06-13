/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Index;

import Utils.MySolrJ;
import BUS.AdminBUS;
import BUS.ViwikiPageBUS;
import DTO.ViwikiPageDTO;
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

}
