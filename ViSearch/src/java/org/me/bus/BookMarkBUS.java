/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.bus;


import java.util.ArrayList;
import org.me.dao.BookMarkDAO;
import org.me.dto.BookMarkDTO;

/**
 *
 * @author VinhPham
 */
public class BookMarkBUS {

    public boolean InsertBookmark(BookMarkDTO bookmark, String database) {
        BookMarkDAO bookMarkDAO = new BookMarkDAO();
        return bookMarkDAO.InsertBookmark(bookmark, database);
    }
    public boolean DeleteBookmark(int id, String database) {
        BookMarkDAO bookMarkDAO = new BookMarkDAO();
        return bookMarkDAO.DeleteBookmark(id, database);
    }
    public ArrayList<BookMarkDTO> lstBookmark(String database, int memberId,  int start, int pagesize) {
        BookMarkDAO bookMarkDAO = new BookMarkDAO();
        return bookMarkDAO.lstBookmark(database, memberId,  start, pagesize);
    }
    public int getNumRow(String database, int memberId) {
        BookMarkDAO bookMarkDAO = new BookMarkDAO();
        return bookMarkDAO.getNumRow(database, memberId);
    }
}
