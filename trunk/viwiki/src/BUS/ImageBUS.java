/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BUS;

import DAO.ImageDAO;
import DTO.ImageDTO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
/**
 *
 * @author tuandom
 */
public class ImageBUS {
public static ArrayList<ImageDTO> getDataList(int start, int end) throws SQLException, ParseException {
        return ImageDAO.getDataList(start, end);
    }

    public static int CountRecord() throws SQLException {
        return ImageDAO.CountRecord();
    }
}