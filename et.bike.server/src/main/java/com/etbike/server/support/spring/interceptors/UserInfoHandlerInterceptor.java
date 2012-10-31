package com.etbike.server.support.spring.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.etbike.server.persistence.AccountRepository;
import com.etbike.server.support.spring.resolver.UserInfoWebArgumentResolver;

public class UserInfoHandlerInterceptor extends HandlerInterceptorAdapter {
	@Autowired private AccountRepository accountRepository;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null){
			String userId = authentication.getName();
			if(StringUtils.hasText(userId) && !"anonymousUser".equals(userId)){
				request.setAttribute(UserInfoWebArgumentResolver.CURRENT_USER_ACCOUNT_ATTRIBUTE, accountRepository.findByUsername(userId));
			}
		}
		
		return true;
	}
}