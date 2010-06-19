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
            cs = cn.prepareCall("{CALL Insert_Bookmark(?, ?, ?, ?)}");
            cs.setInt(1, bookmark.getMemberId());
            cs.setString(2, bookmark.getDocId());
            cs.setInt(3, bookmark.getSearchType());
            cs.setString(4, bookmark.getNameBookmark());

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

    public List<Object[]> GetBookmark(int memberid, String database) {
        Connection cn = DataProvider.getConnection(database);
        try {
            CallableStatement cs;
            cs = cn.prepareCall("{CALL Get_Bookmark(?)}");
            cs.setInt(1, memberid);

            ResultSet rs = cs.executeQuery();
            Object[] bmObj = new Object[2];
            List<Object[]> lstBm = new ArrayList<Object[]>();
            while (rs.next()) {
                bmObj = new Object[2];

                String bookmark_name = rs.getString("bookmark_name");
                String searchtype = rs.getString("searchtype");
                String docid = rs.getString("docid");
                String link = "";
                if (searchtype.equals("1")) {
                    link = "DetailWikiController?id=" + docid;
                } else if (searchtype.equals("2")) {
                    link = "DetailRaoVatController?id=" + docid;
                } else if (searchtype.equals("4")) {
                    link = "DetailImageController?id=" + docid;
                } else if (searchtype.equals("6")) {
                    link = "DetailNewsController?id=" + docid;
                }

                bmObj[0] = link;
                bmObj[1] = bookmark_name;

                lstBm.add(bmObj);
            }
            // Khi da het

            cn.close();

            return lstBm;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
