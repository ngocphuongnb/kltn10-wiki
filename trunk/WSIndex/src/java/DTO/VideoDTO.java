/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.Calendar;

/**
 *
 * @author tuandom
 */
public class VideoDTO {

    private int _id;
    private String _title;
    private String _category;
    private String _url;
    private String _duration;
    private String _keySearch;
    private int _frequency;


public void setId(int _id) {
        this._id = _id;
    }

    public int getId() {
        return _id;
    }
    public String getCategory() {
        return _category;
    }

    public String getDuration() {
        return _duration;
    }


    public String getTitle() {
        return _title;
    }


    public String getUrl() {
        return _url;
    }



    public void setCategory(String _category) {
        this._category = _category;
    }

    public void setDuration(String _duration) {
        this._duration = _duration;
    }


    public void setTitle(String _title) {
        this._title = _title;
    }

    public void setUrl(String _url) {
        this._url = _url;
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
}
