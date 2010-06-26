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
public class BookmarkDTO {

    private int _id;
    private int _memberId;
    private String _docId;
    private int _searchType;
    private String _bookmarkName;
    private Calendar _date_create;
    private int _priority;

    public Calendar getDate_create() {
        return _date_create;
    }

    public void setDate_create(Calendar _date_create) {
        this._date_create = _date_create;
    }

    public String getBookmarkName() {
        return _bookmarkName;
    }

    public String getDocId() {
        return _docId;
    }

    public int getId() {
        return _id;
    }

    public int getMemberId() {
        return _memberId;
    }

    public int getSearchType() {
        return _searchType;
    }

    public void setBookmarkName(String _bookmarkName) {
        this._bookmarkName = _bookmarkName;
    }

    public void setDocId(String _docId) {
        this._docId = _docId;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public void setMemberId(int _memberId) {
        this._memberId = _memberId;
    }

    public void setSearchType(int _searchType) {
        this._searchType = _searchType;
    }

    /**
     * @return the _priority
     */
    public int getPriority() {
        return _priority;
    }

    /**
     * @param priority the _priority to set
     */
    public void setPriority(int priority) {
        this._priority = priority;
    }
}
