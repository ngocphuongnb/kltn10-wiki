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

    private String _title;
    private String _category;
    private String _url;
    private String _duration;



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
}
