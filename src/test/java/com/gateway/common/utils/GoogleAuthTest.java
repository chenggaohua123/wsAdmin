package com.gateway.common.utils;  
  
import org.junit.Test;

import com.gateway.common.utils.GoogleAuthenticator;  
  
/* 
 * Not really a unit test- but it shows usage 
 */  
public class GoogleAuthTest {  
      
    @Test  
    public void genSecretTest() {  
    	String SEED="llE85E1D7521662D53E57A165F1EA29C2B855C6DE9EA1D6B3F";
//        String secret = GoogleAuthenticator.generateSecretKey(SEED);  
    	String secret="H3U4SEMXJDSQD57KTK5UOIU3M6N7MKRU";
        String url = GoogleAuthenticator.getQRBarcodeURL("honghao", "bringall", secret);  
        System.out.println("Please register " + url);  
        System.out.println("Secret key is " + secret);  
    }  
      
    // Change this to the saved secret from the running the above test. 
//    D6KGOEHKK5AGGS3G5MWTTN2IQEA6MZBY5ITKCKG3BWYWA2BEEEYF3L4BNPHHJ5HWAPORN6PHQWGI667P6UXVJ7TZ
//llE85E1D7521662D53E57A165F1EA29C2B85
//WRNJVP7DBELUNINJOXLERYDIZQJHI4XG
//CB7A7EL62P3FYL73AOJ2KPTBLKKVQ===
    static String savedSecret = "V4QBZWGZ6TK6I3GVJDAH3WRLBHDGVR4M";  
      
    //@Test  
    public void authTest() {  
        // enter the code shown on device. Edit this and run it fast before the code expires!  559033
        long code = 750353;  
        long t = System.currentTimeMillis();  
        GoogleAuthenticator ga = new GoogleAuthenticator();  
        ga.setWindowSize(0); //should give 5 * 30 seconds of grace...  
        boolean r = ga.check_code(savedSecret, code, t);  
        System.out.println("Check code = " + r);  
    }  
}  