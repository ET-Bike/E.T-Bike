package com.etbike.server.support.spring.resolver;

import java.util.Locale;

import org.springframework.mobile.device.DeviceUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class NegotiateInternalResourceViewResolver extends InternalResourceViewResolver {

	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		if (DeviceUtils.getCurrentDevice(RequestContextHolder.currentRequestAttributes()).isMobile()) {
			viewName = getMobileViewName(viewName);
		}
		return super.buildView(viewName);
	}

	private String getMobileViewName(String viewName) {
		return viewName + ".mobile";
	}

	@Override
	protected Object getCacheKey(String viewName, Locale locale) {
		if (DeviceUtils.getCurrentDevice(RequestContextHolder.currentRequestAttributes()).isMobile()) {
			return super.getCacheKey(getMobileViewName(viewName), locale);
		} else {
			return super.getCacheKey(viewName, locale);
		}
	}
}