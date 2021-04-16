package com.gateway.fraud.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBUtils {
  
    public static <T> T unmarshal(Class<T> clazz, String xmlStr) {  
  
    	try {
    		JAXBContext jc = JAXBContext.newInstance(clazz);  
    		Unmarshaller u = jc.createUnmarshaller();  
  
    		InputStream is = new ByteArrayInputStream(xmlStr.getBytes());
			
    		@SuppressWarnings("unchecked")
			T t = (T) u.unmarshal(is);
			return t;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
    	return null;
    }  
  
    public static <T> String marshal(Class<T> clazz, T obj) {  
    	
    	try {
	        JAXBContext jc = JAXBContext.newInstance(clazz);  
	        Marshaller m = jc.createMarshaller();  
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
	        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");  
			
	        OutputStream os = new ByteArrayOutputStream();
			m.marshal(obj, os);  
		
			return os.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }  
    
    
    
}
