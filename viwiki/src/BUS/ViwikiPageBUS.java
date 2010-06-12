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

/**
 *
 * @author VinhPham
 */
public class ViwikiPageBUS {

    public ArrayList<ViwikiPageDTO> getDataList(int start, int end) throws SQLException, ParseException {
        ViwikiPageDAO dao = new ViwikiPageDAO();
        return dao.getDataList(start, end);
    }

    public  int CountRecord() throws SQLException {
        ViwikiPageDAO dao = new ViwikiPageDAO();
        return dao.CountRecord();
    }
}
