/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import DTO.ImageDTO;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
/**
 *
 * @author tuandom
 */
public class ImageDAO {

    public static int CountRecord() throws SQLException {
        int iCount = 0;
        Connection cn = (Connection) DataProvider.getConnection("image");
        Statement st = (Statement) cn.createStatement();
        String query = "SELECT count(*) as NumRow FROM data";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            iCount = rs.getInt("NumRow");
        }

        rs.close();
        cn.close();
        return iCount;
    }
     public static ArrayList<ImageDTO> getDataList(int start, int end) throws SQLException, ParseException, java.text.ParseException {
        ArrayList<ImageDTO> list = new ArrayList<ImageDTO>();
        Connection cn = (Connection) DataProvider.getConnection("image");
        Statement st = (Statement) cn.createStatement();
        String query = String.format("SELECT * FROM data LIMIT %d, %d", start, end);
        ResultSet rs = st.executeQuery(query);

        ImageDTO page;

        while (rs.next()) {
            page = new ImageDTO();
            page.setCategory(rs.getString("Category"));
            page.setUrl_thumbnail(rs.getString("URL_Thumbnail"));
            page.setUrl(rs.getString("URL"));
            page.setWebsite(rs.getString("Website"));
            page.setSite_title(rs.getString("Site_Title"));
            page.setSite_body(rs.getString("Site_Body"));
            page.setFileType(rs.getString("FileType"));
            page.setWidth(Integer.parseInt(rs.getString("Width")));
            page.setHeight(Integer.parseInt(rs.getString("Height")));
            page.setSize(Integer.parseInt(rs.getString("Size")));
            
            list.add(page);
        }

        rs.close();
        cn.close();
        return list;
    }

}
