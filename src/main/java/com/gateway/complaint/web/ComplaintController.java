package com.gateway.complaint.web;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.BaseDataListener;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.utils.Tools;
import com.gateway.complaint.model.Complaint;
import com.gateway.complaint.model.ComplaintResult;
import com.gateway.complaint.model.ComplaintType;
import com.gateway.complaint.model.WebsiteCardHolderInfo;
import com.gateway.complaint.model.WebsiteComplaintCardHolderInfo;
import com.gateway.complaint.model.WebsiteComplaintInfo;
import com.gateway.complaint.service.ComplaintService;
import com.gateway.transmgr.model.TransDetailInfo;
import com.gateway.transmgr.service.TransMgrService;

@Controller
@RequestMapping("/complaint")
public class ComplaintController extends BaseController {
	@Autowired
	private ComplaintService complaintService;
	@Resource
	private TransMgrService transMgrService;
	
	
	/**
	 * 查询调查单列表
	 * */
	@RequestMapping("/listComplaintInfo")
	public String listComplaintInfo(){
		Criteria criteria=getCriteria();
		criteria.getCondition().put("type", "0");
		PageInfo<Complaint> page=complaintService.queryListComplaintInfo(criteria);
		getRequest().setAttribute("page", page);
		return "complaint/listComplaintInfo";
	}
	/**
	 * 跳转到添加调查单页面
	 * */
	@RequestMapping("/goAddComplaintInfo")
	public String goAddComplaintInfo(){
		return "complaint/addComplaintInfo";
	}
	
	/** 添加调查单 */
	@RequestMapping("/addComplaintInfo")
	public ModelAndView addComplaintInfo(Complaint info){
		info.setCreatedBy(getLogAccount().getRealName());
		String str = complaintService.addComplaintInfo(info);
		if(StringUtils.isEmpty(str)){
			return ajaxDoneSuccess("添加成功");
		}
		return ajaxDoneError(str);
	}
	/** 添加拒付单 */
	@RequestMapping("/addComplaintInfo1")
	public ModelAndView addComplaintInfo1(Complaint info){
		info.setCreatedBy(getLogAccount().getRealName());
		String str = complaintService.addComplaintInfo(info);
		if(StringUtils.isEmpty(str)){
			return ajaxDoneSuccess("添加成功");
		}
		return ajaxDoneError(str);
	}
	/** 添加投诉单 */
	@RequestMapping("/addComplaintInfo2")
	public ModelAndView addComplaintInfo2(Complaint info){
		info.setCreatedBy(getLogAccount().getRealName());
		String str = complaintService.addComplaintInfo(info);
		if(StringUtils.isEmpty(str)){
			return ajaxDoneSuccess("添加成功");
		}
		return ajaxDoneError(str);
	}
	
	/** 去批量添加调查单页面 */
	@RequestMapping("/goBatchAddComplaintInfo")
	public String goBatchAddComplaintInfo(){
		return "complaint/batchAddComplaintInfo";
	}
	
	/** 批量修改已受理调查单 */
	@RequestMapping("/updateComplaintInfo")
	public ModelAndView updateComplaintInfo(String[] ids,int status){
		int count = complaintService.updateComplaintInfo(ids,status,getLogAccount().getRealName());
		return ajaxDoneSuccess("批量受理调查单总条数为："+ ids.length +" ,操作成功条数为： " + count+" 。");
	}
	
	/** 将调查单处理成已退款 */
	public ModelAndView updateRefundComplaintInfo(Complaint info){
		return null;
	}
	
