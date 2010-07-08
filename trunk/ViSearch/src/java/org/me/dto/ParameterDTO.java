/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.dto;

/**
 *
 * @author VinhPham
 */
public class ParameterDTO {
     private int _Id;
      private String _Name;
       private String _Value;
       private String _Decription;

    public void setDecription(String _Decription) {
        this._Decription = _Decription;
    }

    public String getDecription() {
        return _Decription;
    }

    public int getId() {
        return _Id;
    }

    public String getName() {
        return _Name;
    }

    public String getValue() {
        return _Value;
    }

    public void setId(int _Id) {
        this._Id = _Id;
    }

    public void setName(String _Name) {
        this._Name = _Name;
    }

    public void setValue(String _Value) {
        this._Value = _Value;
    }
       
}
