/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.dto;

import java.util.Calendar;

/**
 *
 * @author VinhPham
 */
public class TrackingDTO {
      private int _Id;
      private String _keySearch;
      private Calendar _timeSearch;
      private String _ip;
      private String _docId;
      private int _memberId;
      private String _timeRange;

    public int getId() {
        return _Id;
    }

    public String getDocId() {
        return _docId;
    }

    public String getIp() {
        return _ip;
    }

    public String getKeySearch() {
        return _keySearch;
    }

    public int getMemberId() {
        return _memberId;
    }

    public Calendar getTimeSearch() {
        return _timeSearch;
    }

    public void setId(int _Id) {
        this._Id = _Id;
    }

    public void setDocId(String _docId) {
        this._docId = _docId;
    }

    public void setIp(String _ip) {
        this._ip = _ip;
    }

    public void setKeySearch(String _keySearch) {
        this._keySearch = _keySearch;
    }

    public void setMemberId(int _memberId) {
        this._memberId = _memberId;
    }

    public void setTimeSearch(Calendar _timeSearch) {
        this._timeSearch = _timeSearch;
    }

    /**
     * @return the _timeRange
     */
    public String getTimeRange() {
        return _timeRange;
    }

    /**
     * @param timeRange the _timeRange to set
     */
    public void setTimeRange(String timeRange) {
        this._timeRange = timeRange;
    }


}
