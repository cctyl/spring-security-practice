package com.itheima.service;

import com.itheima.domain.Permission;

import java.util.List;

public interface IPermissionService {
    /**
     * 查询全部权限
     * @return
     */
    public List<Permission> findAll(int page,int size) throws Exception;

    /**
     * 新增权限信息
     * @param permission
     * @throws Exception
     */
    public void save(Permission permission)throws Exception;

    /**
     * 删除权限
     */
    public void deleteById(String id);
}
