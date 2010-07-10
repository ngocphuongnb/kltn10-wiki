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
public class NewsDTO {

    private int _id;
    private String _title;
    private String _body;
    private String _category;
    private Calendar _created;
    private Calendar _last_update;
    private int _modified_by;
    private String photo;
    private String url;
    private String site;

    public void setBody(String _body) {
        this._body = _body;
    }

    public void setLast_update(Calendar _last_update) {
        this._last_update = _last_update;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return _body;
    }

    public Calendar getLast_update() {
        return _last_update;
    }

    public String getPhoto() {
        return photo;
    }

    public String getSite() {
        return site;
    }

    public String getUrl() {
        return url;
    }

    public void setCategory(String _category) {
        this._category = _category;
    }

    public String getCategory() {
        return _category;
    }

    public Calendar getCreated() {
        return _created;
    }

    public int getId() {
        return _id;
    }

    public int getModified_by() {
        return _modified_by;
    }

    public String getTitle() {
        return _title;
    }

    public void setCreated(Calendar _created) {
        this._created = _created;
    }

    public void setId(int _id) {
        this._id = _id;
    }


    public void setModified_by(int _modified_by) {
        this._modified_by = _modified_by;
    }

    public void setTitle(String _title) {
        this._title = _title;
    }
}
