/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

/**
 *
 * @author VinhPham
 */
public final class MyHashEncryption {
    //private static PasswordService instance;

    public static String hashPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String strHash = "";
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(password.getBytes("UTF-8"));
        byte raw[] = md.digest();
        strHash = (new BASE64Encoder()).encode(raw);
        return strHash;
    }
}
