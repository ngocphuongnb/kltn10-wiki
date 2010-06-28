/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import ViSearchSyncDataService.ViwikiPageDTO;
import info.bliki.wiki.model.WikiModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author VinhPham
 */
public class ViwikiPageDAO {

    public ArrayList<ViwikiPageDTO> getDataList(int start, int end) throws SQLException, ParseException, DatatypeConfigurationException {
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
            page.setRestrictions(rs.getString("restrictions"));
            page.setText(rs.getString("text"));
            page.setTitle(rs.getString("title"));
            page.setUsername(rs.getString("username"));
            String timestamp = rs.getString("timestamp");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date d = sdf.parse(timestamp);
            GregorianCalendar gcal = new GregorianCalendar();
            gcal.setTime(d);
            XMLGregorianCalendar date;
            date = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
            page.setTimestamp(date);
            list.add(page);
        }
        rs.close();
        cn.close();
        return list;
    }

    public int CountRecord() throws SQLException {
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
