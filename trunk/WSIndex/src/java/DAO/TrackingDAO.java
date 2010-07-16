/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author VinhPham
 */
public class TrackingDAO {

    public int countByID(int id, int typeSearch) {
        int iCount = 0;
        try {

            Connection cn = (Connection) DataProvider.getConnection("visearch");
            Statement st = (Statement) cn.createStatement();
            String query = String.format("SELECT count(*) as NumRow FROM tracking where searchtype=%d and docid=%d", typeSearch, id);
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                iCount = rs.getInt("NumRow");
            }

            rs.close();
            cn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return iCount;
    }
}
