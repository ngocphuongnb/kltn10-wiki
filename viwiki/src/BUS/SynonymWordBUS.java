/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BUS;

import DAO.SynonymWordDAO;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import java.sql.SQLException;

/**
 *
 * @author VinhPham
 */
public class SynonymWordBUS {
    public static void generateSynonym() throws SQLException, ParseException, java.text.ParseException{
        SynonymWordDAO.generateSynonym();
    }
}
