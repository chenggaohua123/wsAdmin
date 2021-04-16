package com.gateway.common.utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpos.iso.ISOUtil;
import org.jpos.security.SMException;
import org.jpos.security.SecureDESKey;

import com.union.hsmapi.UnionAPI;


public class Sjj1127SecurityModule{


	private static Log logger = LogFactory.getLog(Sjj1127SecurityModule.class);
	//加密机IP
	private String SJJ1127_SERVER_IP = "172.21.50.200";
	//加密机端口
	private int SJJ1127_SERVER_PORT = 8080;
	//加密机链接超时时间
	private int SJJ1127_TIME_OUT = 60000;
	//是否读取配置文件
	private int SJJ1127_IS_CFG = -1;
	//加密机头部查毒
	private int SJJ1127_HEAD_LEN = 8;
	//便宜量
	private int SJJ1127_ISLENOFHSMMSG = 0;
	//
	private int SJJ1127_CLIENTSOCKET = -1;
	//计算加密向量
	private String SJJ1127_IV = "0000000000000000";
	
	private String posLmkIndex = "0001"; 
	
	private static UnionAPI unionApi = new UnionAPI();
	//计算加密时的校验值
	protected static final byte zeroBlock[] = ISOUtil.hex2byte("0000000000000000");
	
	public Sjj1127SecurityModule() {
		super();
	}

