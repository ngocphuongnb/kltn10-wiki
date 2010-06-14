/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.me.dto.BookMarkDTO;

/**
 *
 * @author VinhPham
 */
public class BookMarkDAO {

    public boolean InsertBookmark(BookMarkDTO bookmark, String database) {
        boolean result = true;
        Connection cn = DataProvider.getConnection(database);
        try {
            CallableStatement cs;
            cs = cn.prepareCall("{CALL Insert_Bookmark(?, ?, ?, ?, ?)}");
            cs.setInt(1, bookmark.getMemberId());
            cs.setString(2, bookmark.getKeySearch());
            cs.setString(3, bookmark.getDocId());
            cs.setInt(4, bookmark.getSearchType());
            cs.setString(5, bookmark.getNameBookmark());

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

    public List<Object[]> GetBookmark(int memberid, int searchType, String database) {
        Connection cn = DataProvider.getConnection(database);
        try {
            CallableStatement cs;
            cs = cn.prepareCall("{CALL Get_Bookmark(?, ?)}");
            cs.setInt(1, memberid);
            cs.setInt(2, searchType);

            ResultSet rs = cs.executeQuery();
            ArrayList<String> arrDocId = new ArrayList<String>();
            Object[] bmObj = new Object[2];
            List<Object[]> lstBm = new ArrayList<Object[]>();
            String keySearch = "";
            while (rs.next()) {
                if (rs.getString("keysearch").equals(keySearch) == false) { // la keySearch moi
                    // Luu lai cai cu
                     bmObj = new Object[2];
                    bmObj[0] = keySearch;
                    bmObj[1] = arrDocId;
                    lstBm.add(bmObj);

                    // Tao cai moi
                    keySearch = rs.getString("keysearch");
                    arrDocId = new ArrayList<String>();
                    arrDocId.add(rs.getString("docid"));
                } else { // la keySearch cu
                    arrDocId.add(rs.getString("docid"));
                }
            }
            // Khi da het
            bmObj = new Object[2];
            bmObj[0] = keySearch;
            bmObj[1] = arrDocId;
            lstBm.add(bmObj);
            
            cn.close();
            if (lstBm.size() >= 1) {
                lstBm.remove(0);
            }

            return lstBm;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
