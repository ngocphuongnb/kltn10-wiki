/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import ViSearchSyncDataService.MusicDTO;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author tuandom
 */
public class MusicDAO {

    public ArrayList<MusicDTO> getDataList(int start, int end) throws SQLException, ParseException, java.text.ParseException, DatatypeConfigurationException {
        ArrayList<MusicDTO> list = new ArrayList<MusicDTO>();
        Connection cn = (Connection) DataProvider.getConnection("music");
        Statement st = (Statement) cn.createStatement();
        String query = String.format("SELECT * FROM media_data LIMIT %d, %d", start, end);
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


            String timestamp = rs.getString("DateUpload");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = sdf.parse(timestamp);
            GregorianCalendar gcal = new GregorianCalendar();
            gcal.setTime(d);
            XMLGregorianCalendar date;
            date = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
            page.setDayUpload(date);

            list.add(page);
        }

        rs.close();
        cn.close();
        return list;
    }

    public int CountRecord() throws SQLException {
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
