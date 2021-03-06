package com.gateway.common.adapter.web.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

import com.gateway.common.utils.DateUtil;

public class DateEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		if (!StringUtils.hasText(text)) {
			setValue(null);
		} else {
			setValue(DateUtil.string2Timestamp(text, "yyyy-MM-dd"));
		}
	}

	@Override
	public String getAsText() {

		return getValue().toString();
	}
}
