/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.bus;

import org.me.dao.ParameterDAO;

/**
 *
 * @author VinhPham
 */
public class ParameterBUS {
    public String GetParameter(String name, String database)
    {
        ParameterDAO pardao = new ParameterDAO();
        return pardao.GetParameter(name, database);
    }
}
