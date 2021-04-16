package com.gateway.common.adapter.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;





import com.gateway.common.adapter.web.editor.DateEditor;
import com.gateway.common.adapter.web.editor.DoubleEditor;
import com.gateway.common.adapter.web.editor.IntegerEditor;
import com.gateway.common.adapter.web.editor.LongEditor;
import com.gateway.common.adapter.web.model.Criteria;
import com.gateway.common.framework.config.AppConfiguration;
import com.gateway.common.framework.config.Constants;
import com.gateway.common.framework.spring.SpringContextHolder;
import com.gateway.common.utils.Funcs;
import com.gateway.common.utils.ServerInfo;
import com.gateway.loginmgr.model.UserInfo;

public abstract class BaseController {
	
	public static final Log log = LogFactory.getLog(BaseController.class);
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ResourceBundleMessageSource _res;
	
	@Autowired
	protected SpringContextHolder _contextHolder;
	
	protected Criteria criteria;
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		
		binder.registerCustomEditor(int.class, new IntegerEditor());
		binder.registerCustomEditor(long.class, new LongEditor());
		binder.registerCustomEditor(double.class, new DoubleEditor());
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	protected ModelAndView ajaxDone(int statusCode, String message, String forwardUrl) {
		ModelAndView mav = new ModelAndView("ajaxDone");
		mav.addObject("statusCode", statusCode);
		mav.addObject("message", message);
		mav.addObject("forwardUrl", forwardUrl);
		if(statusCode == 200){
			mav.addObject("callbackType", "closeCurrent");
		}
		return mav;
	}
	
	protected ModelAndView ajaxDoneSuccess(String message) {
		return ajaxDone(200, message, "");
	}
	protected ModelAndView ajaxDoneSuccess(String message,String forwardUrl) {
		return ajaxDone(200, message, forwardUrl);
	}
	protected ModelAndView ajaxDoneError(String message) {
		return ajaxDone(300, message, "");
	}
	protected String getMessage(String code) {
		return this.getMessage(code, new Object[] {});
	}

	protected String getMessage(String code, Object arg0) {
		return getMessage(code, new Object[] { arg0 });
	}

	protected String getMessage(String code, Object arg0, Object arg1) {
		return getMessage(code, new Object[] { arg0, arg1 });
	}

	protected String getMessage(String code, Object arg0, Object arg1,
			Object arg2) {
		return getMessage(code, new Object[] { arg0, arg1, arg2 });
	}

	protected String getMessage(String code, Object arg0, Object arg1,
			Object arg2, Object arg3) {
		return getMessage(code, new Object[] { arg0, arg1, arg2, arg3 });
	}

	protected String getMessage(String code, Object[] args) {
		//HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		Locale locale = localeResolver.resolveLocale(request);

		return _res.getMessage(code, args, locale);
	}
	
	protected AppConfiguration getAppConfig() {
		return AppConfiguration.getInstance();
	}
	
