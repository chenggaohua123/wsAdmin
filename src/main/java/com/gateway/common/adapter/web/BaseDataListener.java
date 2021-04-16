package com.gateway.common.adapter.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.sysmgr.model.BaseDataInfo;
import com.gateway.sysmgr.service.SysMgrService;

@Service
public class BaseDataListener implements InitializingBean{
	
	private static final Log log = LogFactory.getLog(BaseDataListener.class);
	
	private static Map<String, String> threadMetrix = new HashMap<String, String>();
	
	static{
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(BaseDataListener.class.getClassLoader().getResource("").getPath()+"res/biz/threadmetrix.properties"));
			Set<Object> keys = properties.keySet();
			if(keys!=null && keys.size()>0){
				for(Object key : keys){
					String value = (String) properties.get(key);
					threadMetrix.put((String) key, value);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Autowired
	private  SysMgrService sysMgrService;
	
	private static Map<String, List<BaseDataInfo>>  data = new HashMap<String,  List<BaseDataInfo>>();
	
	
	public  SysMgrService getSysMgrService() {
		return sysMgrService;
	}

	public void setSysMgrService(SysMgrService sysMgrService) {
		this.sysMgrService = sysMgrService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("初始化字典数据开始--------");
		if(this.data.size()==0){
			List<BaseDataInfo> list = sysMgrService.queryBaseDataList(new Criteria());
			for(BaseDataInfo baseData:list){
				if(null !=  baseData.getColumnKeyName() && 
						null != baseData.getColumnVauleName() && 
						null != baseData.getTableName() && 
						!"".equals(baseData.getColumnKeyName().trim()) && 
						!"".equals(baseData.getColumnVauleName().trim())){
					List<BaseDataInfo> tempBaseDataList = sysMgrService.queryBaseDataList(baseData.getTableName(),
							baseData.getColumnKeyName(),
							baseData.getColumnVauleName());
					for(BaseDataInfo tempdata:tempBaseDataList){
						tempdata.setCreateBy(baseData.getCreateBy());
						tempdata.setCreateDate(baseData.getCreateDate());
					}
					data.put(baseData.getTableName(), tempBaseDataList);
				}else{
					List<BaseDataInfo> tempList = data.get(baseData.getTableName());
					if(null == tempList){
						tempList = new ArrayList<BaseDataInfo>();
					}
					tempList.add(baseData);
					data.put(baseData.getTableName(), tempList);
				}
				
			}
		}
		log.info("初始化字典数据结束--------");
	}
	
	/**
	 * 根据表名和key值取value
	 * @param tableName
	 * @param key
	 */
	public static String getStringColumnKey(String tableName,String key,String defaut){
		if(null == key || "".equals(key.trim())){
			return defaut;
		}
		if(null == tableName || "".equals(tableName.trim())){
			return defaut;
		}
		List<BaseDataInfo> list = data.get(tableName);
		for(BaseDataInfo info:list){
			if(key.equalsIgnoreCase(info.getColumnKey())){
				return info.getColumnvalue();
			}
		}
		return defaut;
	}
	
	/**
	 * 查询列表
	 * @param tableName
	 * @return
	 */
	public static List<BaseDataInfo> queryBaseDateByTableName(String tableName){
		return data.get(tableName);
	}
	
	/**
	 * 获取threadmetrix的中文风险项信息
	 */
	public static String getThreadMetrixCNInfo(String key){
		if(key!=null && !("".equals(key))){
			String setKey = key.replace(" ", "_");
			String val = threadMetrix.get(setKey);
			return val!=null?val:"未知风险项";
		}
		return "未知风险项";
	}
	
}
