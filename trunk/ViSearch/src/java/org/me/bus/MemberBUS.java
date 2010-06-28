/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.bus;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.me.dao.MemberDAO;
import org.me.dto.MemberDTO;

/**
 *
 * @author VinhPham
 */
public class MemberBUS {

    public MemberDTO Login(String username, String password, String database) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MemberDAO memDao = new MemberDAO();
        return memDao.Login(username, password, database);
    }

    public boolean AddNewMember(MemberDTO member, String database) {
        MemberDAO memDao = new MemberDAO();
        return memDao.AddNewMember(member, database);
    }
    public boolean isExist(String username, String database) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MemberDAO memDao = new MemberDAO();
        return memDao.isExist(username, database);
    }

    public boolean  ChangePass(int id, String oldpass, String newpass, String database)
    {
        MemberDAO memDao = new MemberDAO();
        return memDao.ChangePass(id, oldpass, newpass, database);
    }
    public boolean UpdateInfo(MemberDTO mem, String database){
        MemberDAO memDao = new MemberDAO();
        return memDao.UpdateInfo(mem, database);
    }
}
