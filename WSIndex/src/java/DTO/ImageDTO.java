/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DTO;

/**
 *
 * @author tuandom
 */
public class ImageDTO {
    private int _id;
     private String _category;
    private String _url;
     private String _url_local;
    private String _website;
    private String _site_title;
    private String _site_body;
    private String _fileType;
    private float _width;
    private float _height;
    private String _size;
    private String _keySearch;
    private int _frequency;

    public void setUrl_local(String _url_local) {
        this._url_local = _url_local;
    }

    public String getUrl_local() {
        return _url_local;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getId() {
        return _id;
    }
    public String getCategory() {
        return _category;
    }

    public String getFileType() {
        return _fileType;
    }

    public float getHeight() {
        return _height;
    }

    public String getSite_body() {
        return _site_body;
    }

    public String getSite_title() {
        return _site_title;
    }

    public String getSize() {
        return _size;
    }

    public String getUrl() {
        return _url;
    }

   

    public String getWebsite() {
        return _website;
    }

    public float getWidth() {
        return _width;
    }

    public void setCategory(String _category) {
        this._category = _category;
    }

    public void setFileType(String _fileType) {
        this._fileType = _fileType;
    }

    public void setHeight(float _height) {
        this._height = _height;
    }

    public void setSite_body(String _site_body) {
        this._site_body = _site_body;
    }

    public void setSite_title(String _site_title) {
        this._site_title = _site_title;
    }

    public void setSize(String _size) {
        this._size = _size;
    }

    public void setUrl(String _url) {
        this._url = _url;
    }

   
    public void setWebsite(String _website) {
        this._website = _website;
    }

    public void setWidth(float _width) {
        this._width = _width;
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
