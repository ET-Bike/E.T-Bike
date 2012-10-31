package com.etbike.server.support.spring.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.etbike.server.support.utils.AjaxUtils;

public class AjaxCheckInterceptor extends HandlerInterceptorAdapter {
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(request));
		return true;
	}
}
