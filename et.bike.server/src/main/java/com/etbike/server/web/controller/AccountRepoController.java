package com.etbike.server.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etbike.server.domain.model.Account;
import com.etbike.server.domain.model.UploadedFile;
import com.etbike.server.persistence.AccountRepository;
import com.etbike.server.persistence.FileRepository;
import com.etbike.server.persistence.FileSpecifications;

@Controller
public class AccountRepoController {
	@Autowired AccountRepository accountRepository;
	@Autowired private FileRepository fileRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value="/account/addUser")
	@ResponseBody
	public String addUser(String firstName, String lastName, String password, String username, String myImagePath){
		
		Account tempAccount  = accountRepository.findByUsername(username);
		
		if(tempAccount == null){
			Account account = new Account(username, password, firstName, lastName);
			account.setPassword(passwordEncoder.encodePassword(account.getPassword(), account.getUsername()));
			
			List<UploadedFile> myImages =fileRepository.findAll(FileSpecifications.isfileName(myImagePath));
			UploadedFile selectedImege = myImages.get(myImages.size() - 1);
			account.setMyImagePath(selectedImege.getFileDownloadUrl());
			
			accountRepository.saveAndFlush(account);
			
			return "OKAY";
			
		} else{
			System.err.println("Error : Already existent username...");
			return "ERROR : Already existent username ";
		}
	}
	
	@RequestMapping(value="/account/getUser/{username}")
	@ResponseBody
	public String getUser(@PathVariable String username){
		Account account = accountRepository.findByUsername(username);
		account.getId();
		return account.getFirstName();
	}
	
	@RequestMapping(value="/account/myInfo/{username}")
	public String getMyDealList(@PathVariable String username, ModelMap map){
		Account account = accountRepository.findByUsername(username);
		map.put("account", account);
		return "jsonView";

	}
	
	@RequestMapping(value="/account/updateGrade")
	@ResponseBody
	public String updateGrade(String username,String grade){
		
		Account selectedAccount  = accountRepository.findByUsername(username);
		
		if(selectedAccount != null){
			selectedAccount.setGrade(grade); //update grade
			accountRepository.saveAndFlush(selectedAccount);
			
			return "OKAY";
			
		} else{
			System.err.println("Error : There is not username...");
			return "Error : There is not username ";
		}
	}
}
