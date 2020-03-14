package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.dao.IRoleDao;
import com.itheima.domain.Permission;
import com.itheima.domain.Role;
import com.itheima.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    @Override
    public List<Role> findAll(int page, int size) throws Exception {
        PageHelper.startPage(page, size);
        return roleDao.findAll();
    }

    @Override
    public void save(Role role) throws Exception {
        roleDao.save(role);
    }

    @Override
    public Role findById(String id)throws Exception {
        return roleDao.findById(id);
    }

    @Override
    public void deleteById(String id) throws Exception {
        //删除role_user中间表与这个id关联的数据
        roleDao.deleteFromRole_UserByRoleId(id);
        //删除role_permission中间表相关联的数据
        roleDao.deleteFromRole_PermissionByRole(id);
        roleDao.deleteById(id);

    }

    @Override
    public List<Permission> findOtherPermission(String id) throws Exception {
     return   roleDao.findOtherPermission(id);
    }

    @Override
    public void addPermissionToRole(String roleId, String[] permissionIds)throws Exception {
        for (String permissionId : permissionIds) {
            roleDao.addPermissionToRole(roleId,permissionId);
        }

    }
}
