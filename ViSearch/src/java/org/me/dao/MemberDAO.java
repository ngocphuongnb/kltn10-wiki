/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import org.me.Utils.MyHashEncryption;
import org.me.dto.MemberDTO;

/**
 *
 * @author VinhPham
 */
public class MemberDAO {

    public boolean  AddNewMember(MemberDTO member, String database) {
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

    public MemberDTO Login(String username, String password, String database) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MemberDTO member = null;
        Connection cn = DataProvider.getConnection(database);
        try {
            CallableStatement cs;
            cs = cn.prepareCall("{CALL member_login(?, ?)}");
            cs.setString(1, username);
            cs.setString(2, MyHashEncryption.hashPassword(password));
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                member = new MemberDTO();
                Calendar cl = Calendar.getInstance();
                cl.setTime(rs.getDate("birthday"));
                member.setBirthDay(cl);
                member.setFullName(cs.getString("fullname"));
                member.setId(cs.getInt("ID"));
                member.setPass(cs.getString("pass"));
                member.setSex(cs.getBoolean("sex"));
                member.setUserName(cs.getString("username"));
            }
            cn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return member;
    }
}
