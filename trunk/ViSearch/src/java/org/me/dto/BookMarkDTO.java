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
    private String _keySearch;
    private String _docId;

    public String getId() {
        return _Id;
    }

    public String getDocId() {
        return _docId;
    }

    public String getKeySearch() {
        return _keySearch;
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

    public void setKeySearch(String _keySearch) {
        this._keySearch = _keySearch;
    }

    public void setMemberId(int _memberId) {
        this._memberId = _memberId;
    }

   
}
