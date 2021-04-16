package com.gateway.api.rest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gateway.api.mapper.BaseMgrMapper;
import com.gateway.api.model.UserForOther;
import com.gateway.common.adapter.web.BaseDataListener;


@Controller
@RequestMapping(value="/api")
public class DateUploadController extends BaseDataListener {
	@Autowired
	private BaseMgrMapper baseMgeMapper;
	@RequestMapping("/download")
	public HttpServletResponse download(String date, HttpServletResponse response) {
        try {
        	String erro=null;
        	if(date == null || "".equals(date)){
        		erro="Date can't be empty";
        	}
            // path是指欲下载的文件的路径。
        	String  path="/usr/local/temp/transInfo"+date+".csv";
            File file = new File(path);
            if(!file.exists()){
            	erro="file does not exist";
            }
            if( null != erro){
            	response.reset();
                // 设置response的Header
                response.addHeader("Content-Disposition", "attachment;filename=erro.txt");
                response.addHeader("Content-Length", "" + erro.length());
                response.setContentType("application/octet-stream");
                OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            	toClient.write(erro.getBytes());
            	toClient.flush();
                toClient.close();
                return response;
            }
            // 取得文件名。
            String filename = file.getName();
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
         
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }
	
	@ResponseBody
	@RequestMapping("/login")
	public UserForOther login(UserForOther info){
		if(info == null ){
			info=new UserForOther();
			info.setErroCode("0001");
			info.setErroMsg("信息不能为空");
			return info;
		}
		if(info.getUserName() ==null || info.getPassword() ==null ){
			info.setErroCode("0002");
			info.setErroMsg("用户名和密码不能为空");
			return info;
		}
		info=baseMgeMapper.queryUserInfo(info);
		if(info != null){
			info.setErroCode("0000");
			info.setErroMsg("Success");
		}else{
			info=new UserForOther();
			info.setErroCode("0003");
			info.setErroMsg("用户名或密码不正确");
			return info;
		}
		return info;
	} 
	
}
