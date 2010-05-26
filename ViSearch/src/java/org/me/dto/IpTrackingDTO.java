/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.dto;

/**
 *
 * @author VinhPham
 */
public class IpTrackingDTO {
private String _ip;
    private int _urlID;
    private String _keySearch;
    private float _frequency;

    /**
     * @return the _ip
     */
    public String getIp() {
        return _ip;
    }

    /**
     * @param ip the _ip to set
     */
    public void setIp(String ip) {
        this._ip = ip;
    }

    /**
     * @return the _keySearch
     */
    public String getKeySearch() {
        return _keySearch;
    }

    /**
     * @param keySearch the _keySearch to set
     */
    public void setKeySearch(String keySearch) {
        this._keySearch = keySearch;
    }

    /**
     * @return the _frequency
     */
    public float getFrequency() {
        return _frequency;
    }

    /**
     * @param frequency the _frequency to set
     */
    public void setFrequency(float frequency) {
        this._frequency = frequency;
    }

    /**
     * @return the _urlID
     */
    public int getUrlID() {
        return _urlID;
    }

    /**
     * @param urlID the _urlID to set
     */
    public void setUrlID(int urlID) {
        this._urlID = urlID;
    }
}
