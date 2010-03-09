/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package demo;

/**
 *
 * @author tuandom
 */
public class VNIWindowsHelper {

    public boolean CheckVNIWindow3(String src){
    int i=0, c, a1;
    int cnt = src.length();
    boolean flag = false;
    while (i < cnt)
    {
    c = src.charAt(i++);

    switch (c)
    {
        case 'a': case 'A':
        if (i == cnt) return false;
        a1 = src.charAt(i++);
        switch (a1)
        {
            case 'ù': case 'Ù': // sac
            case 'ø': case 'Ø': // huyen
            case 'û': case 'Û':// hoi
            case 'õ': case 'Õ':// nga
            case 'ï': case 'Ï':// nang
            case 'ê': case 'Ê':// (
            case 'â': case 'Â': // ^
                return true;
        }

    case 'e': case 'E':
        if (i == cnt) return false;
        a1 = src.charAt(i++);
        switch (a1)
        {
            case 'ù': case 'Ù': // sac
            case 'ø': case 'Ø': // huyen
            case 'û': case 'Û':// hoi
            case 'õ': case 'Õ':// nga
            case 'ï': case 'Ï':// nang
            case 'â': case 'Â': // ^
               return true;
            default:
                return false;
        }

    case 'Æ': case 'æ': // i hoi
        return true;
    case 'Ì': case 'ì': // i huyen
    case 'Í': case 'í': // I SAC

    case 'ó': case 'Ó': // i ngA
    case 'ò': case 'Ò': // i nang
        flag = true; // co the la vni window

    case 'o': case 'O':
        if (i == cnt) return false;
        a1 = src.charAt(i++);
        switch (a1)
        {
            case 'ù': case 'Ù': // sac
            case 'ø': case 'Ø': // huyen
            case 'û': case 'Û':// hoi
            case 'õ': case 'Õ':// nga
            case 'ï': case 'Ï':// nang
            case 'â': case 'Â': // ^
                return true;
             // ko xet cai o co RAU, vi ko phan biet dc
        }
    case 'Ö': case 'ö': // U co rau
        return true;
    case 'u':case 'U':
        if (i == cnt) return false;
        a1 = src.charAt(i++);
        switch (a1)
        {
            case 'ù': case 'Ù': // sac
            case 'ø': case 'Ø': // huyen
            case 'û': case 'Û':// hoi
            case 'õ': case 'Õ':// nga
            case 'ï': case 'Ï':// nang
               return true;
        }
    case 'Î': case 'î': // y nang
        return true;
    case 'y':case 'Y':
            if (i == cnt) return false;
            a1 = src.charAt(i++);
            switch (a1)
            {
                case 'ù': case 'Ù': // sac
                case 'ø': case 'Ø': // huyen
                case 'û': case 'Û':// hoi
                case 'õ': case 'Õ':// nga
                    return true;
            }

    case 'ñ': case 'Ñ': // chu DD
        return true;
     default: break;


    } // end switch (c)

} // end while
    if(flag==true)
        return true;
    else
        return false;

}
    public static boolean checkVNIWindow(String s)
    {
        String[] specChar = {"aù", "aø", "aû", "aõ", "aï", "aê", "aâ", "aé", "aè", "aú", "aü", "aë", "aá", "aà", "aå", "aã", "aä",
                             "AÙ", "AØ", "AÛ", "AÕ", "AÏ", "AÊ", "AÂ", "AÉ", "AÈ", "AÚ", "AÜ", "AË", "AÁ", "AÀ", "AÅ", "AÃ", "AÄ",
                             "Aù", "Aø", "Aû", "Aõ", "Aï", "Aê", "Aâ",

                             "eù", "eø", "eû", "eõ", "eï", "eê", "eá", "eà", "eå", "eã", "eä",
                             "EÙ", "EØ", "EÛ", "EÕ", "EÏ", "EÊ", "EÁ", "EÀ", "EÅ", "EÃ", "EÄ",
                             "Eù", "Eø", "Eû", "Eõ", "Eï", "Eê",

                             "í", "ì", "æ", "ó", "ò",
                             "Í", "Ì", "Æ", "Ó","Ò",

                             "où", "oø", "oû", "oõ", "oï", "ô", "oâ", "ôù", "ôø", "ôû", "ôõ", "ôï", "oá", "oà", "oã", "oä", "oå",
                             "OÙ", "OØ", "OÛ", "OÕ", "OÏ", "Ô", "OÂ", "ÔÙ", "ÔØ", "OÅ", "ÔÕ", "ÔÏ", "OÁ", "OÀ", "OÅ", "OÃ", "OÄ",
                             "Où", "Oø", "Oû", "Oõ", "Oï", "Oâ",

                             "uù", "uø", "uû", "uõ", "uï","ö","öù", "öø", "öû", "öõ", "öï",
                             "Uù", "Uø", "Uû", "Uõ", "Uï","Ö","ÖÙ", "ÖØ", "ÖÛ", "ÖÕ", "ÖÏ",
                             "UÙ", "UØ", "UÛ", "UÕ", "UÏ",

                             "yù", "Yø", "yû", "yõ", "î",
                             "YÙ", "YØ", "YÛ", "YÕ", "Î",

                             "Yù", "Yø", "Yû", "Yõ",
                             "Ñ", "ñ"
                            };


        int i = 0;
        int j = 0;
        out:
        for(i = 0; i < s.length(); i++)
        {
        //    if(s.charAt(i) > '~')
        //    {
                 if(i<=s.length()-2)
                    for(j = 0; j < specChar.length; j++)
                    {
                            if(specChar[j].compareTo(s.substring(i, i+2))==0)
                            {
                                i++;
                                continue out;
                            }
                    }
                 if(i<=s.length()-1)
                    for(j = 0; j < specChar.length; j++)
                    {
                            if(specChar[j].compareTo(s.substring(i, i+1))==0 )
                               continue out;
                    }
                if(j == specChar.length && s.charAt(i) > '~')
                    return false;
        }
        //    }
        return true;
    }
     public static String VNI2Unicode(String s)
    {
        String VNI[] = {"aù","aø","aû","aõ","aï","aê","aé","aè","aú","aü","aë","aâ","aá","aà","aå","aã","aä",
                        "eù","eø","eû","eõ","eï","eâ","eá","eà","eå","eã","eä","í","ì","æ","ó","ò","où","oø",
                        "oû","oõ","oï","oâ","oá","oà","oå","oã","oä","ô","ôù","ôø","ôû","ôõ","ôï","uù","uø",
                        "uû","uõ","uï","ö","öù","öø","öû","öõ","öï","yù","yø","yû","yõ","î","ñ","AÙ","AØ","AÛ",
                        "AÕ","AÏ","AÊ","AÉ","AÈ","AÚ","AÜ","AË","AÂ","AÁ","AÀ","AÅ","AÃ","AÄ","EÙ","EØ","EÛ",
                        "EÕ","EÏ","EÂ","EÁ","EÀ","EÅ","EÃ","EÄ","Í","Ì","Æ","Ó","Ò","OÙ","OØ","OÛ","OÕ","OÏ",
                        "OÂ","OÁ","OÀ","OÅ","OÃ","OÄ","Ô","ÔÙ","ÔØ","ÔÛ","ÔÕ","ÔÏ","UÙ","UØ","UÛ","UÕ","UÏ",
                        "Ö","ÖÙ","ÖØ","ÖÛ","ÖÕ","ÖÏ","YÙ","YØ","YÛ","YÕ","Î","Ñ"};
        String Uni[] = {"á","à","ả","ã","ạ","ă","ắ","ằ","ẳ","ẵ","ặ","â","ấ","ầ","ẩ","ẫ","ậ",
                        "é","è","ẻ","ẽ","ẹ","ê","ế","ề","ể","ễ","ệ","í","ì","ỉ","ĩ","ị",
                        "ó","ò","ỏ","õ","ọ","ô","ố","ồ","ổ","ỗ","ộ","ơ","ớ","ờ","ở","ỡ","ợ",
                        "ú","ù","ủ","ũ","ụ","ư","ứ","ừ","ử","ữ","ự","ý","ỳ","ỷ","ỹ","ỵ","đ",
                        "Á","﻿À","Ả","Ã","Ạ","Ă","Ắ","Ằ","Ẳ","Ẵ","Ặ","Â","Ấ","Ầ","Ẩ","Ẫ","Ậ",
                        "É","È","Ẻ","Ẽ","Ẹ","Ê","Ế","Ề","Ể","Ễ","Ệ","Í","Ì","Ỉ","Ĩ","Ị",
                        "Ó","Ò","Ỏ","Õ","Ọ","Ô","Ố","Ồ","Ổ","Ỗ","Ộ","Ơ","Ớ","Ờ","Ở","Ỡ","Ợ",
                        "Ú","Ù","Ủ","Ũ","Ụ","Ư","Ứ","Ừ","Ử","Ữ","Ự","Ý","Ỳ","Ỷ","Ỹ","Ỵ","Ð"};
        for(int i = 0; i < VNI.length; i++)
            s = s.replaceAll(VNI[i], Uni[i]);
        return s;
    }

