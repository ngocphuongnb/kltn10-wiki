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
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import org.apache.commons.httpclient.util.URIUtil;

/**
 *
 * @author tuandom
 */
public class SaveImageFromURL {

    public void loadImage(ArrayList<ImageDTO> list) throws SQLException, ParseException, MalformedURLException, IOException, URISyntaxException {
        ImageBUS Ibus = new ImageBUS();
        for (int i = 0; i < list.size(); i++) {
            String filename = Integer.toString(list.get(i).getId());
            String ext = getExtension(list.get(i).getUrl());
            String localImage = "VSImageDownload\\" + filename + "." + ext;
            try {
                save(list.get(i).getUrl(), "webapps\\ViSearch\\" + localImage);
                Ibus.UpdateAfterSaveImage(list.get(i).getId(), localImage);
            } catch (Exception ex) {
            }
        }

    }

    private void save(String strLink,
            String destination) throws MalformedURLException,
            IOException,
            URISyntaxException {
        if (strLink != null && destination != null) {

            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;


            try {
                strLink = URIUtil.encodePath(strLink, "UTF-8");
                URL url = new URL(strLink);
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

    private String getExtension(String strURL) {
        String ext = "jpg";
        String[] str = strURL.split("[.]");
        //lay cái cuối cùng chính là phần mở rộng
        ext = str[str.length - 1];
        if (ext.contains("&")) {
            ext = ext.split("[&%]")[0];
        }
        return ext;

    }
}
