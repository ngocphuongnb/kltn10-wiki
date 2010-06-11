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
     private String _category;
    private String _url;
    private String _website;
    private String _site_title;
    private String _site_body;
    private String _fileType;
    private int _width;
    private int _height;
    private int _size;

    public String getCategory() {
        return _category;
    }

    public String getFileType() {
        return _fileType;
    }

    public int getHeight() {
        return _height;
    }

    public String getSite_body() {
        return _site_body;
    }

    public String getSite_title() {
        return _site_title;
    }

    public int getSize() {
        return _size;
    }

    public String getUrl() {
        return _url;
    }

   

    public String getWebsite() {
        return _website;
    }

    public int getWidth() {
        return _width;
    }

    public void setCategory(String _category) {
        this._category = _category;
    }

    public void setFileType(String _fileType) {
        this._fileType = _fileType;
    }

    public void setHeight(int _height) {
        this._height = _height;
    }

    public void setSite_body(String _site_body) {
        this._site_body = _site_body;
    }

    public void setSite_title(String _site_title) {
        this._site_title = _site_title;
    }

    public void setSize(int _size) {
        this._size = _size;
    }

    public void setUrl(String _url) {
        this._url = _url;
    }

   
    public void setWebsite(String _website) {
        this._website = _website;
    }

    public void setWidth(int _width) {
        this._width = _width;
    }

    
}
