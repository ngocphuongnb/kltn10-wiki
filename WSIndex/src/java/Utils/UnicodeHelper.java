/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.Locale;

/**
 *
 * @author VinhPham
 */
public class UnicodeHelper {

    public String toLower(String s) {
        String sResult = "";
        Locale lc = new Locale("vi");
        sResult = s.toLowerCase(lc);
        return sResult;
    }

    public String toUpper(String s) {
        String sResult = "";
        Locale lc = new Locale("vi");
        sResult = s.toUpperCase(lc);
        return sResult;
    }


    public boolean checkUnicode(String s)
    {
        char[] specChar = {'á', 'à', 'ả', 'ã', 'ạ', 'â', 'ấ', 'ầ', 'ẩ', 'ẫ', 'ậ', 'ă', 'ắ', 'ằ', 'ẳ', 'ẵ', 'ặ',
            'Á', 'À', 'Ả', 'Ã', 'Ạ', 'Â', 'Ấ', 'Ầ', 'Ẩ', 'Ẫ', 'Ă', 'Ặ', 'Ắ', 'Ằ', 'Ẳ', 'Ẵ', 'Ặ',
            'đ', 'Đ',
            'é', 'è', 'ẻ', 'ẽ', 'ẹ', 'ê', 'ế', 'ề', 'ể', 'ễ', 'ệ',
            'É', 'È', 'Ẻ', 'Ẽ', 'Ẹ', 'Ê', 'Ế', 'Ề', 'Ể', 'Ễ', 'Ệ',
            'í', 'ì', 'ỉ', 'ĩ', 'ị',
            'Í', 'Ì', 'Ỉ', 'Ĩ', 'Ị',
            'ó', 'ò', 'ỏ', 'õ', 'ọ', 'ô', 'ố', 'ồ', 'ổ', 'ỗ', 'ộ', 'ơ', 'ớ', 'ờ', 'ỡ', 'ợ', 'ở',
            'Ó', 'Ò', 'Ỏ', 'Õ', 'Ọ', 'Ô', 'Ố', 'Ồ', 'Ổ', 'Ỗ', 'Ộ', 'Ơ', 'Ớ', 'Ờ', 'Ỡ', 'Ợ',
            'ú', 'ù', 'ủ', 'ũ', 'ụ', 'ư', 'ứ', 'ừ', 'ử', 'ữ', 'ự',
            'Ú', 'Ù', 'Ủ', 'Ũ', 'Ụ', 'Ư', 'Ứ', 'Ừ', 'Ử', 'Ữ', 'Ự',
            'ý', 'ỳ', 'ỷ', 'ỹ', 'ỵ',
            'Ý', 'Ỳ', 'Ỷ', 'Ỹ', 'Ỵ'};
        int i = 0;
        int j = 0;
        for(i = 0; i < s.length(); i++)
            if(s.charAt(i) > '~')
            {
                for(j = 0; j < specChar.length; j++)
                {
                    if(specChar[j] == s.charAt(i))
                        break;
                }
                
                if(j == specChar.length)
                    return false;
            }
        return true;
    }

    public String removeUnicodeSign(String s) {
        char[] arrChar = {'a', 'A', 'd', 'D', 'e', 'E', 'i', 'I', 'o', 'O', 'u', 'U', 'y', 'Y'};
        char[][] uniChar = {
            {'á', 'à', 'ả', 'ã', 'ạ', 'â', 'ấ', 'ầ', 'ẩ', 'ẫ', 'ậ', 'ă', 'ắ', 'ằ', 'ẳ', 'ẵ', 'ặ'},
            {'Á', 'À', 'Ả', 'Ã', 'Ạ', 'Â', 'Ấ', 'Ầ', 'Ẩ', 'Ẫ', 'Ă', 'Ặ', 'Ắ', 'Ằ', 'Ẳ', 'Ẵ', 'Ặ'},
            {'đ'},
            {'Đ'},
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
   
}
