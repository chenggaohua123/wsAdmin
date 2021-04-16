package com.gateway.common.adapter.web.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

public class MethodAuthorityInterceptor implements MethodInterceptor {

	private Logger logger = LoggerFactory.getLogger(MethodAuthorityInterceptor.class);
	public Object invoke(MethodInvocation invocation) throws Throwable {

		Class<?> clazz = invocation.getThis().getClass();

		if (clazz.isAnnotationPresent(Controller.class)) {
			Controller controller = clazz.getAnnotation(Controller.class);
			String controllerName = controller.value().trim();
			String methodName = invocation.getMethod().getName();
			logger.info("controllerName:"+controllerName);
			logger.info("methodName:"+methodName);
		}
		
		return invocation.proceed();
	}

}
