/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DAO.ImageDAO;
import DTO.ImageDTO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author tuandom
 */
public class ImageBUS {

    public ArrayList<ImageDTO> getDataList(int start, int end) throws SQLException, ParseException {
        ImageDAO dao = new ImageDAO();
        return dao.getDataList(start, end);
    }

    public ArrayList<ImageDTO> getListNotLocal(int start, int end) throws SQLException, ParseException {
        ImageDAO dao = new ImageDAO();
        return dao.getListNotLocal(start, end);
    }

    public int CountRecord() throws SQLException {
        ImageDAO dao = new ImageDAO();
        return dao.CountRecord();
    }

     public int CountRecordNotLocal() throws SQLException {
        ImageDAO dao = new ImageDAO();
        return dao.CountRecordNotLocal();
    }

    public void SyncDataImage(ArrayList<ImageDTO> listPage) throws SQLException {
        ImageDAO dao = new ImageDAO();
        dao.SyncDataImage(listPage);
    }

    public void UpdateAfterIndex(ArrayList<Integer> list) throws SQLException{
        ImageDAO dao = new ImageDAO();
        dao.UpdateAfterIndex(list);
    }
    public void UpdateAfterSaveImage(int id, String localImage) throws SQLException{
        ImageDAO dao = new ImageDAO();
        dao.UpdateAfterSaveImage(id, localImage);
    }
}
