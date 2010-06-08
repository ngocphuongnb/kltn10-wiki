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

    public static String getPaging(int numpage, int pagesize, int currentpage, String URL) {
        String Paging = "";
        int page = 0;

        if (numpage == 0) {
            Paging = "Không tìm được kết quả nào";
        } else {

            if(numpage ==1 )
                return "";

            if (currentpage > 1) {
                page = currentpage - 1;
                Paging = "<a href=\"" + URL + "&currentpage=1\"><img src='images/Button_First.png' width='30' height='30' align='absmiddle'/></a> ";
                Paging += "<a href=\"" + URL + "&currentpage=" + page + "\"><img src='images/Button_Rewind.png' width='30' height='30' align='absmiddle'/></a> ";
            } else {
                Paging = "<img src='images/Button_First_Disable.png' width='30' height='30' align='absmiddle'/> ";
                Paging += "<img src='images/Button_Rewind_Disable.png' width='30' height='30' align='absmiddle'/>  ";
            }

            if (currentpage > 2) {
                page = currentpage - 2;
            } else {
                page = 1;
            }

            Paging += "  <font size='+1'>";
            for (int tam = page + 5; page <= numpage && page < tam; page++) {

                if (page == currentpage) {
                    Paging += "<font color='#FF0000' size='+2'><b>" + page + "</b></font> ";
                } else {
                    Paging += "<a href=\"" + URL + "&currentpage=" + page + "\">" + page + "</a> ";
                }
            }

            Paging += "</font> ";

            if (currentpage < numpage) {
                page = currentpage + 1;
                Paging += "<a href=\"" + URL + "&currentpage=" + page + "\"><img src='images/Button_Fast_Forward.png' width='30' height='30' align='absmiddle'/></a> ";
                Paging += "<a href=\"" + URL + "&currentpage=" + numpage + "\"><img src='images/Button_Last.png' width='30' height='30' align='absmiddle'/></a> ";
            } else {
                Paging += "<img src='images/Button_Fast_Forward_Disable.png' width='30' height='30' align='absmiddle'/>";
                Paging += "<img src='images/Button_Last_Disable.png' width='30' height='30' align='absmiddle'/>";
            }
        }

        return Paging;
    }
}
