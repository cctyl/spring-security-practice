package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.SysLog;
import com.itheima.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    private ISysLogService sysLogService;
    /**
     * 查询全部日志
     * @return
     */
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page",defaultValue = "1") Integer page, @RequestParam(name ="size",defaultValue = "4") Integer size){
        ModelAndView mv = new ModelAndView();
        List<SysLog> logList = sysLogService.findAll(page,size);
        PageInfo pageInfo = new PageInfo(logList);
        mv .addObject("pageInfo",pageInfo);
        mv.setViewName("syslog-list");
        return mv;

    }
}
