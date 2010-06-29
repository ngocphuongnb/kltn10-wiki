/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DAO.RaoVatDAO;
import ViSearchSyncDataService.RaoVatDTO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.xml.datatype.DatatypeConfigurationException;

/**
 *
 * @author VinhPham
 */
public class RaoVatBUS {

    public ArrayList<RaoVatDTO> getDataList(int start, int end) throws SQLException, ParseException, com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException, DatatypeConfigurationException {
        RaoVatDAO dao = new RaoVatDAO();
        return dao.getDataList(start, end);
    }

    public int CountRecord() throws SQLException {
        RaoVatDAO dao = new RaoVatDAO();
        return dao.CountRecord();
    }
}
