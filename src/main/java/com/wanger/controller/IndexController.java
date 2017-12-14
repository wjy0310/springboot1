package com.wanger.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanger.domain.UserEntity;
import com.wanger.service.UserService;

@Controller
public class IndexController {

	@Autowired
	UserService UserService;
	
	@RequestMapping("/index")
	public String index() {
		return "index/index";
	}
	
	
	@RequestMapping("/")
	public String findUser(String userCode){
//		UserEntity user =  UserService.getUser(userCode);
		return "";
	}
}
