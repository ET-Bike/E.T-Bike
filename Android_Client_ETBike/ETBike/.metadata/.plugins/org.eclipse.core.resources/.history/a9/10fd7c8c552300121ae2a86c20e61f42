package com.etbike.server.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.etbike.server.domain.model.Account;
import com.etbike.server.service.AuthService;
import com.etbike.server.support.social.SignInUtils;
import com.etbike.server.support.social.SignupForm;

@Controller
public class AuthController {
	@Autowired private AuthService authService;

	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public void signin() {}

	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public SignupForm signupForm(WebRequest request) {
		Connection<?> connection = ProviderSignInUtils.getConnection(request);
		if (connection != null) {
			return SignupForm.fromProviderUser(connection.fetchUserProfile());
		} else {
			return new SignupForm();
		}
	}

	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(@Valid SignupForm form, BindingResult formBinding, WebRequest request) {
		if (formBinding.hasErrors()) {
			return null;
		}
		
		Account account = createAccount(form, formBinding);
		if (account != null) {
			SignInUtils.signin(account.getUsername());
			ProviderSignInUtils.handlePostSignUp(account.getUsername(), request);
			return "redirect:/";
		}
		return null;
	}

	private Account createAccount(SignupForm form, BindingResult formBinding) {
		try {
			return authService.save(new Account(form.getUsername(), form.getPassword(), form.getFirstName(), form.getLastName()));
		} catch (Exception e) {
			formBinding.rejectValue("username", "user.duplicateUsername", "already in use");
			return null;
		}
	}
}
