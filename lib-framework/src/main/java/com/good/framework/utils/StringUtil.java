package com.good.framework.utils;
////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//         佛祖保佑       永无BUG     永不修改                  //
////////////////////////////////////////////////////////////////////

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 * createTime : 2018/2/25 16:33
 * update by hux on 2018/2/25.
 * version : 0.0.1
 * @since 0.0.1
 */
@Deprecated
public class StringUtil {


    /**
     * 从十六进制字符串到字节数组转换
     * @author hux
     * @createTime 2018/3/14 14:01
     * @since 0.0.1
     * @param hexstr
     *      十进制字符串
     * @return
     *      转换后的字节数组
     */
    public static byte[] hexString2Bytes(String hexstr) {
        int l = hexstr.length();
        if(l%2 != 0){
            StringBuilder sb = new StringBuilder(hexstr);
            sb.insert(hexstr.length()-1, '0');
            hexstr = sb.toString();
        }
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    private static int parse(char c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    /**
     * byte数组转为ASCI码
     * @author hux
     * @createTime 2018/2/27 13:59
     * @since 1.0.0
     * @param bytes
     *      需要转换的byte数组
     * @param offset
     *      复制数组时的下标位置
     * @param dateLen
     *      数组长度
     * @return
     *      转换后的String
     */
    private static String bytesToAscii(byte[] bytes, int offset, int dateLen) {
        if ((bytes == null) || (bytes.length == 0) || (offset < 0) || (dateLen <= 0)) {
            return null;
        }
        if ((offset >= bytes.length) || (bytes.length - offset < dateLen)) {
            return null;
        }

        String asciiStr = null;
        byte[] data = new byte[dateLen];
        System.arraycopy(bytes, offset, data, 0, dateLen);
        try {
            asciiStr = new String(data, "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return asciiStr;
    }

    /**
     * byte数组转换为16进制字符串
     * @author hux
     * @createTime 2018/3/16 14:11
     * @since 0.0.1
     * @param bArray
     *      byte数组
     * @return
     *      16进制字符串
     */
    public static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 字符串转16进制字符串
     * @author hux
     * @createTime 2018/3/21 11:13
     * @since 0.0.1
     * @param s
     *      需要转换的字符串
     * @return
     *      转换后的16进制的字符串
     */
    public static String strToHexString(String s){
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }


    /**
     * BCD码转为10进制(阿拉伯数字)字符串
     * @author hux
     * @createTime 2018/2/27 14:03
     * @since 1.0.0
     * @param bytes
     *      需要转换的BCD字符串对应的byte数组
     * @return
     *      转换后的10进制字符串
     */
    public static String bcdToString(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
                .toString().substring(1) : temp.toString();
    }


    public static void main(String[] args){

        byte i = 65;
        String hex = Integer.toHexString(i & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        String temp = hex.toUpperCase();
        System.out.println(temp);
    }

    /**
     * 10进制串转为BCD码
     * @author hux
     * @createTime 2018/2/27 14:05
     * @since 1.0.0
     * @param asc
     *      10进制字符串转换为BCD码对应的byte数组
     * @return
     *      byte数组
     */
    public static byte[] strToBcd(String asc) {
        int len = asc.length();
        int mod = len % 2;
        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }
        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }
        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;
        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }
            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }


    /**
     * 将String类型数据转换位Ascii码对应的int数组
     * @author hux
     * @createTime 2018/2/8 17:15
     * @since 1.0.0
     * @param
     *      value 需要转换的String类型数据
     * @return
     *      int数组
     */
    public static int[] stringToAscii(String value)  {
        char[] chars = value.toCharArray();
        int[] temp = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            temp[i] = (int)chars[i];
        }
        return temp;
    }


    public static String[] ascillToHexStringArray(int[] ascii){
        String[] array = new String[ascii.length];
        int i = 0;
        for(int a:ascii){
            array[i] = "0x" + Integer.toHexString(a);
            i++;
        }
        return array;
    }


    public static String ascillToHexString(int[] ascii){
        StringBuilder sb = new StringBuilder(128);
        for(int a:ascii){
            sb.append("0x").append(Integer.toHexString(a));
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否为空，空字符串，"null"字符串
     * @author hux
     * @createTime 2018/3/20 13:29
     * @since 0.0.1
     * @param str
     *      需要判断的字符串
     * @return
     *      boolean
     */
    public static boolean isEmpty(String str){
        if(str == null)
            return true;
        str = str.replaceAll(" ","");
        if("".equals(str))
            return true;
        if("null".equals(str))
            return true;
        return false;
    }


    /**
     * byte字节转16进制字符串
     * @author hux
     * @createTime 2018/3/20 15:52
     * @since 0.0.1
     * @param b
     *      byte
     * @return
     *      16进制字符串
     */
    public static String byteToHex(byte b){
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex.toUpperCase();
    }

    /**
     * 判断字符串是否符合手机号码标准
     * @author hux
     * @createTime 2018/3/29 23:34
     * @since 0.0.1
     * @param mobile
     *      需要判断的字符串
     * @return
     *      boolean
     */
    public static boolean isMobile(String mobile){
        if(!isEmpty(mobile)){
            Pattern p = Pattern.compile("^((13[0-9])|(145)|(147)|(15[0-9])|(17[6-7])|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(mobile);
            return m.find();
        }
        return false;
    }

    /**
     * 判断字符串是否是一个6-20位数字加字母组合的密码字符串
     * @author hux
     * @createTime 2018/3/30 16:16
     * @since 0.0.1
     * @param psd
     *      需要判断的字符串
     * @return
     *      boolean
     */
    public static boolean isPassword(String psd){
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(psd);
        if (matcher.find())
        {
            return false;
        }
        pattern = Pattern.compile("^[a-zA-Z]+$");
        matcher = pattern.matcher(psd);
        if (matcher.find())
        {
            return false;
        }

        pattern = Pattern.compile("^[0-9a-zA-Z]{6,}$");
        matcher = pattern.matcher(psd);
        if (!matcher.find())
        {
            return false;
        }
        return true;
    }


    /**
     * 判断字符串是否为一个正确的电子邮箱格式
     * @author hux
     * @createTime 2018/4/17 13:27
     * @since 0.0.1
     * @param email
     *      需要判断的字符串
     * @return
     *      boolean
     */
    public static boolean isEmail(String email){
        if(isEmpty(email))
            return false;
        boolean isExist = false;
        Pattern p = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
        Matcher m = p.matcher(email);
        boolean b = m.matches();
        if(b) {
            isExist=true;
        } else {
            isExist = false;
        }
        return isExist;
    }

    /**
     * 判断4位以上数字加字母组合的字符串
     * @author admin
     * update at 2018年6月6日 下午3:56:58 by admin
     *		version:0.0.1
     *      create the method
     * @param username
     * 		需要判断的字符串
     * @return
     * 		boolean
     */
    public static boolean isUsername(String username){
        if(isEmpty(username))
            return false;
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(username);
        if (matcher.find()){
            return false;
        }
        pattern = Pattern.compile("^[a-zA-Z]+$");
        matcher = pattern.matcher(username);
        if (matcher.find()){
            return false;
        }

        pattern = Pattern.compile("^[0-9a-zA-Z]{4,}$");
        matcher = pattern.matcher(username);
        if (!matcher.find()){
            return false;
        }
        return true;
    }

    /**
     * 手机号码中间四位加星号处理
     * @author hux
     * @createTime 2019/4/9 17:22
     * @since 1.0.0
     * @param mobile
     *      手机号码
     * @return
     *       void
     */
    public static String asteriskMobile(String mobile){
        if(!isMobile(mobile))
            return mobile;
        StringBuilder sb = new StringBuilder(32);
        sb.append(mobile.substring(0,3));
        sb.append(" **** ");
        sb.append(mobile.substring(mobile.length()-4,mobile.length()));
        return sb.toString();
    }
}
