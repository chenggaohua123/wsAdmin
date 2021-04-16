package com.gateway.common.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <strong>Title : PageBreak<br></strong>
 * <strong>Description : </strong>@类注释说明写在此处@<br> 
 * <strong>Create on : 2011-11-1<br></strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * <p>
 * @author peng.shi peng.shi@ecointellects.com<br>
 * @version <strong>Ecointel v1.0.0</strong><br>
 * <br>
 * <strong>修改历史:</strong><br>
 * 修改�?	修改日期		修改描述<br>
 * -------------------------------------------<br>
 * <br>
 * <br>
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PageBreak {
	
}
