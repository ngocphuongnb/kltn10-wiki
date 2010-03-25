/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

/**
 *
 * @author tuandom
 */
class VIQRHelper {
//khai báo các ký tự dấu của VIQR

    private static final int VN_CAA = 0x27; //sắc '
    private static final int VN_CGA = 0x60; //huyền `
    private static final int VN_CHA = 0x3f; //hỏi ?
    private static final int VN_CTD = 0x7e; //ngã ~
    private static final int VN_CDB = 0x2e; //nặng .
    private static final int VN_BRE = 0x28; //dấu ă (
    private static final int VN_CIR = 0x5e; //dấu â ^
    private static final int VN_HOR = 0x2b; //dấu ư +
    private static final int VN_HOR2 = 0x2a; //dấu ư *

    // Chữ Đ có thể gõ: DD, Dd, D-
    // Chữ đ có thể gõ: dd, dD, d-
//hàm chuyển chuỗi VIQR sang Unicode dựng sẵn
//dùng thuật toán duyệt tuần tự
    public static String VIQR2Unicode(String src) {
        String dst = null;
        int i = 0;
        int c, a1, a2;
        int idxo = 0;
        int cnt = src.length();
        char[] buf = new char[cnt + 1];
        precomp:
        //buf[idxo++] = (char)c;
        while (i < cnt) {

            c = src.charAt(i++);

            switch (c) {
                case 'a':
                    if (i == cnt) {
                        buf[idxo++] = (char) c;
                        continue precomp;
                    }
                    a1 = src.charAt(i++);
                    switch (a1) {
                        case VN_CAA:
                            c = 0xe1;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CGA:
                            c = 0xe0;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CHA:
                            c = 0x1ea3;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CTD:
                            c = 0xe3;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CDB:
                            c = 0x1ea1;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_BRE:
                            c = 0x103;
                            if (i == cnt) {
                                buf[idxo++] = (char) c;
                                continue precomp;
                            }
                            a2 = src.charAt(i++);
                            switch (a2) {
                                case VN_CAA:
                                    c = 0x1eaf;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CGA:
                                    c = 0x1eb1;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CHA:
                                    c = 0x1eb3;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CTD:
                                    c = 0x1eb5;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CDB:
                                    c = 0x1eb7;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                default:
                                    i--;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                            }
                        case VN_CIR:
                            c = 0xe2;
                            if (i == cnt) {
                                continue precomp;
                            }
                            a2 = src.charAt(i++);
                            switch (a2) {
                                case VN_CAA:
                                    c = 0x1ea5;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CGA:
                                    c = 0x1ea7;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CHA:
                                    c = 0x1ea9;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CTD:
                                    c = 0x1eab;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CDB:
                                    c = 0x1ead;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                default:
                                    i--;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                            }
                        default:
                            i--;
                            buf[idxo++] = (char) c;
                            continue precomp;
                    }
                case 'A':
                    if (i == cnt) {
                        buf[idxo++] = (char) c;
                        continue precomp;
                    }
                    a1 = src.charAt(i++);
                    switch (a1) {
                        case VN_CAA:
                            c = 0xc1;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CGA:
                            c = 0xc0;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CHA:
                            c = 0x1ea2;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CTD:
                            c = 0xc3;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CDB:
                            c = 0x1ea0;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_BRE:
                            c = 0x102;
                            if (i == cnt) {
                                buf[idxo++] = (char) c;
                                continue precomp;
                            }
                            a2 = src.charAt(i++);
                            switch (a2) {
                                case VN_CAA:
                                    c = 0x1eae;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CGA:
                                    c = 0x1eb0;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CHA:
                                    c = 0x1eb2;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CTD:
                                    c = 0x1eb4;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CDB:
                                    c = 0x1eb6;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                default:
                                    i--;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                            }
                        case VN_CIR:
                            c = 0xc2;
                            if (i == cnt) {
                                buf[idxo++] = (char) c;
                                continue precomp;
                            }
                            a2 = src.charAt(i++);
                            switch (a2) {
                                case VN_CAA:
                                    c = 0x1ea4;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CGA:
                                    c = 0x1ea6;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CHA:
                                    c = 0x1ea8;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CTD:
                                    c = 0x1eaa;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CDB:
                                    c = 0x1eac;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                default:
                                    i--;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                            }
                        default:
                            i--;
                            buf[idxo++] = (char) c;
                            continue precomp;
                    }
                case 'e':
                    if (i == cnt) {
                        buf[idxo++] = (char) c;
                        continue precomp;
                    }
                    a1 = src.charAt(i++);
                    switch (a1) {
                        case VN_CAA:
                            c = 0xe9;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CGA:
                            c = 0xe8;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CHA:
                            c = 0x1ebb;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CTD:
                            c = 0x1ebd;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CDB:
                            c = 0x1eb9;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CIR:
                            c = 0xea;
                            if (i == cnt) {
                                buf[idxo++] = (char) c;
                                continue precomp;
                            }
                            a2 = src.charAt(i++);
                            switch (a2) {
                                case VN_CAA:
                                    c = 0x1ebf;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CGA:
                                    c = 0x1ec1;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CHA:
                                    c = 0x1ec3;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CTD:
                                    c = 0x1ec5;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CDB:
                                    c = 0x1ec7;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                default:
                                    i--;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                            }
                        default:
                            i--;
                            buf[idxo++] = (char) c;
                            continue precomp;
                    }
                case 'E':
                    if (i == cnt) {
                        buf[idxo++] = (char) c;
                        continue precomp;
                    }
                    a1 = src.charAt(i++);
                    switch (a1) {
                        case VN_CAA:
                            c = 0xc9;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CGA:
                            c = 0xc8;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CHA:
                            c = 0x1eba;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CTD:
                            c = 0x1ebc;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CDB:
                            c = 0x1eb8;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CIR:
                            c = 0xca;
                            if (i == cnt) {
                                buf[idxo++] = (char) c;
                                continue precomp;
                            }
                            a2 = src.charAt(i++);
                            switch (a2) {
                                case VN_CAA:
                                    c = 0x1ebe;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CGA:
                                    c = 0x1ec0;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CHA:
                                    c = 0x1ec2;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CTD:
                                    c = 0x1ec4;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CDB:
                                    c = 0x1ec6;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                default:
                                    i--;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                            }
                        default:
                            i--;
                            buf[idxo++] = (char) c;
                            continue precomp;
                    }
                case 'i':
                    if (i == cnt) {
                        buf[idxo++] = (char) c;
                        continue precomp;
                    }
                    a1 = src.charAt(i++);
                    switch (a1) {
                        case VN_CAA:
                            c = 0xed;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CGA:
                            c = 0xec;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CHA:
                            c = 0x1ec9;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CTD:
                            c = 0x129;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CDB:
                            c = 0x1ecb;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        default:
                            i--;
                            buf[idxo++] = (char) c;
                            continue precomp;
                    }
                case 'I':
                    if (i == cnt) {
                        buf[idxo++] = (char) c;
                        continue precomp;
                    }
                    a1 = src.charAt(i++);
                    switch (a1) {
                        case VN_CAA:
                            c = 0xcd;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CGA:
                            c = 0xcc;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CHA:
                            c = 0x1ec8;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CTD:
                            c = 0x128;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CDB:
                            c = 0x1eca;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        default:
                            i--;
                            buf[idxo++] = (char) c;
                            continue precomp;
                    }
                case 'o':
                    if (i == cnt) {
                        buf[idxo++] = (char) c;
                        continue precomp;
                    }
                    a1 = src.charAt(i++);
                    switch (a1) {
                        case VN_CAA:
                            c = 0xf3;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CGA:
                            c = 0xf2;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CHA:
                            c = 0x1ecf;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CTD:
                            c = 0xf5;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CDB:
                            c = 0x1ecd;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CIR:
                            c = 0xf4;
                            if (i == cnt) {
                                buf[idxo++] = (char) c;
                                continue precomp;
                            }
                            a2 = src.charAt(i++);
                            switch (a2) {
                                case VN_CAA:
                                    c = 0x1ed1;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CGA:
                                    c = 0x1ed3;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CHA:
                                    c = 0x1ed5;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CTD:
                                    c = 0x1ed7;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CDB:
                                    c = 0x1ed9;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                default:
                                    i--;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                            }
                        case VN_HOR:
                        case VN_HOR2:
                            c = 0x1a1;
                            if (i == cnt) {
                                buf[idxo++] = (char) c;
                                continue precomp;
                            }
                            a2 = src.charAt(i++);
                            switch (a2) {
                                case VN_CAA:
                                    c = 0x1edb;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CGA:
                                    c = 0x1edd;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CHA:
                                    c = 0x1edf;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CTD:
                                    c = 0x1ee1;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CDB:
                                    c = 0x1ee3;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                default:
                                    i--;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                            }
                        default:
                            i--;
                            buf[idxo++] = (char) c;
                            continue precomp;
                    }
                case 'O':
                    if (i == cnt) {
                        buf[idxo++] = (char) c;
                        continue precomp;
                    }
                    a1 = src.charAt(i++);
                    switch (a1) {
                        case VN_CAA:
                            c = 0xd3;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CGA:
                            c = 0xd2;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CHA:
                            c = 0x1ece;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CTD:
                            c = 0xd5;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CDB:
                            c = 0x1ecc;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CIR:
                            c = 0xd4;
                            if (i == cnt) {
                                buf[idxo++] = (char) c;
                                continue precomp;
                            }
                            a2 = src.charAt(i++);
                            switch (a2) {
                                case VN_CAA:
                                    c = 0x1ed0;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CGA:
                                    c = 0x1ed2;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CHA:
                                    c = 0x1ed4;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CTD:
                                    c = 0x1ed6;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CDB:
                                    c = 0x1ed8;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                default:
                                    i--;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                            }
                        case VN_HOR:
                        case VN_HOR2:
                            c = 0x1a0;
                            if (i == cnt) {
                                buf[idxo++] = (char) c;
                                continue precomp;
                            }
                            a2 = src.charAt(i++);
                            switch (a2) {
                                case VN_CAA:
                                    c = 0x1eda;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CGA:
                                    c = 0x1edc;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CHA:
                                    c = 0x1ede;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CTD:
                                    c = 0x1ee0;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CDB:
                                    c = 0x1ee2;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                default:
                                    i--;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                            }
                        default:
                            i--;
                            buf[idxo++] = (char) c;
                            continue precomp;
                    }
                case 'u':
                    if (i == cnt) {
                        buf[idxo++] = (char) c;
                        continue precomp;
                    }
                    a1 = src.charAt(i++);
                    switch (a1) {
                        case VN_CAA:
                            c = 0xfa;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CGA:
                            c = 0xf9;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CHA:
                            c = 0x1ee7;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CTD:
                            c = 0x169;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CDB:
                            c = 0x1ee5;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_HOR:
                        case VN_HOR2:
                            c = 0x1b0;
                            if (i == cnt) {
                                buf[idxo++] = (char) c;
                                continue precomp;
                            }
                            a2 = src.charAt(i++);
                            switch (a2) {
                                case VN_CAA:
                                    c = 0x1ee9;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CGA:
                                    c = 0x1eeb;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CHA:
                                    c = 0x1eed;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CTD:
                                    c = 0x1eef;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CDB:
                                    c = 0x1ef1;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                default:
                                    i--;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                            }
                        default:
                            i--;
                            buf[idxo++] = (char) c;
                            continue precomp;
                    }
                case 'U':
                    if (i == cnt) {
                        buf[idxo++] = (char) c;
                        continue precomp;
                    }
                    a1 = src.charAt(i++);
                    switch (a1) {
                        case VN_CAA:
                            c = 0xda;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CGA:
                            c = 0xd9;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CHA:
                            c = 0x1ee6;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CTD:
                            c = 0x168;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CDB:
                            c = 0x1ee4;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_HOR:
                        case VN_HOR2:
                            c = 0x1af;
                            if (i == cnt) {
                                buf[idxo++] = (char) c;
                                continue precomp;
                            }
                            a2 = src.charAt(i++);
                            switch (a2) {
                                case VN_CAA:
                                    c = 0x1ee8;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CGA:
                                    c = 0x1eea;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CHA:
                                    c = 0x1eec;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CTD:
                                    c = 0x1eee;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                case VN_CDB:
                                    c = 0x1ef0;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                                default:
                                    i--;
                                    buf[idxo++] = (char) c;
                                    continue precomp;
                            }
                        default:
                            i--;
                            buf[idxo++] = (char) c;
                            continue precomp;
                    }
                case 'y':
                    if (i == cnt) {
                        buf[idxo++] = (char) c;
                        continue precomp;
                    }
                    a1 = src.charAt(i++);
                    switch (a1) {
                        case VN_CAA:
                            c = 0xfd;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CGA:
                            c = 0x1ef3;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CHA:
                            c = 0x1ef7;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CTD:
                            c = 0x1ef9;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CDB:
                            c = 0x1ef5;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        default:
                            i--;
                            buf[idxo++] = (char) c;
                            continue precomp;
                    }
                case 'Y':
                    if (i == cnt) {
                        buf[idxo++] = (char) c;
                        continue precomp;
                    }
                    a1 = src.charAt(i++);
                    switch (a1) {
                        case VN_CAA:
                            c = 0xdd;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CGA:
                            c = 0x1ef2;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CHA:
                            c = 0x1ef6;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CTD:
                            c = 0x1ef8;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        case VN_CDB:
                            c = 0x1ef4;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        default:
                            i--;
                            buf[idxo++] = (char) c;
                            continue precomp;
                    }
                case 'd':
                    if (i == cnt) {
                        buf[idxo++] = (char) c;
                        continue precomp;
                    }
                    a1 = src.charAt(i++);
                    switch (a1) {
                        case 0x2d:
                        case 'D':
                        case 'd':
                            c = 0x111;
                            buf[idxo++] = (char) c;
                            continue precomp; // Có thể gõ Dd hoặc DD hoặc d- D-
                        default:
                            i--;
                            buf[idxo++] = (char) c;
                            continue precomp;
                    }
                case 'D':
                    if (i == cnt) {
                        buf[idxo++] = (char) c;
                        continue precomp;
                    }
                    a1 = src.charAt(i++);
                    switch (a1) {
                        case 0x2d:
                        case 'D':
                        case 'd':
                            c = 0x110;
                            buf[idxo++] = (char) c;
                            continue precomp;
                        default:
                            i--;
                            buf[idxo++] = (char) c;
                            continue precomp;
                    }
                default:
                    buf[idxo++] = (char) c;
                    continue precomp;
            } // end switch (c)
            // precomp:
            // buf[idxo++] = (char)c;
        } // end while
        buf[idxo] = (char) 0;
        dst = new String(buf);
        return dst;
    } //end of VIRQ2PreCompose
//định nghĩa bảng các ký tự Unicode có dấu
    int[] VnPreComposedChar = new int[]{
        0x00c0, 0x00c1, 0x00c2, 0x00c3, 0x00c8, 0x00c9, 0x00ca, 0x00cc, 0x00cd, 0x00d2, 0x00d3, 0x00d4, 0x00d5, 0x00d9, 0x00da, 0x00dd, 0x00e0, 0x00e1, 0x00e2, 0x00e3, 0x00e8, 0x00e9, 0x00ea, 0x00ec, 0x00ed, 0x00f2, 0x00f3, 0x00f4, 0x00f5, 0x00f9, 0x00fa, 0x00fd, 0x0102, 0x0103, 0x0110, 0x0111, 0x0128, 0x0129, 0x0168, 0x0169, 0x01a0, 0x01a1, 0x01af, 0x01b0, 0x1ea0, 0x1ea1, 0x1ea2, 0x1ea3, 0x1ea4, 0x1ea5, 0x1ea6, 0x1ea7, 0x1ea8, 0x1ea9, 0x1eaa, 0x1eab, 0x1eac, 0x1ead, 0x1eae, 0x1eaf, 0x1eb0, 0x1eb1, 0x1eb2, 0x1eb3, 0x1eb4, 0x1eb5, 0x1eb6, 0x1eb7, 0x1eb8, 0x1eb9, 0x1eba, 0x1ebb, 0x1ebc, 0x1ebd, 0x1ebe, 0x1ebf, 0x1ec0, 0x1ec1, 0x1ec2, 0x1ec3, 0x1ec4, 0x1ec5, 0x1ec6, 0x1ec7, 0x1ec8, 0x1ec9, 0x1eca, 0x1ecb, 0x1ecc, 0x1ecd, 0x1ece, 0x1ecf, 0x1ed0, 0x1ed1, 0x1ed2, 0x1ed3, 0x1ed4, 0x1ed5, 0x1ed6, 0x1ed7, 0x1ed8, 0x1ed9, 0x1eda, 0x1edb, 0x1edc, 0x1edd, 0x1ede, 0x1edf, 0x1ee0, 0x1ee1, 0x1ee2, 0x1ee3, 0x1ee4, 0x1ee5, 0x1ee6, 0x1ee7, 0x1ee8, 0x1ee9, 0x1eea, 0x1eeb, 0x1eec, 0x1eed, 0x1eee, 0x1eef, 0x1ef0, 0x1ef1, 0x1ef2, 0x1ef3, 0x1ef4, 0x1ef5, 0x1ef6, 0x1ef7, 0x1ef8, 0x1ef9
    };

