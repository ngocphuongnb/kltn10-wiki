/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.Utils;

/**
 *
 * @author VinhPham
 */
public class MyString {

    public static String cleanQueryTerm(String source) {
        return source.replaceAll("[\\^\\-+:!(){}~*?\"\\\\]", "");
    }
}
