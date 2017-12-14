package com.wanger.dao;

import java.util.List;

import com.wanger.domain.SysMenuEntity;

public interface SysMenuDao {

    public int deleteByPrimaryKey(String menu_code);

    public int insertSelective(SysMenuEntity record);

    public SysMenuEntity selectByPrimaryKey(String menu_code);

    public int updateByPrimaryKeySelective(SysMenuEntity record);

    /**
     * 通过角色ID 查询所有菜单
     * @param roleCode
     * @return
     */
    List<SysMenuEntity> getMenuByRole(String roleCode);
}