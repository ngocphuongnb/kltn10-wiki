/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.ViwikiPageDTO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author VinhPham
 */
public class ViwikiPageDAO {

    public static ArrayList<ViwikiPageDTO> getDataList(int start, int end) throws SQLException, ParseException {
        ArrayList<ViwikiPageDTO> list = new ArrayList<ViwikiPageDTO>();
        Connection cn = DataProvider.getConnection("kltn");
        Statement st = cn.createStatement();
        String query = String.format("SELECT * FROM viwiki LIMIT %d, %d", start, end);
        ResultSet rs = st.executeQuery(query);

        ViwikiPageDTO page;

        while (rs.next()) {
            page = new ViwikiPageDTO();
            page.setComment(rs.getString("comment"));
            page.setIp(rs.getString("ip"));
            page.setMinor(rs.getString("minor"));
            page.setRedirect(rs.getString("redirect"));
            page.setRestrictions(rs.getString("restrictions"));
            page.setText(rs.getString("text"));
            page.setTitle(rs.getString("title"));
            page.setUsername(rs.getString("username"));
            String timestamp = rs.getString("timestamp");
            timestamp = timestamp.replace('T', ' ');
            timestamp = timestamp.replace('Z', ' ');
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            Date d = sdf.parse(timestamp);
            Calendar cl = Calendar.getInstance();
            cl.setTime(d);
            page.setTimestamp(cl);
            list.add(page);
        }

        rs.close();
        cn.close();
        return list;
    }

    public static int CountRecord() throws SQLException {
        int iCount = 0;
        Connection cn = DataProvider.getConnection("kltn");
        Statement st = cn.createStatement();
        String query = "SELECT count(*) as NumRow FROM viwiki";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            iCount = rs.getInt("NumRow");
        }

        rs.close();
        cn.close();
        return iCount;
    }
}
