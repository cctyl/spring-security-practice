package com.itheima.service;

import com.itheima.domain.Permission;
import com.itheima.domain.Role;

import java.util.List;

public interface IRoleService {

    /**
     * 查询所有角色
     *
     * @return
     */
    public List<Role> findAll(int page, int size) throws Exception;

    /**
     * 新增角色
     *
     * @param role
     * @throws Exception
     */
    public void save(Role role) throws Exception;

    /**
     * 查询角色详情
     *
     * @param id
     * @return
     */
    public Role findById(String id) throws Exception;

    /**
     * 删除角色
     *
     * @param id
     */
    public void deleteById(String id) throws Exception;

    /**
     * 查询角色没有的权限信息
     *
     * @param id
     * @return
     */
    public List<Permission> findOtherPermission(String id) throws Exception;

    /**
     * 给角色添加权限
     * @param roleId
     * @param permissionIds
     */
    public void addPermissionToRole(String roleId, String[] permissionIds)throws Exception;
}
