package com.gateway.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encryption {

	/**
	 * 实现：MD5加密工具，支持中文
	 * @param str 需要加密的参数
	 * @return 加密后的密文
	 */
	public static String getMD5Info(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for(int i = 0; i < byteArray.length; i++) {
			if(Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			} else {
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return md5StrBuff.toString();
	}
	/**
	 * 实现：SHA加密工具
	 * @param str 需要加密的参数
	 * @return 加密后的密文
	 */
	public static String getHashEncryption(String str) {
		String result = null;
		try {
			MessageDigest mesd = MessageDigest.getInstance("SHA-1");
			if(null!=str && !"".equals(str)) {
				result = byte2hexString(mesd.digest(str.getBytes("UTF-8")));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return result;
		}  catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	/**
	 * 实现：SHA-256加密工具
	 * @param str 需要加密的参数
	 * @return 加密后的密文
	 */
	public static String getSHA256Encryption(String str) {
		String result = null;
		try {
			MessageDigest mesd = MessageDigest.getInstance("SHA-256");
			result = byte2hexString(mesd.digest(str.getBytes("ISO-8859-1")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return result;
		}  catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	public static String byte2hexString(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString();
	}
	
}