	/** 导出调查单列表 */
	@RequestMapping(value = "/exportComplaintTransInfo")
	public void exportComplaintTransInfo(HttpServletResponse resp) throws Exception, IOException,
			RowsExceededException, WriteException {
		Criteria criteria=getCriteria();
		criteria.getCondition().put("type", "0");
		List<Complaint> complaint=complaintService.queryListComplaintInfoList(criteria);
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "transLogList.xls");
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("拒付列表", 0);
		String[] headerName = { "商户号","流水号","订单号","网站","交易金额","交易时间","调查单通知时间","调查原因","调查单处理截止时间","调查单状态","创建人","创建时间",
				"处理人","处理时间","支付通道","伪冒状态","拒付状态","拒付金额","退款状态","退款金额","冻结状态","冻结金额","订单来源","所属终端号","通道英文账单名称",
				"前六后四卡号","货物信息","姓名","邮箱","电话","IP","支付国家","收货国家","收货省/ 州","收货地址","邮编","货运方式","货运单号","账单国家","账单省/州","账单地址" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		if (!org.springframework.util.StringUtils.isEmpty(complaint)) {
			for (int row = 1; row <= complaint.size(); row++) {
				int col = 0;
				Complaint info = complaint.get(row - 1);
				TransDetailInfo transInfo = transMgrService.queryTransInfo(info.getTradeNo());
				sheet.addCell( new Label(col++, row, transInfo.getMerNo()));//商户号
				sheet.addCell( new Label(col++, row, transInfo.getTradeNo()));//交易流水号
				sheet.addCell( new Label(col++, row, transInfo.getOrderNo()));//订单号
				sheet.addCell( new Label(col++, row, transInfo.getPayWebSite()));//网站
				sheet.addCell( new Label(col++, row, transInfo.getMerBusCurrency() + " " + transInfo.getMerTransAmount()));//交易金额
				sheet.addCell( new Label(col++, row, transInfo.getTransDate()));//交易时间
				sheet.addCell( new Label(col++, row, info.getComplaintDate()));//调查单通知时间
				sheet.addCell( new Label(col++, row, info.getComplaintTypeValue()));//调查原因
				sheet.addCell( new Label(col++, row, info.getDeadline()));//拒付处理截止时间
				sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("COMPLAINT_STATUS_0",info.getStatus()+"","未知类型")));//拒付处理状态
				sheet.addCell( new Label(col++, row, info.getCreatedBy()));//创建人
				sheet.addCell( new Label(col++, row, info.getCreatedDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getCreatedDate()):""));//创建时间
				sheet.addCell( new Label(col++, row, info.getLastUpdateBy()));//处理人
				sheet.addCell( new Label(col++, row, info.getLastUpdateDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getLastUpdateDate()):""));//处理时间
				sheet.addCell( new Label(col++, row, transInfo.getCurrencyName()));//支付通道
				sheet.addCell( new Label(col++, row, info.getIsFake()>0?"是":"非"));//伪冒状态
				sheet.addCell( new Label(col++, row, transInfo.getDishonorStatus()));//拒付状态
				sheet.addCell( new Label(col++, row, transInfo.getDishonorAmount()));//拒付金额
				sheet.addCell( new Label(col++, row, transInfo.getRefundStatus()));//退款状态
				sheet.addCell( new Label(col++, row, transInfo.getRefundAmount()));//退款金额
				sheet.addCell( new Label(col++, row, transInfo.getFrozenStatus()));//冻结状态
				sheet.addCell( new Label(col++, row, transInfo.getFrozenAmount()));//冻结金额
				sheet.addCell( new Label(col++, row, Tools.parseWebInfoToResourceType(transInfo.getWebInfo())));//订单来源
				sheet.addCell( new Label(col++, row, transInfo.getTerNo()));//终端号
				sheet.addCell( new Label(col++, row, transInfo.getAcquirer()));//通道英文账单名称
				sheet.addCell( new Label(col++, row, transInfo.getSixAndFourCardNo()));//前六后四卡号
				if(!org.springframework.util.StringUtils.isEmpty(transInfo.getGoodsInfoByte())){//货物信息
					sheet.addCell(new Label(col++, row, new String(transInfo.getGoodsInfoByte(),"utf-8")));
					System.out.println("===="+new String(transInfo.getGoodsInfoByte(),"utf-8"));
				}else{
					sheet.addCell(new Label(col++, row, ""));
				}
				sheet.addCell( new Label(col++, row, transInfo.getCardFullName()));//姓名
				sheet.addCell( new Label(col++, row, transInfo.getEmail()));//邮箱
				sheet.addCell( new Label(col++, row, transInfo.getCardFullPhone()));//电话
				sheet.addCell( new Label(col++, row, transInfo.getIpAddress()));//IP
				sheet.addCell( new Label(col++, row, transInfo.getCardCountry()));//支付国家
				sheet.addCell( new Label(col++, row, transInfo.getGrCountry()));//收货国家
				sheet.addCell( new Label(col++, row, transInfo.getGrState()));//收货省/ 州
				sheet.addCell( new Label(col++, row, transInfo.getGrAddress()));//收货地址
				sheet.addCell( new Label(col++, row, transInfo.getGrZipCode()));//邮编
				sheet.addCell( new Label(col++, row, transInfo.getIogistics()));//货运方式
				sheet.addCell( new Label(col++, row, transInfo.getTrackNo()));//货运单号
				sheet.addCell( new Label(col++, row, transInfo.getCardCountry()));//账单国家
				sheet.addCell( new Label(col++, row, transInfo.getCardState()));//账单省/州
				sheet.addCell( new Label(col++, row, transInfo.getCardAddress()));//账单地址
			}
		}
		book.write();
		book.close();
	}
	
	/**
	 * 投诉类型管理列表
	 * */
	@RequestMapping("/listComplaintTypeInfo")
	public String listComplaintTypeInfo(){
		PageInfo<ComplaintType> page=complaintService.queryListComplaintTypeInfo(getCriteria());
		getRequest().setAttribute("page", page);
		return "complaint/listComplaintTypeInfo";
	}
	
	@RequestMapping("/complaintListBack")
	public String complaintListBack(String type){
		Criteria criteria=getCriteria();
		criteria.getCondition().put("enabled", "1");
		criteria.getCondition().put("type", type);
		PageInfo<ComplaintType> page=complaintService.queryListComplaintTypeInfo(criteria);
		getRequest().setAttribute("page", page);
		return "complaint/complaintListBack";
	}
	
	@RequestMapping("/goAddComplaintTypeInfo")
	public String goAddComplaintTypeInfo(){
		return "complaint/addComplaintTypeInfo";
	}
	
	/**
	 * 添加投诉类型
	 * */
	@RequestMapping("/addComplaintTypeInfo")
	public ModelAndView addComplaintTypeInfo(ComplaintType info){
		info.setCreatedBy(getLogAccount().getRealName());
		return complaintService.addComplaintTypeInfo(info)>0?ajaxDoneSuccess("添加成功"):ajaxDoneError("添加失败");
	}
	
	/** 去修改投诉类型页面 */
	@RequestMapping("/goUpdateComplaintTypeInfo")
	public String goUpdateComplaintTypeInfo(String id){
		ComplaintType info = complaintService.queryComplaintTypeInfoId(id);
		getRequest().setAttribute("info", info);
		return "complaint/updateComplaintTypeInfo";
	}
	
	/** 修改投诉类型 */
	@RequestMapping("/updateComplaintTypeInfo")
	public ModelAndView updateComplaintTypeInfo(ComplaintType info){
		info.setLastUpdateBy(getLogAccount().getRealName());
		return complaintService.updateComplaintTypeInfo(info)>0?ajaxDoneSuccess("添加成功"):ajaxDoneError("添加失败");
	}
	
	/**
	 * 查询拒付单列表
	 * */
	@RequestMapping("/listDishonorInfo")
	public String listDishonorInfo(){
		Criteria criteria=getCriteria();
		criteria.getCondition().put("type", "1");
		if(getRequest().getMethod().equalsIgnoreCase("get")){
			criteria.getCondition().put("isMerchantSee", "0");
		}
		getRequest().setAttribute("form", criteria.getCondition());
		PageInfo<Complaint> page=complaintService.queryListComplaintInfo(criteria);
		getRequest().setAttribute("page", page);
		return "complaint/listDishonorInfo";
	}
	
	@RequestMapping(value = "/exportDishonorTransInfo")
	public void exportDishonorTransInfo(HttpServletResponse resp) throws Exception, IOException,
			RowsExceededException, WriteException {
		Criteria criteria=getCriteria();
		criteria.getCondition().put("type", "1");
		List<Complaint> complaint=complaintService.queryListComplaintInfoList(criteria);
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "transLogList.xls");
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("拒付列表", 0);
		String[] headerName = { "商户号","流水号","订单号","网站","交易金额","拒付金额","交易状态","交易时间","拒付通知时间","拒付原因","拒付处理截止时间","CPD日期","拒付处理状态",
				"创建人","创建时间","处理人","处理时间","支付通道","伪冒状态","拒付状态","拒付金额","退款状态","退款金额","冻结状态","冻结金额","订单来源","所属终端号",
				"通道英文账单名称","前六后四卡号","货物信息","姓名","邮箱","电话","IP","支付国家","收货国家","收货省/ 州","收货地址","邮编","货运方式","货运单号",
				"账单国家","账单省/州","账单地址" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		if (!org.springframework.util.StringUtils.isEmpty(complaint)) {
			for (int row = 1; row <= complaint.size(); row++) {
				int col = 0;
				Complaint info = complaint.get(row - 1);
				TransDetailInfo transInfo = transMgrService.queryTransInfo(info.getTradeNo());
				sheet.addCell( new Label(col++, row, transInfo.getMerNo()));//商户号
				sheet.addCell( new Label(col++, row, transInfo.getTradeNo()));//交易流水号
				sheet.addCell( new Label(col++, row, transInfo.getOrderNo()));//订单号
				sheet.addCell( new Label(col++, row, transInfo.getPayWebSite()));//网站
				sheet.addCell( new Label(col++, row, transInfo.getMerBusCurrency() + " " + transInfo.getMerTransAmount()));//交易金额
				if("0.00".equals(info.getAmount())){
					sheet.addCell( new Label(col++, row, transInfo.getMerBusCurrency() + " " + transInfo.getMerTransAmount()));//交易金额
				}else{
					sheet.addCell( new Label(col++, row, transInfo.getMerBusCurrency() + " " + info.getAmount()));//交易金额
				}
				sheet.addCell( new Label(col++, row, "00".equals(transInfo.getRespCode())?"支付成功":"支付失败"));//交易状态
				sheet.addCell( new Label(col++, row, transInfo.getTransDate()));//交易时间
				sheet.addCell( new Label(col++, row, info.getComplaintDate()));//拒付通知时间
				sheet.addCell( new Label(col++, row, info.getComplaintTypeValue()));//拒付原因
				sheet.addCell( new Label(col++, row, info.getDeadline()));//拒付处理截止时间
				sheet.addCell( new Label(col++, row, info.getCPDDate()));//CPD日期
				sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("COMPLAINT_STATUS_1",info.getStatus()+"","未知类型")));//拒付处理状态
				sheet.addCell( new Label(col++, row, info.getCreatedBy()));//创建人
				sheet.addCell( new Label(col++, row, info.getCreatedDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getCreatedDate()):""));//创建时间
				sheet.addCell( new Label(col++, row, info.getLastUpdateBy()));//处理人
				sheet.addCell( new Label(col++, row, info.getLastUpdateDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getLastUpdateDate()):""));//处理时间
				sheet.addCell( new Label(col++, row, transInfo.getCurrencyName()));//支付通道
				sheet.addCell( new Label(col++, row, info.getIsFake()>0?"是":"非"));//伪冒状态
				sheet.addCell( new Label(col++, row, transInfo.getDishonorStatus()));//拒付状态
				sheet.addCell( new Label(col++, row, transInfo.getDishonorAmount()));//拒付金额
				sheet.addCell( new Label(col++, row, transInfo.getRefundStatus()));//退款状态
				sheet.addCell( new Label(col++, row, transInfo.getRefundAmount()));//退款金额
				sheet.addCell( new Label(col++, row, transInfo.getFrozenStatus()));//冻结状态
				sheet.addCell( new Label(col++, row, transInfo.getFrozenAmount()));//冻结金额
				sheet.addCell( new Label(col++, row, Tools.parseWebInfoToResourceType(transInfo.getWebInfo())));//订单来源
				sheet.addCell( new Label(col++, row, transInfo.getTerNo()));//终端号
				sheet.addCell( new Label(col++, row, transInfo.getAcquirer()));//通道英文账单名称
				sheet.addCell( new Label(col++, row, transInfo.getSixAndFourCardNo()));//前六后四卡号
				if(!org.springframework.util.StringUtils.isEmpty(transInfo.getGoodsInfoByte())){//货物信息
					sheet.addCell(new Label(col++, row, new String(transInfo.getGoodsInfoByte(),"utf-8")));
					System.out.println("===="+new String(transInfo.getGoodsInfoByte(),"utf-8"));
				}else{
					sheet.addCell(new Label(col++, row, ""));
				}
				sheet.addCell( new Label(col++, row, transInfo.getCardFullName()));//姓名
				sheet.addCell( new Label(col++, row, transInfo.getEmail()));//邮箱
				sheet.addCell( new Label(col++, row, transInfo.getCardFullPhone()));//电话
				sheet.addCell( new Label(col++, row, transInfo.getIpAddress()));//IP
				sheet.addCell( new Label(col++, row, transInfo.getCardCountry()));//支付国家
				sheet.addCell( new Label(col++, row, transInfo.getGrCountry()));//收货国家
				sheet.addCell( new Label(col++, row, transInfo.getGrState()));//收货省/ 州
				sheet.addCell( new Label(col++, row, transInfo.getGrAddress()));//收货地址
				sheet.addCell( new Label(col++, row, transInfo.getGrZipCode()));//邮编
				sheet.addCell( new Label(col++, row, transInfo.getIogistics()));//货运方式
				sheet.addCell( new Label(col++, row, transInfo.getTrackNo()));//货运单号
				sheet.addCell( new Label(col++, row, transInfo.getCardCountry()));//账单国家
				sheet.addCell( new Label(col++, row, transInfo.getCardState()));//账单省/州
				sheet.addCell( new Label(col++, row, transInfo.getCardAddress()));//账单地址
			}
		}
		book.write();
		book.close();
	}
	
	/** 去添加拒付单页面 */
	@RequestMapping("/goAddDishonorInfo")
	public String goAddDishonorInfo(){
		return "complaint/addDishonorInfo";
	}
	
	/** 去批量添加拒付单页面 */
	@RequestMapping("/goBatchAddDishonorInfo")
	public String goBatchAddDishonorInfo(){
		return "complaint/batchAddDishonorInfo";
	}
	
	/** 批量添加拒付单","调查单","投诉单 */
	@RequestMapping("/batchAddDishonorInfo")
	public ModelAndView batchAddDishonorInfo(DefaultMultipartHttpServletRequest request,HttpServletResponse res ,int type) throws Exception{
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		res.setContentType("text/html");
		int count=0;
		int total=0;
		String str=null;
		if(null != files){
			log.info("上传了"+files.size()+"个文件。");
			for(MultipartFile file :files){
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));//构造一个BufferedReader类来读取文件
				String line = br.readLine();
				line = br.readLine();
				while(null != line){
					log.info("line:"+line);
					String [] fields = line.split(",");
					if(fields.length == 8 && type ==  1 ){//拒付单
						total++;
						int index = 0;
						Complaint info=new Complaint();
						info.setType(1);
						info.setTradeNo(fields[index++]);
						info.setComplaintDate(fields[index++]);
						info.setComplaintType(fields[index++]);
						info.setDeadline(fields[index++]);
						info.setCPDDate(fields[index++]);
						info.setIsMerchantSee(fields[index++]);
						info.setIsSp(fields[index++]);
						info.setStatus("0");
						info.setAmount(fields[index++]);
						int y = complaintService.countComplaintTypeByIdAndType(info);
						if(y == 0){
							log.info("投诉类型错误， 错误line:"+line);
							line = br.readLine();
							continue;
						}
						info.setCreatedBy(getLogAccount().getRealName());
						str = complaintService.addComplaintInfo(info);
						if(StringUtils.isEmpty(str)){
							count++;
						}
					}
					if(fields.length == 4 && type == 0 ){//上传调查单
						total++;
						int index = 0;
						Complaint info=new Complaint();
						info.setType(0);
						info.setTradeNo(fields[index++]);
						info.setComplaintDate(fields[index++]);
						info.setComplaintType(fields[index++]);
						info.setDeadline(fields[index++]);
						info.setStatus("0");
						int y = complaintService.countComplaintTypeByIdAndType(info);
						if(y == 0){
							log.info("投诉类型错误， 错误line:"+line);
							line = br.readLine();
							continue;
						}
						info.setCreatedBy(getLogAccount().getRealName());
						str = complaintService.addComplaintInfo(info);
						if(StringUtils.isEmpty(str)){
							count++;
						}
					}
					if(fields.length == 7 && type == 2 ){//上传投诉单
						total++;
						int index = 0;
						Complaint info=new Complaint();
						info.setType(2);
						info.setTradeNo(fields[index++]);
						info.setComplaintDate(fields[index++]);
						info.setComplaintType(fields[index++]);
						info.setComplaintLevel(fields[index++]);
						info.setStatus(fields[index++]);
						info.setRemark(fields[index++]);
						info.setDeadline(fields[index++]);
						int y = complaintService.countComplaintTypeByIdAndType(info);
						if(y == 0){
							log.info("投诉类型错误， 错误line:"+line);
							line = br.readLine();
							continue;
						}
						info.setCreatedBy(getLogAccount().getRealName());
						str = complaintService.addComplaintInfo(info);
						if(StringUtils.isEmpty(str)){
							count++;
						}
					}
					
					if(fields.length == 2 && type == 3 ){//上传伪冒单
						total++;
						int index = 0;
						Complaint info=new Complaint();
						info.setType(3);
						info.setTradeNo(fields[index++]);
						info.setComplaintDate(fields[index++]);
						info.setStatus("0");
						info.setCreatedBy(getLogAccount().getRealName());
						str = complaintService.addComplaintInfo(info);
						if(StringUtils.isEmpty(str)){
							count++;
						}
					}
					if(!StringUtils.isEmpty(str)){
						return ajaxDoneError(str);
					}
					line = br.readLine();
				}
			}
			
		}
		return ajaxDoneSuccess("上传成功，上传了"+total+"条记录,成功上传了"+count+"条记录，失败了"+(total-count)+"条记录");
	}
	
	/** 下载申诉资料 */
	@RequestMapping("/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			return;
		}
    	Complaint info = complaintService.queryComplaintById(id);
    	if(StringUtils.isEmpty(info) || StringUtils.isEmpty(info.getFilePath())){
    		return;
    	}
    	String filePath = info.getFilePath();
    	String storeName="protestTable.xlsx";
        String contentType = "application/octet-stream";  
        download(request, response, storeName, contentType,filePath); 