    public static String removeVIQRSign(String s) {
        CharSequence[] arrChar = {"a", "A", "d", "D", "e", "E", "i", "I", "o", "O", "u", "U", "y", "Y", ".", "?"};
        CharSequence[][] uniChar = {
            {"a('", "a(`", "a(?", "a(~", "a(.", "a^'", "a^`", "a^?", "a^~", "a^.", "a'", "a`", "a?", "a~", "a.", "a(", "a^"},
            {"A('", "A(`", "A(?", "A(~", "A(.", "A^'", "A^`", "A^?", "A^~", "A^.", "A'", "A`", "A?", "A~", "A.", "A(", "A^"},
            {"dd"},
            {"DD"},
            {"e^'", "e^`", "e^?", "e^~", "e^.", "e'", "e`", "e?", "e~", "e.", "e^"},
            {"E^'", "E^`", "E^?", "E^~", "E^.", "E'", "E`", "E?", "E~", "E.", "E^"},
            {"i'", "i`", "i?", "i~", "i."},
            {"I'", "I`", "I?", "I~", "I."},
            {"o+'", "o+`", "o+?", "o+~", "o+.", "o^'", "o^`", "o^?", "o^~", "o^.", "o'", "o`", "o?", "o~", "o.", "o+", "o^"},
            {"O+'", "O+`", "O+?", "O+~", "O+.", "O^'", "o^`", "O^?", "O^~", "O^.", "O'", "O`", "O?", "O~", "O.", "O+", "O^"},
            {"u+'", "u+`", "u+?", "u+~", "u+.", "u'", "u`", "u?", "u~", "u.", "u+",},
            {"U+'", "U+`", "U+?", "U+~", "U+.", "U'", "U`", "U?", "U~", "U.", "U+"},
            {"y'", "y`", "y?", "y~", "y."},
            {"Y'", "Y`", "Y?", "Y~", "Y."},
            {"\\."},
            {"\\?"},};

        for (int i = 0; i < uniChar.length; i++) {
            for (int j = 0; j < uniChar[i].length; j++) {
                CharSequence src = uniChar[i][j].toString();
                CharSequence des = arrChar[i].toString();
                s = s.replace(src, des);
            }
        }
        return s;
    }





