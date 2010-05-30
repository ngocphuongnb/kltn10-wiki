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
    private Calendar _lastedView;
    private Calendar _lastedUpdate;
    private String _uploadBy;
    private int CounterView;

    public int getCounterView() {
        return CounterView;
    }

    public String getCategory() {
        return _category;
    }

    public String getDuration() {
        return _duration;
    }

    public Calendar getLastedUpdate() {
        return _lastedUpdate;
    }

    public Calendar getLastedView() {
        return _lastedView;
    }

    public String getTitle() {
        return _title;
    }

    public String getUploadBy() {
        return _uploadBy;
    }

    public String getUrl() {
        return _url;
    }

    public void setCounterView(int CounterView) {
        this.CounterView = CounterView;
    }

    public void setCategory(String _category) {
        this._category = _category;
    }

    public void setDuration(String _duration) {
        this._duration = _duration;
    }

    public void setLastedUpdate(Calendar _lastedUpdate) {
        this._lastedUpdate = _lastedUpdate;
    }

    public void setLastedView(Calendar _lastedView) {
        this._lastedView = _lastedView;
    }

    public void setTitle(String _title) {
        this._title = _title;
    }

    public void setUploadBy(String _uploadBy) {
        this._uploadBy = _uploadBy;
    }

    public void setUrl(String _url) {
        this._url = _url;
    }
}
