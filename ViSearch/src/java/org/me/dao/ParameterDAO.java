/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import org.me.dto.ParameterDTO;
/**
 *
 * @author VinhPham
 */
public class ParameterDAO {

    public boolean  InsertNewMember(ParameterDTO param, String database) {
        boolean result = true;
        Connection cn = DataProvider.getConnection(database);
        try {
            CallableStatement cs;
            cs = cn.prepareCall("{CALL Insert_Parameter(?, ?)}");
            cs.setString(1, param.getName());
            cs.setString(2, param.getValue());
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