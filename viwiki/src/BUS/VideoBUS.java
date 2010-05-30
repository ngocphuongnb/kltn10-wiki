/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BUS;

import DAO.VideoDAO;
import DTO.VideoDTO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
/**
 *
 * @author tuandom
 */
public class VideoBUS {

    public static ArrayList<VideoDTO> getDataList(int start, int end) throws SQLException, ParseException {
        return VideoDAO.getDataList(start, end);
    }

    public static int CountRecord() throws SQLException {
        return VideoDAO.CountRecord();
    }
}
