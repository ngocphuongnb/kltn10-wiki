/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import ViSearchSyncDataService.ImageDTO;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
/**
 *
 * @author tuandom
 */
public class ImageDAO {
String database = "kltn";
String table = "image_parsecontent";
public int CountRecord() throws SQLException {
        int iCount = 0;
        Connection cn = (Connection) DataProvider.getConnection(database);
        Statement st = (Statement) cn.createStatement();
        String query = "SELECT count(*) as NumRow FROM " + table;
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            iCount = rs.getInt("NumRow");
        }

        rs.close();
        cn.close();
        return iCount;
    }
     public ArrayList<ImageDTO> getDataList(int start, int end) throws SQLException, ParseException, java.text.ParseException {
        ArrayList<ImageDTO> list = new ArrayList<ImageDTO>();
        Connection cn = (Connection) DataProvider.getConnection(database);
        Statement st = (Statement) cn.createStatement();
        String query = String.format("SELECT * FROM %s LIMIT %d, %d", table, start, end);
        ResultSet rs = st.executeQuery(query);

        ImageDTO page;

        while (rs.next()) {
            page = new ImageDTO();
            page.setId(rs.getInt("Id"));
            page.setCategory(rs.getString("Category"));
            page.setUrl(rs.getString("URL"));
            page.setWebsite(rs.getString("Website"));
            page.setSiteTitle(rs.getString("Site_Title"));
            page.setSiteBody(rs.getString("Site_Body"));
            page.setFileType(rs.getString("FileType"));
            page.setWidth(Integer.parseInt(rs.getString("Width")));
            page.setHeight(Integer.parseInt(rs.getString("Height")));
            page.setSize(rs.getString("Size"));

            list.add(page);
        }

        rs.close();
        cn.close();
        return list;
    }

}
