package com.gateway.countAnalysis.model;

import java.util.HashMap;
import java.util.Map;

public class InitEuropeInfo {
	
	
	public static Map<String, Map<String, String>> getResultMap(){
		Map<String, Map<String, String>> codeMap = new HashMap<String, Map<String,String>>();
		//z14
		Map<String, String> z14ResultMap = new HashMap<String, String>();
		z14ResultMap.put("M", "CVV2/CVC2 Match");
		z14ResultMap.put("N", "CVV2/CVC2 No Match");
		z14ResultMap.put("P", "Not Processed");
		z14ResultMap.put("S", "The CVV2 should be on the card but the merchant indicates it is not");
		z14ResultMap.put("U", "CVV2/CVC2 Unavailable - issuer does not support");
		z14ResultMap.put("Y", "CVC1 Incorrect");
		codeMap.put("z14", z14ResultMap);
		
		//z5
//		Map<String, String> z5ResultMap = new HashMap<String, String>();
//		z5ResultMap.put("", "Risk score");
//		codeMap.put("z5", z5ResultMap);
		
		//z2
		Map<String, String> z2ResultMap = new HashMap<String, String>();
		z2ResultMap.put("-13", "Transaction must include valid 3D secure data");
		z2ResultMap.put("-12", "Card Secure Code has to be present");
		z2ResultMap.put("-11", "Currency is not supported for the given merchant");
		z2ResultMap.put("-10", "Unclassified Error");
		z2ResultMap.put("-9", "Parameter is malformed");
		z2ResultMap.put("-8", "Package Signature is malformed");
		z2ResultMap.put("-7", "No Response from the gateway. Connection is broken");
		z2ResultMap.put("-5", "Transaction has been rejected. Processing has been stopped");
		z2ResultMap.put("-3", "Account status was not updated");
		z2ResultMap.put("-2", "Account does not exist");
		z2ResultMap.put("-1", "Account already exists");
		z2ResultMap.put("0", "Transaction has been executed successfully");
		z2ResultMap.put("1", "Transaction has been denied by the gateway");
		z2ResultMap.put("2", "Transaction has been denied by the gateway due to its fraud high risk");
		z2ResultMap.put("3", "Transaction has been denied by the gateway due to its AVS high risk");
		z2ResultMap.put("4", "Transaction has been denied by the gateway due to the interchange timeout");
		z2ResultMap.put("5", "Transaction has been declined");
		z2ResultMap.put("7", "Redirect URL issued");
		z2ResultMap.put("9", "Transaction has been denied by the gateway due to the LUHN check failure");
		z2ResultMap.put("100", "Transaction is 3D enrolled");
		codeMap.put("z2", z2ResultMap);
		
		//z6
		Map<String, String> z6ResultMap = new HashMap<String, String>();
		z6ResultMap.put("00", "Approved or completed successfully");
		z6ResultMap.put("01", "Approved or completed successfully");
		z6ResultMap.put("02", "Refer to card issuer");
		z6ResultMap.put("03", "Invalid merchant");
		z6ResultMap.put("04", "Do not honour");
		z6ResultMap.put("05", "Do not Honour");
		z6ResultMap.put("06", "Invalid Transaction for Terminal");
		z6ResultMap.put("07", "Honour with ID");
		z6ResultMap.put("08", "Time-Out");
		z6ResultMap.put("09", "No Original");
		z6ResultMap.put("10", "Unable to Reverse");
		z6ResultMap.put("11", "Partial Approval");
		z6ResultMap.put("12", "Invalid transaction card / issuer / acquirer");
		z6ResultMap.put("13", "Invalid amount");
		z6ResultMap.put("14", "Invalid card number");
		z6ResultMap.put("17", "Invalid Capture date (terminal business date)");
		z6ResultMap.put("19", "System Error; Re-enter transaction");
		z6ResultMap.put("20", "No From Account");
		z6ResultMap.put("21", "No To Account");
		z6ResultMap.put("22", "No Checking Account");
		z6ResultMap.put("23", "No Saving Account");
		z6ResultMap.put("24", "No Credit Account");
		z6ResultMap.put("30", "Format error");
		codeMap.put("z6", z2ResultMap);
		
		return codeMap;
		
	}
	
