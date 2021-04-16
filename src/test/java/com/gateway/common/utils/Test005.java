package com.gateway.common.utils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.sun.net.ssl.SSLContext;

public class Test005 {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException {
		SSLContext context = SSLContext.getInstance("TLSv1.2");
		 
		context.init(null, null, null);
		 
		SSLSocketFactory factory = (SSLSocketFactory) context.getSocketFactory();
		 
		SSLSocket socket = (SSLSocket) factory.createSocket();
		 
		String[] protocols = socket.getSupportedProtocols();
		 
		System.out.println("Supported Protocols: " + protocols.length);
		 
		for (int i = 0; i < protocols.length; i++) {
		 
		System.out.println(" "+ protocols[i]);
		 
		}
		 
		protocols = socket.getEnabledProtocols();
		 
		System.out.println("Enabled Protocols: " + protocols.length);
		 
		for (int i = 0; i < protocols.length; i++) {
		 
		System.out.println(" "+ protocols[i]);
		 
		}
	}
}
