/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DAO.AdminDAO;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author VinhPham
 */
public class AdminBUS {

    public boolean CheckSecurity(String code, Date timeRequest) {
        try {
            AdminDAO dao = new AdminDAO();
            return dao.CheckSecurity(code, timeRequest);
        } catch (UnsupportedEncodingException ex) {
            return false;
        } catch (NoSuchAlgorithmException ex) {
            return false;
        }
    }
}
