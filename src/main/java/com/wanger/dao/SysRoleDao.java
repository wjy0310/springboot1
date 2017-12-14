package com.wanger.dao;

import java.util.List;

import com.wanger.domain.SysRoleEntity;

public interface SysRoleDao {

    public int deleteByPrimaryKey(String role_code);

    public int insertSelective(SysRoleEntity record);

    public SysRoleEntity selectByPrimaryKey(String role_code);

    public int updateByPrimaryKeySelective(SysRoleEntity record);
    
    /**
     * 根据用户ID 查询所有角色
     * @param userCode
     * @return
     */
    List<SysRoleEntity> getRoleByUser(String userCode);

}