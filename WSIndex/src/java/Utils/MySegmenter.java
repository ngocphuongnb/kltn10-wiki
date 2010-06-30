/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import JVnSegmenter.JVnSegmenter;
import JVnSegmenter.TaggingInputData;
import java.io.IOException;

/**
 *
 * @author VinhPham
 */
public class MySegmenter {

    private static String modelDir = "models";
    private static JVnSegmenter ws;
    private static TaggingInputData taggerData;

    public String getwordBoundaryMark(String strSrc) {
        strSrc = strSrc.replaceAll("[.,!~#$%^&*:;?@]", "");
        taggerData.readOriginalDataFromString(strSrc);
        strSrc = ws.wordBoundaryMark(taggerData);
        strSrc = strSrc.replaceAll(" ", " && ");
        return strSrc;
    }

    public static void init() throws IOException{
        ws = new JVnSegmenter();
        if (!ws.init(modelDir)) {
            return;
        }
        taggerData = new TaggingInputData();
        if (!taggerData.init(modelDir)) {
            return;
        }
    };
}
