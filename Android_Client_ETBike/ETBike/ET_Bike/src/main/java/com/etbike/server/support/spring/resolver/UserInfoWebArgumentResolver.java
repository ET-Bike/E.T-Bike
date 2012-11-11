package com.etbike.server.support.spring.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

import com.etbike.server.domain.model.Account;

public class UserInfoWebArgumentResolver implements WebArgumentResolver {
	public static final String CURRENT_USER_ACCOUNT_ATTRIBUTE = "userAccount";
	
	public Object resolveArgument(MethodParameter param, NativeWebRequest request) throws Exception {
		if (Account.class.isAssignableFrom(param.getParameterType())) {
			return request.getAttribute(CURRENT_USER_ACCOUNT_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
		} else {
			return WebArgumentResolver.UNRESOLVED;
		}
	}
	
}