package com.wanger.service;

import java.util.HashMap;
import java.util.List;

import com.wanger.domain.SysMenuEntity;
import com.wanger.domain.SysRoleEntity;
import com.wanger.domain.UserEntity;

public interface UserService {

	UserEntity getUser(String userCode);
	
	HashMap<String, Object> queryUser();
	
	UserEntity getUserByUserName(String userName);

	/**
	 * 根据用户ID 查询所有角色信息
	 * @param userCode
	 * @return
	 */
	List<SysRoleEntity> queryRoleByUserCode(String userCode);
	
	/**
	 * 根据用户ID 查询所有菜单
	 * @param userCode
	 * @return
	 */
	List<SysMenuEntity> queryMenuByUserCode(String userCode);
	
}
