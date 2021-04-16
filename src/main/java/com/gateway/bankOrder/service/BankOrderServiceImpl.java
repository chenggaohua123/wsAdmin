package com.gateway.bankOrder.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gateway.bankOrder.model.BankOrderModel;
import com.gateway.bankOrder.utils.BankOrderUtils;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.utils.MD5Encryption;
@Service
public class BankOrderServiceImpl implements BankOrderService {

	private static Logger log=Logger.getLogger(BankOrderServiceImpl.class);
	
	/**
	 * 获取银行原始数据转化成MAP
	 */
	@Override
	public Map<String,Object> getRefundOrder(String url,int merchantid,String signature,int year,int month){
		log.info("getRefundOrder()******start:"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		//url
		String urlRefund = url + "?" + "merchantid="+merchantid+"&signature="+signature+"&year="+year+"&month="+month;
		
		//创建URL对象
		URL connURL=null;
		HttpURLConnection httpConn=null;
		try {
			connURL = new URL(urlRefund);
			//解决网站证书不信任问题
			BankOrderUtils.trustAllHttpsCertificates();
			HttpsURLConnection.setDefaultHostnameVerifier(BankOrderUtils.getHv());
			//打开url链接获得HttpUrlConnection
			httpConn = (HttpURLConnection) connURL.openConnection();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**设置URLConnection的参数和普通的请求属性****start***/
		httpConn.setRequestProperty("accept", "Application/json");
		httpConn.setRequestProperty("connection", "Keep-Alive");
		httpConn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		/**设置URLConnection的参数和普通的请求属性****end***/

		httpConn.setDoInput(true);
		InputStream is=null;
		BufferedReader br=null;
        String strJson="";
		try {
			httpConn.setRequestMethod("GET");
			/**GET方法请求*****start*/
			
			httpConn.connect();
			
			//获取URLConnection对象对应的输入流
			is = httpConn.getInputStream();
			//读取数据
			br = new BufferedReader(new InputStreamReader(is,"utf-8"));
			String str = "";
	        while ((str = br.readLine()) != null) {
	        	strJson=strJson+str;
	            System.out.println(strJson);
	        }
		} catch (ProtocolException e1) {
			e1.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/**GET方法请求*****End*/
       log.info("原始数据:"+strJson);
       log.info("url:"+urlRefund);
       log.info("BankOrderUtils.getMap()******start:"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
       Map<String,Object> map=BankOrderUtils.getMap(strJson);
       log.info("BankOrderUtils.getMap()******end:"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
       log.info("getRefundOrder()******end:"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
       return map;
	}

	/**
	 * 把JSON数组转化成BankOrderModel对象
	 */
	@Override
	public List<BankOrderModel> getBankOrderModelList(JSONArray json){
		log.info("getBankOrderModelList()******start:"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		 List<BankOrderModel> list=new ArrayList<BankOrderModel>();
		 for (int i = 0; i < json.size(); i++) {
				Object str=json.get(i);
				//把每一条订单转化成MAP
				JSONObject jsonObj=(JSONObject) str;
				BankOrderModel bandOrderModel=new BankOrderModel();
				for(Map.Entry<String, Object> entry : jsonObj.entrySet()) {
					String objKey=entry.getKey();
					String objValue=entry.getValue().toString();
					
					Method method=null;
					try {
						method = bandOrderModel.getClass().getMethod("set"+objKey,objValue.getClass());
						Object val= method.invoke(bandOrderModel,objValue);
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				list.add(bandOrderModel);
				
			}
		 log.info("getBankOrderModelList()******end:"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		 return list;
	 }

	/**
	 * 对数据进行分页处理
	 */
	@Override
	public PageInfo<BankOrderModel> getPageList(Criteria criteria,List<BankOrderModel> list) {
		log.info("getPageList()******start:"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		PageInfo<BankOrderModel> page=new PageInfo<BankOrderModel>(criteria.getPageNum(), criteria.getPageSize());
		page.setTotal(list.size());
		List<BankOrderModel> data=new ArrayList<BankOrderModel>();
		for (int i = 0; i < list.size(); i++) {
			if(i>=page.getOffset()&&i<(page.getPageSize()+page.getOffset())){
				data.add(list.get(i));
			}
		}
		page.setData(data);
		log.info("getPageList()******end:"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		return page;
	}


	/**
	 * 通过get方法请求银行数据，并转化成BankOrderModel对象
	 * criteria 参数
	 * url 请求的url
	 * map 商户id和密钥
	 * isLimit 是否查所以数据1：是  0：否
	 */
	@Override
	public PageInfo<BankOrderModel> getPage(Criteria criteria, String url,Map<String,String> map,String isLimit) {
		log.info("getPage******start:"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		//通道id
		//签名
		String signature="";
		//银行商户号
		String merchantid="";
		//密钥
		String md5Key="";
		//年份
		int year=Integer.parseInt((String)criteria.getCondition().get("year"));
		//月份
		int month=Integer.parseInt((String)criteria.getCondition().get("month"));
		//
		PageInfo<BankOrderModel> page=new PageInfo<BankOrderModel>(criteria.getPageNum(), criteria.getPageSize());
		if(MapUtils.isNotEmpty(map)){
			merchantid=map.get("merchantid");
			md5Key=map.get("md5Key");
			if( (merchantid != null && !"".equals(merchantid.trim())) &&(md5Key != null && !"".equals(md5Key.trim()))){
				signature=MD5Encryption.getMD5Info(merchantid+md5Key);
				Map<String,Object> resultMap=new HashMap<String,Object>();
				//调用接口
				//获取银行接口返回数据
				resultMap=getRefundOrder(url,Integer.parseInt(merchantid), signature, year, month);
				String status=(String) resultMap.get("Status");
				System.out.println("Success="+status+":"+("Success".equals(status)));
				if("Success".equals(status)){
					//获取数据成功
					Object obj= resultMap.get("Result");
					System.out.println(obj);
					JSONArray json=(JSONArray) obj;
					log.info("银行订单数量"+json.size());
					List<BankOrderModel> list=this.getBankOrderModelList(json);
					log.info("转换成银行订单对象数量:"+list.size());
					if("0".equals(isLimit)){
						page= this.getPageList(criteria, list);
					}else{
						page=new PageInfo<BankOrderModel>(criteria.getPageNum(), criteria.getPageSize());
						page.setTotal(list.size());
						page.setData(list);
					}
					
				}else{
					//获取数据失败
					page.setData(null);
				}
			}	
		}else{
			page.setData(null);
		}
		log.info("getPage******end:"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		return page;
	}

}
