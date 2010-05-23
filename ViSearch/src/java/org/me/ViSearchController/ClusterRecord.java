/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.ViSearchController;

/**
 *
 * @author tuandom
 */
public class ClusterRecord {

    String Label;
    String Docs;
    String[] DocIDs;

    //Tach ra thanh cac Id
    public String[] getDocIDs() {
        int len = Docs.length();
        Docs = Docs.substring(1, (len - 1));
        String[] ids = Docs.split(",");
        for(int i=0; i < ids.length;i++)
        {
            ids[i] = ids[i].substring(1, ids[i].length()-1);
        }
        return ids;
    }

    public void setDocs(String Docs) {
        this.Docs = Docs;
    }

    public String getLabel() {
        int len = Label.length();
        Label = Label.substring(2, (len - 2));
        return Label;
    }

    public void setLabel(String Label) {
        this.Label = Label;
    }
}
