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
public class MemberDTO {

    private int _id;
    private String _userName;
    private String _pass;
    private String _fullName;
    private Calendar _birthDay;
    private int _sex;

    /**
     * @return the _id
     */
    public int getId() {
        return _id;
    }

    /**
     * @param id the _id to set
     */
    public void setId(int id) {
        this._id = id;
    }

    /**
     * @return the _userName
     */
    public String getUserName() {
        return _userName;
    }

    /**
     * @param userName the _userName to set
     */
    public void setUserName(String userName) {
        this._userName = userName;
    }

    /**
     * @return the _pass
     */
    public String getPass() {
        return _pass;
    }

    /**
     * @param pass the _pass to set
     */
    public void setPass(String pass) {
        this._pass = pass;
    }

    /**
     * @return the _fullName
     */
    public String getFullName() {
        return _fullName;
    }

    /**
     * @param fullName the _fullName to set
     */
    public void setFullName(String fullName) {
        this._fullName = fullName;
    }

    /**
     * @return the _birthDay
     */
    public Calendar getBirthDay() {
        return _birthDay;
    }

    /**
     * @param birthDay the _birthDay to set
     */
    public void setBirthDay(Calendar birthDay) {
        this._birthDay = birthDay;
    }

    /**
     * @return the _sex
     */
    public int getSex() {
        return _sex;
    }

    /**
     * @param sex the _sex to set
     */
    public void setSex(int sex) {
        this._sex = sex;
    }
}
