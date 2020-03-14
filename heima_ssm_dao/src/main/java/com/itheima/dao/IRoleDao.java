package com.itheima.dao;

import com.itheima.domain.Permission;
import com.itheima.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IRoleDao {
    /**
     * 根据用户id查出角色
     *
     * @param userId
     * @return
     * @throws Exception
     */
    //select * from role where id in (select roleId from users_role where userid = '1');
    @Select("select * from role where id in (select roleId from users_role where userid = #{userId})")
    @Results({
            @Result(id = true, property = "id", column = "id"),  /* 这是表示 orders中的id与表中id列对应，并且这个字段是主键*/
            @Result(property = "roleName", column = "roleName"),
            @Result(property = "roleDesc", column = "roleDesc"),
            /**
             * 通过当前的role id去查询permission信息
             * 下面这句话的意思是
             *  查询Role类中的permissions，需要用到Role类的id，会调用com.itheima.dao.IPermissionDao.findPermissionByRoleId,返回一个list
             */
            @Result(property = "permissions", column = "id", javaType = java.util.List.class, many = @Many(select = "com.itheima.dao.IPermissionDao.findPermissionByRoleId"))

    })
    public List<Role> findRoleByUserId(String userId) throws Exception;

    /**
     * 查询所有角色
     *
     * @return
     */
    @Select("select * from role")
    public List<Role> findAll() throws Exception;

    /**
     * 新增角色
     *
     * @param role
     */
    @Insert("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    public void save(Role role);

    /**
     * 查询角色详情
     *
     * @param id
     * @return
     */
    @Select("select* from role where id = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),  /* 这是表示 orders中的id与表中id列对应，并且这个字段是主键*/
            @Result(property = "roleName", column = "roleName"),
            @Result(property = "roleDesc", column = "roleDesc"),
            /**
             * 通过当前的role id去查询permission信息
             * 下面这句话的意思是
             *  查询Role类中的permissions，需要用到Role类的id，会调用com.itheima.dao.IPermissionDao.findPermissionByRoleId,返回一个list
             */
            @Result(property = "permissions", column = "id", javaType = java.util.List.class, many = @Many(select = "com.itheima.dao.IPermissionDao.findPermissionByRoleId")),
            @Result(property = "users", column = "id", javaType = java.util.List.class, many = @Many(select = "com.itheima.dao.IUserDao.findUserByRoleId"))
    })
    public Role findById(String id);

    /**
     * 删除一个角色
     *
     * @param id
     */
    @Delete("delete from role where id= #{id}")
    public void deleteById(String id) throws Exception;

    /**
     * 删除users_role中间表相关联的数据
     * @param id
     */
    @Delete("delete from users_role where roleid = #{id}")
    public void deleteFromRole_UserByRoleId(String id)throws Exception;

    /**
     * 删除role_permission中间表相关联的数据
     * @param id
     */
    @Delete("delete from role_permission where roleid = #{id}")
    public void deleteFromRole_PermissionByRole(String id)throws Exception;

    /**
     * 查询角色没有的权限信息
     * @param roleId
     * @return
     */
    @Select("select * from permission where id not in (select permissionid from role_permission where roleid=#{roleId} )")
    public List<Permission> findOtherPermission(String roleId)throws Exception;

    /**
     * 给角添加权限
     * @param roleId
     * @param permissionId
     * @throws Exception
     */
    //insert into role_permission values('3','01E8F20E44484A29A7BB4A5CF86C9584')
    @Insert("insert into role_permission values(#{pid},#{rid})")
    public void addPermissionToRole(@Param("rid") String roleId, @Param("pid") String permissionId)throws Exception;
}
