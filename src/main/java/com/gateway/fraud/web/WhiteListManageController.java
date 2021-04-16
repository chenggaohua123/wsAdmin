package com.gateway.fraud.web;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.APIException;
import com.gateway.fraud.model.WhiteListInfo;
import com.gateway.fraud.service.WhiteListManageService;
import com.gateway.loginmgr.model.UserInfo;
import com.gateway.merchantmgr.model.MerchantInfo;
import com.gateway.merchantmgr.service.MerchantMgrService;

@Controller
@RequestMapping(value="/whiteListMagage")
public class WhiteListManageController extends BaseController{
	
	@Autowired(required=true)
	WhiteListManageService whiteListManageService;
	
	@Autowired
	private MerchantMgrService merchantMgrService;
	
	/**
	 *跳转白名单查询 
	 */
	@RequestMapping(value="/queryWhiteList")
	public String queryWhiteList(){
		PageInfo<WhiteListInfo> page=whiteListManageService.queryWhiteList(getCriteria());
		getRequest().setAttribute("param", getCriteria().getCondition());
		getRequest().setAttribute("result", page);
		return"fraud/rule/white_list";
	}
	
	/**
	 * 
	 *跳转白名单添加页面
	 */
	@RequestMapping(value="/goAddWhiteList")
	public String goAddWhiteList(){
		
		return "fraud/rule/add_white_list";
	}
	
	/**
	 * 
	 *白名单添加 
	 */
	@RequestMapping(value="/addWhiteList")
	public ModelAndView addWhiteList(WhiteListInfo whiteListInfo){
		whiteListInfo.setCreatedBy(getLogAccount().getRealName());
		whiteListInfo.setLastUpdateBy(getLogAccount().getRealName());
		int i=whiteListManageService.addWhiteList(whiteListInfo);
		if(i>0){
			return ajaxDoneSuccess("添加成功");
		}else{
			return ajaxDoneError("添加失败");
		}
	}
	
