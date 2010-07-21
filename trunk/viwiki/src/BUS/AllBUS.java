/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package viwiki;

import DAO.AllDAO;
import ViSearchSyncDataService.PageDTO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.xml.datatype.DatatypeConfigurationException;
/**
 *
 * @author tuandom
 */
class AllBUS {
 public ArrayList<PageDTO> getDataList(int start, int end) throws SQLException, ParseException, DatatypeConfigurationException {
        AllDAO dao = new AllDAO();
        return dao.getDataList(start, end);
    }

    public int CountRecord() throws SQLException {
        AllDAO  dao = new AllDAO();
        return dao.CountRecord();
    }
}
