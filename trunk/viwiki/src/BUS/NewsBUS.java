/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BUS;
import DAO.NewsDAO;
import ViSearchSyncDataService.NewsDTO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.xml.datatype.DatatypeConfigurationException;
/**
 *
 * @author tuandom
 */
public class NewsBUS {
public  ArrayList<NewsDTO> getDataList(int start, int end) throws SQLException, ParseException, DatatypeConfigurationException {
    NewsDAO dao = new NewsDAO();
    return dao.getDataList(start, end);
    }

    public  int CountRecord() throws SQLException {
        NewsDAO dao = new NewsDAO();
        return dao.CountRecord();
    }
}
