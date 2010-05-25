/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import org.me.dto.MemberDTO;

/**
 *
 * @author VinhPham
 */
public class MemberDAO {

    public boolean  InsertNewMember(MemberDTO member, String database) {
        boolean result = true;
        Connection cn = DataProvider.getConnection(database);
        try {
            CallableStatement cs;
            cs = cn.prepareCall("{CALL Insert_Member(?, ?, ?, ?, ?)}");
            cs.setString(1, member.getUserName());
            cs.setString(2, member.getPass());
            cs.setString(3, member.getFullName());
            Calendar cl = Calendar.getInstance();
            cl = member.getBirthDay();
            //String birthdate = cl.get(Calendar.YEAR) + "-" + cl.get(Calendar.MONTH) + "-" + cl.get(Calendar.DAY_OF_MONTH);
            cs.setDate(4, (Date) cl.getTime());
            cs.setBoolean(5, member.isSex());
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
