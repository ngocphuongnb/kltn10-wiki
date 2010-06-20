/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.DataProvider;
import DTO.VideoDTO;
import DTO.VideoDTO;
import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author tuandom
 */
public class VideoDAO {

    public ArrayList<VideoDTO> getDataList(int start, int end) throws SQLException, ParseException, java.text.ParseException {
        ArrayList<VideoDTO> list = new ArrayList<VideoDTO>();
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        Statement st = (Statement) cn.createStatement();
        String query = String.format("SELECT * FROM data_video where tracking_updated=1 LIMIT %d, %d", start, end);
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
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        Statement st = (Statement) cn.createStatement();
        String query = "SELECT count(*) as NumRow FROM data_video where tracking_updated=1";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            iCount = rs.getInt("NumRow");
        }

        rs.close();
        cn.close();
        return iCount;
    }

    public void SyncDataVideo(ArrayList<VideoDTO> listPage) throws SQLException {
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        CallableStatement cs;
        cs = (CallableStatement) cn.prepareCall("{Call SyncDataVideo(?, ?, ?, ?, ?)}");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        for (VideoDTO videoDTO : listPage) {
            cs.setInt(1, videoDTO.getId());
            cs.setString(2, videoDTO.getTitle());
            cs.setString(3, videoDTO.getCategory());
            cs.setString(4, videoDTO.getUrl());
            cs.setString(5, videoDTO.getDuration());
            cs.executeUpdate();
        }
        cs.close();
        cn.close();
    }

    public void UpdateAfterIndex(ArrayList<Integer> list) throws SQLException {
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        CallableStatement cs;
        cs = (CallableStatement) cn.prepareCall("{Call update_indexed_video(?)}");
        for (int i : list) {
            cs.setInt(1, i);
            cs.executeUpdate();
        }
        cs.close();
        cn.close();
    }
}