	protected boolean verifyValidationCode(
			String validationCode) {

		boolean enableVerify = getAppConfig().getBoolean("validation-code.validation-code-enable");

		if (enableVerify) {
			if (validationCode == null)
				return false;

			String randomCode = null;
			try {
				randomCode = (String) request.getSession().getAttribute(
						Constants.VALIDATION_CODE);
				System.out.println(randomCode + " : " + validationCode);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (randomCode == null)
				return false;
			else if (!randomCode.equalsIgnoreCase(validationCode.trim()))
				return false;
		}
		return true;
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView exception(Exception e, HttpServletRequest request) {
		e.printStackTrace();
		
		request.setAttribute("exception", e);
		
		if (ServerInfo.isAjax(request) || request.getParameter("ajax") != null) {
			return ajaxDoneError(e.getMessage());
		}
		
		ModelAndView mav = new ModelAndView("error");
		mav.addObject("statusCode", 300);
		mav.addObject("message", e.getMessage());
		
		return mav;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public ResourceBundleMessageSource get_res() {
		return _res;
	}

	public void set_res(ResourceBundleMessageSource _res) {
		this._res = _res;
	}

	public SpringContextHolder get_contextHolder() {
		return _contextHolder;
	}

	public void set_contextHolder(SpringContextHolder _contextHolder) {
		this._contextHolder = _contextHolder;
	}

	
	/**
	 * 鍙栧弬鏁扮殑Long 
	 * 
	 * @param request
	 * @param paraName
	 * @return
	 */
	public Long getLong(String paraName)
	{
		String tempStr = request.getParameter(paraName);
		if (StringUtils.isBlank(tempStr) || !StringUtils.isNumeric(tempStr))
		{
			return 0L;
		}
		return Long.parseLong(tempStr);
	}

	/**
	 * 鍙栧弬鏁扮殑Long 鍊兼暟
	 * 
	 * @param request
	 * @param paraName
	 * @return
	 */
	public List<Long> getLongs(String paraName)
	{
		String tempStrArray[] = request.getParameterValues(paraName);
		List<Long> valueArray = new ArrayList<Long>();
		if (null != tempStrArray)
		{
			for (String tempStr : tempStrArray)
			{
				if (StringUtils.isNotBlank(tempStr) && StringUtils.isNumeric(tempStr))
				{
					valueArray.add(Long.parseLong(tempStr.trim()));
				}
			}
		}
		return valueArray;
	}

	/**
	 * 鍙栧弬鏁�
	 * 
	 * @param request
	 * @param paraName
	 * @return
	 */
	public String getString(String paraName)
	{
		String tempStr = request.getParameter(paraName);
		if (null == tempStr)
		{
			return null;
		}
		if (StringUtils.isBlank(tempStr))
		{
			return "";
		}
		return tempStr;
	}

	/**
	 * 鍙栧弬鏁扮殑Integer
	 * 
	 * @param request
	 * @param paraName
	 * @return
	 */
	public Integer getInteger(String paraName)
	{
		String tempStr = request.getParameter(paraName);
		if (StringUtils.isBlank(tempStr) || !StringUtils.isNumeric(tempStr))
		{
			return 0;
		}
		return Integer.parseInt(tempStr);
	}
	
	public Criteria getCriteria(){
		Criteria criteria = new Criteria();
		Enumeration<String> names =  request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			if(name.equals("cardEnd")||name.equals("cardStart")){
				if(request.getParameter(name)!=null&&!"".equals(request.getParameter(name))){
					criteria.put(name,Funcs.encrypt(request.getParameter(name)));
				}
			}else{
				criteria.put(name,request.getParameter(name));
			}
		}
		String userRefMerNo=getLogAccount().getUserRefMerNo();
		if(userRefMerNo==null){
			userRefMerNo="0";
		}
		criteria.put("userRefMerNo",userRefMerNo);
		Integer pageNum = getInteger( "pageNum");
        if (pageNum == null || pageNum <= 0) {// 鍒ゆ柇椤电爜鏄惁涓虹┖
            pageNum = 1;
        }
        Integer numPerPage = getInteger("numPerPage");
        if(numPerPage == null || numPerPage <= 0){
        	numPerPage =new Integer(20);
        }
        criteria.setPageSize(numPerPage);
        criteria.setPageNum(pageNum);
        criteria.setNumPerPage(numPerPage);
		return criteria;
	}

	/**
	 * 鍙栧弬鏁扮殑Double
	 * 
	 * @param request
	 * @param paraName
	 * @return
	 */
	public  Double getDouble( String paraName)
	{
		String tempStr = request.getParameter(paraName);
		if (StringUtils.isBlank(tempStr))
		{
			return null;
		}
		try
		{
			return Double.parseDouble(tempStr);
		}
		catch (Exception ex)
		{
		}
		return null;
	}

	/**
	 * 鍙栧弬鏁扮殑Float
	 * 
	 * @param request
	 * @param paraName
	 * @return
	 */
	public Float getFloat(String paraName)
	{
		String tempStr = request.getParameter(paraName);
		if (StringUtils.isBlank(tempStr))
		{
			return null;
		}
		try
		{
			return Float.parseFloat(tempStr);
		}
		catch (Exception ex)
		{
		}
		return null;
	}

	/**
	 * 鍙栧弬鏁扮殑Date
	 * 
	 * @param request
	 * @param paraName
	 * @param format
	 * @return
	 */
	public Date getDate(String paraName, String format)
	{
		String tempStr = request.getParameter(paraName);
		if (StringUtils.isBlank(tempStr))
		{
			return null;
		}
		try
		{
			return new SimpleDateFormat(format).parse(tempStr);
		}
		catch (Exception e)
		{
		}
		return null;
	}

	/**
	 * 鑾峰彇鐧诲綍鐢ㄦ埛淇℃伅
	 * @param request
	 * @return
	 */
	public UserInfo getLogAccount(){
		Object obj  =(UserInfo)request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
		if(null == obj){
			return null;
		}
		return (UserInfo)obj;
	}
	
	/**
	 * 鍙栧弬鏁扮殑Date
	 * 
	 * @param request
	 * @param paraName
	 * @param format
	 * @return
	 */
	public Timestamp getTimestamp(String paraName, String format)
	{
		String tempStr = request.getParameter(paraName);
		if (StringUtils.isBlank(tempStr))
		{
			return null;
		}
		try
		{
			return new Timestamp(new SimpleDateFormat(format).parse(tempStr).getTime());
		}
		catch (Exception e)
		{
		}
		return null;
	}

	/**
	 * 鍙栧弬鏁扮殑Calendar
	 * 
	 * @param request
	 * @param paraName
	 * @param format
	 * @return
	 */
	public Calendar getCalendar(String paraName, String format)
	{
		Date date = getDate(paraName, format);
		Calendar cal = Calendar.getInstance();
		if (date != null)
		{
			cal.setTime(date);
		}
		else
		{
			cal = null;
		}
		return cal;
	}

	/**
	 * 鍙栧弬鏁扮殑boolean
	 * 
	 * @param request
	 * @param paraName
	 * @return
	 */
	public  boolean getBool(String paraName)
	{
		String tempStr = request.getParameter(paraName);
		if (tempStr == null)
		{
			return false;
		}
		tempStr = tempStr.trim();
		if ("true".equalsIgnoreCase(tempStr) || "1".equals(tempStr))
		{
			return true;
		}
		return false;
	}

	/**
	 * 鍙栧弬鏁扮殑Boolean
	 * 
	 * @param request
	 * @param paraName
	 * @return
	 */
	public Boolean getBoolean(String paraName)
	{
		String tempStr = request.getParameter(paraName);
		if (StringUtils.isBlank(tempStr))
		{
			return null;
		}
		tempStr = tempStr.trim();
		if ("true".equalsIgnoreCase(tempStr) || "1".equals(tempStr))
		{
			return true;
		}
		return false;
	}

	/**
	 * 鍙栧弬鏁扮殑Integer鍊煎垪
	 * 
	 * @param request
	 * @param paraName
	 * @return
	 */
	public List<Integer> getIntegers(String paraName)
	{
		String tempStrArray[] = request.getParameterValues(paraName);
		List<Integer> valueArray = new ArrayList<Integer>();
		if (null != tempStrArray)
		{
			for (String tempStr : tempStrArray)
			{
				if (StringUtils.isNotBlank(tempStr) && StringUtils.isNumeric(tempStr))
				{
					valueArray.add(Integer.parseInt(tempStr.trim()));
				}
			}
		}
		return valueArray;
	}

	/**
	 * 鍙栧弬鏁扮殑Double鍊煎垪
	 * 
	 * @param request
	 * @param paraName
	 * @return
	 */
	public List<Double> getDoubles(String paraName)
	{
		String tempStrArray[] = request.getParameterValues(paraName);
		List<Double> valueArray = new ArrayList<Double>();
		if (null != tempStrArray)
		{
			for (String tempStr : tempStrArray)
			{
				if (StringUtils.isNotBlank(tempStr))
				{
					try
					{
						valueArray.add(Double.parseDouble(tempStr));
					}
					catch (Exception ex)
					{
					}
				}
			}
		}
		return valueArray;
	}

	/**
	 * 鍙栧弬鏁扮殑Float鍊煎垪
	 * 
	 * @param request
	 * @param paraName
	 * @return
	 */
	public List<Float> getFloats(String paraName)
	{
		String tempStrArray[] = request.getParameterValues(paraName);
		List<Float> valueArray = new ArrayList<Float>();
		if (null != tempStrArray)
		{
			for (String tempStr : tempStrArray)
			{
				if (StringUtils.isNotBlank(tempStr))
				{
					try
					{
						valueArray.add(Float.parseFloat(tempStr));
					}
					catch (Exception ex)
					{
					}
				}
			}
		}
		return valueArray;
	}

	/**
	 * 鍙栧弬鏁扮殑
	 * 
	 * @param request
	 * @param paraName
	 * @return
	 */
	public  String[] getStringArray(String paraName)
	{
		return request.getParameterValues(paraName);
	}

	/**
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getAllRequestParameter()
	{
		Enumeration en = request.getParameterNames();
		String parameterName = null;
		StringBuilder buf = new StringBuilder();
		String valueArray[] = null;
		while (en.hasMoreElements())
		{
			parameterName = (String) en.nextElement();
			valueArray = request.getParameterValues(parameterName);
			for (String value : valueArray)
			{
				buf.append("&").append(parameterName).append("=").append(value);
			}
		}
		return buf.toString();
	}

	public String getSubIpAddr()
	{
		String ip = request.getHeader("X-Forwarded-For");
		if (null != ip && ip.length() > 0)
		{
			return ip;
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public  String getUserIpAddr()
	{
		String ip = request.getHeader("X-Forwarded-For");
		if (null != ip && ip.length() > 0)
		{
			String tmpIps[] = ip.split(",");
			for (String tmpIp : tmpIps)
			{
				tmpIp = tmpIp.trim();
				if (null != tmpIp && tmpIp.length() > 0 && !"unknown".equalsIgnoreCase(tmpIp))
				{
					ip = tmpIp;
					break;
				}
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	@SuppressWarnings("rawtypes")
	public  void printAllHeaders()
	{
		Enumeration en = request.getHeaderNames();
		String headerName = null;
		System.out.println("<------------------print header begin----------------------->");
		Enumeration valueArray = null;
		String value = null;
		while (en.hasMoreElements())
		{
			headerName = (String) en.nextElement();
			valueArray = request.getHeaders(headerName);
			while (valueArray.hasMoreElements())
			{
				value = (String) valueArray.nextElement();
				System.out.println(headerName + "=" + value);
			}
		}
		System.out.println("<------------------print header end------------------------->");
	}
	
	/**
     * 浠巆ookie鍙栧��
     * 
     * @param request
     * @param paraName
     * @return
     */
    public String getStringAtCookie(String paraName)
    {
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
        {
            for (Cookie cookie : cookies)
            {
                if (paraName.equals(cookie.getName()))
                {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    
	public void setCriteria(Criteria criteria) {
		this.criteria = getCriteria();
	}
	
	
	/**
	 * 鏂囦欢涓嬭浇锛屽鍑篢XT妯℃澘
	 * @param HttpServletResponse resp;
	 * @param String fileName 鏂囦欢鍚� trans.text;
	 * @param String Catalog 绗竴琛岀洰褰�;
	 * @param String [] contents 鍐呭妯℃澘;
	 * */
	public void downloadFile( HttpServletResponse resp,String fileName,String Catalog,List<String> contents) {
		resp.setContentType("application/zip");
		resp.setHeader("Content-Type","text/html;charset=GBK");
		resp.addHeader("Content-Disposition", "attachment;filename="+ fileName);
		OutputStream outp = null;
		try {
			
			outp = resp.getOutputStream();
			PrintWriter pw=new PrintWriter(outp);
			pw.print(Catalog +"\r\n");
			for(String str:contents){
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
	}
    
    
}
