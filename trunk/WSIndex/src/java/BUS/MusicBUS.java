/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BUS;

import DAO.MusicDAO;
import DTO.MusicDTO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
/**
 *
 * @author tuandom
 */
public class MusicBUS {

    public ArrayList<MusicDTO> getDataList(int start, int end) throws SQLException, ParseException {
        return MusicDAO.getDataList(start, end);
    }

    public int CountRecord() throws SQLException {
        return MusicDAO.CountRecord();
    }
     public void SyncDataMusic(ArrayList<MusicDTO> listPage) throws SQLException
    {
        MusicDAO dao = new MusicDAO();
        dao.SyncDataMusic(listPage);
    }
}