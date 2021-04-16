package com.gateway.fraud.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import com.gateway.fraud.common.util.HttpUtil;
import com.gateway.fraud.common.util.MessageDigestUtil;
//import com.common.util.SysPropertiesConfig;
import com.gateway.fraud.common.exception.FraudExcetion;
import com.gateway.fraud.model.FraudInfo;
import com.google.common.collect.Maps;

public class MaxMindUtil {
	private static Log logger = LogFactory.getLog(MaxMindUtil.class);
	/**
	 * 查询maxMind信息
	 * @param info
	 * @return
	 * @throws FraudExcetion 
	 */
	public static FraudInfo queryMaxMindInfo(FraudInfo info) throws FraudExcetion{
		if(null == info){
			throw new FraudExcetion(Constants.ERROR_CODE_1002,"Fraud Info is null.");
		}
		String url =  (String)SysPropertiesConfig.getContextProperty("maxmind.service.url");
		String licenseKey =  (String)SysPropertiesConfig.getContextProperty("maxmind.service.licensekey");
		logger.info("maxmind service url:"+url);
		HttpClient client = HttpUtil.getThreadSafeHttpClient();
		HttpPost post = new HttpPost(url);
		Map<String, String> param = Maps.newHashMap();
    	param.put("i", info.getIpAddress());
		param.put("city", info.getBillCity());// billCity
		param.put("region", info.getBillState());// billState
		param.put("postal", info.getZip());// billZip
		param.put("country", info.getBillCountry());// billCountry
		param.put("license_key", licenseKey);// MaxMind license key.
		param.put("shipAddr", info.getShipAddress());
		param.put("shipCity", info.getShipCity());
		param.put("shipRegion", info.getShipState());// shipState
		param.put("shipPostal", info.getZip());// shipZip
		param.put("shipCountry", info.getShipCountry());
		param.put("domain", info.getEmail());// email
		param.put("custPhone", info.getPhone());// phone
		param.put("emailMD5", MessageDigestUtil.getMD5(info.getEmail().toLowerCase()));
		if(null != info.getCardBin() && info.getCardBin().length()>6){
			param.put("bin", info.getCardBin().substring(0,6));
		}else{
			param.put("bin", info.getCardBin());
		}
		param.put("binName", info.getBinName());
		param.put("binPhone", info.getBinPhone());
		param.put("txnID", info.getTxId());
		param.put("order_amount", info.getSourceAmount());
		param.put("order_currency", info.getSourceCurrency());
		param.put("shopID", info.getWebsite());
		param.put("txn_type", "creditcard");
		param.put("cvv_result", "N");
		param.put("requested_type", "premium");
		HttpResponse httpResponse = HttpUtil.doPost(client, post, param, "UTF-8");
		Map<String, String> map = Maps.newHashMap();
		if(httpResponse.getStatusLine().getStatusCode() == 200) {
			String result;
			try {
				result = EntityUtils.toString(httpResponse.getEntity());
				map = new HashMap<String, String>();
				logger.info("MaxMind结果=="+result);
				String[] re = result.split(";");
				for(int i=0;i<re.length;i++) {
					if(null!=re[i] && !"".equals(re[i])) {
						String key = re[i].substring(0, re[i].indexOf("="));
						map.put(key, re[i].replace((key+"="), ""));
					}
				}
				info.setBinName(map.get("binName"));
				info.setBinPhone(map.get("binPhone"));
				info.setIPCountry(map.get("countryCode"));
				info.setFraudScore(map.get("riskScore")==null?map.get("score"):map.get("riskScore"));
				info.setCountryMatch(map.get("countryMatch"));
				info.setHighRiskCountry(map.get("highRiskCountry"));
				info.setDistance(map.get("distance"));
				info.setIpAccuracyRadius(map.get("ip_accuracyRadius"));
				info.setIpCity(map.get("ip_city"));
				info.setIpRegion(map.get("ip_region"));
				info.setIpRegionName(map.get("ip_regionName"));
				info.setIpPostalCode(map.get("ip_postalCode"));
				info.setIpMetroCode(map.get("ip_metroCode"));
				info.setIpAreaCode(map.get("ip_areaCode"));
				info.setCountryCode(map.get("countryCode"));
				info.setIpCountryName(map.get("ip_countryName"));
				info.setIpContinentCode(map.get("ip_continentCode"));
				info.setIpLatitude(map.get("ip_latitude"));
				info.setIpLongitude(map.get("ip_longitude"));
				info.setIpTimeZone(map.get("ip_timeZone"));
				info.setIpAsnum(map.get("ip_asnum"));
				info.setIpUserType(map.get("ip_asnum"));
				info.setIpNetSpeedCell(map.get("ip_netSpeedCell"));
				info.setIpDomain(map.get("ip_domain"));
				info.setIpIsp(map.get("ip_isp"));
				info.setIpOrg(map.get("ip_org"));
				info.setIpCityConf(map.get("ip_cityConf"));
				info.setIpRegionConf(map.get("ip_regionConf"));
				info.setIpPostalConf(map.get("ip_postalConf"));
				info.setIpCountryConf(map.get("ip_countryConf"));
				info.setProxyScore(map.get("proxyScore"));
				info.setAnonymousProxy(map.get("anonymousProxy"));
				info.setIsTransProxy(map.get("isTransProxy"));
				info.setIpCorporateProxy(map.get("ip_corporateProxy"));
				info.setFreeMail(map.get("freeMail"));
				info.setCarderEmail(map.get("carderEmail"));
				info.setBinMatch(map.get("binMatch"));
				info.setBinCountry(map.get("binCountry"));
				info.setBinNameMatch(map.get("binNameMatch"));
				info.setBinPhoneMatch(map.get("binPhoneMatch"));
				info.setPrepaid(map.get("prepaid"));
				info.setCustPhoneInBillingLoc(map.get("custPhoneInBillingLoc"));
				info.setShipForward(map.get("shipForward"));
				info.setCityPostalMatch(map.get("cityPostalMatch"));
				info.setShipCityPostalMatch(map.get("shipCityPostalMatch"));
				info.setMaxmindID(map.get("maxmindID"));
				info.setMinfraudVersion(map.get("minfraud_version"));
				info.setServiceLevel(map.get("service_level"));
				info.setErr(map.get("err"));
			} catch (ParseException e) {
				throw new FraudExcetion(Constants.ERROR_CODE_1001,"query maxmind err.");
			} catch (IOException e) {
				throw new FraudExcetion(Constants.ERROR_CODE_1001,"query maxmind err.");
			}
		} else {
			logger.info("调用MaxMind失败,HttpClient status="+httpResponse.getStatusLine().getStatusCode());
			return info;
		}
		return info;
	}
}
