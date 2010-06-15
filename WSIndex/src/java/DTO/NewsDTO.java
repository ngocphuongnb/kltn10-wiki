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
    private String _title_alias;
    private String _introtext;
    private String _fulltext;
     private String _category;
    private int _state;
    private int _sectionid;
    private int _mask;
    private int _catid;
    private Calendar _created;
    private int _created_by;
    private String _created_by_alias;
    private Calendar _modified;
    private int _modified_by;
    private int _checked_out;
    private Calendar _checked_out_time;
    private Calendar publish_up;
    private Calendar   	publish_down;
    private String images;
    private String urls;
    private String attribs;
     private int version;
    private int parentid;
    private int ordering;
    private String metakey;
    private String metadesc;
    private int access;
    private int hits;

    public int getCatid() {
        return _catid;
    }

    public void setCategory(String _category) {
        this._category = _category;
    }

    public String getCategory() {
        return _category;
    }

    public int getChecked_out() {
        return _checked_out;
    }

    public Calendar getChecked_out_time() {
        return _checked_out_time;
    }

    public Calendar getCreated() {
        return _created;
    }

    public int getCreated_by() {
        return _created_by;
    }

    public String getCreated_by_alias() {
        return _created_by_alias;
    }

    public String getFulltext() {
        return _fulltext;
    }

    public int getId() {
        return _id;
    }

    public String getIntrotext() {
        return _introtext;
    }

    public int getMask() {
        return _mask;
    }

    public Calendar getModified() {
        return _modified;
    }

    public int getModified_by() {
        return _modified_by;
    }

    public int getSectionid() {
        return _sectionid;
    }

    public int getState() {
        return _state;
    }

    public String getTitle() {
        return _title;
    }

    public String getTitle_alias() {
        return _title_alias;
    }

    public int getAccess() {
        return access;
    }

    public String getAttribs() {
        return attribs;
    }

    public int getHits() {
        return hits;
    }

    public String getImages() {
        return images;
    }

    public String getMetadesc() {
        return metadesc;
    }

    public String getMetakey() {
        return metakey;
    }

    public int getOrdering() {
        return ordering;
    }

    public int getParentid() {
        return parentid;
    }

    public Calendar getPublish_down() {
        return publish_down;
    }

    public Calendar getPublish_up() {
        return publish_up;
    }

    public String getUrls() {
        return urls;
    }

    public int getVersion() {
        return version;
    }

    public void setCatid(int _catid) {
        this._catid = _catid;
    }

    public void setChecked_out(int _checked_out) {
        this._checked_out = _checked_out;
    }

    public void setChecked_out_time(Calendar _checked_out_time) {
        this._checked_out_time = _checked_out_time;
    }

    public void setCreated(Calendar _created) {
        this._created = _created;
    }

    public void setCreated_by(int _created_by) {
        this._created_by = _created_by;
    }

    public void setCreated_by_alias(String _created_by_alias) {
        this._created_by_alias = _created_by_alias;
    }

    public void setFulltext(String _fulltext) {
        this._fulltext = _fulltext;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public void setIntrotext(String _introtext) {
        this._introtext = _introtext;
    }

    public void setMask(int _mask) {
        this._mask = _mask;
    }

    public void setModified(Calendar _modified) {
        this._modified = _modified;
    }

    public void setModified_by(int _modified_by) {
        this._modified_by = _modified_by;
    }

    public void setSectionid(int _sectionid) {
        this._sectionid = _sectionid;
    }

    public void setState(int _state) {
        this._state = _state;
    }

    public void setTitle(String _title) {
        this._title = _title;
    }

    public void setTitle_alias(String _title_alias) {
        this._title_alias = _title_alias;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public void setAttribs(String attribs) {
        this.attribs = attribs;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setMetadesc(String metadesc) {
        this.metadesc = metadesc;
    }

    public void setMetakey(String metakey) {
        this.metakey = metakey;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public void setPublish_down(Calendar publish_down) {
        this.publish_down = publish_down;
    }

    public void setPublish_up(Calendar publish_up) {
        this.publish_up = publish_up;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public void setVersion(int version) {
        this.version = version;
    }


}
