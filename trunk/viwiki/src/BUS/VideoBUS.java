/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import ViSearchSyncDataService.VideoDTO;
import DAO.VideoDAO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;


/**
 *
 * @author tuandom
 */
public class VideoBUS {

    public ArrayList<VideoDTO> getDataList(int start, int end) throws SQLException, ParseException {
        VideoDAO dao = new VideoDAO();
        return dao.getDataList(start, end);
    }

    public int CountRecord() throws SQLException {
        VideoDAO dao = new VideoDAO();
        return dao.CountRecord();
    }
}
