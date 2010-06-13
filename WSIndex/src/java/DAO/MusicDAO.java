/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.MusicDTO;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        Statement st = (Statement) cn.createStatement();
         String query = String.format("SELECT * FROM viwiki where tracking_updated=1 LIMIT %d, %d", start, end);
        ResultSet rs = st.executeQuery(query);

        MusicDTO page;

        while (rs.next()) {
            page = new MusicDTO();
            page.setId(rs.getInt("Id"));
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
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        Statement st = (Statement) cn.createStatement();
        String query = "SELECT count(*) as NumRow FROM data_music";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            iCount = rs.getInt("NumRow");
        }

        rs.close();
        cn.close();
        return iCount;
    }
    public void SyncDataMusic(ArrayList<MusicDTO> listPage) throws SQLException
    {
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        CallableStatement cs;
        cs = (CallableStatement) cn.prepareCall("{Call SyncDataMusic(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        for (MusicDTO musicDTO : listPage) {
            cs.setInt(1, musicDTO.getId());
            cs.setString(2, sdf.format(musicDTO.getTitle()));
            cs.setString(3, musicDTO.getSinger());
            cs.setString(4, musicDTO.getAlbum());
            cs.setString(5, musicDTO.getCategory());
            cs.setString(6, musicDTO.getUrl());
            cs.setString(7, musicDTO.getArtist());
            cs.setString(8, musicDTO.getLyric());
            cs.setDate(9, (java.sql.Date) musicDTO.getDayUpload().getTime());
            cs.executeUpdate();
        }
        cs.close();
        cn.close();
    }
}
