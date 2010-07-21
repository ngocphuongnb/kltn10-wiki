/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.PageDTO;
import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author VinhPham
 */
public class PageDAO {

    public ArrayList<PageDTO> getDataList(int start, int end) throws SQLException, ParseException, java.text.ParseException {
        ArrayList<PageDTO> list = new ArrayList<PageDTO>();
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        Statement st = (Statement) cn.createStatement();
        String query = String.format("SELECT * FROM data_all where tracking_updated=1 LIMIT %d, %d", start, end);
        ResultSet rs = st.executeQuery(query);

        PageDTO page;

        while (rs.next()) {
            page = new PageDTO();
            page.setId(rs.getInt("Id"));
            page.setTitle(rs.getString("Title"));
            page.setBody(rs.getString("Body"));
            page.setUrl(rs.getString("URL"));
            page.setKeySearch(rs.getString("keysearch"));
            page.setFrequency(rs.getInt("frequency"));
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
        String query = "SELECT count(*) as NumRow FROM data_all where tracking_updated = 1";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            iCount = rs.getInt("NumRow");
        }

        rs.close();
        cn.close();
        return iCount;
    }

    public void SyncDataAll(ArrayList<PageDTO> listPage) throws SQLException {

        Connection cn = (Connection) DataProvider.getConnection("visearch");
        CallableStatement cs;
        cs = (CallableStatement) cn.prepareCall("{Call SyncDataAll(?, ?, ?, ?)}");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
        for (PageDTO PageDTO : listPage) {
            cs.setInt(1, PageDTO.getId());
            cs.setString(2, PageDTO.getTitle());
            cs.setString(3, PageDTO.getBody());
            cs.setString(4, PageDTO.getUrl());
            cs.executeUpdate();
        }
        cs.close();
        cn.close();
        }catch(Exception ex){
            String s = ex.getMessage();
        }
    }

    public void UpdateAfterIndex(ArrayList<Integer> list) throws SQLException {
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        CallableStatement cs;
        cs = (CallableStatement) cn.prepareCall("{Call update_indexed_all(?)}");
        for (int i : list) {
            cs.setInt(1, i);
            cs.executeUpdate();
        }
        cs.close();
        cn.close();
    }
}
