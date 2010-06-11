/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.MusicDTO;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author tuandom
 */
public class MusicDAO {

    public static ArrayList<MusicDTO> getDataList(int start, int end) throws SQLException, ParseException, java.text.ParseException {
        ArrayList<MusicDTO> list = new ArrayList<MusicDTO>();
        Connection cn = (Connection) DataProvider.getConnection("music");
        Statement st = (Statement) cn.createStatement();
        String query = String.format("SELECT * FROM media_data LIMIT %d, %d", start, end);
        ResultSet rs = st.executeQuery(query);

        MusicDTO page;

        while (rs.next()) {
            page = new MusicDTO();
            page.setTitle(rs.getString("Title"));
            page.setCategory(rs.getString("Category"));
            page.setSinger(rs.getString("Singer"));
            page.setAlbum(rs.getString("Album"));
            page.setUrl(rs.getString("URL"));
            page.setLyric(rs.getString("Lyric"));
            page.setArtist(rs.getString("Artist"));

            Date d = new Date();
            d = rs.getDate("DateUpload");

            Calendar cl = Calendar.getInstance();
            cl.setTime(d);
            page.setDayUpload(cl);
            list.add(page);
        }

        rs.close();
        cn.close();
        return list;
    }

    public static int CountRecord() throws SQLException {
        int iCount = 0;
        Connection cn = (Connection) DataProvider.getConnection("music");
        Statement st = (Statement) cn.createStatement();
        String query = "SELECT count(*) as NumRow FROM media_data";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            iCount = rs.getInt("NumRow");
        }

        rs.close();
        cn.close();
        return iCount;
    }
}