//        synComplaintService.downloadFile(request, response, storeName, synComplaintService.getDownloadPath("complaint.url"), info);
    }
	
	//文件下载 主要方法
    private void download(HttpServletRequest request,  
            HttpServletResponse response, String storeName, String contentType,String filePath
           ) throws Exception {  
        request.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
        //获取项目根目录
        String ctxPath = request.getSession().getServletContext()  
                .getRealPath("");
        //获取下载文件路劲
        String downLoadPath="";
        if(null!=filePath){
        	downLoadPath=filePath;
        }else{
        	downLoadPath= ctxPath+"/WEB-INF/download/model/"+ storeName;
        }
        
        //获取文件的长度
        long fileLength = new File(downLoadPath).length();  
        //设置文件输出类型
        response.setContentType("application/octet-stream");  
        response.setHeader("Content-disposition", "attachment; filename="  
                + new String(storeName.getBytes("utf-8"), "ISO8859-1")); 
        //设置输出长度
        response.setHeader("Content-Length", String.valueOf(fileLength));  
        //获取输入流
        bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
        //输出流
        bos = new BufferedOutputStream(response.getOutputStream());  
        byte[] buff = new byte[2048];  
        int bytesRead;  
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
            bos.write(buff, 0, bytesRead);  
        }  
        //关闭流
        bis.close();  
        bos.close();  
    }
	
	/** 批量修改拒付单 */
	@RequestMapping("/updateDishonorInfo")
	public ModelAndView updateDishonorInfo(String[] ids,int status){
		int count = complaintService.updateDishonorInfo(ids,status,getLogAccount().getRealName());
		return ajaxDoneSuccess("批量受理调查单总条数为："+ ids.length +" ,操作成功条数为： " + count+" 。");
	}
	
	/**
	 * 查询投诉单列表
	 * */
	@RequestMapping("/listCardholderInfo")
	public String listCardholderInfo(){
		Criteria criteria=getCriteria();
		criteria.getCondition().put("type", "2");
		PageInfo<Complaint> page=complaintService.queryListComplaintInfo(criteria);
		getRequest().setAttribute("page", page);
		return "complaint/listCardholderInfo";
	}
	
	/** 去添加投诉单页面 */
	@RequestMapping("/goAddCardholderInfo")
	public String goAddCardholderInfo(){
		return "complaint/addCardholderInfo";
	}
	
	/** 去批量上传投诉单页面 */
	@RequestMapping("/goBatchAddCardholderInfo")
	public String goBatchAddCardholderInfo(){
		return "complaint/batchAddCardholderInfo";
	}
	
	/** 批量导出投诉单数据 */
	@RequestMapping(value = "/exportCardholderTransInfo")
	public void exportCardholderTransInfo(HttpServletResponse resp) throws Exception, IOException,
			RowsExceededException, WriteException {
		Criteria criteria=getCriteria();
		criteria.getCondition().put("type", "2");
		List<Complaint> complaint=complaintService.queryListComplaintInfoList(criteria);
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "transList.xls");
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("投诉列表", 0);
		String[] headerName = { "商户号","流水单","订单号","网站","交易金额","交易时间","投诉日期","投诉问题","投诉级别","投诉处理状态","投诉补充说明","投诉处理截止日期","创建人",
				"创建时间","处理人","处理时间","商户处理结果","支付通道","伪冒状态","拒付状态","拒付金额","退款状态","退款金额","冻结状态","冻结金额","订单来源","所属终端号",
				"通道英文账单名称","前六后四卡号","货物信息","姓名","邮箱","电话","IP","支付国家","收货国家","收货省/ 州","收货地址","邮编","货运方式","货运单号",
				"账单国家","账单省/州","账单地址" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		if (!org.springframework.util.StringUtils.isEmpty(complaint)) {
			for (int row = 1; row <= complaint.size(); row++) {
				int col = 0;
				Complaint info = complaint.get(row - 1);
				TransDetailInfo transInfo = transMgrService.queryTransInfo(info.getTradeNo());
				ComplaintResult result = new ComplaintResult();
				result.setComplaintId(info.getId()+"");
				List<ComplaintResult> resultList = complaintService.queryComplaintResultInfo(result);
				String str = "\"";
				if(!StringUtils.isEmpty(resultList)){
					for(ComplaintResult li:resultList){
						str += "处理人："+li.getCreateBy()+","+"处理时间:"+li.getCreateDate() +"," + "处理结果:"+ li.getResult()+",";
						str += "\n";
					}
				}
				str += "\"";
				sheet.addCell( new Label(col++, row, transInfo.getMerNo()));//商户号
				sheet.addCell( new Label(col++, row, transInfo.getTradeNo()));//交易流水号
				sheet.addCell( new Label(col++, row, transInfo.getOrderNo()));//订单号
				sheet.addCell( new Label(col++, row, transInfo.getPayWebSite()));//网站
				sheet.addCell( new Label(col++, row, transInfo.getMerBusCurrency() + " " + transInfo.getMerTransAmount()));//交易金额
				sheet.addCell( new Label(col++, row, transInfo.getTransDate()));//交易时间
				sheet.addCell( new Label(col++, row, info.getComplaintDate()));//投诉日期
				sheet.addCell( new Label(col++, row, info.getComplaintTypeValue()));//投诉问题
				sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("complaint_Level",info.getComplaintLevel()+"","未知类型")));//投诉处理状态
				sheet.addCell( new Label(col++, row, BaseDataListener.getStringColumnKey("COMPLAINT_STATUS_2",info.getStatus()+"","未知类型")));//投诉处理状态
				sheet.addCell( new Label(col++, row, info.getRemark()));//投诉补充说明
				sheet.addCell( new Label(col++, row, info.getDeadline()));//投诉处理截止日期
				sheet.addCell( new Label(col++, row, info.getCreatedBy()));//创建人
				sheet.addCell( new Label(col++, row, info.getCreatedDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getCreatedDate()):""));//创建时间
				sheet.addCell( new Label(col++, row, info.getLastUpdateBy()));//处理人
				sheet.addCell( new Label(col++, row, info.getLastUpdateDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getLastUpdateDate()):""));//处理时间
				sheet.addCell( new Label(col++, row, str));//商户处理结果
				sheet.addCell( new Label(col++, row, transInfo.getCurrencyName()));//支付通道
				sheet.addCell( new Label(col++, row, info.getIsFake()>0?"是":"非"));//伪冒状态
				sheet.addCell( new Label(col++, row, transInfo.getDishonorStatus()));//拒付状态
				sheet.addCell( new Label(col++, row, transInfo.getDishonorAmount()));//拒付金额
				sheet.addCell( new Label(col++, row, transInfo.getRefundStatus()));//退款状态
				sheet.addCell( new Label(col++, row, transInfo.getRefundAmount()));//退款金额
				sheet.addCell( new Label(col++, row, transInfo.getFrozenStatus()));//冻结状态
				sheet.addCell( new Label(col++, row, transInfo.getFrozenAmount()));//冻结金额
				sheet.addCell( new Label(col++, row, Tools.parseWebInfoToResourceType(transInfo.getWebInfo())));//订单来源
				sheet.addCell( new Label(col++, row, transInfo.getTerNo()));//终端号
				sheet.addCell( new Label(col++, row, transInfo.getAcquirer()));//通道英文账单名称
				sheet.addCell( new Label(col++, row, transInfo.getSixAndFourCardNo()));//前六后四卡号
				if(!org.springframework.util.StringUtils.isEmpty(transInfo.getGoodsInfoByte())){//货物信息
					sheet.addCell(new Label(col++, row, new String(transInfo.getGoodsInfoByte(),"utf-8")));
					System.out.println("===="+new String(transInfo.getGoodsInfoByte(),"utf-8"));
				}else{
					sheet.addCell(new Label(col++, row, ""));
				}
				sheet.addCell( new Label(col++, row, transInfo.getCardFullName()));//姓名
				sheet.addCell( new Label(col++, row, transInfo.getEmail()));//邮箱
				sheet.addCell( new Label(col++, row, transInfo.getCardFullPhone()));//电话
				sheet.addCell( new Label(col++, row, transInfo.getIpAddress()));//IP
				sheet.addCell( new Label(col++, row, transInfo.getCardCountry()));//支付国家
				sheet.addCell( new Label(col++, row, transInfo.getGrCountry()));//收货国家
				sheet.addCell( new Label(col++, row, transInfo.getGrState()));//收货省/ 州
				sheet.addCell( new Label(col++, row, transInfo.getGrAddress()));//收货地址
				sheet.addCell( new Label(col++, row, transInfo.getGrZipCode()));//邮编
				sheet.addCell( new Label(col++, row, transInfo.getIogistics()));//货运方式
				sheet.addCell( new Label(col++, row, transInfo.getTrackNo()));//货运单号
				sheet.addCell( new Label(col++, row, transInfo.getCardCountry()));//账单国家
				sheet.addCell( new Label(col++, row, transInfo.getCardState()));//账单省/州
				sheet.addCell( new Label(col++, row, transInfo.getCardAddress()));//账单地址
			}
		}
		book.write();
		book.close();
	}
	
	/** 去显示结果页面 */
	@RequestMapping(value="/goShowHandleResult")
	public String goShowHandleResult(ComplaintResult result){
		List<ComplaintResult> resultList = complaintService.queryComplaintResultInfo(result);
		getRequest().setAttribute("resultList", resultList);
		getRequest().setAttribute("systemId", result.getSystemId());
		getRequest().setAttribute("complaintId", result.getComplaintId());
		return "complaint/showHandleResult";
	}
	
	/** 保存处理结果 */
	@ResponseBody
	@RequestMapping(value="/addComplaintResultInfo")
	public ModelAndView  addComplaintResultInfo(ComplaintResult result){
		result.setSystemId("1");//操作系统类型
		
		result.setCreateBy(getLogAccount().getRealName());
		int i = complaintService.addComplaintResultInfo(result);
		if(0 < i){
			List<ComplaintResult> resultList = complaintService.queryComplaintResultInfo(result);
			getRequest().setAttribute("resultList", resultList);
			getRequest().setAttribute("systemId", result.getSystemId());
			getRequest().setAttribute("complaintId", result.getComplaintId());
			return ajaxDoneSuccess("添加成功");
		}
		return ajaxDoneError("添加失败");
	}
	
	/** 批量修改已受理调查单 */
	@RequestMapping("/updateCardholderInfo")
	public ModelAndView updateCardholderInfo(String[] ids,int status){
		int i = complaintService.updateCardholderInfo(ids,status,getLogAccount().getRealName());
		return ajaxDoneSuccess("操作条数为："+ids.length + ",操作成功条数为：" + i);
	}
	/**
	 * 下载批量添加拒付单模板
	 * */

	@RequestMapping("/downloadDisFile")
	public void downloadDisFile(HttpServletResponse resp) {
		resp.setContentType("application/zip");
		resp.addHeader("Content-Disposition", "attachment;filename=DisFile.txt");
		OutputStream outp = null;
		try {
			outp = resp.getOutputStream();
			PrintWriter pw = new PrintWriter(outp);
			pw.print("流水号,拒付通知时间,拒付原因,拒付处理截止时间,CPD日期,商户可见,特殊拒付,拒付金额\r\n");
			pw.print("BR1508100956058644,2019-08-08 08:25:16,1,2019-09-09 08:25:26,2019-09-01 08:25:36,0,1,0.1\r\n");
			pw.print("BR1508100956058645,2019-08-09 08:55:26,1,2019-09-10 08:25:56,2019-09-01 08:25:46,0,1,0.1\r\n");
			pw.flush();
			outp.flush();
		} catch (Exception e) {
			// log.error("", e);
		} finally {
			if (outp != null) {
				try {
					outp.close();
					outp = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 下载批量添加调查单模板
	 * */
	@RequestMapping("/downloadResFile")
	public void downloadResFile(HttpServletResponse resp) {
		resp.setContentType("application/zip");
		resp.addHeader("Content-Disposition", "attachment;filename=ResFile.txt");
		OutputStream outp = null;
		try {

			outp = resp.getOutputStream();
			PrintWriter pw = new PrintWriter(outp);
			pw.print("流水号,调查单通知时间,调查原因,调查单处理截止时间\r\n");
			pw.print("BR1508100956058644,2019-08-08 08:25:16,1,2019-09-09 08:25:26\r\n");
			pw.print("BR1508100956058645,2019-08-09 08:55:26,1,2019-09-10 08:25:56\r\n");
			pw.flush();
			outp.flush();
		} catch (Exception e) {
			// log.error("", e);
		} finally {
			if (outp != null) {
				try {
					outp.close();
					outp = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 下载批量添加投诉单模板
	 * */
	@RequestMapping("/downloadCompFile")
	public void downloadCompFile(HttpServletResponse resp) {
		resp.setContentType("application/zip");
		resp.addHeader("Content-Disposition", "attachment;filename=Compfile.txt");
		OutputStream outp = null;
		try {

			outp = resp.getOutputStream();
			PrintWriter pw = new PrintWriter(outp);
			pw.print("流水号,投诉日期,投诉问题,投诉级别,投诉处理状态,投诉补充说明,投诉处理截止日期\r\n");
			pw.print("BR1508100956058644,2019-08-08 08:25:16,2,0,0,这个是一般级别的未处理投诉单,2019-09-01 08:25:36\r\n");
			pw.print("BR1508100956058645,2019-08-08 08:25:16,2,1,2,这个是经济级别的已退款投诉单,2019-09-01 08:25:36\r\n");
			pw.flush();
			outp.flush();
		} catch (Exception e) {
			// log.error("", e);
		} finally {
			if (outp != null) {
				try {
					outp.close();
					outp = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	@RequestMapping("/goUpdateDisCPDDate")
	public String goUpdateDisCPDDate(String id){
		Complaint info=complaintService.queryDisInfoById(id);
		getRequest().setAttribute("info", info);
		return "complaint/updateDisCPDDate";
	}
	@RequestMapping("/updateDisCPDDate")
	public ModelAndView updateDisCPDDate(Complaint info){
		info.setCPDUpdateBy(getLogAccount().getRealName());
		int i=complaintService.updateDisCPDDateById(info);
		if(i>0){
			return ajaxDoneSuccess("修改成功");
		}else{
			return ajaxDoneError("修改失败");
		}
	}
	/**
	 * 跳转到批量修改CPD日期
	 */
	@RequestMapping("/uploadCPDDateBatchFile")
	public String uploadCPDDateBatchFile(){
		return "complaint/batchUpdateCPDDate/uploadCPDDateBatchFile";
	}
	/** 批量修改CPD日期文件下载上传模板 */
	@RequestMapping(value = "/exportBatchUpdateCPDDateModel")
	public void exportBatchUpdateCPDDateModel(HttpServletResponse resp, int type){
		//type=1 修改CPD时间 type =2 批量修改特殊拒付为非特殊拒付 type=3批量修改为已申诉
		ArrayList<String> strArray = new ArrayList<String> ();
		if(type==1){
			strArray.add("流水号 ,CPD时间");
			strArray.add("BR1506161604560740,2019-12-22 09:34:34");
			strArray.add("BR1506161604560740,2019-12-22 09:34:34");
		}else if(type==2){
			strArray.add("流水号 ");
			strArray.add("BR1506161604560740");
			strArray.add("BR1506161604560740");
		}else if(type==3){
			strArray.add("流水号 ");
			strArray.add("BR1506161604560740");
			strArray.add("BR1506161604560740");
		}else{
			strArray.add("类型错误 ");
		}
		resp.setContentType("application/zip");
		resp.addHeader("Content-Disposition", "attachment;filename=BatchUpdateCPDDateModel.txt");
		OutputStream outp = null;
		try {
			
			outp = resp.getOutputStream();
			PrintWriter pw=new PrintWriter(outp);
			if(type==1){
				pw.print("modelType:CPD\r\n");
			}else if(type==2){
				pw.print("modelType:SP\r\n");
			}else if(type==3){
				pw.print("modelType:COMP\r\n");
			}
			for(String str:strArray){
				pw.print(str + "\r\n");
			}
			pw.flush();
			outp.flush();
		} catch (Exception e) {
			
		} finally {
			if (outp != null) {
				try {
					outp.close();
					outp = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return;
	}
	/**
	 * 批量修改CPD文件解析
	 * @throws Exception
	 */
	@RequestMapping(value ="/resBatchUpdateCPDDateFile")
	public ModelAndView resBatchUpdateCPDDateFile(DefaultMultipartHttpServletRequest request) throws Exception,
			WriteException {
		List<Complaint> list = new ArrayList<Complaint>();
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		StringBuffer sb = new StringBuffer();
		int type=1;
		if (null != files) {
			log.info("上传了" + files.size() + "个文件。");
			Criteria criteria = new Criteria();
			for (MultipartFile file : files) {
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(),"GBK"));//
				// 构造一个BufferedReader类来读取文件
				String line = br.readLine();//第一列标题
				String title=null;
				if(line.contains(":")){
					title=line.split(":")[1];
				}else{
					sb.append("上传模板错误");
				}
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
					Complaint info=new Complaint();
					if ("CPD".equals(title)) {
						type=1;
						int index = 0;
						String tradeNo= fileds[index++];//流水号
						
						int id = complaintService.checkTradeNoInDis(tradeNo);
						if(id>0) {
							String CPDDate=fileds[index++];
							info.setTradeNo(tradeNo);
							info.setId(id);
							info.setCPDDate(CPDDate);
							list.add(info);
						}else{
							sb.append("第"+lineCount+ "行数据：" +line+";流水号不存在<br>");
						}
					}else if ("SP".equals(title)) {
						type=2;
						int index = 0;
						String tradeNo= fileds[index++];//流水号
						
						int id = complaintService.checkTradeNoInDis(tradeNo);
						if(id>0) {
							info.setTradeNo(tradeNo);
							info.setId(id);
							info.setCPDDate("批量修改特殊拒付");
							list.add(info);
						}else{
							sb.append("第"+lineCount+ "行数据：" +line+";流水号不存在<br>");
						}
					}else if ("COMP".equals(title)) {
						type=3;
						int index = 0;
						String tradeNo= fileds[index++];//流水号
						
						int id = complaintService.checkTradeNoInDis(tradeNo);
						if(id>0) {
							info.setTradeNo(tradeNo);
							info.setId(id);
							info.setCPDDate("批量修改为已申诉");
							list.add(info);
						}else{
							sb.append("第"+lineCount+ "行数据：" +line+";流水号不存在<br>");
						}
					}else{
						sb.append("第"+lineCount+ "行数据：" +line+";数据错误<br>");
					}
					line = br.readLine();
					lineCount++;
				}
			}

		}
		getRequest().getSession().setAttribute("batchUpdateDisType", type);
		getRequest().getSession().setAttribute("updateCPDList", list);
		getRequest().getSession().setAttribute("updateCPDErrorInfo", sb + "");
		log.info("错误信息："+sb.toString());
		return ajaxDoneSuccess("上传成功。返回信息");
	}
	@RequestMapping(value ="/goShowUpdateCPDBatchInfo")
	public String goShowUpdateCPDBatchInfo(){
		return "complaint/batchUpdateCPDDate/showUpdateCPDBatchInfo";
	}
	@RequestMapping(value ="/batchUpdateCPDDate")
	public ModelAndView batchUpdateCPDDate(String[] tradeNos){
		int count=0;
		List<Complaint> list =(List<Complaint>) getRequest().getSession().getAttribute("updateCPDList");
		List<Complaint> batchList=new ArrayList<Complaint>();
		for(Complaint info:list){
			for(String tradeNo:tradeNos){
				if(tradeNo.equalsIgnoreCase(info.getTradeNo())){
					batchList.add(info);
					break;
				}
			}
		}
		int type=(Integer) getRequest().getSession().getAttribute("batchUpdateDisType");
		if(type==1){
			for(Complaint c:batchList){
				c.setCPDUpdateBy(getLogAccount().getRealName());
				count+=complaintService.updateDisCPDDateById(c);
			}
		}else if (type==2){
			for(Complaint c:batchList){
				c.setCPDDate(null);
				c.setIsSp("0");
				count+=complaintService.updateDisCPDDateById(c);
			}
		}else if(type==3){
			for(Complaint c:batchList){
				c.setCPDDate(null);
				c.setIsComp("1");
				count+=complaintService.updateDisCPDDateById(c);
			}
		}
		return ajaxDoneSuccess("批量更新了"+count+"条数据");
	}
	
	
	/**
	 * 查询伪冒单列表
	 * */
	@RequestMapping("/getFakeTransList")
	public String getFakeTransList(){
		Criteria criteria=getCriteria();
		criteria.getCondition().put("type", "3");
		PageInfo<Complaint> page=complaintService.queryListComplaintInfo(criteria);
		getRequest().setAttribute("page", page);
		return "complaint/listFakeTrans";
	}
	/**
	 * 添加伪冒单
	 * @return
	 */
	@RequestMapping(value ="/goAddFakeInfo")
	public String goAddFakeInfo(){
		return "complaint/addFakeInfo";
	}
	
	/** 添加伪冒单 */
	@RequestMapping("/addFakeInfo")
	public ModelAndView addFakeInfo(Complaint info){
		info.setCreatedBy(getLogAccount().getRealName());
		String str = complaintService.addComplaintInfo(info);
		if(StringUtils.isEmpty(str)){
			return ajaxDoneSuccess("添加成功");
		}
		return ajaxDoneError(str);
	}
	/**
	 * 下载批量添加伪冒单模板
	 * */
	@RequestMapping("/downloadFakeFile")
	public void downloadFakeFile(HttpServletResponse resp) {
		resp.setContentType("application/zip");
		resp.addHeader("Content-Disposition", "attachment;filename=FakeFile.txt");
		OutputStream outp = null;
		try {

			outp = resp.getOutputStream();
			PrintWriter pw = new PrintWriter(outp);
			pw.print("流水号,伪冒单通知时间\r\n");
			pw.print("BR1508100956058644,2019-08-08 08:25:16\r\n");
			pw.print("BR1508100956058645,2019-08-09 08:55:26\r\n");
			pw.flush();
			outp.flush();
		} catch (Exception e) {
			// log.error("", e);
		} finally {
			if (outp != null) {
				try {
					outp.close();
					outp = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/** 导出调查单列表 */
	@RequestMapping(value = "/exportFakeTransInfo")
	public void exportFakeTransInfo(HttpServletResponse resp) throws Exception, IOException,
			RowsExceededException, WriteException {
		Criteria criteria=getCriteria();
		criteria.getCondition().put("type", "3");
		List<Complaint> complaint=complaintService.queryListComplaintInfoList(criteria);
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "transLogList.xls");
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("伪冒列表", 0);
		String[] headerName = { "商户号","流水号","订单号","网站","交易金额","交易时间","伪冒单通知时间","创建人","创建时间",
				"支付通道","伪冒状态","拒付状态","拒付金额","退款状态","退款金额","冻结状态","冻结金额","订单来源","所属终端号","通道英文账单名称",
				"前六后四卡号","货物信息","姓名","邮箱","电话","IP","支付国家","收货国家","收货省/ 州","收货地址","邮编","货运方式","货运单号","账单国家","账单省/州","账单地址" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		if (!org.springframework.util.StringUtils.isEmpty(complaint)) {
			for (int row = 1; row <= complaint.size(); row++) {
				int col = 0;
				Complaint info = complaint.get(row - 1);
				TransDetailInfo transInfo = transMgrService.queryTransInfo(info.getTradeNo());
				sheet.addCell( new Label(col++, row, transInfo.getMerNo()));//商户号
				sheet.addCell( new Label(col++, row, transInfo.getTradeNo()));//交易流水号
				sheet.addCell( new Label(col++, row, transInfo.getOrderNo()));//订单号
				sheet.addCell( new Label(col++, row, transInfo.getPayWebSite()));//网站
				sheet.addCell( new Label(col++, row, transInfo.getMerBusCurrency() + " " + transInfo.getMerTransAmount()));//交易金额
				sheet.addCell( new Label(col++, row, transInfo.getTransDate()));//交易时间
				sheet.addCell( new Label(col++, row, info.getComplaintDate()));//调查单通知时间
				sheet.addCell( new Label(col++, row, info.getCreatedBy()));//创建人
				sheet.addCell( new Label(col++, row, info.getCreatedDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getCreatedDate()):""));//创建时间
				sheet.addCell( new Label(col++, row, transInfo.getCurrencyName()));//支付通道
				sheet.addCell( new Label(col++, row, info.getIsFake()>0?"是":"非"));//伪冒状态
				sheet.addCell( new Label(col++, row, transInfo.getDishonorStatus()));//拒付状态
				sheet.addCell( new Label(col++, row, transInfo.getDishonorAmount()));//拒付金额
				sheet.addCell( new Label(col++, row, transInfo.getRefundStatus()));//退款状态
				sheet.addCell( new Label(col++, row, transInfo.getRefundAmount()));//退款金额
				sheet.addCell( new Label(col++, row, transInfo.getFrozenStatus()));//冻结状态
				sheet.addCell( new Label(col++, row, transInfo.getFrozenAmount()));//冻结金额
				sheet.addCell( new Label(col++, row, Tools.parseWebInfoToResourceType(transInfo.getWebInfo())));//订单来源
				sheet.addCell( new Label(col++, row, transInfo.getTerNo()));//终端号
				sheet.addCell( new Label(col++, row, transInfo.getAcquirer()));//通道英文账单名称
				sheet.addCell( new Label(col++, row, transInfo.getSixAndFourCardNo()));//前六后四卡号
				if(!org.springframework.util.StringUtils.isEmpty(transInfo.getGoodsInfoByte())){//货物信息
					sheet.addCell(new Label(col++, row, new String(transInfo.getGoodsInfoByte(),"utf-8")));
					System.out.println("===="+new String(transInfo.getGoodsInfoByte(),"utf-8"));
				}else{
					sheet.addCell(new Label(col++, row, ""));
				}
				sheet.addCell( new Label(col++, row, transInfo.getCardFullName()));//姓名
				sheet.addCell( new Label(col++, row, transInfo.getEmail()));//邮箱
				sheet.addCell( new Label(col++, row, transInfo.getCardFullPhone()));//电话
				sheet.addCell( new Label(col++, row, transInfo.getIpAddress()));//IP
				sheet.addCell( new Label(col++, row, transInfo.getCardCountry()));//支付国家
				sheet.addCell( new Label(col++, row, transInfo.getGrCountry()));//收货国家
				sheet.addCell( new Label(col++, row, transInfo.getGrState()));//收货省/ 州
				sheet.addCell( new Label(col++, row, transInfo.getGrAddress()));//收货地址
				sheet.addCell( new Label(col++, row, transInfo.getGrZipCode()));//邮编
				sheet.addCell( new Label(col++, row, transInfo.getIogistics()));//货运方式
				sheet.addCell( new Label(col++, row, transInfo.getTrackNo()));//货运单号
				sheet.addCell( new Label(col++, row, transInfo.getCardCountry()));//账单国家
				sheet.addCell( new Label(col++, row, transInfo.getCardState()));//账单省/州
				sheet.addCell( new Label(col++, row, transInfo.getCardAddress()));//账单地址
			}
		}
		book.write();
		book.close();
	}
	@RequestMapping("/goBatchAddFakeInfo")
	public String goBatchAddFakeInfo(){
		return "complaint/batchAddFakeInfo";
	}
	/**
	 * 标记拒付单为已申诉
	 */
	@RequestMapping("/updateDisOrderToIsComp")
	public ModelAndView updateDisOrderToIsComp(String id){
		int i=complaintService.updateDisOrderToIsCompById(id);
		if(i>0){
			return ajaxDoneSuccess("标记成功");
		}else{
			return ajaxDoneError("标记失败");
		}
	}
	
	/**
	 * 标记调查订单为已拒付
	 */
	@RequestMapping("/updateCompOrderToIsDis")
	public ModelAndView updateCompOrderToIsDis(String id){
		int i=complaintService.updateCompOrderToIsDis(id);
		if(i>0){
			return ajaxDoneSuccess("标记成功");
		}else{
			return ajaxDoneError("标记失败");
		}
	}
	
	/**
	 * 统计商户投诉网址信息
	 */
	@RequestMapping(value="/queryCompWebsiteInfoList")
	public String queryCompWebsiteInfoList(){
		Criteria criteria = getCriteria();
		if("post".equalsIgnoreCase(getRequest().getMethod())){
			PageInfo<WebsiteComplaintInfo> page = complaintService.queryWebsiteComplaintInfoList(criteria);
			getRequest().setAttribute("form", criteria.getCondition());
			getRequest().setAttribute("page", page);
		}else{
			SimpleDateFormat year = new SimpleDateFormat("yyyy");
			SimpleDateFormat month = new SimpleDateFormat("MM");
			criteria.getCondition().put("year", year.format(new Date()));
			criteria.getCondition().put("month", month.format(new Date()));
			getRequest().setAttribute("form", criteria.getCondition());
		}
		return "complaint/listComplaintWebsiteInfo";
	}
	
	/**
	 * 统计商户投诉网址投诉类型划分情况
	 */
	@RequestMapping(value="/queryCompWebsiteCount")
	public String queryCompWebsiteCount(String payWebSite, String type, String complaintType, String year, String month){
		List<WebsiteComplaintCardHolderInfo> list = complaintService.queryWebsiteComplaintCountInfo(type, payWebSite, complaintType, year, month);
		getRequest().setAttribute("list", list);
		return "complaint/complaintWebsiteCountInfo";
	}
	
	/**
	 * 查询投诉人投诉历史记录
	 */
	@RequestMapping(value="/queryCompHistoryInfo")
	public String queryCompHistoryInfo(String complTradeNo, String type, String complaintType, String year, String month){
		List<WebsiteCardHolderInfo> list = complaintService.queryWebsiteCardHodlerInfo(type, complTradeNo, complaintType, year, month);
		getRequest().setAttribute("list", list);
		return "complaint/complaintWebsiteHistoryInfo";
	}
	
	/**
	 * 导出统计商户投诉网址信息
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	@RequestMapping(value="/exportCompWebsiteInfoList")
	public void exportCompWebsiteInfoList(HttpServletRequest request, HttpServletResponse response) throws IOException, RowsExceededException, WriteException{
		Criteria criteria=getCriteria();
		List<WebsiteComplaintInfo> list = complaintService.exportCompWebsiteInfoList(criteria);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="
				+ "weibsiteComplaint.xls");
		WritableWorkbook book = Workbook.createWorkbook(response.getOutputStream());
		WritableSheet sheet = book.createSheet("伪冒列表", 0);
		String[] headerName = {"商户号","终端号","网址","品牌","产品","网站状态","投诉数量","投诉类型","历史投诉记录"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		if (!org.springframework.util.StringUtils.isEmpty(list)) {
			for (int row = 1; row <= list.size(); row++) {
				int col = 0;
				WebsiteComplaintInfo info = list.get(row - 1);
				sheet.addCell( new Label(col++, row, info.getMerNo()));
				sheet.addCell( new Label(col++, row, info.getTerNo()));
				sheet.addCell( new Label(col++, row, info.getPayWebSite()));
				sheet.addCell( new Label(col++, row, info.getBrand()));
				sheet.addCell( new Label(col++, row, info.getProduct()));
				if(info.getWebsiteStatus()==null || "".equals(info.getWebsiteStatus())){
					sheet.addCell( new Label(col++, row, "未知状态"));
				}else if("0".equals(info.getWebsiteStatus())){
					sheet.addCell( new Label(col++, row, "待审批"));
				}else if("1".equals(info.getWebsiteStatus())){
					sheet.addCell( new Label(col++, row, "审批通过"));
				}else if("2".equals(info.getWebsiteStatus())){
					sheet.addCell( new Label(col++, row, "审批驳回"));
				}else if("3".equals(info.getWebsiteStatus())){
					sheet.addCell( new Label(col++, row, "不允许交易"));
				}
				sheet.addCell( new Label(col++, row, info.getComplCount()+""));
				sheet.addCell( new Label(col++, row, info.getCount().toString()));
				sheet.addCell( new Label(col++, row, info.getCardHolder().toString()));
			}
		}
		book.write();
		book.close();
	}
	
	/**
	 * 查询调查单类型List<ComplaintType>
	 */
	@RequestMapping(value="/queryComplaintTypeInfo")
	@ResponseBody
	public List<ComplaintType> queryComplaintTypeInfo(){
		Criteria criteria = getCriteria();
		criteria.getCondition().put("enabled", "1");
		return complaintService.queryComplaintTypeInfo(criteria);
	}
	
}
