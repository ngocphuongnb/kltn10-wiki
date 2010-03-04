/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xltv;

import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author VinhPham
 */
public class VNString {

    public static String toLower(String s) {
        String sResult = "";
        Locale lc = new Locale("vi");
        sResult = s.toLowerCase(lc);
        return sResult;
    }

    public static String toUpper(String s) {
        String sResult = "";
        Locale lc = new Locale("vi");
        sResult = s.toUpperCase(lc);
        return sResult;
    }

    public static String removeSign(String s) {
        char[] arrChar = {'a', 'A', 'd', 'Đ', 'e', 'E', 'i', 'I', 'o', 'O', 'u', 'U', 'y', 'Y'};
        char[][] uniChar = {{'á', 'à', 'ả', 'ã', 'ạ', 'â', 'ấ', 'ầ', 'ẩ', 'ẫ', 'ậ', 'ă', 'ắ', 'ằ', 'ẳ', 'ẵ', 'ặ'},
            {'Á', 'À', 'Ả', 'Ã', 'Ạ', 'Â', 'Ấ', 'Ầ', 'Ẩ', 'Ẫ', 'Ă', 'Ặ', 'Ắ', 'Ằ', 'Ẳ', 'Ẵ', 'Ặ'},
            {'đ'}, {'Đ'},
            {'é', 'è', 'ẻ', 'ẽ', 'ẹ', 'ê', 'ế', 'ề', 'ể', 'ễ', 'ệ'},
            {'É', 'È', 'Ẻ', 'Ẽ', 'Ẹ', 'Ê', 'Ế', 'Ề', 'Ể', 'Ễ', 'Ệ'},
            {'í', 'ì', 'ỉ', 'ĩ', 'ị'},
            {'Í', 'Ì', 'Ỉ', 'Ĩ', 'Ị'},
            {'ó', 'ò', 'ỏ', 'õ', 'ọ', 'ô', 'ố', 'ồ', 'ổ', 'ỗ', 'ộ', 'ơ', 'ớ', 'ờ', 'ỡ', 'ợ'},
            {'Ó', 'Ò', 'Ỏ', 'Õ', 'Ọ', 'Ô', 'Ố', 'Ồ', 'Ổ', 'Ỗ', 'Ộ', 'Ơ', 'Ớ', 'Ờ', 'Ỡ', 'Ợ'},
            {'ú', 'ù', 'ủ', 'ũ', 'ụ', 'ư', 'ứ', 'ừ', 'ử', 'ữ', 'ự'},
            {'Ú', 'Ù', 'Ủ', 'Ũ', 'Ụ', 'Ư', 'Ứ', 'Ừ', 'Ử', 'Ữ', 'Ự'},
            {'ý', 'ỳ', 'ỷ', 'ỹ', 'ỵ'},
            {'Ý', 'Ỳ', 'Ỷ', 'Ỹ', 'Ỵ'}};
        for (int i = 0; i < uniChar.length; i++) {
            for (int j = 0; j < uniChar[i].length; j++) {
                s = s.replace(uniChar[i][j], arrChar[i]);
            }
        }
        return s;
    }

    public static boolean checkTCVN(String s)
    {
        char[] specChar = {0xB8, 0xB5, 0xB6, 0xB7, 0xB9, 0xA8, 0xBE, 0xBB, 0xBC, 0xBD, 0xC6, 0xA9, 0xCA, 0xC7, 0xC8, 0xC9
        , 0xCB, 0xD0, 0xCC, 0xCE, 0xCF, 0xD1, 0xAA, 0xD5, 0xD2, 0xD3, 0xD4, 0xD6, 0xDD, 0xD7, 0xD8, 0xDC, 0xDE, 0xE3, 0xDF
        , 0xE1, 0xE2, 0xE4, 0xAB, 0xE8, 0xE5, 0xE6, 0xE7, 0xE9, 0xAC, 0xED, 0xEA, 0xEB, 0xEC, 0xEE, 0xF3, 0xEF, 0xF1, 0xF2
        , 0xF4, 0xAD, 0xF8, 0xF5, 0xF6, 0xF7, 0xF9, 0xFD, 0xFA, 0xFB, 0xFC, 0xFE, 0xAE, 0xA1, 0xA2, 0xA3, 0xA4, 0xA5, 0xA6, 0xA7};
        boolean result = true;
        int i = 0;
        int j = 0;
        for(i = 0; i < s.length(); i++)
            if(s.charAt(i) > '~')
            {
                for(j = 0; j < specChar.length; j++)
                    if(specChar[j] == s.charAt(i))
                        break;
                if(j == specChar.length)
                    result = false;
            }
        return result;
    }
}
