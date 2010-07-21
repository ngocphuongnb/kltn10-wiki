/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import ViSearchSyncDataService.*;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
/**
 *
 * @author tuandom
 */
public class AllDAO {
String database = "kltn";
String table = "data_all";
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
     public ArrayList<PageDTO> getDataList(int start, int end) throws SQLException, ParseException, java.text.ParseException {
        ArrayList<PageDTO> list = new ArrayList<PageDTO>();
        Connection cn = (Connection) DataProvider.getConnection(database);
        Statement st = (Statement) cn.createStatement();
        String query = String.format("SELECT * FROM %s LIMIT %d, %d", table, start, end);
        ResultSet rs = st.executeQuery(query);

        PageDTO page;

        while (rs.next()) {
            page = new PageDTO();
            page.setId(rs.getInt("Id"));
            page.setUrl(rs.getString("url"));
            page.setTitle(rs.getString("title"));
            page.setBody(rs.getString("body"));
 
            list.add(page);
        }

        rs.close();
        cn.close();
        return list;
    }

}
