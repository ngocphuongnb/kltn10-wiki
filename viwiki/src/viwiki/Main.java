/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package viwiki;

import BUS.ViwikiPageBUS;
import DTO.ViwikiPageDTO;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.solr.client.solrj.SolrServerException;

/**
 *
 * @author VinhPham
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, MalformedURLException, SolrServerException, IOException {
        // TODO code application logic here
        ViwikiPageBUS bus = new ViwikiPageBUS();
        ArrayList<ViwikiPageDTO> list = bus.getDataList(0, 10);
        MySolrJ ms = new MySolrJ();
        ms.Import2Solr(list);
    }

}
