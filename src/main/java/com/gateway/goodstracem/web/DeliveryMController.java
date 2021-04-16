package com.gateway.goodstracem.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.utils.Funcs;
import com.gateway.common.utils.Tools;
import com.gateway.goodstrace.model.DeliveryInfo;
import com.gateway.goodstrace.model.ExpDeliveryInfo;
import com.gateway.goodstrace.model.IogisticsInfo;
import com.gateway.goodstracem.service.DeliveryMService;
@Controller
@RequestMapping("/deliveryM")
public class DeliveryMController extends BaseController {
	@Autowired
	private DeliveryMService deliveryMService;
	
	/** 妥投查询 -马来西亚渠道暂时独立使用*/
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
			PageInfo<DeliveryInfo> page = deliveryMService
					.queryDeliveryInfo(getCriteria());
			getRequest().setAttribute("page", page);
		}
		return "goodstracem/deliveryInfoListM";
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
		List<ExpDeliveryInfo> list = deliveryMService
				.exportDelivery(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("运单列表", 0);

		String[] headerName = { "商户号", "流水号", "订单号", "交易时间", "交易金额", "发货状态",
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
			sheet.addCell(new Label(col++, row, info.getBankCurrency()+" "+info.getBankTransAmount()));
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
	
	@RequestMapping(value = "/exportIogistics")
	public void exportIogistics(HttpServletResponse resp) throws Exception {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition", "attachment;filename="
				+ "exportDelivery.xls");
		List<IogisticsInfo> list = deliveryMService
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
