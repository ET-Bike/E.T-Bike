package com.etbike.server.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etbike.server.domain.model.Account;
import com.etbike.server.persistence.AccountRepository;

@Controller
public class AccountRepoController {
	@Autowired AccountRepository accountRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value="/account/addUser")
	@ResponseBody
	public String addUser(String firstName, String lastName, String password, String username){
		Account account = new Account(username, password, firstName, lastName);
		account.setPassword(passwordEncoder.encodePassword(account.getPassword(), account.getUsername()));
		accountRepository.saveAndFlush(account);
		
		//System.err.println("GET Error");
		return "OKAY";
	}
	
	@RequestMapping(value="/account/getUser/{username}")
	@ResponseBody
	public String getUser(@PathVariable String username){
		Account account = accountRepository.findByUsername(username);
		account.getId();
		return account.getFirstName();
	}
}
