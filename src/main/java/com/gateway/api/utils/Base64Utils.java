package com.gateway.api.utils;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Utils {
    /**
     * BASE64解密
     * @param str
     * @return
     */
    public static byte [] decodeByBASE64(String str){
 		BASE64Decoder bd = new BASE64Decoder();
 		byte[] bt=null;
 		try {
 			bt = bd.decodeBuffer(str);
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
 		return bt;
    
    }
    
    /**
     * BASE64加密
     * @param str
     * @return
     */
    public static String encodeByBASE64(byte [] bytes){
 	   BASE64Encoder b = new BASE64Encoder();
 	   String res = b.encode(bytes);
 	   return res;
    }
}
