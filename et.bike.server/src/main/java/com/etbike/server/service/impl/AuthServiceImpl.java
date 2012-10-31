package com.etbike.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.etbike.server.domain.model.Account;
import com.etbike.server.persistence.AccountRepository;
import com.etbike.server.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired private AccountRepository accountRepository;
	@Autowired private PasswordEncoder passwordEncoder;

	@Override
	public Account save(Account account) {
		account.setPassword(passwordEncoder.encodePassword(account.getPassword(), account.getUsername()));
		return accountRepository.save(account);
	}
	
}
