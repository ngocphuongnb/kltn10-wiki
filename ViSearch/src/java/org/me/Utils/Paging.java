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
                Paging = "<a href=\"" + URL + "&currentpage=1\"><img style=\"border:none\" src='images/btFirst.png' onmouseover=\"this.src='images/btFirstMouseOver.png';\" onmouseout=\"this.src='images/btFirst.png';\"  align='absmiddle'/></a>";
                Paging += "<a href=\"" + URL + "&currentpage=" + page + "\"><img style=\"border:none\" src='images/btPrevious.png' onmouseover=\"this.src='images/btPreviousMouseOver.png';\" onmouseout=\"this.src='images/btPrevious.png';\" align='absmiddle'/></a> ";
            } else {
                Paging = "<img style=\"border:none\" src='images/btFirstDisable.png' align='absmiddle'/> ";
                Paging += "<img style=\"border:none\" src='images/btPreviousDisable.png' align='absmiddle'/>  ";
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
                Paging += "<a  href=\"" + URL + "&currentpage=" + page + "\"><img style=\"border:none\" src='images/btNext.png' onmouseover=\"this.src='images/btNextMouseOver.png';\" onmouseout=\"this.src='images/btNext.png';\" align='absmiddle'/></a>";
                Paging += "<a  href=\"" + URL + "&currentpage=" + numpage + "\"><img style=\"border:none\" src='images/btLast.png' onmouseover=\"this.src='images/btLastMouseOver.png';\" onmouseout=\"this.src='images/btLast.png';\" align='absmiddle'/></a>";
            } else {
                Paging += "<img style=\"border:none\" src='images/btNextDisable.png' align='absmiddle'/> ";
                Paging += "<img style=\"border:none\" src='images/btLastDisable.png' align='absmiddle'/>";
            }
        }

        return Paging;
    }
}
