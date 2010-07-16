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
public class RaoVatDTO {

    private int _id;
    private String _title;
    private String _body;
    private String _price;
    private String _category;
    private String _url;
    private String _photo;
    private int _score;
    private String _site;
    private String _location;
    private String _contact;
    private Calendar _lastUpdate;
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
     * @return the _body
     */
    public String getBody() {
        return _body;
    }

    /**
     * @param body the _body to set
     */
    public void setBody(String body) {
        this._body = body;
    }

    /**
     * @return the _price
     */
    public String getPrice() {
        return _price;
    }

    /**
     * @param price the _price to set
     */
    public void setPrice(String price) {
        this._price = price;
    }

    /**
     * @return the _category
     */
    public String getCategory() {
        return _category;
    }

    /**
     * @param category the _category to set
     */
    public void setCategory(String category) {
        this._category = category;
    }

    /**
     * @return the _url
     */
    public String getUrl() {
        return _url;
    }

    /**
     * @param url the _url to set
     */
    public void setUrl(String url) {
        this._url = url;
    }

    /**
     * @return the _photo
     */
    public String getPhoto() {
        return _photo;
    }

    /**
     * @param photo the _photo to set
     */
    public void setPhoto(String photo) {
        this._photo = photo;
    }

    /**
     * @return the _score
     */
    public int getScore() {
        return _score;
    }

    /**
     * @param score the _score to set
     */
    public void setScore(int score) {
        this._score = score;
    }

    /**
     * @return the _site
     */
    public String getSite() {
        return _site;
    }

    /**
     * @param site the _site to set
     */
    public void setSite(String site) {
        this._site = site;
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

    /**
     * @return the _contact
     */
    public String getContact() {
        return _contact;
    }

    /**
     * @param contact the _contact to set
     */
    public void setContact(String contact) {
        this._contact = contact;
    }

    /**
     * @return the _lastUpdate
     */
    public Calendar getLastUpdate() {
        return _lastUpdate;
    }

    /**
     * @param lastUpdate the _lastUpdate to set
     */
    public void setLastUpdate(Calendar lastUpdate) {
        this._lastUpdate = lastUpdate;
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
