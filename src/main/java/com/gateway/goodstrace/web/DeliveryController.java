package com.gateway.goodstrace.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.jpos.iso.ISOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.utils.Funcs;
import com.gateway.common.utils.Tools;
import com.gateway.goodstrace.model.DeliveryInfo;
import com.gateway.goodstrace.model.ExpDeliveryInfo;
import com.gateway.goodstrace.model.IogisticsInfo;
import com.gateway.goodstrace.service.DeliveryService;
@Controller
@RequestMapping("/delivery")
public class DeliveryController extends BaseController {
	@Autowired
	private DeliveryService deliveryServiceImpl;

	/** 妥投单上传 */
	@RequestMapping("/uploadCheckFile")
	public String uploadCheckFile() {
		return "goodstrace/uploadCheckFile";
	}

	/** 妥投上传数据检测 */
	@RequestMapping(value = "/acceptCheckFile")
	public ModelAndView acceptCheckFile(
			DefaultMultipartHttpServletRequest request) throws IOException,
			ParseException, ISOException {
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		if (null != files) {
			log.info("上传了" + files.size() + "个文件。");
			final List<DeliveryInfo> deliveryInfo = new ArrayList<DeliveryInfo>();
			for (MultipartFile file : files) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						file.getInputStream()));// 构造一个BufferedReader类来读取文件
				String line = br.readLine();
				while (null != line) {
					log.info("line:" + line);
					String[] fields = line.split(",");
					if (fields.length == 6) {
						int index = 0;
						DeliveryInfo info = new DeliveryInfo();
						info.setTradeNo(fields[index++]);
						info.setMerNo(fields[index++]);
						info.setTrackNo(fields[index++]);
						info.setIogistics(fields[index++]);
						info.setStatus(fields[index++]);
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						info.setTradetime(new Timestamp(sdf.parse(
								fields[index++]).getTime()));
						// info.setAmount(fields[index++]);
						info.setCreateby(getLogAccount().getRealName());
						deliveryInfo.add(info);
					} else {
						log.info("错误line:" + line);
					}
					line = br.readLine();
				}
			}
			// 保存对账记录
			if (deliveryInfo.size() > 0) {
				new Thread(new Runnable() {
					public void run() {
						int count = deliveryServiceImpl
								.saveDeliveryDetail(deliveryInfo);
						log.info("更新记录数：" + count);
					}
				}).start();
			}

		}
		return ajaxDoneSuccess("上传成功。");
	}

	/** 妥投查询 */
	@RequestMapping(value = "/queryDeliveryInfo")
	public String queryDeliveryInfo() {
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			String transDateStart=sdf.format(new Date(date.getTime()-1000L*60*60*24*15));
			String transDateEnd=sdf.format(date);
			criteria.getCondition().put("transDateStart", transDateStart);
			criteria.getCondition().put("transDateEnd", transDateEnd);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			getRequest().setAttribute("form", criteria.getCondition());
			PageInfo<DeliveryInfo> page = deliveryServiceImpl
					.queryDeliveryInfo(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "goodstrace/deliveryInfoList";
	}

	/** 跳转妥投状态修改 */
	@RequestMapping(value = "/goUpdateDeliveryInfo")
	public String goUpdateDeliveryInfo(int id) {
		DeliveryInfo deliveryInfo = deliveryServiceImpl
				.queryDeliveryInfoById(id);
		getRequest().setAttribute("deliveryInfo", deliveryInfo);
		return "goodstrace/updateDelvieryInfo";
	}

	/** 妥投状态修改 */
	@RequestMapping(value = "/updateDeliveryInfo")
	public ModelAndView updateDeliveryInfo(String id, String status,String operationStatus) {
		DeliveryInfo deliveryInfo = new DeliveryInfo();
		deliveryInfo.setId(Integer.valueOf(id));
		deliveryInfo.setStatus(status);
		deliveryInfo.setOperationStatus(operationStatus);
		deliveryInfo.setLastModifyBy(getLogAccount().getRealName());
		int i = deliveryServiceImpl.updateDeliveryInfo(deliveryInfo);
		ModelAndView mav = new ModelAndView("ajaxDone");
		if (i > 0) {
			mav.addObject("statusCode", "200");
			mav.addObject("message", "修改成功");
			return mav;
			// return ajaxDoneSuccess("更新成功");

		} else {
			mav.addObject("statusCode", "200");
			mav.addObject("message", "修改失败");
			return mav;
			// return ajaxDoneError("更新失败");
		}
	}



	/**
	 * 货运单导出
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportDelivery")
	public void exportDelivery(HttpServletResponse resp) throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "exportDelivery.xls");
		List<ExpDeliveryInfo> list = deliveryServiceImpl
				.exportDelivery(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("运单列表", 0);

		String[] headerName = { "商户号", "流水号", "订单号", "交易时间", "交易金额","网站", "发货状态",
				"货运公司", "货运单号", "上传时间", "货运查询状态", "操作人", "操作时间", "备注说明", "卡种",
				"交易状态", "交易返回信息", "欺诈分数", "支付通道", "勾兑状态", "入账状态", "拒付状态",
				"拒付金额", "退款状态", "退款金额", "冻结状态", "冻结金额", "订单来源", "所属终端号",
				"通道英文账单名称", "前六后四卡号", "邮箱", "IP", "货物信息", "姓名", "电话", "支付国家",
				"收货国家", "收货省/ 州", "收货地址", "邮编", "账单国家", "账单省/州", "账单地址","账单电话" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			ExpDeliveryInfo info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getMerNo()));
			sheet.addCell(new Label(col++, row, info.getTradeNo()));
			sheet.addCell(new Label(col++, row, info.getOrderNo()));
			sheet.addCell(new Label(col++, row,
					info.getTransDate() != null ? new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").format(info.getTransDate())
							: ""));
			sheet.addCell(new Label(col++, row, info.getMerBusCurrency()+" "+info.getMerTransAmount()));
			sheet.addCell(new Label(col++, row, info.getPayWebSite()));
			if (0 == info.getStatus()) {
				sheet.addCell(new Label(col++, row, "未发货"));
			} else if (1 == info.getStatus()) {
				sheet.addCell(new Label(col++, row, "已发货"));
			}
			sheet.addCell(new Label(col++, row, info.getIogistics()));
			sheet.addCell(new Label(col++, row, info.getTrackNo()));
			sheet.addCell(new Label(col++, row, info.getUpLoadTime()));
			if("0".equals(info.getOperationStatus())){
				sheet.addCell(new Label(col++, row, "未妥投"));// 货运查询状态
			}else if("1".equals(info.getOperationStatus())){
				sheet.addCell(new Label(col++, row, "已妥投"));// 货运查询状态
			}else{
				sheet.addCell(new Label(col++, row, "未妥投"));// 货运查询状态
			}
			sheet.addCell(new Label(col++, row, info.getOperationBy()));// 操作人
			sheet.addCell(new Label(col++, row, info.getLastDateTime()));// 操作时间
			sheet.addCell(new Label(col++, row, info.getRemark()));// 备注说明
			sheet.addCell(new Label(col++, row, info.getCardType()));// 卡类型
			sheet.addCell(new Label(col++, row,
					"00".equals(info.getRespCode()) ? "支付成功" : "支付失败"));
			sheet.addCell(new Label(col++, row, info.getRespMsg()));// 交易返回信息
			sheet.addCell(new Label(col++, row, info.getRiskScore()));// 欺诈分数
			sheet.addCell(new Label(col++, row, info.getCurrencyName()));// 通道名称
			if (0 == info.getIschecked()) {
				sheet.addCell(new Label(col++, row, "未勾兑"));// 勾兑状态
			} else if (1 == info.getIschecked()) {
				sheet.addCell(new Label(col++, row, "已勾兑"));// 勾兑状态
			}
			// 入账状态
			if (0 == info.getAccess()) {
				sheet.addCell(new Label(col++, row, "未入账"));// 是否入账
			} else if (1 == info.getAccess()) {
				sheet.addCell(new Label(col++, row, "已入账"));
			}

			sheet.addCell(new Label(col++, row, "1".equals(info
					.getDishonorStatus()) ? "已拒付" : "未拒付"));
			sheet.addCell(new Label(col++, row, info.getMerSettleCurrency()
					+ " " + info.getDishonorAmount()));
			sheet.addCell(new Label(col++, row, "1".equals(info
					.getRefundStatus()) ? "已退款" : "未退款"));
			sheet.addCell(new Label(col++, row, info.getMerSettleCurrency()
					+ " " + info.getRefundAmount()));
			sheet.addCell(new Label(col++, row, "1".equals(info
					.getFrozenStatus()) ? "已冻结" : "未冻结"));
			sheet.addCell(new Label(col++, row, info.getMerSettleCurrency()
					+ " " + info.getFrozenAmount()));
			sheet.addCell(new Label(col++, row, Tools
					.parseWebInfoToResourceType(info.getWebInfo())));// 所需要的包未加载
			sheet.addCell(new Label(col++, row, info.getTerNo()));
			sheet.addCell(new Label(col++, row, info.getAcquirer()));
			if (info.getCheckNo() != null && !"".equals(info.getCheckNo())) {
				sheet.addCell(new Label(col++, row, Funcs.decrypt(info
						.getCheckNo()) + "***" + Funcs.decrypt(info.getLast())));
			} else {
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell(new Label(col++, row, info.getEmail()));
			sheet.addCell(new Label(col++, row, info.getIpAddress()));
			if (null != info.getGoodsInfo()) {
				sheet.addCell(new Label(col++, row, new String(info
						.getGoodsInfo(), "utf-8")));
				System.out.println("===="
						+ new String(info.getGoodsInfo(), "utf-8"));
			} else {
				sheet.addCell(new Label(col++, row, ""));
			}
			sheet.addCell(new Label(col++, row, info.getGrPerName()));
			sheet.addCell(new Label(col++, row, info.getGrphoneNumber()));
			sheet.addCell(new Label(col++, row, info.getCardCountry()));
			sheet.addCell(new Label(col++, row, info.getGrCountry()));
			sheet.addCell(new Label(col++, row, info.getGrState()));
			sheet.addCell(new Label(col++, row, info.getGrAddress()));
			sheet.addCell(new Label(col++, row, info.getGrZipCode()));
			sheet.addCell(new Label(col++, row, info.getCardCountry()));
			sheet.addCell(new Label(col++, row, info.getCardState()));
			sheet.addCell(new Label(col++, row, info.getCardAddress()));
			sheet.addCell(new Label(col++, row, info.getCardFullPhone()));

		}

		book.write();
		book.close();
	}
	
	/** 
	 * 
	 * 跳转运单状态与备注修改
	 * 
	 */
	@RequestMapping(value = "/goUpdateStatusAndRemark")
	public String goUpdateStatusAndRemark(int id) {
		DeliveryInfo deliveryInfo = deliveryServiceImpl
				.queryDeliveryInfoById(id);
		getRequest().setAttribute("deliveryInfo", deliveryInfo);
		return "goodstrace/updateStatusAndRemark";
	}
	/**
	 * 
	 *运单状态与备注修改 
	 */
	
	@RequestMapping(value = "/UpdateStatusAndRemark")
	public ModelAndView UpdateStatusAndRemark(DeliveryInfo deliveryInfo) {
		deliveryInfo.setLastModifyBy(getLogAccount().getRealName());
		int i = deliveryServiceImpl.UpdateStatusAndRemark(deliveryInfo);
		if (i > 0) {
			return ajaxDoneSuccess("操作成功");
			

		} else {
			return ajaxDoneError("操作失败");
			
		}
	}
	
	
	
	/** 批量修改货运状态页面 */
	@RequestMapping("/uploadCheckRemarkAndStatus")
	public String uploadCheckRemarkAndStatus() {
		return "goodstrace/uploadRemarkAndStatus";
	}

	/** 批量修改货运状态 */
	@RequestMapping(value = "/acceptCheckRemarkAndStatus")
	public ModelAndView acceptCheckRemarkAndStatus(
			DefaultMultipartHttpServletRequest request) throws IOException,
			ParseException, ISOException {
		List<MultipartFile> files = request.getMultiFileMap().get("file");
		if (null != files) {
			log.info("上传了" + files.size() + "个文件。");
			final List<DeliveryInfo> deliveryInfo = new ArrayList<DeliveryInfo>();
			for (MultipartFile file : files) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						file.getInputStream(),"GB2312"));// 构造一个BufferedReader类来读取文件
				String line = br.readLine();
				while (null != line) {
					log.info("line:" + line);
					String[] fields = line.split(",");
					if (fields.length == 3) {
						int index = 0;
						DeliveryInfo info = new DeliveryInfo();
						info.setTradeNo(fields[index++]);
						info.setOperationStatus(fields[index++]);
						info.setRemark(fields[index++]);
						info.setLastModifyBy(getLogAccount().getRealName());
						deliveryInfo.add(info);
					} else {
						log.info("错误line:" + line);
					}
					line = br.readLine();
				}
			}
			// 保存对账记录
			if (deliveryInfo.size() > 0) {
				new Thread(new Runnable() {
					public void run() {
						int count = deliveryServiceImpl
								.saveDeliveryRemarkAndStatus(deliveryInfo);
						log.info("更新记录数：" + count);
					}
				}).start();
			}

		}
		return ajaxDoneSuccess("上传成功。");
	}
	
	/**
	 * 获取物流公司列表
	 * @return
	 */
	@RequestMapping(value="/getIogisticsList")
	public String getIogisticsList(){
		PageInfo<IogisticsInfo> page = deliveryServiceImpl.getIogisticsInfo(getCriteria());
		getRequest().setAttribute("page", page);
		return "goodstrace/iogisticsList";
	}
	
	/**
	 * 添加物流信息
	 * @return
	 */
	@RequestMapping(value="/goAddIogistics")
	public String goAddIogistics(){
		return "goodstrace/addIogistics";
	}
	
	/**
	 * 添加物流公司
	 * @return
	 */
	@RequestMapping(value="/addIogistics")
	public ModelAndView addIogistics(IogisticsInfo info){
		info.setCreateby(getLogAccount().getUserName());
		int i = deliveryServiceImpl.addIogistics(info);
		if(i>0){
			return ajaxDoneSuccess("添加成功。");
		}else{
			return ajaxDoneError("添加失败。");
		}
	}
	
	/**
	 * 更新物流公司信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/goUpdateIogistics")
	public String goUpdateIogistics(String id){
		IogisticsInfo info = deliveryServiceImpl.queryIogisticsById(id);
		getRequest().setAttribute("info", info);
		return "goodstrace/updateIogistics";
		
	}
	/**
	 * 更新物流公司
	 * @param info
	 * @return
	 */
	@RequestMapping(value="/updateIogistics")
	public ModelAndView updateIogistics(IogisticsInfo info){
		info.setCreateby(getLogAccount().getUserName());
		int i = deliveryServiceImpl.updateIogistics(info);
		if(i>0){
			return ajaxDoneSuccess("添加成功。");
		}else{
			return ajaxDoneError("添加失败。");
		}
	}
	@RequestMapping("/deleteIogistics")
	public ModelAndView deleteIogistics(String id){
		int i=deliveryServiceImpl.deleteIogistics(id);
		if(i>0){
			return ajaxDoneSuccess("删除成功。");
		}else{
			return ajaxDoneError("删除失败。");
		}
	}
	
	
	
	@RequestMapping(value = "/exportIogistics")
	public void exportIogistics(HttpServletResponse resp) throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "exportDelivery.xls");
		List<IogisticsInfo> list = deliveryServiceImpl
				.getIogisticsInfoAll(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("货运公司", 0);

		String[] headerName = { "货运公司简称",	"货运公司全称",	"运单查询网址",	"添加人",	"添加时间" };
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			IogisticsInfo info = list.get(row - 1);
			sheet.addCell(new Label(col++, row, info.getIogistics()));
			sheet.addCell(new Label(col++, row, info.getName()));
			sheet.addCell(new Label(col++, row, info.getIogisticsUrl()));
			sheet.addCell(new Label(col++, row, info.getCreateby()));
			sheet.addCell(new Label(col++, row, info.getCreateDate()+""));

		}

		book.write();
		book.close();
	}
}
