/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DTO;

/**
 *
 * @author VinhPham
 */
public class LocationDTO {
private int _id;
private String _location;

    /**
     * @return the _id
     */
    public int getId() {
        return _id;
    }

    /**
     * @param id the _id to set
     */
    public void setId(int id) {
        this._id = id;
    }

    /**
     * @return the _location
     */
    public String getLocation() {
        return _location;
    }

    /**
     * @param location the _location to set
     */
    public void setLocation(String location) {
        this._location = location;
    }
}
