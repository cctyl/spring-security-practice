package com.itheima.service;

import com.itheima.domain.Role;
import com.itheima.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService  extends UserDetailsService {
   /**
    * 查询全部用户
    * @param page
    * @param size
    * @return
    * @throws Exception
    */
   public List<UserInfo> findAll(int page, int size) throws Exception;

   /**
    * 保存用户
    * @param userInfo
    */
   public void save(UserInfo userInfo) throws Exception;

    /**
     * 根据id查询用户
     * @param id
     * @return
     * @throws Exception
     */
    public UserInfo findById(String id)throws Exception;

    /**
     * 根据用户id查询没有添加的角色
     * @param userId
     * @return
     * @throws Exception
     */
    public List<Role> findOtherRoles(String userId)throws Exception;

    /**
     * 给用户添加角色
     * @param userId
     * @param roleIds
     */
    public void addRoleToUser(String userId, String[] roleIds);
}
