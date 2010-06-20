/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.bus;

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
}
