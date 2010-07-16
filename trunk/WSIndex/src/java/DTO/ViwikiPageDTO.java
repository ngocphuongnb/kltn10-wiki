/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.Calendar;

/**
 *
 * @author VinhPham
 */
public class ViwikiPageDTO {

    private int _id;
    private String _title;
    private Calendar _timestamp;
    private String _ip;
    private String _text;
    private String _restrictions;
    private String _username;
    private String _comment;
    private String _keySearch;
    private int _frequency;

    /**
     * @return the _title
     */
    public String getTitle() {
        return _title;
    }

    /**
     * @param title the _title to set
     */
    public void setTitle(String title) {
        this._title = title;
    }

    /**
     * @return the _redirect
     */
//    public String getRedirect() {
//        return _redirect;
//    }
    /**
     * @param redirect the _redirect to set
     */
//    public void setRedirect(String redirect) {
//        this._redirect = redirect;
//    }
    /**
     * @return the _timestamp
     */
    public Calendar getTimestamp() {
        return _timestamp;
    }

    /**
     * @param timestamp the _timestamp to set
     */
    public void setTimestamp(Calendar timestamp) {
        this._timestamp = timestamp;
    }

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
     * @return the _text
     */
    public String getText() {
        return _text;
    }

    /**
     * @param text the _text to set
     */
    public void setText(String text) {
        this._text = text;
    }

    /**
     * @return the _restrictions
     */
    public String getRestrictions() {
        return _restrictions;
    }

    /**
     * @param restrictions the _restrictions to set
     */
    public void setRestrictions(String restrictions) {
        this._restrictions = restrictions;
    }

    /**
     * @return the _username
     */
    public String getUsername() {
        return _username;
    }

    /**
     * @param username the _username to set
     */
    public void setUsername(String username) {
        this._username = username;
    }

    /**
     * @return the _minor
     */
//    public String getMinor() {
//        return _minor;
//    }
    /**
     * @param minor the _minor to set
     */
//    public void setMinor(String minor) {
//        this._minor = minor;
//    }
    /**
     * @return the _comment
     */
    public String getComment() {
        return _comment;
    }

    /**
     * @param comment the _comment to set
     */
    public void setComment(String comment) {
        this._comment = comment;
    }

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
    public int getFrequency() {
        return _frequency;
    }

    /**
     * @param frequency the _frequency to set
     */
    public void setFrequency(int frequency) {
        this._frequency = frequency;
    }
}
