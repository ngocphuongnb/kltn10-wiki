/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DAO.TrackingDAO;

/**
 *
 * @author VinhPham
 */
public class TrackingBUS {

    public int countByID(int id, int typeSearch) {
        TrackingDAO dao = new TrackingDAO();
        return dao.countByID(id, typeSearch);
    }
}
