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

    private String _title;
    private String _singer;
    private String _album;
    private String _category;
    private String _url;
    private String artist;
    private String _lyric;
    private Calendar _dayUpload;

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

    public String getSinger() {
        return _singer;
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

    public void setSinger(String _singer) {
        this._singer = _singer;
    }

    public void setTitle(String _title) {
        this._title = _title;
    }

    public void setUrl(String _url) {
        this._url = _url;
    }

    
}
