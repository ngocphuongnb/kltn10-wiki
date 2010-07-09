/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.bus;

import java.util.ArrayList;
import org.me.dao.ParameterDAO;
import org.me.dto.ParameterDTO;

/**
 *
 * @author VinhPham
 */
public class ParameterBUS {

    public String GetParameter(String name, String database) {
        ParameterDAO pardao = new ParameterDAO();
        return pardao.GetParameter(name, database);
    }

    public ArrayList<ParameterDTO> GetList(String database) {
        ParameterDAO pardao = new ParameterDAO();
        return pardao.GetList(database);
    }
    public void updateParameter(String database, String param, String value) {
        ParameterDAO pardao = new ParameterDAO();
        pardao.updateParameter(database, param, value);
    }
    public void settingDefaultParameter(String database) {
        ParameterDAO pardao = new ParameterDAO();
        pardao.settingDefaultParameter(database);
    }
}
