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
            String birthdate = cl.get(Calendar.YEAR) + "-" + cl.get(Calendar.MONTH) + "-" + cl.get(Calendar.DAY_OF_MONTH);
            cs.setString(4, birthdate);
            cs.setInt(5, member.getSex());
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

    public boolean  ChangePass(int id, String oldpass, String newpass, String database) {
        boolean result = true;
        Connection cn = DataProvider.getConnection(database);
        try {
            CallableStatement cs;
            cs = cn.prepareCall("{CALL change_password(?, ?, ?)}");
            cs.setInt(1, id);
            cs.setString(2, oldpass);
            cs.setString(3, newpass);
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

    public boolean UpdateInfo(MemberDTO mem, String database) {
        boolean result = true;
        Connection cn = DataProvider.getConnection(database);
        try {
            CallableStatement cs;
            cs = cn.prepareCall("{CALL update_member_info(?, ?, ?, ?)}");
            cs.setInt(1, mem.getId());
            cs.setString(2, mem.getFullName());
            Calendar cl = Calendar.getInstance();
            cl = mem.getBirthDay();
            String birthdate = cl.get(Calendar.YEAR) + "-" + cl.get(Calendar.MONTH) + "-" + cl.get(Calendar.DAY_OF_MONTH);
            cs.setString(3, birthdate);
            cs.setInt(4, mem.getSex());
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
                Date date = rs.getDate("birthday");
                cl.setTime(date);
                member.setBirthDay(cl);
                member.setFullName(rs.getString("fullname"));
                member.setId(rs.getInt("ID"));
                member.setPass(rs.getString("pass"));
                member.setSex(rs.getInt("sex"));
                member.setUserName(rs.getString("username"));
            }
            cn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return member;
    }
    public boolean isExist(String username, String database) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Connection cn = DataProvider.getConnection(database);
        try {
            CallableStatement cs;
            cs = cn.prepareCall("{CALL isExistUsername(?)}");
            cs.setString(1, username);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                return true;
            }
            cn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
