/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BUS;

import DAO.MusicDAO;
import ViSearchSyncDataService.MusicDTO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.xml.datatype.DatatypeConfigurationException;

/**
 *
 * @author tuandom
 */
public class MusicBUS {

    public static ArrayList<MusicDTO> getDataList(int start, int end) throws SQLException, ParseException, DatatypeConfigurationException {
        MusicDAO dao = new MusicDAO();
        return dao.getDataList(start, end);
    }

     public  int CountRecord() throws SQLException {
        MusicDAO dao = new MusicDAO();
        return dao.CountRecord();
    }
}
