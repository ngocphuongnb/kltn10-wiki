/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import BUS.ImageBUS;
import DTO.ImageDTO;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author tuandom
 */
public class SaveImageFromURL {

    public void loadImage() throws SQLException, ParseException, MalformedURLException, IOException{
        ImageBUS Ibus = new ImageBUS();
        int numOfRecords = Ibus.CountRecord();
        int start = 0;
         while (start < numOfRecords) {
            ArrayList<ImageDTO> list = new ArrayList<ImageDTO>();
            list = Ibus.getDataList(start, 100);
            for(int i=0; i < list.size(); i++){
                String filename = Integer.toString(list.get(i).getId());
                String localImage = "C:\\"+filename+".jpg";
                save(list.get(i).getUrl(), localImage);
                Ibus.UpdateAfterSaveImage(list.get(i).getId(), localImage);
            }
            start += 100;
        }
    }
    private void save(String ftpServer,
            String destination) throws MalformedURLException,
            IOException {
        if (ftpServer != null && destination != null) {
            
            // check for authentication else assume its anonymous access.

            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                URL url = new URL(ftpServer);
                URLConnection urlc = url.openConnection();

                bis = new BufferedInputStream(urlc.getInputStream());
                bos = new BufferedOutputStream(new FileOutputStream(
                        destination));

                int i;
                while ((i = bis.read()) != -1) {
                    bos.write(i);
                }
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Input not available");
        }
    }
}