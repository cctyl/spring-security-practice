package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.dao.IUserDao;
import com.itheima.domain.Role;
import com.itheima.domain.UserInfo;
import com.itheima.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

   @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    /**
     * 根据用户名查询用户
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserInfo userInfo =null;
        try {
             userInfo = userDao.findByUsername(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        * 显然，这里要返回的是UserDetails，但是我们查到的是UserInfo
        * 那么如何进行转换呢？
        * 创建一个security提供的user对象，然后把用户名密码等数据放进去
        * 密码没做密文的情况下，需要加上一个 {noop}前缀
        * 第三个参数，是指这个用户是否启用 0不可用，1可用
        * 最后一个参数，是用户的权限信息 实际上，这个权限信息也是存在数据库中的。应该直接从userInfo中取出
        * 返回这个user即可
        * */


        User user = new User(userInfo.getUsername(),userInfo.getPassword(),userInfo.getStatus()==1?true:false,true,true,true,getAuthority(userInfo.getRoles()));
        return user;

    }

    /**
     * 这个就是为了拿到一个角色描述
     * @return
     */
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roleList) {
        /**
         * 根据角色表中的信息，手动给用户添加权限
         */
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        //添加权限
        for (Role role : roleList) {
            //ROLE_ADMIN  ROLE_USER
            //角色名称似乎都是规定为ROLE_开头，后面的自己写
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }
        return list;

    }


    /**
     * 查询所有用户
     * @return
     */
    @Override
   public List<UserInfo> findAll(int page, int size) throws Exception{
        PageHelper.startPage(page, size);
        return userDao.findAll();
    }

    /**
     * 保存用户
     * @param userInfo
     */
    @Override
    public void save(UserInfo userInfo) throws Exception {
        //加密密码
        String encode = passwordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(encode);

        userDao.save(userInfo);

    }

    @Override
    public UserInfo findById(String id) throws Exception {
        return userDao.findById(id);
    }

    @Override
    public List<Role> findOtherRoles(String userId) throws Exception {
        return userDao.findOtherRoles(userId);
    }

    @Override
    public void addRoleToUser(String userId, String[] roleIds) {
        for (String roleId : roleIds) {
            userDao.addRoleToUser(userId,roleId);
        }



    }
}
