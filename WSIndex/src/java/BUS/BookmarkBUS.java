/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DAO.BookmarkDAO;
import DTO.BookmarkDTO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author tuandom
 */
public class BookmarkBUS {

    public ArrayList<BookmarkDTO> getDataList(int start, int end) throws SQLException, ParseException {
        BookmarkDAO dao = new BookmarkDAO();
        return dao.getDataList(start, end);
    }

    public int CountRecord() throws SQLException {
        BookmarkDAO dao = new BookmarkDAO();
        return dao.CountRecord();
    }

    public void UpdateAfterIndex(ArrayList<Integer> list) throws SQLException {
        BookmarkDAO dao = new BookmarkDAO();
        dao.UpdateAfterIndex(list);
    }
     public ArrayList<Integer> GetList2Del() throws SQLException {
        BookmarkDAO dao = new BookmarkDAO();
        return dao.GetList2Del();
    }

     public void EmptyDataSetDel() throws SQLException {
         BookmarkDAO dao = new BookmarkDAO();
        dao.EmptyDataSetDel();
     }
}
