/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DAO.ViwikiPageDAO;
import DTO.ViwikiPageDTO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author VinhPham
 */
public class ViwikiPageBUS {

    public static ArrayList<ViwikiPageDTO> getDataList(int start, int end) throws SQLException
    {
        return ViwikiPageDAO.getDataList(start, end);
    }
}