 public static boolean checkVIQR(String s)
    {
       String[] specChar = {
            "a'", "a`", "a?", "a~", "a.", "a^", "a^'", "a^`", "a^?", "a^~", "a^.", "a(", "a('", "a(`", "a(?", "a(~", "a(.",
            "A'", "A`", "A?", "A~", "A.", "A^", "A^'", "A^`", "A^?", "A^~", "A^.", "A(", "A('", "A(`", "A(?", "A(~", "A(.",
            "dd", "DD",
            "e'", "e`", "e?", "e~", "e.", "e^", "e^'", "e^`", "e^?", "e^~", "e^.",
            "E'", "E`", "E?", "E~", "E.", "E^", "E^'", "E^`", "E^?", "E^~", "E^.",
            "i'", "i`", "i?", "i~", "i.",
            "I'", "I`", "I?", "I~", "I.",
            "o'", "o`", "o?", "o~", "o.", "o^", "o^'", "o^`", "o^?", "o^~", "o^.", "o+", "o+'", "o+`", "o+?", "o+~", "o+.",
            "O'", "O`", "O?", "O~", "O.", "O^", "O^'", "O^`", "O^?", "O^~", "O^.", "O+", "O+'", "O+`", "O+?", "O+~", "O+.",
            "u'", "u`", "u?", "u~", "u.", "u+", "u+'", "u+`", "u+?", "u+~", "u+.",
            "U'", "U`", "U?", "U~", "U.", "U+", "U+'", "U+`", "U+?", "U+~", "U+.",
            "y'", "y`", "y?", "y~", "y.",
            "Y'", "Y`", "Y?", "Y~", "Y." };
        int i = 0;
        int j = 0;
        for(i = 0; i < s.length()-1; i++)
            if(s.charAt(i) > '~')
            {
                for(j = 0; j < specChar.length; j++)
                {
                    if(s.substring(i, i+1).compareTo(specChar[j])==0)
                        break; // thoat re de tiep tuc
                }

                if(j == specChar.length)
                    return false;
            }
        return true;
    }



}
