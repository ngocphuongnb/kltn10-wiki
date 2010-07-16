/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.dao;

import com.mysql.jdbc.Statement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            cs.setString(2, bookmark.getDocId());
            cs.setInt(3, bookmark.getSearchType());
            cs.setString(4, bookmark.getNameBookmark());
            cs.setInt(5, bookmark.getPriority());

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

    public boolean DeleteBookmark(int id, String database) {
        boolean result = true;
        Connection cn = DataProvider.getConnection(database);
        try {
            CallableStatement cs;
            cs = cn.prepareCall("{CALL Delete_Bookmark(?)}");
            cs.setInt(1, id);

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

    public int getNumRow(String database, int memberid) {
        Connection cn = DataProvider.getConnection(database);
        try {
            CallableStatement cs;
            cs = cn.prepareCall("{CALL Get_Bookmark2_Count(?)}");
            cs.setInt(1, memberid);
            ResultSet rs = cs.executeQuery();
            rs = cs.executeQuery();
            while (rs.next()) {
               String count = rs.getString("NumRow");
               cn.close();
               return Integer.parseInt(count);
            }
        } catch (SQLException ex) {
        }
        return -1;
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
                bmObj = new Object[3];

                String bookmark_name = rs.getString("bookmark_name");
                String searchtype = rs.getString("searchtype");
                String docid = rs.getString("docid");
                String link = "";
                if (searchtype.equals("1")) {
                    link = "DetailWikiController?id=" + docid + "&KeySearch=";
                } else if (searchtype.equals("2")) {
                    link = "DetailRaoVatController?id=" + docid + "&KeySearch=";
                } else if (searchtype.equals("3")) {
                    link = "SearchMusicController?type=0&sp=1&f=8&KeySearch=" + docid;
                } else if (searchtype.equals("4")) {
                    link = "DetailImageController?id=" + docid + "&KeySearch=";
                } else if (searchtype.equals("5")) {
                    link = "SearchVideoController?type=0&more=detail&KeySearch=" + docid;
                } else if (searchtype.equals("6")) {
                    link = "DetailNewsController?id=" + docid + "&KeySearch=";
                }

                bmObj[0] = rs.getString("id");
                bmObj[1] = link;
                bmObj[2] = bookmark_name;

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

    public ArrayList<BookMarkDTO> lstBookmark(String database, int memberid,  int start, int pagesize) {
        Connection cn = DataProvider.getConnection(database);
        try {
            Statement st = (Statement) cn.createStatement();
            String query = String.format("select * from bookmark where member_ID = %d limit %d, %d", memberid, start, pagesize);
            ResultSet rs = st.executeQuery(query);
            ArrayList<BookMarkDTO> lstBm = new ArrayList<BookMarkDTO>();
            BookMarkDTO dto;
            while (rs.next()) {
                dto = new BookMarkDTO();

                String searchtype = rs.getString("searchtype");
                String docid = rs.getString("docid");

                dto.setNameBookmark(rs.getString("bookmark_name"));
                dto.setId(rs.getString("id"));
                dto.setSearchType(Integer.parseInt(searchtype));
                dto.setDocId(docid);

                String timestamp = rs.getString("date_created");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = new Date();
                try {
                    d = sdf.parse(timestamp);
                } catch (ParseException ex) {
                    Logger.getLogger(BookMarkDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                Calendar cl = Calendar.getInstance();
                cl.setTime(d);
                dto.setDate_Create(cl);

                String link = "";
                if (searchtype.equals("1")) {
                    link = "DetailWikiController?id=" + docid + "&KeySearch=";
                } else if (searchtype.equals("2")) {
                    link = "DetailRaoVatController?id=" + docid + "&KeySearch=";
                } else if (searchtype.equals("3")) {
                    link = "SearchMusicController?type=0&sp=1&f=8&KeySearch=" + docid;
                } else if (searchtype.equals("4")) {
                    link = "DetailImageController?id=" + docid + "&KeySearch=";
                } else if (searchtype.equals("5")) {
                    link = "SearchVideoController?type=0&more=detail&KeySearch=" + docid;
                } else if (searchtype.equals("6")) {
                    link = "DetailNewsController?id=" + docid + "&KeySearch=";
                }
                dto.setLink(link);
                lstBm.add(dto);
            }
            // Khi da het
            cn.close();

            return lstBm;
        } catch (SQLException ex) {
        }
        return null;
    }
}
