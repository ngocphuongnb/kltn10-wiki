/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package impl;

import IndexData.WSIndex;
import IndexData.WSIndexService;
import java.text.SimpleDateFormat;
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
public class IndexViwikiJob implements Job {

    public void execute(JobExecutionContext jec) throws JobExecutionException {
        try {
            System.out.println("Start index...");
            Date d = new Date();
            GregorianCalendar gcal = new GregorianCalendar();
            gcal.setTime(d);
            XMLGregorianCalendar date;
            date = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
            String code = "123";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
            String s = sdf.format(d);
            s = MyHashEncryption.hashPassword(s);
            code = MyHashEncryption.hashPassword(code);
            code += s;
            WSIndexService service = new WSIndexService();
            WSIndex port = service.getWSIndexPort();
            port.indexDataViwiki(code, date);
            System.out.println("Index finish...");
        } catch (Exception ex) {
            System.out.println("Index fail...");
        }
    }

}
