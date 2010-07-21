/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impl;

import index.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import myquartz.MyHashEncryption;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author VinhPham
 */
public class IndexLocationJob implements Job {

    public void execute(JobExecutionContext jec) throws JobExecutionException {
//        try {
//            System.out.println("Start index...");
//            Date d = new Date();
//            GregorianCalendar gcal = new GregorianCalendar();
//            gcal.setTime(d);
//            XMLGregorianCalendar date;
//            date = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
//            String code = "123";
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
//            String s = sdf.format(d);
//            s = MyHashEncryption.hashPassword(s);
//            code = MyHashEncryption.hashPassword(code);
//            code += s;
//            ArrayList<LocationDTO> listLocation = new ArrayList<LocationDTO>();
//            FileReader fr = new FileReader("vnlocations.txt");
//            BufferedReader br = new BufferedReader(fr);
//            String sLine = "";
//            int id = 0;
//            LocationDTO loc;
//            while ((sLine = br.readLine()) != null) {
//                id++;
//                loc = new LocationDTO();
//                loc.setId(id);
//                loc.setLocation(sLine);
//                listLocation.add(loc);
//
//                if (id % 100 == 0) {
//                    WSIndexService service = new WSIndexService();
//                    WSIndex port = service.getWSIndexPort();
//                    port.indexDataLocation(code, date, listLocation);
//                    System.out.println(id);
//                    listLocation = new ArrayList<LocationDTO>();
//                }
//            }
//
//            if (id % 100 != 0) {
//                WSIndexService service = new WSIndexService();
//                WSIndex port = service.getWSIndexPort();
//                port.indexDataLocation(code, date, listLocation);
//                System.out.println(id);
//            }
//
//            System.out.println("Index finish...");
//            br.close();
//            fr.close();
//        } catch (Exception ex) {
//            System.out.println("Index fail...");
//        }
    }
}
