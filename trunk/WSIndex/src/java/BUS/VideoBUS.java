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

    public ArrayList<VideoDTO> getDataList(int start, int end) throws SQLException, ParseException {
        VideoDAO dao = new VideoDAO();
        return dao.getDataList(start, end);
    }

    public int CountRecord() throws SQLException {
        VideoDAO dao = new VideoDAO();
        return dao.CountRecord();
    }

    public void SyncDataVideo(ArrayList<VideoDTO> listPage) throws SQLException {
        VideoDAO dao = new VideoDAO();
        dao.SyncDataVideo(listPage);
    }

    public void UpdateAfterIndex(ArrayList<Integer> list) throws SQLException {
        VideoDAO dao = new VideoDAO();
        dao.UpdateAfterIndex(list);
    }
}
