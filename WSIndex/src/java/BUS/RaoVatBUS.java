/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DAO.RaoVatDAO;
import DTO.RaoVatDTO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author VinhPham
 */
public class RaoVatBUS {

    public ArrayList<RaoVatDTO> getDataList(int start, int end) throws SQLException, ParseException {
        RaoVatDAO dao = new RaoVatDAO();
        return dao.getDataList(start, end);
    }

    public int CountRecord() throws SQLException {
        RaoVatDAO dao = new RaoVatDAO();
        return dao.CountRecord();
    }

    public void SyncDataRaovat(ArrayList<RaoVatDTO> listPage) throws SQLException
    {
        RaoVatDAO dao = new RaoVatDAO();
        dao.SyncDataRaovat(listPage);
    }
}