	/**
	 * 
	 *白名单删除 
	 */
	@RequestMapping(value="/delWhiteList")
	public ModelAndView delWhiteList(int id){
		int i=whiteListManageService.delWhiteList(id);
		if(i>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
		}
	} 
	/***
	 * 
	 *跳转修改 
	 * 
	 */
	@RequestMapping("/goUpdateWhiteList")
	public String goUpdateWhiteList(int id){
		WhiteListInfo whiteListInfo =whiteListManageService.queryWhiteListById(id);
		getRequest().setAttribute("whiteListInfo", whiteListInfo);
		return "fraud/rule/updateWhiteList";
	}
	/**
	 * 
	 *状态修改 
	 */
	@RequestMapping("/updateWhiteList")
	public ModelAndView updateWhiteList(WhiteListInfo whiteListInfo){
		whiteListInfo.setLastUpdateBy(getLogAccount().getRealName());
		int i=whiteListManageService.updateWhiteList(whiteListInfo);
		if(i>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	
	/**
	 * 跳转白名单批量上传界面
	 */
	@RequestMapping(value="/showWhiteUpload")
	public String showWhiteUpload(){
		
		return "fraud/rule/uploadWhiteRiskFile";
	}
	
	/**
	 * 下载白名单批量商品模板
	 * @throws IOException 
	 */
	@RequestMapping(value="/downloadWhiteFile")
	public void downloadWhiteFile(HttpServletResponse resp) throws IOException{
		resp.setContentType("application/zip");
		resp.addHeader("Content-Disposition", "attachment;filename=addwhiterisk.txt");
		OutputStream os = resp.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(os);
		List<String> list = new ArrayList<String>();
		list.add("modeltype:merNo,terNO,blackType,type,blackText,enableFlag,remark\r\n");
		list.add("商户号,终端号,规则类型,处理方式(0:例外,1:只接受),规则信息,状态(1:开通,0:关闭),备注(可选)\r\n");
		list.add("2561,95,IP,0,192.11.25.54,0\r\n");
		list.add("2561,95,cardNo,1,4444444444444444,1,\r\n");
		list.add("2561,95,email,1,123456@163.com,1,邮箱白名单\r\n");
		for(String info : list){
			bos.write(info.getBytes());
		}
		bos.flush();
		bos.close();
	}
	
	/**
	 * 上传白名单信息
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/uploadWhiteFile")
	public ModelAndView uploadWhiteFile(DefaultMultipartHttpServletRequest request) throws UnsupportedEncodingException, IOException{
		List<WhiteListInfo> list = new ArrayList<WhiteListInfo>();
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		StringBuffer sb = new StringBuffer();
		if (null != files) {
			log.info("上传了" + files.size() + "个文件。");
			for (MultipartFile file : files) {
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"));//
				// 构造一个BufferedReader类来读取文件
				String line = br.readLine();//第一列标题
				line = br.readLine();//第二列标题
				line = br.readLine();//读取第3列
				int lineCount = 3;//从第二行开始读
				while (null != line) {
					log.info("line:" + line);
					if(line==null||"".equals(line.replaceAll("\\s", ""))){
						line = br.readLine();
						continue;
					}
					String[] fileds = line.split(",");
					if (fileds.length==7 || fileds.length==6) {
						int index = 0;
						try{
							WhiteListInfo white = new WhiteListInfo();
							white.setId(index+1);
							white.setMerNo(fileds[index++]);
							white.setTerNo(fileds[index++]);
							white.setBlackType(fileds[index++]);
							white.setType(Integer.parseInt(fileds[index++]));
							white.setBlackText(fileds[index++]);
							Integer enableFalag = new Integer(0);
							enableFalag = Integer.parseInt(fileds[index++]);
							white.setEnableFlag(enableFalag.intValue()+"");
							if (fileds.length==7) {
								white.setRemark(fileds[index]!=null?fileds[index]:"");
								index++;
							}
							//验证商户是否存在
							MerchantInfo mer = merchantMgrService.queryMerchantInfoByMerNo(white.getMerNo());
							if(mer==null){
								sb.append("第"+lineCount+ "行数据：" +line+"; 商户号:"+white.getMerNo()+"不存在<br/>");
							}
							int flag = 0;
							if(mer!=null){
								//验证终端号是否存在
								flag = merchantMgrService.queryMerchantInfoByMerNo(white.getMerNo(), white.getTerNo());
								if(!(flag>0)){
									sb.append("第"+lineCount+ "行数据：" +line+"; 商户号:"+white.getMerNo()+"的终端号:"+white.getTerNo()+"不存在<br/>");
								}
							}
							//验证规则信息是否重复
							int whitecount = whiteListManageService.queryWhiteDupInfoCount(white);
							if(whitecount>0){
								sb.append("第"+lineCount+ "行数据：" +line+"; 规则信息:"+white.getBlackText()+"重复<br/>");
							}
							
							if(mer!=null && flag>0 && !(whitecount>0)){
								list.add(white);
							}
						}catch (Exception e){
							sb.append("第"+lineCount+ "行数据：" +line+";数据错误<br>");
						}
					}else{
						sb.append("第"+lineCount+ "行数据：" +line+";数据错误<br>");
					}
					line = br.readLine();
					lineCount++;
				}
			}

		}
		getRequest().getSession().setAttribute("whiteList", list);
		getRequest().getSession().setAttribute("errorInfo", sb.toString());
		log.info("错误信息："+sb.toString());
		return ajaxDoneSuccess("上传成功。返回信息");
	}
	
	/**
	 * 显示上传白名单信息
	 */
	@RequestMapping(value="/showUploadWhiteRiskInfo")
	public String showUploadWhiteRiskInfo(){
		
		return "fraud/rule/showUploadWhiteRiskListInfo";
	}
	
	/**
	 * 添加信息
	 */
	@RequestMapping(value="/addWhiteRiskInfos")
	public ModelAndView addWhiteRiskInfo(String[] info){
		UserInfo user = getLogAccount();
		StringBuffer sb = new StringBuffer();
		int count = 0;
		if(info!=null && info.length>0){
			for(int i=0; i<info.length; i++){
				String str = info[i];
				if(str!=null && !("".equals(str))){
					String[] infos = str.split(",");
					if(infos.length==8 || infos.length==7){
						int index = 0;
						try{
							WhiteListInfo white = new WhiteListInfo();
							white.setId(Integer.parseInt(infos[index++]));
							white.setMerNo(infos[index++]);
							white.setTerNo(infos[index++]);
							white.setBlackType(infos[index++]);
							white.setType(Integer.parseInt(infos[index++]));
							white.setBlackText(infos[index++]);
							Integer enableFalag = new Integer(0);
								enableFalag = Integer.parseInt(infos[index++]);
							white.setEnableFlag(enableFalag.intValue()+"");
							if(infos.length==8){
								white.setRemark(infos[index]!=null?infos[index]:"");
								index++;
							}
							white.setCreatedBy(user.getRealName());
							white.setLastUpdateBy(user.getRealName());
							int a = whiteListManageService.addWhiteList(white);
							if(a>0){
								count++;
								sb.append(",").append(white.getId());
							}
						}catch (Exception e){
							e.printStackTrace();
							continue;
						}
					}
				}
			}
		}
		return ajaxDoneSuccess("添加成功"+count+"条数据"+sb.toString());
	}
	
	/**
	 * 批量删除白名单信息
	 */
	@RequestMapping(value="/delWhiteInfoList")
	public ModelAndView delWhiteInfoList(String[] ids){
		if(ids==null || !(ids.length>0)){
			return ajaxDoneError("请选择删除信息");
		}
		int a = -1;
		try {
			a = whiteListManageService.deleteWhiteInfoList(ids);
		} catch (APIException e) {
			return ajaxDoneError("删除失败");
		}
		if(a>0){
			return ajaxDoneSuccess("删除成功");
		}else{
			return ajaxDoneError("删除失败");
		}
	}
	
	/**
	 * 导出数据
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	@RequestMapping(value="/exportWhiteInfoList")
	public void exportWhiteInfoList(HttpServletResponse resp) throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "whiteList.xls" );
		List<WhiteListInfo> list = whiteListManageService.queryExportWhiteList(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("商户结算列表", 0);
		String[] headerName = {"序号","商户号","终端号","规则类型","处理方式","规则信息","状态","备注"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			WhiteListInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getId()+""));
			sheet.addCell( new Label(col++, row, info.getMerNo()!=null?info.getMerNo():""));
			sheet.addCell( new Label(col++, row, info.getTerNo()!=null?info.getTerNo():""));
			sheet.addCell( new Label(col++, row, info.getBlackType()!=null?info.getBlackType():""));
			sheet.addCell( new Label(col++, row, ("1".equals(info.getType())?"只接受":"例外")));
			sheet.addCell( new Label(col++, row, info.getBlackText()!=null?info.getBlackText():""));
			sheet.addCell( new Label(col++, row, ("1".equals(info.getEnableFlag())?"开通":"关闭")));
			sheet.addCell( new Label(col++, row, info.getRemark()!=null?info.getRemark():""));
		}
		book.write();
		book.close();
	}
	
}
