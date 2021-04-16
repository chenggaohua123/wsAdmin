package com.gateway.common.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/** 银行卡号加密 */
public class BankCardEncryp {
	private final static String DES = "DES";
	private final static String KEY = "!#%&(123";
	/** 单向加密 SHA */
	public static String eccryptSHA(String info) throws NoSuchAlgorithmException{  
        MessageDigest md5 = MessageDigest.getInstance("SHA");  
        byte[] srcBytes = info.getBytes();  
        //使用srcBytes更新摘要  
        md5.update(srcBytes);  
        //完成哈希计算，得到result  
        byte[] resultBytes = md5.digest();  
        BASE64Encoder base64E = new BASE64Encoder();
        return base64E.encode(resultBytes);
    }
	
	/** 单向加密 MD5 */
	public static String eccryptMD5(String info) throws NoSuchAlgorithmException{  
		//根据MD5算法生成MessageDigest对象  
		MessageDigest md5 = MessageDigest.getInstance("MD5");  
		byte[] srcBytes = info.getBytes();  
		//使用srcBytes更新摘要  
		md5.update(srcBytes);  
		//完成哈希计算，得到result  
		byte[] resultBytes = md5.digest();  
		BASE64Encoder base64E = new BASE64Encoder();
        return base64E.encode(resultBytes);
	}  
  
    /**
     * Description 根据键值进行加密
     * @param data 
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        byte[] bt = encrypt(data.getBytes(), KEY.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }
 
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data) throws IOException,
            Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf,KEY.getBytes());
        return new String(bt);
    }
    
    public static void main(String[] args) {
		String data = "65654654654654654";
		try {
			System.out.println(">>>>>>>>>>>" + encrypt(data));//LM6EeHuR5ASquE5yRNdEjnzRdo/8ubCA
			System.out.println("月份"+">>>>>>>>>>>" + decrypt("VXh5ztjOksM="));
			System.out.println("年份"+">>>>>>>>>>>" + decrypt("cf6I275bH34="));
			System.out.println(">>>>>"+eccryptSHA("4085316126382274"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
     
     
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
  
}
