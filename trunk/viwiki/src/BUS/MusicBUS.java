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

    public static ArrayList<MusicDTO> getDataList(int start, int end) throws SQLException, ParseException {
        return MusicDAO.getDataList(start, end);
    }

    public static int CountRecord() throws SQLException {
        return MusicDAO.CountRecord();
    }
}
