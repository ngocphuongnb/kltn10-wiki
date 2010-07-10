/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DAO.ImageDAO;
import ViSearchSyncDataService.ImageDTO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.xml.datatype.DatatypeConfigurationException;

/**
 *
 * @author tuandom
 */
public class ImageBUS {

    public ArrayList<ImageDTO> getDataList(int start, int end) throws SQLException, ParseException, DatatypeConfigurationException {
        ImageDAO dao = new ImageDAO();
        return dao.getDataList(start, end);
    }

    public int CountRecord() throws SQLException {
        ImageDAO  dao = new ImageDAO();
        return dao.CountRecord();
    }
}
