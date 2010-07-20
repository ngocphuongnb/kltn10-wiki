/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BUS;

import DAO.PageDAO;
import DTO.PageDTO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author VinhPham
 */
public class PageBUS {
 public ArrayList<PageDTO> getDataList(int start, int end) throws SQLException, ParseException {
        PageDAO dao = new PageDAO();
        return dao.getDataList(start, end);
    }

    public int CountRecord() throws SQLException {
        PageDAO dao = new PageDAO();
        return dao.CountRecord();
    }

    public void SyncDataAll(ArrayList<PageDTO> listPage) throws SQLException {
        PageDAO dao = new PageDAO();
        dao.SyncDataAll(listPage);
    }

    public void UpdateAfterIndex(ArrayList<Integer> list) throws SQLException {
        PageDAO dao = new PageDAO();
        dao.UpdateAfterIndex(list);
    }
}
