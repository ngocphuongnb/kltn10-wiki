/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.dto;

/**
 *
 * @author VinhPham
 */
public class BookMarkDTO {

    private String _Id;
    private int _memberId;
    private String _docId;
    private int _searchType;
    private String _nameBookmark;
    private int _priority;

    public String getId() {
        return _Id;
    }

    public String getDocId() {
        return _docId;
    }

    public int getMemberId() {
        return _memberId;
    }

    public void setId(String _Id) {
        this._Id = _Id;
    }

    public void setDocId(String _docId) {
        this._docId = _docId;
    }

    public void setMemberId(int _memberId) {
        this._memberId = _memberId;
    }

    /**
     * @return the _searchType
     */
    public int getSearchType() {
        return _searchType;
    }

    /**
     * @param searchType the _searchType to set
     */
    public void setSearchType(int searchType) {
        this._searchType = searchType;
    }

    /**
     * @return the _nameBookmark
     */
    public String getNameBookmark() {
        return _nameBookmark;
    }

    /**
     * @param nameBookmark the _nameBookmark to set
     */
    public void setNameBookmark(String nameBookmark) {
        this._nameBookmark = nameBookmark;
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
