/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.bus;

import java.util.ArrayList;
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
    public ArrayList<String> GetTopSearch(int searchtype, String database)
    {
        TrackingDAO tdao = new TrackingDAO();
        return tdao.GetTopSearch(searchtype, database);
    }

    public boolean UpdateKeysearch(int id_link, String keySearch, String table, String database)
    {
        TrackingDAO tdao = new TrackingDAO();
        return tdao.UpdateKeysearch(id_link, keySearch, table, database);
    }
}
