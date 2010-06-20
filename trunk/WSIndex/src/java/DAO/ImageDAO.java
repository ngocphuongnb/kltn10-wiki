/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.ImageDTO;
import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author tuandom
 */
public class ImageDAO {

//    public static int CountRecord() throws SQLException {
//        int iCount = 0;
//        Connection cn = (Connection) DataProvider.getConnection("image");
//        Statement st = (Statement) cn.createStatement();
//        String query = "SELECT count(*) as NumRow FROM data";
//        ResultSet rs = st.executeQuery(query);
//
//        if (rs.next()) {
//            iCount = rs.getInt("NumRow");
//        }
//
//        rs.close();
//        cn.close();
//        return iCount;
//    }
    public ArrayList<ImageDTO> getDataList(int start, int end) throws SQLException, ParseException, java.text.ParseException {
        ArrayList<ImageDTO> list = new ArrayList<ImageDTO>();
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        Statement st = (Statement) cn.createStatement();
        String query = String.format("SELECT * FROM data_image where tracking_updated=1 LIMIT %d, %d", start, end);
        ResultSet rs = st.executeQuery(query);

        ImageDTO page;

        while (rs.next()) {
            page = new ImageDTO();
            page.setId(rs.getInt("Id"));
            page.setCategory(rs.getString("Category"));
            page.setUrl(rs.getString("URL"));
            page.setUrl_local(rs.getString("URL_Local"));
            page.setWebsite(rs.getString("Website"));
            page.setSite_title(rs.getString("Site_Title"));
            page.setSite_body(rs.getString("Site_Body"));
            page.setFileType(rs.getString("FileType"));
            page.setWidth(rs.getFloat("Width"));
            page.setHeight(rs.getFloat("Height"));
            page.setSize(rs.getString("Size"));

            list.add(page);
        }

        rs.close();
        cn.close();
        return list;
    }

    public int CountRecord() throws SQLException {
        int iCount = 0;
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        Statement st = (Statement) cn.createStatement();
        String query = "SELECT count(*) as NumRow FROM data_image where tracking_updated=1";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            iCount = rs.getInt("NumRow");
        }

        rs.close();
        cn.close();
        return iCount;
    }

    public void SyncDataImage(ArrayList<ImageDTO> listPage) throws SQLException {
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        CallableStatement cs;
        cs = (CallableStatement) cn.prepareCall("{Call SyncDataImage(?, ?, ?, ?, ?, ?, ?, ?, ?,?)}");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        for (ImageDTO imageDTO : listPage) {
            cs.setInt(1, imageDTO.getId());
            cs.setString(2, imageDTO.getCategory());
            cs.setString(3, imageDTO.getUrl());
            cs.setString(4, imageDTO.getWebsite());
            cs.setString(5, imageDTO.getSite_title());
            cs.setString(6, imageDTO.getSite_body());
            cs.setString(7, imageDTO.getFileType());
            cs.setFloat(8, imageDTO.getWidth());
            cs.setFloat(9, imageDTO.getHeight());
            cs.setString(10, imageDTO.getSize());
            cs.executeUpdate();
        }
        cs.close();
        cn.close();
    }

    public void UpdateAfterIndex(ArrayList<Integer> list) throws SQLException {
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        CallableStatement cs;
        cs = (CallableStatement) cn.prepareCall("{Call update_indexed_image(?)}");
        for (int i : list) {
            cs.setInt(1, i);
            cs.executeUpdate();
        }
        cs.close();
        cn.close();
    }

    public void UpdateAfterSaveImage(int id, String localImage) throws SQLException {
        Connection cn = (Connection) DataProvider.getConnection("visearch");
        CallableStatement cs;
        cs = (CallableStatement) cn.prepareCall("{Call Update_Image_After_Save(?, ?)}");

        cs.setInt(1, id);
        cs.setString(2, localImage);
        cs.executeUpdate();

        cs.close();
    }
}
