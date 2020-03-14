package com.itheima.dao;

import com.itheima.domain.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ISysLogDao {
    /**
     * 保存日志信息
     * @param sysLog
     * @throws Exception
     */
    @Insert("insert into syslog(visitTime,username,ip,url,executionTime,method) values(#{visitTime},#{username},#{ip},#{url},#{executionTime},#{method})")
    public void save(SysLog sysLog)throws Exception;

    /**
     * 查询全部日志信息
     * @param page
     * @param size
     * @return
     */
    @Select("select * from syslog")
    public List<SysLog> findAll(Integer page, Integer size);
}
