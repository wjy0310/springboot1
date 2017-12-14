package com.wanger.utils.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wanger.domain.SysMenuEntity;
import com.wanger.domain.SysRoleEntity;
import com.wanger.domain.UserEntity;
import com.wanger.service.UserService;
import com.wanger.utils.constants.common.MVCConstants;

public class MyShiroRealm extends AuthorizingRealm {

	private Logger logger =  LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	
	/**  
     * 权限认证（为当前登录的Subject授予角色和权限）  
     *  
     * 该方法的调用时机为需授权资源被访问时，并且每次访问需授权资源都会执行该方法中的逻辑，这表明本例中并未启用AuthorizationCache，  
     * 如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），  
     * 超过这个时间间隔再刷新页面，该方法会被执行  
     *  
     * doGetAuthorizationInfo()是权限控制，  
     * 当访问到页面的时候，使用了相应的注解或者shiro标签才会执行此方法否则不会执行，  
     * 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可  
     */ 
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		logger.info("##################执行Shiro权限认证##################");
		//获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
        String loginName = (String)super.getAvailablePrincipal(principalCollection); 
        //到数据库查是否有此对象
        UserEntity user = userService.getUserByUserName(loginName);
        if(user!=null){
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
            //用户的角色集合
            List<SysRoleEntity> rolelist = userService.queryRoleByUserCode(user.getUser_code());
            if(null != rolelist && rolelist.size() > 0){
            	List<String> roleNames = new ArrayList<>();
            	 for (SysRoleEntity roleName : rolelist) {
            		 info.addRole(roleName.getRole_name());
     			}
            }
           
           
            //用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
            List<SysMenuEntity> menuList = userService.queryMenuByUserCode(user.getUser_code());
            for (SysMenuEntity menu : menuList) {
                info.addStringPermission(menu.getUrl());
            }
            // 或者按下面这样添加
            //添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色    
//            simpleAuthorInfo.addRole("admin");  
            //添加权限  
//            simpleAuthorInfo.addStringPermission("admin:manage");  
//            logger.info("已为用户[mike]赋予了[admin]角色和[admin:manage]权限");
            return info;
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;  
        logger.info("登录认证!");  
        logger.info("验证当前Subject时获取到token为：" + token);  
        UserEntity user = userService.getUserByUserName(token.getUsername());  
        if (user != null){  
            logger.info("用户: " + user.getUsername());  
            if(user.getIs_del() != 0){  
                throw new DisabledAccountException();  
            }
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            session.setAttribute(MVCConstants.SESSION_USER, user);
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验  
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), user.getUsername()); 
        }  
        return null;  
	}
    
}
