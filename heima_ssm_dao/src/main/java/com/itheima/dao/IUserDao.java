package com.itheima.dao;

import com.itheima.domain.Role;
import com.itheima.domain.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IUserDao {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     * @throws Exception
     */
    @Select("select  * from users where username=#{username}")
    @Results({
            @Result(id=true,property = "id" ,column = "id"),  /* 这是表示 orders中的id与表中id列对应，并且这个字段是主键*/
            @Result(property = "username",column = "username"),
            @Result(property = "email",column = "email"),
            @Result(property = "password",column = "password"),
            @Result(property = "phoneNum",column = "phoneNum"),
            @Result(property = "status",column = "status"),

            //怎么查出用户的角色呢？显然是要去角色表中拿出来
            //user表和角色表之间的关系，需要通过一张中间表来查询，也就是说，通过userid，去角色表中找到角色id
            //再去角色表中，通过角色id查到角色
            @Result(property = "roles",column = "id",javaType =java.util.List.class,many = @Many(select="com.itheima.dao.IRoleDao.findRoleByUserId"))
    })
    public UserInfo findByUsername(String  username) throws Exception;


    /**
     * 查询所有用户
     * @return
     */
    //因为这里不需要封装Role对象，所以可以不进行联合查询
    @Select("select * from users")
   public List<UserInfo> findAll() throws Exception;


    /**
     * 保存用户
     * @param userInfo
     */
    @Insert("insert into users(email,username,password,phoneNum,status) values(#{email},#{username},#{password},#{phoneNum},#{status})")
   public void save(UserInfo userInfo) throws Exception;

    /**
     * 通过id查询用户
     * @param id
     * @return
     * @throws Exception
     */
    @Select("select * from users where id = #{id}")
    @Results({
            @Result(id=true,property = "id" ,column = "id"),  /* 这是表示 orders中的id与表中id列对应，并且这个字段是主键*/
            @Result(property = "username",column = "username"),
            @Result(property = "email",column = "email"),
            @Result(property = "password",column = "password"),
            @Result(property = "phoneNum",column = "phoneNum"),
            @Result(property = "status",column = "status"),
            @Result(property = "roles",column = "id",javaType =java.util.List.class,many = @Many(select="com.itheima.dao.IRoleDao.findRoleByUserId")),
            /**
             * 下面要根据role id去查询一个permission
             * 先要到RoleDao里的findRoleByUserId，它也需要用@Results封装数据
             * 逻辑是这样的：
             *      userdao调用roledao查询出Role
             *      roleDao在查询role的时候，同时把permission查询出来，封装到Role里面，返回给userDao
             *      这样userDao查到的role，就已经包含了permission信息
             */

    })
    public UserInfo findById(String id)throws Exception;


    /**
     * 根据roleid查询用户
     * @param roleId
     * @return
     */
    @Select("select * from users where id in (select userid from users_role where roleid = #{roleId} )")
    public List<UserInfo> findUserByRoleId(String roleId);


    /**
     * 根据用户id查询没有添加的角色
     * @param userId
     * @return
     * @throws Exception
     */
    @Select("select * from role where id not in(select roleId from  users_role where userId =#{userId})")
    public List<Role> findOtherRoles(String userId);

    /**
     * 给用户添加角色
     * @param userId
     * @param roleId
     */
    @Insert("insert into users_role values(#{userId},#{roleId})")
    public void addRoleToUser(@Param("userId") String userId, @Param("roleId") String roleId);
}
