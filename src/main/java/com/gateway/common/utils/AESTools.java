package com.gateway.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESTools {

	/**
	 * 实现：AES加密
	 * @param content
	 * @param password
	 * @return
	 */
	public static String encrypt(String content, String password) {
		try {
//			byte[] raw = password.getBytes("utf-8");
	        SecretKeySpec skeySpec = new SecretKeySpec(hex2byte(password), "AES");
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	        byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
	        return new Base64().encodeToString(encrypted);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 解密
	 * @param content
	 * @param password
	 * @return
	 */
	public static String decrypt(byte[] content, String password) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(hex2byte(password), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = new Base64().decode(content);//先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			return new String(original,"utf-8");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	private static byte[] hex2byte(String hexStr){
		if (hexStr.length() % 2 == 0) {
			return hex2byte(hexStr.getBytes(), 0, hexStr.length() >> 1);
		}
		return hex2byte("0" + hexStr);
	}
	private static byte[] hex2byte(byte[] b, int offset, int len) {
		byte[] d = new byte[len];
		for (int i = 0; i < len * 2; i++) {
			int shift = i % 2 == 1 ? 0 : 4;
			int tmp30_29 = (i >> 1);
			byte[] tmp30_25 = d; tmp30_25[tmp30_29] = (byte)(tmp30_25[tmp30_29] | Character.digit((char)b[(offset + i)], 16) << shift);
		}
		return d;
	}
	public static String parseByte2HexStr(byte[] buf) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String pas = "32179713140527518150536713505510";
		//解密
		String url = "jdbc:mysql://192.168.20.38:3306/payment?useUnicode=true&characterEncoding=UTF-8";
		String username = "wk";
		String password = "wk123456";
		System.out.println("密文:");
		System.out.println("jdbc.url="+AESTools.encrypt(url, pas));
		System.out.println("jdbc.username="+AESTools.encrypt(username, pas));
		System.out.println("jdbc.password="+AESTools.encrypt(password, pas));
		//解密
		String urlPas="oLHO+ls2oKwMo0nkPxjuW5rh+K1kRrerFeec/EgX0nXJSx2B6X33I8VElmfgbMBjAn6UvuY7COXFJmV+ELaRIXbeawCNP1hYeOCoNn6rjSE=";
		String usernamePas="4kKkel2P1PGh5MqjffQL+Q==";
		String passwordPas="vXDyqF/hC28Rngty0F/a+g==";
		System.out.println("原文:");
		System.out.println("jdbc.url="+AESTools.decrypt(urlPas.getBytes(), pas));
		System.out.println("jdbc.username="+AESTools.decrypt(usernamePas.getBytes(), pas));
		System.out.println("jdbc.password="+AESTools.decrypt(passwordPas.getBytes(), pas));
	}
}