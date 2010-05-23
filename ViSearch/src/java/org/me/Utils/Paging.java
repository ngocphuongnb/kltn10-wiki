/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.Utils;

/**
 *
 * @author VinhPham
 */
public class Paging {

    public static String getPaging(int numpage, int pagesize, int currentpage, String keysearch, String URL, int type) {
        String Paging;
        int page = 0;

        if (currentpage > 1) {
            page = currentpage - 1;
            Paging = "<a href=\"" + URL + "?currentpage=1&type=" + type + "&KeySearch=" + keysearch + "\">[Trang đầu]</a> ";
            Paging += "<a href=\"" + URL + "?currentpage=" + page + "&type=" + type + "&KeySearch=" + keysearch + "\">[Trang trước]</a> ";
        } else {
            Paging = "[Trang đầu]";
            Paging += "[Trang trước]";
        }

        if (currentpage > 2) {
            page = currentpage - 2;
        } else {
            page = 1;
        }

        for (int tam = page + 5; page <= numpage && page < tam; page++) {

            if (page == currentpage) {
                Paging += "<font color='#FF0000'>" + page + "</font> ";
            } else {
                Paging += "<a href=\"" + URL + "?currentpage=" + page + "&type=" + type + "&KeySearch=" + keysearch + "\">" + page + "</a> ";
            }
        }

        if (currentpage < numpage) {
            page = currentpage + 1;
            Paging += "<a href=\"" + URL + "?currentpage=" + page + "&type=" + type + "&KeySearch=" + keysearch + "\">[Trang kế]</a> ";
            Paging += "<a href=\"" + URL + "?currentpage=" + numpage + "&type=" + type + "&KeySearch=" + keysearch + "\">[Trang cuối]</a> ";
        } else {
            Paging += "[Trang kế]";
            Paging += "[Trang cuối]";
        }

        return Paging;
    }
}
