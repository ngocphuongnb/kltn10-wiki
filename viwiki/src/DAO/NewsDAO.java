/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import ViSearchSyncDataService.NewsDTO;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * @author tuandom
 */
public class NewsDAO {

    String database = "thao1807";
    String table = "news_parsecontent";
    public  int CountRecord() throws SQLException {
        int iCount = 0;
        Connection cn = (Connection) DataProvider.getConnection(database);
        Statement st = (Statement) cn.createStatement();
        String query = "SELECT count(*) as NumRow FROM news_parsecontent";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            iCount = rs.getInt("NumRow");
        }

        rs.close();
        cn.close();
        return iCount;
    }

    public ArrayList<NewsDTO> getDataList(int start, int end) throws SQLException, ParseException, java.text.ParseException, DatatypeConfigurationException {
        ArrayList<NewsDTO> list = new ArrayList<NewsDTO>();
        Connection cn = (Connection) DataProvider.getConnection(database);
        Statement st = (Statement) cn.createStatement();
        String query = String.format("SELECT * FROM news_parsecontent LIMIT %d, %d", start, end);
        ResultSet rs = st.executeQuery(query);

        NewsDTO page;

        while (rs.next()) {
            page = new NewsDTO();
            page.setId(rs.getInt("id"));
            page.setTitle(rs.getString("title"));
            page.setBody(rs.getString("body"));
            page.setSite(rs.getString("site"));
            page.setUrl(rs.getString("url"));
            page.setPhoto(rs.getString("photo"));

            String timestamp = rs.getString("last_update");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = sdf.parse(timestamp);
            GregorianCalendar gcal = new GregorianCalendar();
            gcal.setTime(d);
            XMLGregorianCalendar date;
            date = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
            page.setLastUpdate(date);

            page.setCategory(rs.getString("category"));

            list.add(page);
        }

        rs.close();
        cn.close();
        return list;
    }
}
