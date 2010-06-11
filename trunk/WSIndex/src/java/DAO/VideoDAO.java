/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.DataProvider;
import DTO.VideoDTO;
import DTO.VideoDTO;
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
public class VideoDAO {

    public static ArrayList<VideoDTO> getDataList(int start, int end) throws SQLException, ParseException, java.text.ParseException {
        ArrayList<VideoDTO> list = new ArrayList<VideoDTO>();
        Connection cn = (Connection) DataProvider.getConnection("video");
        Statement st = (Statement) cn.createStatement();
        String query = String.format("SELECT * FROM data LIMIT %d, %d", start, end);
        ResultSet rs = st.executeQuery(query);

        VideoDTO page;

        while (rs.next()) {
            page = new VideoDTO();
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

    public static int CountRecord() throws SQLException {
        int iCount = 0;
        Connection cn = (Connection) DataProvider.getConnection("video");
        Statement st = (Statement) cn.createStatement();
        String query = "SELECT count(*) as NumRow FROM data";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            iCount = rs.getInt("NumRow");
        }

        rs.close();
        cn.close();
        return iCount;
    }
}
