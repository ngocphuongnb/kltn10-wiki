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

import java.util.ArrayList;
import javax.xml.datatype.DatatypeConfigurationException;


/**
 *
 * @author tuandom
 */
public class MusicDAO {

    String database = "kltn";
    String table = "media_parsecontent";
    public ArrayList<MusicDTO> getDataList(int start, int end) throws SQLException, ParseException, java.text.ParseException, DatatypeConfigurationException {
        ArrayList<MusicDTO> list = new ArrayList<MusicDTO>();
        Connection cn = (Connection) DataProvider.getConnection(database);
        Statement st = (Statement) cn.createStatement();
        String query = String.format("SELECT * FROM %s LIMIT %d, %d",table, start, end);
        ResultSet rs = st.executeQuery(query);

        MusicDTO page;

        while (rs.next()) {
            page = new MusicDTO();
            page.setId(rs.getInt("Id"));
            page.setTitle(rs.getString("Title"));
            page.setCategory(rs.getString("Category"));
            page.setArtist(rs.getString("Artist"));
            page.setAlbum(rs.getString("Album"));
            page.setUrl(rs.getString("URL"));
            page.setLyric(rs.getString("Lyrics"));
            page.setAuthor(rs.getString("Author"));

//            String timestamp = rs.getString("DateUpload");
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date d = sdf.parse(timestamp);
//            GregorianCalendar gcal = new GregorianCalendar();
//            gcal.setTime(d);
//            XMLGregorianCalendar date;
//            date = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
//            page.setDayUpload(date);

            list.add(page);
        }

        rs.close();
        cn.close();
        return list;
    }

    public int CountRecord() throws SQLException {
        int iCount = 0;
        Connection cn = (Connection) DataProvider.getConnection(database);
        Statement st = (Statement) cn.createStatement();
        String query = "SELECT count(*) as NumRow FROM " + table;
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            iCount = rs.getInt("NumRow");
        }

        rs.close();
        cn.close();
        return iCount;
    }
}
