package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.Orders;
import com.itheima.service.IOrdersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private IOrdersService ordersService;

    /**
     * 查询全部订单，未分页



    /**
     * 查询全部订单
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,@RequestParam(name="size",required = true,defaultValue = "4")Integer size) throws Exception {

        ModelAndView mv =new ModelAndView();
        List<Orders> all = ordersService.findAll(page, size);
        PageInfo pageInfo = new PageInfo(all);//把查到的list封装到pageInfo里面
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("orders-page-list");
        return mv;

    }

    /**
     * 查询订单详情
     * @param ordersId
     * @return
     * @throws Exception
     */
    @RequestMapping("/findById.do")
    @Secured("ROLE_ADMIN")
    public ModelAndView findById(@RequestParam(name = "id",required = true) String ordersId) throws Exception {

        ModelAndView mv = new ModelAndView();
        Orders order = ordersService.findById(ordersId);

        mv.addObject("orders",order);
        mv.setViewName("orders-show");

        return mv;

    }
}






















