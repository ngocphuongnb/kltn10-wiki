/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmMain.java
 *
 * Created on Mar 13, 2010, 2:13:28 PM
 */
package viwiki;

import BUS.ImageBUS;
import BUS.MusicBUS;
import BUS.RaoVatBUS;
import BUS.SynonymWordBUS;
import BUS.VideoBUS;
import BUS.ViwikiPageBUS;
import ViSearchSyncDataService.ViwikiPageDTO;
import ViSearchSyncDataService.WSIndex;
import ViSearchSyncDataService.WSIndexService;
import java.awt.Cursor;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.solr.client.solrj.SolrServerException;

/**
 *
 * @author VinhPham
 */
public class frmMain extends javax.swing.JDialog {

    /** Creates new form frmMain */
    public frmMain(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setText("Sync Data wiki");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Generate Synonyms");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Inport Data rao vat");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Import Data Music");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Import Data Video");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Import data Image");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Remove WiiTag");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jButton2)
                .addGap(46, 46, 46)
                .addComponent(jButton7)
                .addContainerGap(90, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                .addGap(64, 64, 64))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7))
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        jButton1.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            //        try {
            //            int numOfRecords = ViwikiPageBUS.CountRecord();
            //            // TODO add your handling code here:
            //            //importData(1);
            //            importDataWiki(numOfRecords);
            //        } catch (SQLException ex) {
            //            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
            //        } catch (MalformedURLException ex) {
            //            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
            //        } catch (SolrServerException ex) {
            //            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
            //        } catch (IOException ex) {
            //            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
            //        } catch (ParseException ex) {
            //            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
            //        }
            ViwikiPageBUS bus = new ViwikiPageBUS();
            int numOfRecords = bus.CountRecord();
            importDataWiki(numOfRecords);
        } catch (MalformedURLException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SolrServerException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        jButton1.setEnabled(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
//        try {
//            jButton2.setEnabled(false);
//            try {
//                SynonymWordBUS.generateSynonym();
//            } catch (IOException ex) {
//                Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            jButton2.setEnabled(true);
//        } catch (SQLException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ParseException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
//        try {
//            jButton3.setEnabled(false);
//            // TODO add your handling code here:
//            int numOfRecords;
//            numOfRecords = RaoVatBUS.CountRecord();
//            importDataRaoVat(numOfRecords);
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SolrServerException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ParseException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        jButton3.setEnabled(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    // Import data music
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

//        jButton4.setEnabled(false);
//        int numOfRecords;
//        try {
//            numOfRecords = MusicBUS.CountRecord();
//            importDataMusic(numOfRecords);
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SolrServerException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ParseException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        jButton4.setEnabled(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    // Import data Video
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
//        jButton5.setEnabled(false);
//        int numOfRecords;
//        try {
//            numOfRecords = VideoBUS.CountRecord();
//            importDataVideo(numOfRecords);
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SolrServerException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ParseException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        jButton5.setEnabled(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
//        int numOfRecords;
//        try {
//            numOfRecords = ImageBUS.CountRecord();
//             importDataImage(numOfRecords);
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SolrServerException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ParseException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
//        try {
//            //FileWriter fw = new FileWriter("wikiText.txt");
//            try {
//                ArrayList<ViwikiPageDTO> list = new ArrayList<ViwikiPageDTO>();
//                for(int i=0; i< list.size(); i++){
//                    fw.write(i+" ");
//                    fw.write(list.get(i).getTitle());
//                    fw.write("\r\n");
//                    fw.write(list.get(i).getText());
//                    fw.write("\r\n\r\n\r\n\r\n\r\n\r\n");
//                }
//                fw.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ParseException ex) {
//                Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        JOptionPane.showMessageDialog(rootPane, "Xong!");
    }//GEN-LAST:event_jButton7ActionPerformed

    public void importDataWiki(int numRecord) throws SQLException, MalformedURLException, SolrServerException, IOException, ParseException {
        // TODO code application logic here
//        MySolrJ ms = new MySolrJ();
//        ms.EmptyData("wikipedia");
//        int start = 0;
//        while (start < 10000) {
//            ArrayList<ViwikiPageDTO> list = ViwikiPageBUS.getDataList(start, 2000);
//            ms.ImportWiki2Solr(list, start);
//            start += 2000;
//        }

        WSIndexService service  = new WSIndexService();
        WSIndex port = service.getWSIndexPort();
        int start = 0;
        while (start < 10000) {
            ArrayList<ViwikiPageDTO> list = new ArrayList<ViwikiPageDTO>();
            ViwikiPageBUS bus = new ViwikiPageBUS();
            list = bus.getDataList(start, 2000);
            port.syncDataWiki(list);
            start += 2000;
        }
    }

//     public void importDataImage(int numRecord) throws SQLException, MalformedURLException, SolrServerException, IOException, ParseException {
//        MySolrJ ms = new MySolrJ();
//        ms.EmptyData("image");
//        int start = 0;
//        while (start < 10000) {
//            ArrayList<ImageDTO> list = ImageBUS.getDataList(start, 2000);
//            ms.ImportImage2Solr(list, start);
//            start += 2000;
//        }
//    }
//     public void importDataVideo(int numRecord) throws SQLException, MalformedURLException, SolrServerException, IOException, ParseException {
//
//        MySolrJ ms = new MySolrJ();
//        ms.EmptyData("video");
//        int start = 0;
//        ArrayList<VideoDTO> list = VideoBUS.getDataList(start, numRecord);
//        ms.ImportVideo2Solr(list, start);
////        while (start < numRecord) {
////            ArrayList<VideoDTO> list = VideoBUS.getDataList(start, 2000);
////            ms.ImportVideo2Solr(list, start);
////            start += 2000;
////        }
//    }
//
//    public void importDataMusic(int numRecord) throws SQLException, MalformedURLException, SolrServerException, IOException, ParseException {
//        // TODO code application logic here
//        MySolrJ ms = new MySolrJ();
//        ms.EmptyData("music");
//        int start = 0;
//        // ArrayList<MusicDTO> list = MusicBUS.getDataList(start, numRecord);
//        // ms.ImportMusic2Solr(list, start);
//
//
//        while (start < numRecord) {
//            ArrayList<MusicDTO> list = MusicBUS.getDataList(start, 2000);
//            ms.ImportMusic2Solr(list, start);
//            start += 2000;
//        }
//    }
//
//    public void importDataRaoVat(int numRecord) throws SQLException, MalformedURLException, SolrServerException, IOException, ParseException {
//        // TODO code application logic here
//        MySolrJ ms = new MySolrJ();
//        ms.EmptyData("raovat");
//        int start = 0;
//        while (start < numRecord) {
//            ArrayList<RaoVatDTO> list = RaoVatBUS.getDataList(start, 1000);
//            ms.ImportRaoVat2Solr(list, start);
//            start += 1000;
//        }
//    }
//
//    /**
//     * @param args the command line arguments
//     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                frmMain dialog = new frmMain(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    // End of variables declaration//GEN-END:variables
}
