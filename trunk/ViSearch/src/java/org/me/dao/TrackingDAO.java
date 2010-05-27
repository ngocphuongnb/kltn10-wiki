/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import org.me.dto.TrackingDTO;
import java.util.Calendar;
/**
 *
 * @author VinhPham
 */
public class TrackingDAO {

    public boolean  InsertTracking(TrackingDTO tracking, String database) {
        boolean result = true;
        Connection cn = DataProvider.getConnection(database);
        try {
            CallableStatement cs;
            cs = cn.prepareCall("{CALL Insert_Tracking(?, ?, ?, ?, ?)}");
            cs.setString(1, tracking.getKeySearch());

            Calendar cl = Calendar.getInstance();
            cl = tracking.getTimeSearch();
            cs.setDate(2, (Date)cl.getTime());

            cs.setString(3, tracking.getIp());
            cs.setString(4, tracking.getDocId());
            cs.setInt(5, tracking.getMemberId());


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
