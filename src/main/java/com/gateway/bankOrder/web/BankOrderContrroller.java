package com.gateway.bankOrder.web;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson15.JSONArray;
import com.alibaba.fastjson15.JSONObject;
import com.gateway.bankOrder.model.BankOrderModel;
import com.gateway.bankOrder.model.SxyBankModel;
import com.gateway.bankOrder.service.BankOrderService;
import com.gateway.bankmgr.model.CurrencyInfo;
import com.gateway.bankmgr.service.BankMgrService;
import com.gateway.common.adapter.web.BaseController;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.adapter.web.model.PageInfo;
import com.gateway.common.excetion.ServiceException;
import com.gateway.transmgr.model.TransInfo;
import com.gateway.transmgr.service.TransMgrService;
import com.upay.sdk.exception.HmacVerifyException;
import com.upay.sdk.exception.RequestException;
import com.upay.sdk.exception.ResponseException;
import com.upay.sdk.exception.UnknownException;
import com.upay.sdk.executer.ResultListenerAdpater;
import com.upay.sdk.icc.v_3.builder.QueryBuilder;
import com.upay.sdk.icc.v_3.executer.QueryOrderExecuter;

/**
 * 银行订单管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/bankOrder")
public class BankOrderContrroller extends BaseController {

	@Resource
	private BankMgrService bankMgrService;
	@Resource
	private BankOrderService bankOrderService;
	@Resource
	private TransMgrService transMgrService;
	//退款
	private static String urlRefund="https://merchant.remitepay.com/api/GetRefund";
	//拒付
	private static String  urlRefuse="https://merchant.remitepay.com/api/getrefuse";
	/**
	 * 银行退款订单
	 * @return
	 */
	@RequestMapping(value="/getBankRefundList")
	public String getBankRefundList() throws ServiceException{
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			String year=new SimpleDateFormat("yyyy").format(new Date());
			String month=new SimpleDateFormat("MM").format(new Date());
			log.info("year:"+year);
			log.info("month:"+month);
			criteria.getCondition().put("year", year);
			criteria.getCondition().put("month", month);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			//通道id
			if(criteria.getCondition().get("currencyId")==null||criteria.getCondition().get("currencyId").toString().trim().equals("")){
				throw new ServiceException("请选择通道");
			}
			int currencyId= Integer.parseInt((String)criteria.getCondition().get("currencyId"));
			//获取密钥和商户号
			Map<String,String> map=bankMgrService.queryCurrencyConfigByCurrencyId(currencyId);
			//get请求外网数据
			PageInfo<BankOrderModel> page=bankOrderService.getPage(criteria, urlRefund, map,"0");
			getRequest().setAttribute("page", page);
			getRequest().setAttribute("form", criteria.getCondition());
		}
		return "bankOrder/bankRefundList";
	}
	
	
	/**
	 * 银行拒付订单
	 * @return
	 */
	@RequestMapping(value="/getBankDishonourList")
	public String getBankDishonourList() throws ServiceException{
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			String year=new SimpleDateFormat("yyyy").format(new Date());
			String month=new SimpleDateFormat("MM").format(new Date());
			log.info("year:"+year);
			log.info("month:"+month);
			criteria.getCondition().put("year", year);
			criteria.getCondition().put("month", month);
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			//通道id
			if(criteria.getCondition().get("currencyId")==null||criteria.getCondition().get("currencyId").toString().trim().equals("")){
				throw new ServiceException("请选择通道");
			}
			int currencyId= Integer.parseInt((String)criteria.getCondition().get("currencyId"));
			//获取密钥和商户号
			Map<String,String> map=bankMgrService.queryCurrencyConfigByCurrencyId(currencyId);
			//get请求外网数据
			PageInfo<BankOrderModel> page=bankOrderService.getPage(criteria, urlRefuse, map,"0");
			getRequest().setAttribute("page", page);
			getRequest().setAttribute("form", criteria.getCondition());
		}
		return "bankOrder/bankDishonourList";
	}
	
	/** 导出退款订单跟踪 */
	@RequestMapping(value="/uploadRefundOrderFile")
	public void uploadDishonourOrderFile(HttpServletResponse resp) throws ServiceException,Exception{
		Criteria criteria=getCriteria();
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "bankOrderRefundList.xls" );
		//通道id
		int currencyId= Integer.parseInt((String)criteria.getCondition().get("currencyId"));
		//获取密钥和商户号
		Map<String,String> map=bankMgrService.queryCurrencyConfigByCurrencyId(currencyId);
		//get请求外网数据
		PageInfo<BankOrderModel> page=bankOrderService.getPage(criteria, urlRefund, map,"1");
		List<BankOrderModel> list=page.getData();
		//导出excle格式
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("退款订单列表", 0);
		String[] headerName = { "交易号","订单号","商户号","订单原始金额(原币种)","退款金额(CNY)","退款状态","退款原因","退款添加日期"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		if(list!=null){
			for (int row = 1; row <= list.size(); row++) {
				int col = 0;
				BankOrderModel bankOrderModel = list.get(row-1);
				sheet.addCell( new Label(col++, row, bankOrderModel.getTransactionId()));//交易号
				sheet.addCell( new Label(col++, row, bankOrderModel.getOrderId()));//订单号
				sheet.addCell( new Label(col++, row, bankOrderModel.getMerchantId()));//商户号
				sheet.addCell( new Label(col++, row, bankOrderModel.getAmountOrder()));//订单原始金额
				sheet.addCell( new Label(col++, row, bankOrderModel.getAmount()));//退款金额(CNY)
				//退款状态
				String statusStr="";
				if("Pending".equals(bankOrderModel.getStatus())){
					statusStr="等待处理";
				}else if("Success".equals(bankOrderModel.getStatus())){
					statusStr="退款成功";
				}else if("Rejected".equals(bankOrderModel.getStatus())){
					statusStr="已拒绝";
				}
				sheet.addCell( new Label(col++, row, statusStr));
				sheet.addCell( new Label(col++, row, bankOrderModel.getReason()));//退款原因
				sheet.addCell( new Label(col++, row, bankOrderModel.getDateCreated()));//退款添加日期
			}
		}
		book.write();
		book.close();
	}
	
	
	/** 导出拒付订单 */
	@RequestMapping(value="/uploadDishonourOrderFile") 
	public void uploadRefundOrderFile(HttpServletResponse resp) throws ServiceException,Exception{
		Criteria criteria=getCriteria();
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-disposition","attachment;filename="+ "bankOrderDishonourList.xls" );
		//通道id
		int currencyId= Integer.parseInt((String)criteria.getCondition().get("currencyId"));
		//获取密钥和商户号
		Map<String,String> map=bankMgrService.queryCurrencyConfigByCurrencyId(currencyId);
		//get请求外网数据
		PageInfo<BankOrderModel> page=bankOrderService.getPage(criteria, urlRefuse, map,"1");
		List<BankOrderModel> list=page.getData();
		//导出excle格式
		WritableWorkbook book = Workbook.createWorkbook(resp.getOutputStream());
		WritableSheet sheet = book.createSheet("拒付订单列表", 0);
		String[] headerName = { "交易号","订单号","商户号","拒付金额(CNY)","拒付日期","拒付状态","拒付原因","拒付添加日期"};
		// 写入标题
		for (int index = 0; index < headerName.length; index++) {
			Label label = new Label(index, 0, headerName[index]);
			sheet.addCell(label);
		}
		if(list!=null){
			for (int row = 1; row <= list.size(); row++) {
				int col = 0;
				BankOrderModel bankOrderModel = list.get(row-1);
				sheet.addCell( new Label(col++, row, bankOrderModel.getTransactionId()));//交易号
				sheet.addCell( new Label(col++, row, bankOrderModel.getOrderId()));//订单号
				sheet.addCell( new Label(col++, row, bankOrderModel.getMerchantId()));//商户号
				sheet.addCell( new Label(col++, row, bankOrderModel.getAmount()));//拒付金额(CNY)
				sheet.addCell( new Label(col++, row, bankOrderModel.getDateRefused()));//订单原始金额
				//拒付状态
				String statusStr="";
				if("Freeze".equals(bankOrderModel.getStatus())){
					statusStr="冻结";
				}else if("Refused".equals(bankOrderModel.getStatus())){
					statusStr="已拒付";
				}else if("Thawed".equals(bankOrderModel.getStatus())){
					statusStr="已解冻";
				}else if("Cancel".equals(bankOrderModel.getStatus())){
					statusStr="取消";
				}
				sheet.addCell( new Label(col++, row, statusStr));
				sheet.addCell( new Label(col++, row, bankOrderModel.getReason()));//拒付原因
				sheet.addCell( new Label(col++, row, bankOrderModel.getDateCreated()));//拒付添加日期
			}
		}
		book.write();
		book.close();
	}
	/**下拉框查询通道**/
	@RequestMapping("/queryCurrencyIfoList")
	@ResponseBody
	public List<CurrencyInfo> queryCurrencyIfoList(){
		Criteria criteria=getCriteria();
		Map<String,Object> map=criteria.getCondition();
		List<String> list=new ArrayList<String>();
		List<Integer> bankIdList=new ArrayList<Integer>();
		//int bankId=Integer.parseInt((String) map.get("bankId"));
		String bankId=(String) map.get("bankId");
		if(bankId!=null){
			String[] bankIds=bankId.split("/");
			for(String bank:bankIds){
				bankIdList.add(Integer.parseInt(bank));
			}
		}
		String currencyId=(String) map.get("currencyId");
		if(currencyId!=null){
			String[] ids=currencyId.split("/");
			for(String str:ids){
				list.add(str);
			}
		}
		List<CurrencyInfo> currencyInfoList=bankMgrService.queryCurrencyIdByBankId(bankIdList, list);
		return currencyInfoList;
	}
	
	/**
	 *  银行订单查询(暂时只有首信易)
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/getBankOrderList")
	public String getBankOrderList() throws ServiceException{
		Criteria criteria=getCriteria();
		if("get".equalsIgnoreCase(getRequest().getMethod())){
			getRequest().setAttribute("form", criteria.getCondition());
		}else{
			Object tradeNoObj= criteria.getCondition().get("tradeNo");
			if(tradeNoObj==null||"".equals(tradeNoObj.toString().trim())){
				throw new ServiceException("流水号不能为空");
			}
			String tradeNo=(String) tradeNoObj;
			TransInfo transInfo=transMgrService.queryTransDetailByTradeNo(tradeNo);
			if(transInfo==null){
				throw new ServiceException("流水号不存在");
			}
			CurrencyInfo currencyInfo=bankMgrService.queryCurrencyInfoById(transInfo.getCurrencyId());
			String merchantId=currencyInfo.getMerchantNo();
			String requestId=transInfo.getBankRefNo();
			if(requestId==null){
				merchantId="890001964";
				requestId="20190823-890001964-BR1908231654333744";
			}
			if(!requestId.contains("-")){
				requestId=requestId.substring(0, 8)+"-"+merchantId+"-"+tradeNo;
			}
			//请求银行(首信易)数据，并把数据存在request中
			this.getDate(merchantId, requestId, getRequest());
			//获取数据
			JSONObject jsonObject=(JSONObject) getRequest().getAttribute("json");
			//SxyBankModel sxyBankModel=new SxyBankModel();
			//sxyBankModel.setTradeNo(tradeNo);
			//把jsonObject里的值赋给sxyBankModel
			//sxyBankModel=(SxyBankModel) objTransformation(sxyBankModel, jsonObject);
			getRequest().setAttribute("form", criteria.getCondition());
			//getRequest().setAttribute("data", sxyBankModel);
			getRequest().setAttribute("data", jsonObject);
			//退款详情/拒付详情存储在session中
			if(jsonObject.get("refundDetails")!=null){
				getRequest().getSession().removeAttribute("bankRefundDetails");
				//getRequest().getSession().setAttribute("bankRefundDetails", sxyBankModel.getRefundDetails());
				getRequest().getSession().setAttribute("bankRefundDetails", jsonObject.get("refundDetails"));
			}
			if(jsonObject.get("refuseDetails")!=null){
				getRequest().getSession().removeAttribute("bankRefuseDetails");
				//getRequest().getSession().setAttribute("bankRefuseDetails", sxyBankModel.getRefuseDetails());
				getRequest().getSession().setAttribute("bankRefuseDetails", jsonObject.get("refuseDetails"));
			}
			Object obj=getRequest().getSession().getAttribute("bankRefundDetails");
			System.out.println("bankRefundDetails:"+obj);
			Object obj1=getRequest().getSession().getAttribute("bankRefuseDetails");
			System.out.println("bankRefuseDetails:"+obj1);
		}
		return "bankOrder/bankOrderList";
	}
	
	/**
	 * 查看退款详情
	 * @return
	 */
	@RequestMapping("/refundDetails")
	public String refundDetails(){
		return "bankOrder/refundDetails";
	}
	@RequestMapping("/refuseDetails")
	public String refuseDetails(){
		return "bankOrder/refuseDetails";
	}
	
	
	/**
	 * 首信易单笔订单查询接口
	 * @param merchantId 商户编号
	 * @param requestId 订单号
	 * @param req
	 * @throws ServiceException
	 */
	public  void getDate(String merchantId,String requestId,final HttpServletRequest req) throws ServiceException{
			QueryBuilder builder = new QueryBuilder(merchantId).setRequestId(requestId);
			//JSONObject json=null;
			//final PrintWriter out = resp.getWriter();
			try{
		    QueryOrderExecuter executer = new QueryOrderExecuter();
		    executer.bothQuery(builder, new ResultListenerAdpater() {
		        /**
		         * 提交成功，不代表支付成功
		         */
		        @Override
		        public void success(JSONObject jsonObject) {
		        	//json=jsonObject;
		            log.info("success jsonObject:[" + jsonObject + "]");
		            req.setAttribute("json",jsonObject.toJSONString());
		            //out.println("提交成功</br>");
		            //out.println(jsonObject);
		        }
		
		        @Override
		        public void failure(JSONObject jsonObject) {
		        	//json=jsonObject;
		            log.info("failure jsonObject:[" + jsonObject + "]");
		        	req.setAttribute("json",jsonObject.toJSONString());
		            //out.println("提交成功</br>");
		            //out.println(jsonObject);
		        }
		    });
		}
		catch(ResponseException e){
			throw new ServiceException("响应异常");
		}
		catch(HmacVerifyException e){
			throw new ServiceException("签名验证异常");
		}
		catch(RequestException e){
			throw new ServiceException("请求异常");
		}
		catch(UnknownException e){
			String strE=e.toString();
			if(strE.contains("{")){
				strE=strE.substring(strE.indexOf("{"), strE.indexOf("}")+1);
			}else{
				throw new ServiceException("未知异常");
			}
			log.info(strE);
			JSONObject jsonObj=JSONObject.parseObject(strE);
			String cause=(String) jsonObj.get("cause");
			//测试
			String json="{\"status\":\"SUCCESS\",\"pstatus\":\"SUCCESS\",\"statusDescription\":\"0000\",\"merchantId\":\"888\",\"paymentModeAlias\":\"Master\",\"requestId\":\"20190813-888-123456\",\"serialNumber\":\"123456\",\"bankNumber\":\"3456789098\",\"orderCurrency\":\"CNY\",\"orderAmount\":\"1.00\",\"submissionTime\":\"2019-08-13 01:12:30\",\"completeTime\":\"2019-08-13 01:12:31\",\"totalRefundCount\":\"2\",\"totalRefundAmount\":\"10.00\",\"refundDetails\":[{\"status\":\"SUCCESS\",\"refundId\":\"816290053119947\",\"requestId\":\"12345\",\"bankNumber\":\"345678\",\"refundAmount\":\"1.00\",\"refundSubmissionTime\":\"2019-08-13 21:30:00\",\"refundCompleteTime\":\"2019-08-13 21:30:01\"},{\"status\":\"SUCCESS\",\"refundId\":\"816290053119947\",\"requestId\":\"12345\",\"bankNumber\":\"345679\",\"refundAmount\":\"2.00\",\"refundSubmissionTime\":\"2019-08-13 21:30:00\",\"refundCompleteTime\":\"2019-08-13 21:30:01\"}],\"totalRefuseCount\":\"2\",\"totalRefuseAmount\":\"10.00\",\"refuseDetails\":[{\"refuseId\":\"34567878\",\"bankNumber\":\"25657890\",\"refuseAmount\":\"1.00\",\"refuseCompleteTime\":\"2019-08-13 21:30:02\"},{\"refuseId\":\"34567878\",\"bankNumber\":\"25657891\",\"refuseAmount\":\"2.00\",\"refuseCompleteTime\":\"2019-08-13 21:30:02\"}],\"remark\":\"test\",\"hmac\":\"af291df5d288690d2b7d7bcff7dc5a74447ce1502c1edcc9293051bb6a517d686fbb31de72bfd14938d474fa6dd0afb82a2c222a6a345e41683d456a142e3376c5c8f07dc5bc92dd5b09 af316fa478a003189ed4d4c956d33162de11a8aec8ab6abcf66b19833302780cffaa25c7e40 bb9b2ccc59adc44e3fcb4632bce5d9cf9 \"}";
			JSONObject jsonObj1=JSONObject.parseObject(json);
			req.setAttribute("json", jsonObj1);
			//throw new ServiceException(cause);
		}
	}
	
	
	
	/**
	 * 把jsonObject赋值给obj(相同参数赋值)
	 * @param obj
	 * @param jsonObject
	 * @return
	 */
	public static Object objTransformation(Object obj,JSONObject jsonObject) {
		Set<String> set=jsonObject.keySet();
		Iterator<String> its=set.iterator();
		Field[] fields=obj.getClass().getDeclaredFields();
		while(its.hasNext()){
			String key=its.next();
			for(Field field:fields){
				String name=field.getName();
				if(key.trim().equalsIgnoreCase(name.trim())){
					name=name.substring(0, 1).toUpperCase()+name.substring(1);
					String value=jsonObject.getString(key);
					log.info(value);
					try {
						Class clazz=obj.getClass().getMethod("get"+name).getReturnType();
						if(clazz.isAssignableFrom(String.class)){
							//当属性是String类型，调用set方法赋值
							Method method=obj.getClass().getMethod("set"+name, String.class);
							method.invoke(obj, value);
						}else{
							//当属性是对象类型，创建对象，并赋值
							try {
								//创建对象
								Object object=clazz.newInstance();
								if(!"".equals(value)&&value!=null){
									value=value.substring(value.indexOf("[")+1, value.indexOf("]"));
									JSONObject jsonObject1=JSONObject.parseObject(value);
									object=jsonStringTransformationObj(object, jsonObject1);
									//赋值
									Method method=obj.getClass().getMethod("set"+name, clazz);
									method.invoke(obj, object);
								}
								
							} catch (InstantiationException e) {
								e.printStackTrace();
							}
						}
						
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return obj;
	}
	
	public static Object jsonStringTransformationObj(Object obj,JSONObject jsonObject){
		Set<String> set=jsonObject.keySet();
		Iterator<String> its=set.iterator();
		Field[] fields=obj.getClass().getDeclaredFields();
		while(its.hasNext()){
			String key=its.next();
			for(Field field:fields){
				String name=field.getName();
				if(key.trim().equalsIgnoreCase(name.trim())){
					name=name.substring(0, 1).toUpperCase()+name.substring(1);
					String value=jsonObject.getString(key);
					try {
						Class clazz=obj.getClass().getMethod("get"+name).getReturnType();
						if(clazz.isAssignableFrom(String.class)){
							Method method=obj.getClass().getMethod("set"+name, String.class);
							method.invoke(obj, value);
						}
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return obj;
	}
		
}

