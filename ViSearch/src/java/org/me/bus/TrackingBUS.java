/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.bus;

import org.me.dao.TrackingDAO;
import org.me.dto.TrackingDTO;

/**
 *
 * @author VinhPham
 */
public class TrackingBUS {

    public boolean InsertTracking(TrackingDTO tracking, String database) {
        TrackingDAO tdao = new TrackingDAO();
        return tdao.InsertTracking(tracking, database);
    }
}
