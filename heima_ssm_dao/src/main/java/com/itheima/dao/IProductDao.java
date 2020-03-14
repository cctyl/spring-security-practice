package com.itheima.dao;

import com.itheima.domain.Product;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IProductDao {

    /**
     * 根据id查询产品，是为了查询Orders时封装Product用的
     * @return
     * @throws Exception
     */
    @Select("select * from product where id=#{id}")
    public Product findById() throws Exception;

    //查询所有的产品信息
    @Select("select * from product")
    public List<Product> findAll() throws Exception;


    /**
     * 保存产品
     * @param product
     */
    @Insert("insert into product(productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus) values(" +
            "#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    void save(Product product);
}
