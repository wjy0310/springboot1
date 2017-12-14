package com.wanger.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wanger.domain.UserEntity;
import com.wanger.service.UserService;
import com.wanger.utils.constants.common.MVCConstants;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/login")
public class LoginController {
	private Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserService userService;

	@RequestMapping("/index")
	public String enterLogin(){
		return "login/login";
	}
	
	@RequestMapping("/login")
	ModelAndView login(String userName, String password,HttpSession httpSession){
		logger.info("userName:"+userName +",password:"+password);
		ModelAndView mav = new ModelAndView();
		
		Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try{
            subject.login(token);//会跳到我们自定义的realm中
//            httpSession.setAttribute(MVCConstants.SESSION_USER, token.getPrincipal().);
            mav.setViewName("index/index");
            System.out.println(httpSession.getAttribute(MVCConstants.SESSION_USER));
			return mav;
        }catch(Exception e){
            e.printStackTrace();
            mav.addObject("message","用户密码错误！");
    		mav.setViewName("login/login");
    		return mav;
        }
		
	}
}
