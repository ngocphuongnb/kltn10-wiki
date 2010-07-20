/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DTO;

/**
 *
 * @author tuandom
 */
public class PageDTO {

    private int _id;
     private String _category;
    private String _title;
    private String _body;
    private String _url;
    private String _keySearch;
    private int _frequency;

    public void setBody(String _body) {
        this._body = _body;
    }

    public void setCategory(String _category) {
        this._category = _category;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public void setTitle(String _title) {
        this._title = _title;
    }

    public String getBody() {
        return _body;
    }

    public String getCategory() {
        return _category;
    }

    public int getId() {
        return _id;
    }

    public String getTitle() {
        return _title;
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
