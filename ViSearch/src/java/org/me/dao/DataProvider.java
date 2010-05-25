/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author VinhPham
 */
public class DataProvider {

    public static Connection getConnection(String database) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String connectionString = "jdbc:mysql://localhost/" + database;
            Properties pros = new Properties();
            pros.setProperty("characterEncoding", "utf8");
            pros.setProperty("user", "root");
            pros.setProperty("password", "admin");
            connection = (Connection) DriverManager.getConnection(connectionString, pros);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection;
    }
}
