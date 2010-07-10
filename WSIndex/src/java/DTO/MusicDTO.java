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
public class MusicDTO {

    private int _id;
    private String _title;
    private String _author;
    private String _album;
    private String _category;
    private String _url;
    private String artist;
    private String _lyric;
    private Calendar _dayUpload;

    public void setAuthor(String _author) {
        this._author = _author;
    }

    public String getAuthor() {
        return _author;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getId() {
        return _id;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return _album;
    }

    public String getCategory() {
        return _category;
    }

    public Calendar getDayUpload() {
        return _dayUpload;
    }

    public String getLyric() {
        return _lyric;
    }

   

    public String getTitle() {
        return _title;
    }

    public String getUrl() {
        return _url;
    }

    public void setAlbum(String _album) {
        this._album = _album;
    }

    public void setCategory(String _category) {
        this._category = _category;
    }

    public void setDayUpload(Calendar _dayUpload) {
        this._dayUpload = _dayUpload;
    }

    public void setLyric(String _lyric) {
        this._lyric = _lyric;
    }

  

    public void setTitle(String _title) {
        this._title = _title;
    }

    public void setUrl(String _url) {
        this._url = _url;
    }

    
}
