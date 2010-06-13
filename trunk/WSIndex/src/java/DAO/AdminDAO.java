/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Utils.MyHashEncryption;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author VinhPham
 */
public class AdminDAO {

    public boolean CheckSecurity(String code, Date timeRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        String s = sdf.format(timeRequest);
        s = MyHashEncryption.hashPassword(s);
        code = code.replaceAll(s, "");
        String rightCode = "QL0AFWMIX8NRZTKeof9cXsvbvu8=";
        if(code.equals(rightCode))
            return true;
        return false;
    }
}
