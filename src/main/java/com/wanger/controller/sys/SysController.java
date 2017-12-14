package com.wanger.controller.sys;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanger.domain.UserEntity;
import com.wanger.service.UserService;

@Controller
@RequestMapping("/sys")
public class SysController {

	@Autowired
	UserService UserService;
	
	@RequestMapping("/menuManager")
	public String index() {
		return "temp/table";
	}
	
	
	@RequestMapping("/")
	public String findUser(String userCode){
//		UserEntity user =  UserService.getUser(userCode);
		return "";
	}
}
