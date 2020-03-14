package com.itheima.service;

import com.itheima.domain.SysLog;

import java.util.List;

public interface ISysLogService {
    /**
     * 保存日志信息
     * @param sysLog
     * @throws Exception
     */
    public void save(SysLog sysLog)throws Exception;

    /**
     * 查询全部日志信息
     * @return
     * @param page
     * @param size
     */
    public  List<SysLog> findAll(Integer page, Integer size);
}
