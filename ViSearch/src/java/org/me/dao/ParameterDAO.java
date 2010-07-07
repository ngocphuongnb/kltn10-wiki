/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.dao;

import com.mysql.jdbc.Statement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.me.dto.ParameterDTO;
/**
 *
 * @author VinhPham
 */
public class ParameterDAO {

    public boolean  AddParameter(ParameterDTO param, String database) {
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

    public String GetParameter(String name, String database){
        String value = "";
        Connection cn = DataProvider.getConnection(database);
        try {
            CallableStatement cs;
            cs = cn.prepareCall("{CALL select_parameter(?)}");
            cs.setString(1, name);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                value = rs.getString("value");
            }
            cn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return value;
    }

    public ArrayList<ParameterDTO> GetList(String database){
        ArrayList<ParameterDTO> list = new ArrayList<ParameterDTO>();
        Connection cn = DataProvider.getConnection(database);
        try {
            String query = "select * from parameter";
            Statement st = (Statement) cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            ParameterDTO pdto;
            while (rs.next()) {
                pdto = new ParameterDTO();
                pdto.setId(Integer.parseInt(rs.getString("id")));
                pdto.setName(rs.getString("name"));
                pdto.setValue(rs.getString("value"));
                list.add(pdto);
            }
            cn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
