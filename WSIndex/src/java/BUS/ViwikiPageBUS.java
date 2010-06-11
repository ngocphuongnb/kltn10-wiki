/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DAO.ViwikiPageDAO;
import DTO.ViwikiPageDTO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author VinhPham
 */
public class ViwikiPageBUS {

    public static ArrayList<ViwikiPageDTO> getDataList(int start, int end) throws SQLException, ParseException {
        return ViwikiPageDAO.getDataList(start, end);
    }

    public static int CountRecord() throws SQLException {
        return ViwikiPageDAO.CountRecord();
    }
}
