/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import ViSearchSyncDataService.VideoDTO;

/**
 *
 * @author tuandom
 */
public class VideoDAO {
String database = "thao1807";
String table = "video_parsecontent";
    public  ArrayList<VideoDTO> getDataList(int start, int end) throws SQLException, ParseException, java.text.ParseException {
        ArrayList<VideoDTO> list = new ArrayList<VideoDTO>();
        Connection cn = (Connection) DataProvider.getConnection(database);
        Statement st = (Statement) cn.createStatement();
        String query = String.format("SELECT * FROM %s LIMIT %d, %d", table, start, end);
        ResultSet rs = st.executeQuery(query);

        VideoDTO page;

        while (rs.next()) {
            page = new VideoDTO();
             page.setId(rs.getInt("Id"));
            page.setTitle(rs.getString("Title"));
            page.setCategory(rs.getString("Category"));
            page.setUrl(rs.getString("URL"));
            page.setDuration(rs.getString("Duration"));


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
        String query = "SELECT count(*) as NumRow FROM video_parsecontent";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            iCount = rs.getInt("NumRow");
        }

        rs.close();
        cn.close();
        return iCount;
    }
}
