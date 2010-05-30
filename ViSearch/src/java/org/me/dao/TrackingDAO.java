/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
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

            Calendar cl = Calendar.getInstance();
            cl = tracking.getTimeSearch();
            String datesearch = cl.get(Calendar.YEAR) + "-" + cl.get(Calendar.MONTH) + "-" + cl.get(Calendar.DAY_OF_MONTH);

            cs.setString(2, datesearch);

            cs.setString(3, tracking.getIp());
            cs.setString(4, tracking.getDocId());
            if (tracking.getMemberId() > 0) {
                cs.setInt(5, tracking.getMemberId());
            }
            else
                cs.setNull(5, Types.INTEGER);
            cs.setString(6, tracking.getTimeRange());

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
