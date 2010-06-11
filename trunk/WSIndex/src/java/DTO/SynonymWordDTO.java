/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DTO;

/**
 *
 * @author VinhPham
 */
public class SynonymWordDTO {
    private String _word;
    private String _synonymWord;

    /**
     * @return the _word
     */
    public String getWord() {
        return _word;
    }

    /**
     * @param word the _word to set
     */
    public void setWord(String word) {
        this._word = word;
    }

    /**
     * @return the _synonymWord
     */
    public String getSynonymWord() {
        return _synonymWord;
    }

    /**
     * @param synonymWord the _synonymWord to set
     */
    public void setSynonymWord(String synonymWord) {
        this._synonymWord = synonymWord;
    }
}
