/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DAO.NewsDAO;
import DTO.NewsDTO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author tuandom
 */
public class NewsBUS {

    public ArrayList<NewsDTO> getDataList(int start, int end) throws SQLException, ParseException {
        NewsDAO dao = new NewsDAO();
        return dao.getDataList(start, end);
    }

    public int CountRecord() throws SQLException {
        NewsDAO dao = new NewsDAO();
        return dao.CountRecord();
    }

    public void SyncDataNews(ArrayList<NewsDTO> listPage) throws SQLException {
         NewsDAO dao = new NewsDAO();
        dao.SyncDataNews(listPage);
    }
}
