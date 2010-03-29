/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author VinhPham
 */
public class DataProvider {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String connectionString = "jdbc:mysql://localhost/kltn";
            Properties pros = new Properties();
            pros.setProperty("characterEncoding", "utf8");
            pros.setProperty("user", "root");
            pros.setProperty("password", "");
            connection = (Connection) DriverManager.getConnection(connectionString, pros);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection;
    }
}
