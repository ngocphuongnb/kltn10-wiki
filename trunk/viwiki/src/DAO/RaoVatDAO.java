/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import ViSearchSyncDataService.RaoVatDTO;
import com.mysql.jdbc.Statement;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class RaoVatDAO {

    public  ArrayList<RaoVatDTO> getDataList(int start, int end) throws SQLException, ParseException, java.text.ParseException, DatatypeConfigurationException {
        ArrayList<RaoVatDTO> list = new ArrayList<RaoVatDTO>();
        Connection cn = DataProvider.getConnection("raovatdb");
        Statement st = (Statement) cn.createStatement();
        String query = String.format("SELECT * FROM ads_posts LIMIT %d, %d", start, end);
        ResultSet rs = st.executeQuery(query);

        RaoVatDTO page;

        while (rs.next()) {
            page = new RaoVatDTO();
            page.setId(rs.getInt("id"));
            page.setBody(rs.getString("body"));
            page.setCategory(rs.getString("category"));
            page.setContact(rs.getString("contact"));
            page.setLocation(rs.getString("location"));
            page.setPhoto(rs.getString("photo"));
            page.setPrice(rs.getString("price"));
            page.setScore(rs.getInt("score"));
            page.setSite(rs.getString("site"));
            page.setTitle(rs.getString("title"));
            page.setUrl(rs.getString("url"));
            String last_update = rs.getString("last_update");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = sdf.parse(last_update);
            GregorianCalendar gcal = new GregorianCalendar();
            gcal.setTime(d);
            XMLGregorianCalendar date;
            date = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
            page.setLastUpdate(date);
            list.add(page);
        }

        rs.close();
        cn.close();
        return list;
    }

    public  int CountRecord() throws SQLException {
        int iCount = 0;
        Connection cn = DataProvider.getConnection("raovatdb");
        Statement st = (Statement) cn.createStatement();
        String query = "SELECT count(*) as NumRow FROM ads_posts";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            iCount = rs.getInt("NumRow");
        }

        rs.close();
        cn.close();
        return iCount;
    }
}
