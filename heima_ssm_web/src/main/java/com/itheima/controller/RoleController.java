package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Permission;
import com.itheima.domain.Role;
import com.itheima.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    /**
     * 查询所有角色（只包含基本信息）
     *
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name="page",required = true) Integer page, @RequestParam(name="size",required = true)Integer size) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Role> roleList = roleService.findAll(page, size);
        PageInfo pageInfo = new PageInfo(roleList);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("role-list");
        return mv;
    }

    /**
     * 新增角色（只包含简单信息）
     *
     * @param role
     * @return
     */
    @RequestMapping("/save.do")
    @Secured("ROLE_ADMIN")
    public String save(Role role) throws Exception {
        roleService.save(role);
        return "redirect:findAll.do?page=1&size=4";
    }

    /**
     * 查询角色详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById.do")
    @Secured("ROLE_ADMIN")
    public ModelAndView findById(@RequestParam(name = "id", required = true) String id) throws Exception {

        ModelAndView mv = new ModelAndView();
        Role role = roleService.findById(id);
        mv.addObject("role", role);
        mv.setViewName("role-show");

        return mv;

    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteById.do")
    @Secured("ROLE_ADMIN")
    public String deleteRoleById(@RequestParam(name = "id", required = true) String id) throws Exception {
        roleService.deleteById(id);
        return "redirect:findAll.do?page=1&size=4";
    }

    /**
     * 查询角色详情以及角色没有的权限
     *
     * @param id
     * @return
     */
    @RequestMapping("/findUserByIdAndAllRole.do")
    @Secured("ROLE_ADMIN")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id", required = true) String id) throws Exception {
        ModelAndView mv = new ModelAndView();
        Role role = roleService.findById(id);
        List<Permission> permissionList = roleService.findOtherPermission(id);

        mv.addObject("role", role);
        mv.addObject("permissionList", permissionList);
        mv.setViewName("role-permission-add");
        return mv ;
    }

    /**
     * 给角色添加权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    @RequestMapping("/addPermissionToRole.do")
    @Secured("ROLE_ADMIN")
    public String addPermissionToRole(@RequestParam(name = "roleId",required = true) String roleId,@RequestParam(name = "ids",required = true) String[] permissionIds) throws Exception {
        roleService.addPermissionToRole(roleId,permissionIds);
        return "redirect:findAll.do?page=1&size=4";
    }
}
