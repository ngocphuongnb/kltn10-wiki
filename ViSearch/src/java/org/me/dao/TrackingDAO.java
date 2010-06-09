/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import org.me.dto.TrackingDTO;
import java.util.Calendar;

/**
 *
 * @author VinhPham
 */
public class TrackingDAO {

    public boolean InsertTracking(TrackingDTO tracking, String database) {
        boolean result = true;
        Connection cn = DataProvider.getConnection(database);
        try {
            CallableStatement cs;
            cs = cn.prepareCall("{CALL Insert_Tracking(?, ?, ?, ?, ?, ?)}");
            cs.setString(1, tracking.getKeySearch());
            cs.setString(2, tracking.getIp());
            cs.setString(3, tracking.getDocId());
            if (tracking.getMemberId() > 0) {
                cs.setInt(4, tracking.getMemberId());
            } else {
                cs.setNull(4, Types.INTEGER);
            }
            cs.setString(5, tracking.getTimeRange());
            cs.setInt(6, tracking.getSearchType());

            int n = cs.executeUpdate();
            if (n == 0) {
                result = false;
            }
            cn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public ArrayList<String> GetTopSearch(int searchtype, String database){
        ArrayList<String> result = new ArrayList<String>();
        Connection cn = DataProvider.getConnection(database);
        try {
            CallableStatement cs;
            cs = cn.prepareCall("{CALL top_search(?)}");
            cs.setInt(1, searchtype);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("keysearch"));
            }
            cn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public boolean UpdateKeysearch(int id_link, String keySearch, String table, String database) {
        boolean result = true;
        Connection cn = DataProvider.getConnection(database);
        try {
            CallableStatement cs;
            cs = cn.prepareCall("{CALL update_keysearch_" + table + "(?, ?)}");
            cs.setInt(1, id_link);
            cs.setString(2, keySearch);

            int n = cs.executeUpdate();
            if (n == 0) {
                result = false;
            }
            cn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
