package com.gateway.ratemgr.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.merchantmgr.model.MerchantTerInfo;
import com.gateway.merchantmgr.service.MerchantMgrService;
import com.gateway.ratemgr.model.RateInfo;
import com.gateway.ratemgr.service.RateMgrService;
import com.gateway.sysmgr.model.BaseDataInfo;

@Controller
@RequestMapping(value="/ratemgr")
public class RateMgController extends BaseController{
	
	@Resource
	private RateMgrService rateMgrService;
	
	@Resource
	private MerchantMgrService merchantMgrService;
	
	
	public MerchantMgrService getMerchantMgrService() {
		return merchantMgrService;
	}

	public void setMerchantMgrService(MerchantMgrService merchantMgrService) {
		this.merchantMgrService = merchantMgrService;
	}

	public RateMgrService getRateMgrService() {
		return rateMgrService;
	}

	public void setRateMgrService(RateMgrService rateMgrService) {
		this.rateMgrService = rateMgrService;
	}

	@RequestMapping(value="/getListRateInfo")
	public String getListRateInfo(){
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			return "ratemgr/listRateInfo";
		}else{
			
			PageInfo<RateInfo> page = rateMgrService.getListRateInfo(getCriteria());
			getRequest().setAttribute("page", page);
			return "ratemgr/listRateInfo";
		}
	}
	
	/**
	 * ????????????????????????
	 * @return
	 */
	@RequestMapping(value="/goAddRateInfo")
	public String goAddRateInfo(){
		List <RateInfo> currencyNameList=rateMgrService.getCurrencyName();
		getRequest().setAttribute("currencyNameList",currencyNameList);
		return "ratemgr/addRateInfo";
	}
	
	/** ???????????????????????? */
	@ResponseBody
	@RequestMapping(value="/queryBankInfoList")
	public List<RateInfo> queryBankInfoList(){
		return rateMgrService.queryBankInfoList();
	}
	
	/**
	 * ?????????????????????????????????
	 * @return
	 */
	@RequestMapping(value="/getTerListBack")
	public String getMerchantTerList(){
		Criteria criteria=getCriteria();
		criteria.getCondition().put("enabled", "1");
		PageInfo<MerchantTerInfo> page = merchantMgrService.getMerchantTerList(criteria);
		getRequest().setAttribute("type", getRequest().getParameter("type"));
		getRequest().setAttribute("page",page);
		return "ratemgr/terListBack";
	}
	
	
	/**
	 * ????????????
	 * @param rateInfo
	 * @return
	 */
	@RequestMapping(value="/addRateInfo")
	public ModelAndView addRateInfo(RateInfo rateInfo){
		rateInfo.setCreateBy(getLogAccount().getRealName());
		rateInfo.setLastUpdateBy(getLogAccount().getRealName());
		if(!StringUtils.isEmpty(checkRateInfo(rateInfo.getBondRate()))){//?????????
			return ajaxDoneError("???????????????"+checkRateInfo(rateInfo.getBondRate()));
		}
		if(!StringUtils.isEmpty(checkRateInfo(rateInfo.getMerRate()))){//?????????
			return ajaxDoneError("????????????"+checkRateInfo(rateInfo.getMerRate()));
		}
		RateInfo info=rateMgrService.queryRateInfoByTerNo(rateInfo);
		if(null==info){
			int i=rateMgrService.addRateInfo(rateInfo);
			if(i>0){
				return ajaxDoneSuccess("????????????");
			}else{
				return ajaxDoneError("????????????");
			}
		}else{
			return ajaxDoneError("??????????????????????????????");
		}
		
	}
	
	/** ?????????????????????0-1?????? */
	private String checkRateInfo(BigDecimal rate){
//		if(1 > rate.compareTo(new BigDecimal("0"))){
//			return "??????????????????0";
//		}
//		if(-1 < rate.compareTo(new BigDecimal("1"))){
//			return "??????????????????1";
//		}
		return null;
	}
	
	/**
	 * ????????????????????????
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/goUpdateRateInfo")
	public String goUpdateRateInfo(int id){
		RateInfo rateInfo=rateMgrService.queryRateInfoById(id);
		getRequest().setAttribute("rateInfo", rateInfo);
		return "ratemgr/updateRateInfo";
	}
	
	/**
	 * ????????????
	 * @param rateInfo
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value="/updateRateInfo")
	public ModelAndView updateRateInfo(RateInfo rateInfo) throws ServiceException{
		if(!StringUtils.isEmpty(checkRateInfo(rateInfo.getBondRate()))){//?????????
			return ajaxDoneError("???????????????"+checkRateInfo(rateInfo.getBondRate()));
		}
		if(!StringUtils.isEmpty(checkRateInfo(rateInfo.getMerRate()))){//?????????
			return ajaxDoneError("????????????"+checkRateInfo(rateInfo.getMerRate()));
		}
		rateInfo.setLastUpdateBy(getLogAccount().getRealName());
		int i=rateMgrService.updateRateInfo(rateInfo);
		if(i>0){
			return ajaxDoneSuccess("????????????");
		}else{
			return ajaxDoneError("????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * @param rateId
	 * @return
	 */
	@RequestMapping(value="/queryRateInfoLogById")
	public String queryRateInfoLogById(int rateId){
		List<RateInfo> list=rateMgrService.queryRateInfoLogById(rateId);
		getRequest().setAttribute("list", list);
		return "ratemgr/rateListLog";
	}
	
	/**
	 * 
	 * ??????????????????
	 */
	@RequestMapping(value="/exportRateInfo")
	public void exportRateInfo(HttpServletResponse resp) throws IOException, RowsExceededException, WriteException{
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "merchantList.xls" ); 
		List<RateInfo> list = rateMgrService.exportRateInfo(getCriteria());
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("????????????", 0);
		String[] headerName = {"?????????","?????????","??????","????????????","??????","???????????????","???????????????","???????????????","?????????","????????????"};
		// ????????????
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		for (int row = 1; row <= list.size(); row++) {
			int col = 0;
			RateInfo info = list.get(row-1);
			sheet.addCell( new Label(col++, row, info.getMerNo()));
			sheet.addCell( new Label(col++, row, info.getTerNo()));
			sheet.addCell( new Label(col++, row, info.getCardType()));
			sheet.addCell( new Label(col++, row, "0".equals(info.getBankId())?"??????":info.getBankName()));
			sheet.addCell( new Label(col++, row, null==info.getCountrys()||"".equals(info.getCountrys())?"??????":info.getCountrys()));
			sheet.addCell( new Label(col++, row, String.valueOf(info.getMerRate())));
			sheet.addCell( new Label(col++, row, String.valueOf(info.getBondRate())));
			sheet.addCell( new Label(col++, row, String.valueOf(info.getSingleFee())));
			sheet.addCell( new Label(col++, row, info.getCreateBy()));
			sheet.addCell( new Label(col++, row, info.getCreateDate()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getCreateDate()):""));
			
		}
		book.write();
		book.close();
	}
}
