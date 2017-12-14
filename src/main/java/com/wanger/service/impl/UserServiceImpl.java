package com.wanger.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanger.dao.SysMenuDao;
import com.wanger.dao.SysRoleDao;
import com.wanger.dao.SysRoleMenuDao;
import com.wanger.dao.SysUserRoleDao;
import com.wanger.dao.UserDao;
import com.wanger.domain.SysMenuEntity;
import com.wanger.domain.SysRoleEntity;
import com.wanger.domain.UserEntity;
import com.wanger.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private SysMenuDao menuDao;
	@Autowired
	private SysRoleDao roleDao;

	@Cacheable(value = "wangerCache", key = "#userCode")
	@Override
	public UserEntity getUser(String userCode) {
		UserEntity user = userDao.selectByPrimaryKey(userCode);
		return user;
	}

	@Override
	public HashMap<String, Object> queryUser() {
		// 分页处理，显示第一页的10条数据
		PageHelper.startPage(1, 10);
		List<UserEntity> list = userDao.queryUser(null);// 查询
		// 取分页信息
		PageInfo<UserEntity> pageInfo = new PageInfo<UserEntity>(list);
		long total = pageInfo.getTotal(); // 获取总记录数

		HashMap<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("total", total);
		return map;
	}

	@Override
	public UserEntity getUserByUserName(String userName) {
		UserEntity user = userDao.selectByUserName(userName);
		return user;
	}

	@Override
	public List<SysRoleEntity> queryRoleByUserCode(String userCode) {
		List<SysRoleEntity> roleList = roleDao.getRoleByUser(userCode);
		return roleList;
	}

	@Override
	public List<SysMenuEntity> queryMenuByUserCode(String userCode) {

		List<SysRoleEntity> roleList = queryRoleByUserCode(userCode);
		if (null != roleList && roleList.size() > 0) {
			Set<SysMenuEntity> menuSet = new HashSet<SysMenuEntity>();
			for (SysRoleEntity roleEntity : roleList) {
				List<SysMenuEntity> menuList= menuDao.getMenuByRole(roleEntity.getRole_code());
				if(null != menuList && menuList.size() > 0){
					menuSet.addAll(menuList);
				}
			}
			List<SysMenuEntity> menuList = new ArrayList<SysMenuEntity>();
				menuList.addAll(menuSet);
				return menuList;
		}
		return null;
	}

}
