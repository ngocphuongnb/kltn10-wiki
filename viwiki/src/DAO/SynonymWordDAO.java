/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author VinhPham
 */
public class SynonymWordDAO {

    public static String cleanQueryTerm(String source) {
        return source.replaceAll("[\\^\\-+:!(){}~*?\"\\\\]", " ");
    }

//    public static void generateSynonym() throws SQLException, ParseException, IOException {
//        ArrayList<SynonymWordDTO> list = new ArrayList<SynonymWordDTO>();
//        Connection cn = (Connection) DataProvider.getConnection("kltn");
//        Statement st = (Statement) cn.createStatement();
//        String query = "SELECT title, text FROM viwiki Where text like '%#redirect [[%'";
//        ResultSet rs = st.executeQuery(query);
//
//        SynonymWordDTO synWord;
//        String strSyn;
//
//        while (rs.next()) {
//            synWord = new SynonymWordDTO();
//            synWord.setWord(cleanQueryTerm(rs.getString("title")));
//            strSyn = rs.getString("text");
//            int i = strSyn.toLowerCase().indexOf("#redirect [[") + "#redirect [[".length();
//            int j = strSyn.indexOf("]]", i);
//            synWord.setSynonymWord(cleanQueryTerm(strSyn.substring(i, j)));
//            list.add(synWord);
//        }
//
//        ArrayList<ArrayList<String>> lstTu = new ArrayList<ArrayList<String>>();//Danh sach cac bo
//        boolean bhas = false;
//        for (SynonymWordDTO synonymWordDTO : list) {
//
//            bhas = false;
//            //Tim kiem da co trong danh sach hay chua
//            for (ArrayList<String> arrayList : lstTu) {
//
//                //Quet tung bo trong danh sach
//                for (String string : arrayList) {
//                    if (string.equals(synonymWordDTO.getWord())) {
//                        arrayList.add(synonymWordDTO.getSynonymWord());
//                        bhas = true;
//                        break;
//                    } else {
//                        if (string.equals(synonymWordDTO.getSynonymWord())) {
//                            arrayList.add(synonymWordDTO.getWord());
//                            bhas = true;
//                            break;
//                        }
//                    }
//                }
//            }
//
//            //Xu ly khi chua co
//            if (bhas == false) {
//                ArrayList<String> mlist = new ArrayList<String>();
//                mlist.add(synonymWordDTO.getWord());
//                mlist.add(synonymWordDTO.getSynonymWord());
//                lstTu.add(mlist);
//            }
//        }
//
//        FileWriter writer = new FileWriter(new File("syn.txt"));
//        for (ArrayList<String> arrayList : lstTu) {
//            for (int i = 0; i < arrayList.size() - 1; i++) {
//                writer.write(arrayList.get(i).replaceAll("#", ", "));
//                writer.write(", ");
//            }
//            writer.write(arrayList.get(arrayList.size() - 1));
//            writer.write("\r\n");
//        }
//        writer.close();
//        st.close();
//        rs.close();
//        cn.close();
//    }
}
