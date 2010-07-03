/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.Config;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author VinhPhamXP
 */
public class ViSearchConfig {
    public static String database = "";
    public static String username = "";
    public static String password = "";
    private static void init() throws ParserConfigurationException, SAXException, IOException
    {
        File file = new File("vsConfig\\config.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        NodeList nlist = doc.getElementsByTagName("DATABASE");
        Element ele = (Element) nlist.item(0);
        database = ele.getAttribute("DatabaseName");
        username = ele.getAttribute("Username");
        password = ele.getAttribute("Password");
    }
}
