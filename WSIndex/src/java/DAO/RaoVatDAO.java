/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.RaoVatDTO;
import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Statement;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author VinhPham
 */
public class RaoVatDAO {

    public ArrayList<RaoVatDTO> getDataList(int start, int end) throws SQLException, ParseException, java.text.ParseException {
        ArrayList<RaoVatDTO> list = new ArrayList<RaoVatDTO>();
        Connection cn = DataProvider.getConnection("visearch");
        Statement st = (Statement) cn.createStatement();
        String query = String.format("SELECT * FROM data_raovat where tracking_updated=1 LIMIT %d, %d", start, end);
        ResultSet rs = st.executeQuery(query);

        RaoVatDTO page;

        while (rs.next()) {
            page = new RaoVatDTO();
            page.setId(rs.getInt("id"));
            page.setBody(rs.getString("body"));
            page.setCategory(rs.getString("category"));
            page.setContact(rs.getString("contact"));
            page.setLocation(rs.getString("location"));
            page.setPhoto(rs.getString("photo"));
            page.setPrice(rs.getString("price"));
            page.setScore(rs.getInt("score"));
            page.setSite(rs.getString("site"));
            page.setTitle(rs.getString("title"));
            page.setUrl(rs.getString("url"));
            String last_update = rs.getString("last_update");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = sdf.parse(last_update);
            Calendar cl = Calendar.getInstance();
            cl.setTime(d);
            page.setLastUpdate(cl);
            list.add(page);
        }

        rs.close();
        cn.close();
        return list;
    }
    public int CountRecord() throws SQLException {
        int iCount = 0;
        Connection cn = DataProvider.getConnection("visearch");
        Statement st = (Statement) cn.createStatement();
        String query = "SELECT count(*) as NumRow FROM data_raovat where tracking_updated=1";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            iCount = rs.getInt("NumRow");
        }

        rs.close();
        cn.close();
        return iCount;
    }

    public void SyncDataRaovat(ArrayList<RaoVatDTO> listPage) throws SQLException
    {
        Connection cn = DataProvider.getConnection("visearch");
        CallableStatement cs;
        cs = (CallableStatement) cn.prepareCall("{Call SyncDataRaovat(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (RaoVatDTO rvdto : listPage) {
            cs.setInt(1, rvdto.getId());
            cs.setString(2, rvdto.getTitle());
            cs.setString(3, rvdto.getBody());
            cs.setString(4, rvdto.getPrice());
            cs.setString(5, rvdto.getCategory());
            cs.setString(6, rvdto.getUrl());
            cs.setString(7, rvdto.getPhoto());
            cs.setInt(8, rvdto.getScore());
            cs.setString(9, rvdto.getSite());
            cs.setString(10, rvdto.getLocation());
            cs.setString(11, rvdto.getContact());
            cs.setString(12, sdf.format(rvdto.getLastUpdate().getTime()));
            cs.executeUpdate();
        }
        cs.close();
        cn.close();
    }
    
    public void UpdateAfterIndex(ArrayList<Integer> list) throws SQLException {
        Connection cn = DataProvider.getConnection("visearch");
        CallableStatement cs;
        cs = (CallableStatement) cn.prepareCall("{Call update_indexed_raovat(?)}");
        for (int i : list) {
            cs.setInt(1, i);
            cs.executeUpdate();
        }
        cs.close();
        cn.close();
    }
}
