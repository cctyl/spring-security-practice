package com.itheima.service;

import com.itheima.domain.Orders;

import java.util.List;

public interface IOrdersService {

    /**
     * 查询所有订单
     *
     * @return
     */
    public List<Orders> findAll(int page, int size) throws Exception;

    /**
     * 根据id查询订单详情
     * @param ordersId
     * @return
     */
    public Orders findById(String ordersId) throws Exception;
}