	/**
	 * 获取z14信息
	 */
	public static Map<String, String> getZ14ResultMap(){
		Map<String, String> z14ResultMap = new HashMap<String, String>();
		z14ResultMap.put("M", "CVV2/CVC2 Match");
		z14ResultMap.put("N", "CVV2/CVC2 No Match");
		z14ResultMap.put("P", "Not Processed");
		z14ResultMap.put("S", "The CVV2 should be on the card but the merchant indicates it is not");
		z14ResultMap.put("U", "CVV2/CVC2 Unavailable - issuer does not support");
		z14ResultMap.put("Y", "CVC1 Incorrect");
		return z14ResultMap;
	}
	
	/**
	 * z2
	 */
	public static Map<String, String> getZ2ResultMap(){
		Map<String, String> z2ResultMap = new HashMap<String, String>();
		z2ResultMap.put("-13", "Transaction must include valid 3D secure data");
		z2ResultMap.put("-12", "Card Secure Code has to be present");
		z2ResultMap.put("-11", "Currency is not supported for the given merchant");
		z2ResultMap.put("-10", "Unclassified Error");
		z2ResultMap.put("-9", "Parameter is malformed");
		z2ResultMap.put("-8", "Package Signature is malformed");
		z2ResultMap.put("-7", "No Response from the gateway. Connection is broken");
		z2ResultMap.put("-5", "Transaction has been rejected. Processing has been stopped");
		z2ResultMap.put("-3", "Account status was not updated");
		z2ResultMap.put("-2", "Account does not exist");
		z2ResultMap.put("-1", "Account already exists");
		z2ResultMap.put("0", "Transaction has been executed successfully");
		z2ResultMap.put("1", "Transaction has been denied by the gateway");
		z2ResultMap.put("2", "Transaction has been denied by the gateway due to its fraud high risk");
		z2ResultMap.put("3", "Transaction has been denied by the gateway due to its AVS high risk");
		z2ResultMap.put("4", "Transaction has been denied by the gateway due to the interchange timeout");
		z2ResultMap.put("5", "Transaction has been declined");
		z2ResultMap.put("7", "Redirect URL issued");
		z2ResultMap.put("9", "Transaction has been denied by the gateway due to the LUHN check failure");
		z2ResultMap.put("100", "Transaction is 3D enrolled");
		return z2ResultMap;
	}
	
	/*
	 * z6
	 */
	public static Map<String, String> getZ6ResultMap(){
		Map<String, String> z6ResultMap = new HashMap<String, String>();
		z6ResultMap.put("00", "Approved or completed successfully");
		z6ResultMap.put("01", "Approved or completed successfully");
		z6ResultMap.put("02", "Refer to card issuer");
		z6ResultMap.put("03", "Invalid merchant");
		z6ResultMap.put("04", "Do not honour");
		z6ResultMap.put("05", "Do not Honour");
		z6ResultMap.put("06", "Invalid Transaction for Terminal");
		z6ResultMap.put("07", "Honour with ID");
		z6ResultMap.put("08", "Time-Out");
		z6ResultMap.put("09", "No Original");
		z6ResultMap.put("10", "Unable to Reverse");
		z6ResultMap.put("11", "Partial Approval");
		z6ResultMap.put("12", "Invalid transaction card / issuer / acquirer");
		z6ResultMap.put("13", "Invalid amount");
		z6ResultMap.put("14", "Invalid card number");
		z6ResultMap.put("17", "Invalid Capture date (terminal business date)");
		z6ResultMap.put("19", "System Error; Re-enter transaction");
		z6ResultMap.put("20", "No From Account");
		z6ResultMap.put("21", "No To Account");
		z6ResultMap.put("22", "No Checking Account");
		z6ResultMap.put("23", "No Saving Account");
		z6ResultMap.put("24", "No Credit Account");
		z6ResultMap.put("30", "Format error");
		return z6ResultMap;
	}
	
}
