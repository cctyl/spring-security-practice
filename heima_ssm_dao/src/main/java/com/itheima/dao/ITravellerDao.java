package com.itheima.dao;

import com.itheima.domain.Traveller;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ITravellerDao {

    //这里查travllers，是先根据ordersId查到travellerId，再用travellerId去查出Traveller.这实际上用到的是子查询
    //select * from traveller where id in (select travellerID from order_traveller where orderId = '0E7231DC797C486290E8713CA3C6ECCC');
    @Select("select * from traveller where id in (select travellerID from order_traveller where orderId = #{orderId})")
    public List<Traveller> findByOrdersId(String orderId) throws Exception;
}
