/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.ViwikiPageDTO;
import Utils.MySolrJ;
import com.mysql.jdbc.CallableStatement;
import info.bliki.wiki.model.WikiModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author VinhPham
 */
public class ViwikiPageDAO {

    public ArrayList<ViwikiPageDTO> getDataList(int start, int end) throws SQLException, ParseException {
        ArrayList<ViwikiPageDTO> list = new ArrayList<ViwikiPageDTO>();
        Connection cn = DataProvider.getConnection("visearch");
        Statement st = cn.createStatement();
        String query = String.format("SELECT * FROM viwiki where tracking_updated=1 LIMIT %d, %d", start, end);
        ResultSet rs = st.executeQuery(query);

        ViwikiPageDTO page;
        WikiModel wkmodel = new WikiModel("http://vi.wikipedia.org/wiki/${image}", "http://vi.wikipedia.org/wiki/${title}");
        while (rs.next()) {
            page = new ViwikiPageDTO();
            page.setComment(rs.getString("comment"));
            page.setIp(rs.getString("ip"));
            page.setId(rs.getInt("id"));
            page.setRestrictions(rs.getString("restrictions"));
            String s = "";
            try {
                s = wkmodel.render(rs.getString("text"));
            } catch (Exception ex) {
                s = "Xin lỗi vì dữ liệu quá lớn nên không thể hiển thị\r\nVui lòng click vào link bên trên để xem chi tiết";
                MySolrJ.ierror++;
            }
            page.setText(s);
            page.setTitle(rs.getString("title"));
            page.setUsername(rs.getString("username"));
            String timestamp = rs.getString("timestamp");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date d = sdf.parse(timestamp);
            Calendar cl = Calendar.getInstance();
            cl.setTime(d);
            page.setTimestamp(cl);
            page.setKeySearch(rs.getString("keysearch"));
            list.add(page);
        }

        rs.close();
        cn.close();
        return list;
    }

    public int CountRecord() throws SQLException {
        int iCount = 0;
        Connection cn = DataProvider.getConnection("visearch");
        Statement st = cn.createStatement();
        String query = "SELECT count(*) as NumRow FROM viwiki where tracking_updated=1";
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
            iCount = rs.getInt("NumRow");
        }

        rs.close();
        cn.close();
        return iCount;
    }

    public void SyncDataViwiki(ArrayList<ViwikiPageDTO> listPage) throws SQLException {
        Connection cn = DataProvider.getConnection("visearch");
        CallableStatement cs;
        cs = (CallableStatement) cn.prepareCall("{Call SyncDataViwiki(?, ?, ?, ?, ?, ?, ?)}");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        for (ViwikiPageDTO viwikiPageDTO : listPage) {
            cs.setString(1, viwikiPageDTO.getTitle());
            cs.setString(2, sdf.format(viwikiPageDTO.getTimestamp().getTime()));
            cs.setString(3, viwikiPageDTO.getIp());
            cs.setString(4, viwikiPageDTO.getText());
            cs.setString(5, viwikiPageDTO.getRestrictions());
            cs.setString(6, viwikiPageDTO.getUsername());
            cs.setString(7, viwikiPageDTO.getComment());
            cs.executeUpdate();
        }
        cs.close();
        cn.close();
    }

    public void UpdateAfterIndex(ArrayList<Integer> list) throws SQLException {
        Connection cn = DataProvider.getConnection("visearch");
        CallableStatement cs;
        cs = (CallableStatement) cn.prepareCall("{Call update_indexed_viwiki(?)}");
        for (int i : list) {
            cs.setInt(1, i);
            cs.executeUpdate();
        }
        cs.close();
        cn.close();
    }
}