     public static String removeVNIWindowsSign(String s) {
        char[] arrChar = {'a', 'A',  'd', 'D', 'e', 'E', 'i', 'I', 'o', 'O', 'u', 'U', 'y', 'Y'};


        String[][] uniChar = {
            {"aù", "aø", "aû", "aõ", "aï", "aê", "aâ", "aé", "aè", "aú", "aü", "aë", "aá", "aà", "aå", "aã", "aä"},
            {"AÙ", "AØ", "AÛ", "AÕ", "AÏ", "AÊ", "AÂ", "AÉ", "AÈ", "AÚ", "AÜ", "AË", "AÁ", "AÀ", "AÅ", "AÃ", "AÄ", "Aù", "Aø", "Aû", "Aõ", "Aï", "Aê", "Aâ"},

            {"ñ"},
            {"Ñ"},

            {"eù", "eø", "eû", "eõ", "eï", "eâ", "eá", "eà", "eå", "eã", "eä", "eâ"},
            {"EÙ", "EØ", "EÛ", "EÕ", "EÏ", "EÊ", "EÁ", "EÀ", "EÅ", "EÃ", "EÄ", "Eù", "Eø", "Eû", "Eõ", "Eï", "Eê"},

            { "í", "ì", "æ", "ó", "ò"},
            {"Í", "Ì", "Æ", "Ó","Ò"},

            {"où", "oø", "oû", "oõ", "oï", "oâ", "ôù", "ôø", "ôû", "ôõ", "ôï", "oá", "oà", "oã", "oä", "oå", "ô"},
            {"OÙ", "OØ", "OÛ", "OÕ", "OÏ", "Ô", "OÂ", "ÔÙ", "ÔØ", "OÅ", "ÔÕ", "ÔÏ", "OÁ", "OÀ", "OÅ", "OÃ", "OÄ", "Où", "Oø", "Oû", "Oõ", "Oï", "Oâ"},

            {"uù", "uø", "uû", "uõ", "uï", "öù", "öø", "öû", "öõ", "öï", "ö"},
            {"Uù", "Uø", "Uû", "Uõ", "Uï", "ÖÙ", "ÖØ", "ÖÛ", "ÖÕ", "ÖÏ", "Ö","UÙ", "UØ", "UÛ", "UÕ", "UÏ"},

            {"yù", "Yø", "yû", "yõ", "î"},
            {"YÙ", "YØ", "YÛ", "YÕ", "Î"}

            };
        for (int i = 0; i < uniChar.length; i++) {
            for (int j = 0; j < uniChar[i].length; j++) {
                s = s.replaceAll(uniChar[i][j], Character.toString(arrChar[i]));
            }
        }
        return s;
    }
}
