/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DAO.ViwikiPageDAO;
import ViSearchSyncDataService.ViwikiPageDTO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;

/**
 *
 * @author VinhPham
 */
public class ViwikiPageBUS {

    public ArrayList<ViwikiPageDTO> getDataList(int start, int end) throws SQLException, ParseException {
        ViwikiPageDAO dao = new ViwikiPageDAO();
        try {
            return dao.getDataList(start, end);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(ViwikiPageBUS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public  int CountRecord() throws SQLException {
        ViwikiPageDAO dao = new ViwikiPageDAO();
        return dao.CountRecord();
    }
}
