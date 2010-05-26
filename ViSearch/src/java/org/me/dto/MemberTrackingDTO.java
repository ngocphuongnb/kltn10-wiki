/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.dto;

/**
 *
 * @author VinhPham
 */
public class MemberTrackingDTO {
private int _memberID;
    private int _urlID;
    private String _keySearch;
    private float _frequency;

    /**
     * @return the _memberID
     */
    public int getMemberID() {
        return _memberID;
    }

    /**
     * @param memberID the _memberID to set
     */
    public void setMemberID(int memberID) {
        this._memberID = memberID;
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
}
