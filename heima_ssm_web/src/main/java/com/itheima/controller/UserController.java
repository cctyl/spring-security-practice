package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Msg;
import com.itheima.domain.Role;
import com.itheima.domain.UserInfo;
import com.itheima.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 查询登录用户信息
     * @return
     */
    @RequestMapping("/findUser.do")
    @ResponseBody
    public Msg findUser(){
        //从上下文中获了当前登录的用户
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();
        return Msg.success().add("user",user.getUsername());
    }


    /**
     * 查询全部员工
     *
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @RequestMapping("/findAll.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findAll(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page, @RequestParam(name = "size", required = true, defaultValue = "4") Integer size) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<UserInfo> userInfoList = userService.findAll(page, size);
        PageInfo pageInfo = new PageInfo(userInfoList);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("user-list");
        return mv;
    }


    /**
     * 保存用户
     *
     * @param userInfo
     * @return
     * @throws Exception
     */
    @RequestMapping("/save.do")
    @Secured("ROLE_ADMIN")
    @PreAuthorize("authentication.principal.username == 'admin'")
    public String save(UserInfo userInfo) throws Exception {
        userService.save(userInfo);
        return "redirect:findAll.do";//因为不需要携带数据，只是让他看一下，新增的用户。所以不返回modelAndview

    }

    /**
     * 通过id查询用户详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById.do")
    @Secured("ROLE_ADMIN")
    public ModelAndView findById(@RequestParam(name = "id", required = true) String id) throws Exception {

        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = userService.findById(id);
        mv.addObject("user", userInfo);
        mv.setViewName("user-show");
        return mv;

    }

    /**
     * 查询用户信息以及没有添加的角色信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @RequestMapping("/findUserByIdAndAllRole.do")
    @Secured("ROLE_ADMIN")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id", required = true) String userId) throws Exception {
        ModelAndView mv = new ModelAndView();
        //1.根据用户id查询用户
        UserInfo userInfo = userService.findById(userId);
        //2.根据用户id查询可以添加的角色
        List<Role> otherRoles = userService.findOtherRoles(userId);
        mv.addObject("user", userInfo);
        mv.addObject("roleList", otherRoles);
        mv.setViewName("user-role-add");
        return mv;
    }


    /**
     * 给用户添加角色
     *
     * @param userId
     * @param roleIds
     */

    //给用户添加角色
    @RequestMapping("/addRoleToUser.do")
    @Secured("ROLE_ADMIN")
    public String addRoleToUser(@RequestParam(name = "userId", required = true) String userId, @RequestParam(name = "ids", required = true) String[] roleIds) {
        userService.addRoleToUser(userId, roleIds);
        return "redirect:findAll.do";
    }

}
