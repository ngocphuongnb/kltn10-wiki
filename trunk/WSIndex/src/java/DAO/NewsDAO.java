/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.NewsDTO;
import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
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
public class NewsDAO {

    public int CountRecord() throws SQLException {
        int iCount = 0;
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        Statement st = (Statement) cn.createStatement();
        String query = "SELECT count(*) as NumRow FROM data_news where tracking_updated=1";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            iCount = rs.getInt("NumRow");
        }

        rs.close();
        cn.close();
        return iCount;
    }

    public ArrayList<NewsDTO> getDataList(int start, int end) throws SQLException, ParseException, java.text.ParseException {
        ArrayList<NewsDTO> list = new ArrayList<NewsDTO>();
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        Statement st = (Statement) cn.createStatement();
        String query = String.format("SELECT * FROM data_news where tracking_updated=1 LIMIT %d, %d", start, end);
        ResultSet rs = st.executeQuery(query);

        NewsDTO page;

        while (rs.next()) {
            page = new NewsDTO();
            page.setId(rs.getInt("id"));
            page.setTitle(rs.getString("title"));
            page.setBody(rs.getString("body"));

            String created = rs.getString("last_update");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = sdf.parse(created);
            Calendar cl = Calendar.getInstance();
            cl.setTime(d);
            page.setLast_update(cl);


            list.add(page);
        }

        rs.close();
        cn.close();
        return list;
    }

    public void SyncDataNews(ArrayList<NewsDTO> listPage) throws SQLException {
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        CallableStatement cs;
        cs = (CallableStatement) cn.prepareCall("{Call SyncDataNews(?, ?, ?, ?, ?, ?, ?)}");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (NewsDTO newsDTO : listPage) {
            cs.setInt(1, newsDTO.getId());
            cs.setString(2, newsDTO.getTitle());
            cs.setString(3, newsDTO.getBody());
            cs.setString(4, newsDTO.getCategory());
            cs.setString(5, newsDTO.getSite());
            cs.setString(6, newsDTO.getUrl());
            cs.setString(7, newsDTO.getPhoto());
            cs.setString(8, sdf.format(newsDTO.getLast_update().getTime()));
            cs.executeUpdate();
        }
        cs.close();
        cn.close();
    }

    public void UpdateAfterIndex(ArrayList<Integer> list) throws SQLException {
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        CallableStatement cs;
        cs = (CallableStatement) cn.prepareCall("{Call update_indexed_news(?)}");
        for (int i : list) {
            cs.setInt(1, i);
            cs.executeUpdate();
        }
        cs.close();
        cn.close();
    }
}
