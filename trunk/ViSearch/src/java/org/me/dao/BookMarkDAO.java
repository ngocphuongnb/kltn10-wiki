/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import org.me.dto.BookMarkDTO;
/**
 *
 * @author VinhPham
 */
public class BookMarkDAO {

    public boolean  InsertBookmark(BookMarkDTO bookmark, String database) {
        boolean result = true;
        Connection cn = DataProvider.getConnection(database);
        try {
            CallableStatement cs;
            cs = cn.prepareCall("{CALL Insert_Bookmark(?, ?, ?, ?)}");
            cs.setInt(1, bookmark.getMemberId());
            cs.setString(2, bookmark.getKeySearch());
            cs.setString(3, bookmark.getDocId());
            cs.setInt(4, bookmark.getSearchType());

            int n = cs.executeUpdate();
            if (n == 0) {
                result = false;
            }
            cn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
