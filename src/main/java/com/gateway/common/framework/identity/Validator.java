/***********************************************************************
 * Module:  Validator.java
 * Author:  Zhang Huihua
 * Purpose: Defines the Interface Validator
 ***********************************************************************/

package com.gateway.common.framework.identity;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gateway.common.framework.config.Constants;
import com.gateway.common.framework.context.AppContext;
import com.gateway.common.framework.context.AppContextHolder;
import com.gateway.common.framework.context.DefaultAppContext;
import com.gateway.common.framework.identity.impl.SessionIdentity;
import com.gateway.loginmgr.model.UserInfo;


public class Validator implements IdentityProvider {

	private static final Log log = LogFactory.getLog(Validator.class);

	private static ThreadLocal<Validator> validatorHolder = new ThreadLocal<Validator>() {

		protected Validator initialValue() {
			return new Validator();
		}

	};


	private HttpSession session = null;
	
	HttpServletRequest request = null;

	private UserInfo user = null;

	private Validator() {
	}

	public static Validator getInstance() {
		return validatorHolder.get();
	}

	public void init(HttpServletRequest request) {
		this.request = request;
		this.session = request.getSession();
	}

	
	@SuppressWarnings("unchecked")
	public boolean validatePermission(){
		log.debug("will validate permission.");
		if(session == null ){
			log.warn("the session is null.");
			return false;
		}
		String reqUri = request.getRequestURI();
		reqUri = reqUri.replaceFirst(request.getContextPath()+"/", "");
		if(reqUri.indexOf("?")>0){
			reqUri = reqUri.substring(0, reqUri.indexOf("?"));
		}
		log.debug("Permission URL:"+reqUri);
		boolean hadpermission = false;
		Map<String, String>  permissionMap = (Map<String,String>)session.getAttribute(Constants.USER_PERMISSION);
		if(null == permissionMap){
			hadpermission =false;
		}else{
			if(permissionMap.keySet().contains(reqUri)){
				hadpermission = true;
				log.debug("validate permission successfully.");
				
			}else{
				hadpermission = false;
			}
		}
		return hadpermission;
	}
	
	public boolean validate() {
		log.debug("will validate session.");
		if (session == null) {
			log.warn("the session is null.");
			return false;
		}

		boolean expired = false;

		try {
			this.user = (UserInfo) session.getAttribute(Constants.AUTHENTICATION_KEY);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (this.user == null) {
			expired = true;
		}
		if (!expired) {
			if (log.isDebugEnabled()) {
				log.debug("validating session successfully.");
			}
			
			log.debug("validate account successfully.");

			return true;
		}

		log.debug("validating session failed.");

		return false;
	}

	public void confirm() {
		if (this.user == null) {
			throw new IllegalArgumentException("authentication is null.");
		}

		AppContext context = AppContextHolder.getContext();
		if (context == null) {
			context = new DefaultAppContext();
			AppContextHolder.setContext(context);
		}
		
		context.setUser(user);
		
	}

	public void cancel() {
		this.session = null;
		this.user = null;
		this.request = null;
		AppContext context = AppContextHolder.getContext();
		if (context != null) {
			context.setUser(null);
		}
	}

	public void clear() {
		this.session = null;
		this.user = null;
		this.request = null;
		AppContext context = AppContextHolder.getContext();
		if (context != null) {
			context.setUser(null);
		}
	}

	public Identity createIdentity(String identityString) {
		if (identityString == null) {
			return null;
		}

		return new SessionIdentity(identityString);
	}
}