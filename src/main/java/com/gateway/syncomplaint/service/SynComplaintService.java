package com.gateway.syncomplaint.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.gateway.syncomplaint.utils.URLHttpClient;

@Component(value="synComplaintService")
public class SynComplaintService {

	@Resource
	private TaskExecutor taskExecutor;
	
	@Resource
	private URLHttpClient urlHttpClient;
	
	private static Properties properties = new Properties();

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
	public URLHttpClient getUrlHttpClient() {
		return urlHttpClient;
	}

	public void setUrlHttpClient(URLHttpClient urlHttpClient) {
		this.urlHttpClient = urlHttpClient;
	}

	static{
		try {
			properties.load(new FileInputStream(SynComplaintService.class.getClassLoader().getResource("").getPath()+"dowloadfile.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void downloadFile(final HttpServletRequest request,  
			final  HttpServletResponse response, final String storeName, final String url, final Object obj){
		try {
			urlHttpClient.downloadFile(request, response, storeName, url, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getDownloadPath(String key){
		return (String) properties.get(key);
	}
	
}
