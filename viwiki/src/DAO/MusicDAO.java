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
        Connection cn = (Connection) DataProvider.getConnection("musicpro");
        Statement st = (Statement) cn.createStatement();
        String query = String.format("SELECT * FROM media_data, media_album, media_singer where media_data.m_album = media_album.album_id and media_data.m_singer = media_singer.singer_id LIMIT %d, %d", start, end);
        ResultSet rs = st.executeQuery(query);

        MusicDTO page;

        while (rs.next()) {
            page = new MusicDTO();
            page.setTitle(rs.getString("m_title"));
            page.setCategory(rs.getString("m_cat"));
            page.setSinger(rs.getString("singer_name"));
            page.setAlbum(rs.getString("album_name"));
            page.setUrl(rs.getString("m_url"));
            page.setLyric(rs.getString("m_lyric"));
            
            Date d = new Date();
           // if(rs.getDate("m_date")!=null && rs.getDate("m_date")!="")
            //    d = rs.getDate("m_date");
            //else

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
        Connection cn = (Connection) DataProvider.getConnection("musicpro");
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
