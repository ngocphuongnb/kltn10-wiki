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
}
