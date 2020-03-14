package com.itheima.dao;

import com.itheima.domain.Permission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IPermissionDao {

    /**
     * 根据roleId查询permission
     * @param roleId
     * @return
     */
    @Select("select * from permission where id in (select permissionId from role_permission where roleid =#{roleId})")
    public List<Permission> findPermissionByRoleId(String roleId) throws Exception;

    /**
     * 查询全部
     * @return
     */
    @Select("select * from permission")
    public List<Permission> findAll() throws Exception;

    /**
     * 添加一个权限
     * @param permission
     */
    @Insert("insert into permission(permissionName,url) values(#{permissionName},#{url})")
    public void save(Permission permission);

    /**
     * 删除中间表相关联的信息
     * @param id
     */
    @Delete("delete from role_permission where permissionId = #{id}")
    public void deleteFromRole_PermissionByPermissionId(String id);

    /**
     * 删除一个权限
     * @param id
     */
    @Delete("delete from permission where id = #{id}")
    public void deleteById(String id);
}
