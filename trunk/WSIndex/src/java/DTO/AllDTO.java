/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DTO;

/**
 *
 * @author tuandom
 */
public class AllDTO {

    private int _id;
     private String _category;
    private String _title;
    private String _body;

    public void setBody(String _body) {
        this._body = _body;
    }

    public void setCategory(String _category) {
        this._category = _category;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public void setTitle(String _title) {
        this._title = _title;
    }

    public String getBody() {
        return _body;
    }

    public String getCategory() {
        return _category;
    }

    public int getId() {
        return _id;
    }

    public String getTitle() {
        return _title;
    }

}