	/**
	 * 加密数据
	 * @param typeOfAlgo
	 * @param indexOfKey
	 * @param keyValByMK
	 * @param iniVec
	 * @param lenOfSData
	 * @param SData
	 * @return
	 */
	public byte[] enctyptDate(
			String typeOfAlgo, 
			String indexOfKey,
			String keyValByMK, 
			String iniVec, 
			int lenOfSData, 
			String SData){
		byte[] DData = new byte[SData.length()];
		try {
			int flag = unionApi.union_syj1001_enctyptData(
					SJJ1127_SERVER_IP, 
					SJJ1127_SERVER_PORT, 
					SJJ1127_TIME_OUT, 
					SJJ1127_IS_CFG, 
					SJJ1127_HEAD_LEN, 
					SJJ1127_ISLENOFHSMMSG, 
					SJJ1127_CLIENTSOCKET,
					typeOfAlgo, 
					indexOfKey, 
					keyValByMK, 
					iniVec, 
					lenOfSData, 
					SData, 
					DData);
			logger.info("加密返回码："+flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DData;
	}
	

	/**
	 * 生成一个由主密钥加密的密钥
	 */
	public SecureDESKey generateKeyImpl(short keyLength, String keyType){
		byte [] keyValByMK = new byte[keyLength/4];
		byte [] chkVal = new byte[16];
		try {
			int flag = unionApi.union_syj1001_genKey(
					SJJ1127_SERVER_IP, 
					SJJ1127_SERVER_PORT, 
					SJJ1127_TIME_OUT, 
					SJJ1127_IS_CFG, 
					SJJ1127_HEAD_LEN, 
					SJJ1127_ISLENOFHSMMSG,
					SJJ1127_CLIENTSOCKET, 
					keyLength/4, 
					"FFFF",
					keyValByMK, 
					chkVal);
			if(flag == 0){
				SecureDESKey key = new SecureDESKey();
				key.setKeyBytes(ISOUtil.hex2byte(new String(keyValByMK)));
				logger.info("生成的密钥密文："+ISOUtil.hexString(key.getKeyBytes()));
				key.setKeyLength(keyLength);
				key.setKeyCheckValue(ISOUtil.trim(ISOUtil.hex2byte(new String(chkVal)), 4));
				logger.info("生成的密钥校验码："+ISOUtil.hexString(key.getKeyCheckValue()));
				key.setKeyType(keyType);
				return key;
			}else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param keyType 密钥类型
	 * @param sourceKeyIndex 源保护密钥索引
	 * @param destKeyIndex 目标保护密钥索引
	 * @param protectedKeyIndex 受保护密钥索引
	 * @param transLateType 转换类型01：从MK加密到KEK加密   02：从KEK加密到MK加密  03:从KEK1 加密到KEK2 加密
	 * @param sourceKey
	 * @param destKey
	 * @param protectedKey
	 * @return
	 */
	public SecureDESKey translateKey(String keyType ,
			String sourceKeyIndex,
			String destKeyIndex,
			String protectedKeyIndex,
			String transLateType,
			SecureDESKey sourceKey,
			SecureDESKey destKey,
			SecureDESKey protectedKey){
		byte [] keyByte = new byte[protectedKey.getKeyLength()/4];
		byte [] chckedValue = new byte[32];
		//转加密
		try {
			unionApi.union_syj1001_translateKey(
					SJJ1127_SERVER_IP,
					SJJ1127_SERVER_PORT,
					SJJ1127_TIME_OUT,
					SJJ1127_IS_CFG,
					SJJ1127_HEAD_LEN,
					SJJ1127_ISLENOFHSMMSG,
					SJJ1127_CLIENTSOCKET,
					transLateType,
					sourceKeyIndex,
					null != sourceKey ? ISOUtil.hexString(sourceKey.getKeyBytes()):"",	// FFFF 时有此域
					destKeyIndex,
					null != destKey ? ISOUtil.hexString(destKey.getKeyBytes()):"",
					protectedKey.getKeyLength()/4,
					null !=protectedKey ? ISOUtil.hexString(protectedKey.getKeyBytes()):"",
					"N",
					"",
					keyByte,
					chckedValue
				);
			SecureDESKey key = new SecureDESKey();
			key.setKeyBytes(ISOUtil.hex2byte(new String(keyByte)));
			logger.info("转换密钥密文："+new String(keyByte));
			logger.info("转换密钥校验码："+ISOUtil.hexString(ISOUtil.trim(ISOUtil.hex2byte(new String(chckedValue)), 4)));
			key.setKeyLength(protectedKey.getKeyLength());
			key.setKeyType(keyType);
			key.setKeyCheckValue(ISOUtil.trim(ISOUtil.hex2byte(new String(chckedValue)), 4));
			return key;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	protected byte[] generateCBC_MACImpl(byte[] data, SecureDESKey kd)
			throws SMException {
		//异域mac数据
		data = ISOUtil.concat(data, new byte[8]);
		int len = data.length - data.length % 8;
		if (data.length != len)
			data = ISOUtil.trim(data, len);
		else
			data = ISOUtil.trim(data, len - 8);
		byte mac[] = new byte[16];
		byte tmp[] = new byte[8];
		byte block[] = new byte[8];
		for (int i = 0; i < data.length / 8; i++)
		{
			System.arraycopy(data, i * 8, tmp, 0, 8);
			block = ISOUtil.xor(block, tmp);
		}
		block = ISOUtil.hexString(block).getBytes();
		System.arraycopy(block, 0, tmp, 0, 8);
		logger.info("block 数据块："+ ISOUtil.hexString(block));
		logger.info("macKey："+ISOUtil.hexString(kd.getKeyBytes()));
		
		//计算mac
		try {
			int flag = unionApi.union_syj1001_genMac(
					SJJ1127_SERVER_IP,
					SJJ1127_SERVER_PORT,
					SJJ1127_TIME_OUT,
					SJJ1127_IS_CFG,
					SJJ1127_HEAD_LEN,
					SJJ1127_ISLENOFHSMMSG,
					SJJ1127_CLIENTSOCKET,
					"02",
					"FFFF",
					ISOUtil.hexString(kd.getKeyBytes()),
					SJJ1127_IV,
					ISOUtil.hexString(block).length(),
					ISOUtil.hexString(block),
					mac
				);
			if(0==flag){
				logger.info("计算的MAC为："+ISOUtil.hexString(ISOUtil.trim(mac,8)));
			}else{
				logger.info("计算MAC的返回错误码："+flag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ISOUtil.trim(mac,8);
	}
	
	/**
	 * 
	 * @param pin pinKey加密下的密文
	 * @param pan 主账号
	 * @param pinKey 加密pin的密钥密文,MK加密下的密文
	 * @param pinKey1 需要转换到pinkey1的密钥密文，MK加密下面的密文
	 * @param format
	 * 				01：FORMAT 01：ANSI X9.8 FORMAT
					02：FORMAT 02: Docutel
					03：FORMAT 03：IBM FORMAT
					04：FORMAT 04：PLUS FORMAT
					05：FORMAT 05：ISO 9564 FORMAT 1
	 * @return
	 * @throws Exception 
	 */
	public String translatePinExt(String pin,String pan,String posFormat,String bankFormat,SecureDESKey pinKey ,SecureDESKey pinKey1) throws Exception{
		StringBuffer msg = new StringBuffer();
		logger.info("主账号："+pan);
		logger.info("POS TPK："+ISOUtil.hexString(pinKey.getKeyBytes()));
		logger.info("BANK TPK："+ISOUtil.hexString(pinKey1.getKeyBytes()));
		//组合转加密密文
		msg.append("0022")
		   .append("FFFF")
		   .append("02")
		   .append(ISOUtil.hexString(pinKey.getKeyBytes()))
		   .append("FFFF")
		   .append("02")
		   .append(ISOUtil.hexString(pinKey1.getKeyBytes()))
		   .append(posFormat)
		   .append(bankFormat)
		   .append(pin)
		   .append(pan);
		byte [] revMsg = new byte[1024];
		unionApi.union_syj1001_direct_sendMsg(
				msg.toString(), 
				SJJ1127_SERVER_IP, 
				SJJ1127_SERVER_PORT, 
				SJJ1127_TIME_OUT, 
				SJJ1127_IS_CFG, 
				SJJ1127_HEAD_LEN, 
				SJJ1127_ISLENOFHSMMSG, 
				SJJ1127_CLIENTSOCKET, 
				revMsg);
		logger.info("发送到加密机的指令："+msg.toString());
		String pinString = ISOUtil.hexString(ISOUtil.hex2byte(new String(revMsg)));
		String opType = pinString.substring(0,4);
		String respCode = pinString.substring(4,6);
		pinString = pinString.substring(6,22);
		logger.info("pin转加密返回指令编码"+opType);
		logger.info("pin转加密返回码"+respCode);
		logger.info("pin转加密返回结果"+pinString);
		return pinString;
	}
	
	/**
	 * 加密PIN
	 * @param indexOfKey
	 * @param keyValByMK
	 * @param formatOfPinBlock
	 * @param clearPin
	 * @param accNo
	 * @return
	 * @throws Exception
	 */
	public String enctyptPin(String indexOfKey,String keyValByMK,String formatOfPinBlock,String clearPin,String accNo) throws Exception{
		byte [] pinByKEK = new byte[16];
		int i = unionApi.union_syj1001_enctyptPin(
				SJJ1127_SERVER_IP,
				SJJ1127_SERVER_PORT,
				SJJ1127_TIME_OUT,
				SJJ1127_IS_CFG,
				SJJ1127_HEAD_LEN,
				SJJ1127_ISLENOFHSMMSG,
				SJJ1127_CLIENTSOCKET, 
				indexOfKey, 
				keyValByMK, 
				formatOfPinBlock, 
				clearPin, 
				accNo, 
				pinByKEK);
		logger.info("返回码："+i);
		logger.info("加密pin值："+new String(pinByKEK));
		return new String(pinByKEK);
	}
	
	/**
	 * 解密PIN
	 * @param keyValByMK
	 * @param pinByKEK
	 * @param accNo
	 * @return
	 * @throws Exception
	 */
	public String dectyptPin(SecureDESKey keyValByMK,String pinByKEK,String accNo) throws Exception{
		byte [] clearPin = new byte[12];
		int a = unionApi.union_syj1001_setAuthApply(
				SJJ1127_SERVER_IP,
				SJJ1127_SERVER_PORT,
				SJJ1127_TIME_OUT,
				SJJ1127_IS_CFG,
				SJJ1127_HEAD_LEN,
				SJJ1127_ISLENOFHSMMSG,
				SJJ1127_CLIENTSOCKET);
		logger.info("返回码："+a);
		int i = unionApi.union_syj1001_dectyptPin(
				SJJ1127_SERVER_IP,
				SJJ1127_SERVER_PORT,
				SJJ1127_TIME_OUT,
				SJJ1127_IS_CFG,
				SJJ1127_HEAD_LEN,
				SJJ1127_ISLENOFHSMMSG,
				SJJ1127_CLIENTSOCKET, 
				"FFFF", 
				ISOUtil.hexString(keyValByMK.getKeyBytes()), 
				"01", 
				pinByKEK, 
				accNo, 
				clearPin);
		logger.info("返回码："+i);
		logger.info("pin="+new String(clearPin));
		return new String(clearPin);
	}
	
	public String getSJJ1127_SERVER_IP() {
		return SJJ1127_SERVER_IP;
	}

	public void setSJJ1127_SERVER_IP(String sJJ1127_SERVER_IP) {
		SJJ1127_SERVER_IP = sJJ1127_SERVER_IP;
	}

	public int getSJJ1127_SERVER_PORT() {
		return SJJ1127_SERVER_PORT;
	}

	public void setSJJ1127_SERVER_PORT(int sJJ1127_SERVER_PORT) {
		SJJ1127_SERVER_PORT = sJJ1127_SERVER_PORT;
	}

	public int getSJJ1127_TIME_OUT() {
		return SJJ1127_TIME_OUT;
	}

	public void setSJJ1127_TIME_OUT(int sJJ1127_TIME_OUT) {
		SJJ1127_TIME_OUT = sJJ1127_TIME_OUT;
	}

	public int getSJJ1127_IS_CFG() {
		return SJJ1127_IS_CFG;
	}

	public void setSJJ1127_IS_CFG(int sJJ1127_IS_CFG) {
		SJJ1127_IS_CFG = sJJ1127_IS_CFG;
	}

	public int getSJJ1127_HEAD_LEN() {
		return SJJ1127_HEAD_LEN;
	}

	public void setSJJ1127_HEAD_LEN(int sJJ1127_HEAD_LEN) {
		SJJ1127_HEAD_LEN = sJJ1127_HEAD_LEN;
	}

	public int getSJJ1127_ISLENOFHSMMSG() {
		return SJJ1127_ISLENOFHSMMSG;
	}

	public void setSJJ1127_ISLENOFHSMMSG(int sJJ1127_ISLENOFHSMMSG) {
		SJJ1127_ISLENOFHSMMSG = sJJ1127_ISLENOFHSMMSG;
	}

	public int getSJJ1127_CLIENTSOCKET() {
		return SJJ1127_CLIENTSOCKET;
	}

	public void setSJJ1127_CLIENTSOCKET(int sJJ1127_CLIENTSOCKET) {
		SJJ1127_CLIENTSOCKET = sJJ1127_CLIENTSOCKET;
	}

	public String getSJJ1127_IV() {
		return SJJ1127_IV;
	}

	public void setSJJ1127_IV(String sJJ1127_IV) {
		SJJ1127_IV = sJJ1127_IV;
	}

	public static UnionAPI getUnionApi() {
		return unionApi;
	}

	public static void setUnionApi(UnionAPI unionApi) {
		Sjj1127SecurityModule.unionApi = unionApi;
	}	
	
	public String getPosLmkIndex() {
		return posLmkIndex;
	}

	public void setPosLmkIndex(String posLmkIndex) {
		this.posLmkIndex = posLmkIndex;
	}
}
