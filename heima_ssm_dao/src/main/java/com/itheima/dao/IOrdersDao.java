package com.itheima.dao;

import com.itheima.domain.Member;
import com.itheima.domain.Orders;
import com.itheima.domain.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.core.annotation.Order;

import java.util.List;

public interface IOrdersDao {
    /**
     * 查询全部订单
     * @return 订单集合
     */
    @Select("select* from orders")
    @Results({
            @Result(id=true,property = "id" ,column = "id"),  /* 这是表示 orders中的id与表中id列对应，并且这个字段是主键*/
            @Result(property = "orderNum",column = "orderNum"),
            @Result(property = "orderTime",column = "orderTime"),
            @Result(property = "orderStatus",column = "orderStatus"),
            @Result(property = "peopleCount",column = "peopleCount"),
            @Result(property = "payType",column = "payType"),
            @Result(property = "orderDesc",column = "orderDesc"),
            /*以下开始针对 类中的其他类*/
            @Result(property = "product",column = "productId",javaType = Product.class,one = @One(select = "com.itheima.dao.IProductDao.findById"))//类中的product对应表中的productId，后面两个属性，其实是通过调用ProductDao查出Product，再封装到这里。
                                                                                                                // 也就是说，本质还是多个dao查出的数据再封装到同一个类里面



    })
    public List<Orders> findAll() throws Exception;

    /**
     * 根据id查询订单详情
     * 涉及多表操作
     * @return
     */
    @Select("select* from orders where id= #{ordersId}")
    @Results({
            @Result(id=true,property = "id" ,column = "id"),  /* 这是表示 orders中的id与表中id列对应，并且这个字段是主键*/
            @Result(property = "orderNum",column = "orderNum"),
            @Result(property = "orderTime",column = "orderTime"),
            @Result(property = "orderStatus",column = "orderStatus"),
            @Result(property = "peopleCount",column = "peopleCount"),
            @Result(property = "payType",column = "payType"),
            @Result(property = "orderDesc",column = "orderDesc"),
            /*以下开始针对 类中的其他类*/
            //查出product
            @Result(property = "product",column = "productId",javaType = Product.class,one = @One(select = "com.itheima.dao.IProductDao.findById")),
            //查出membeer
            @Result(property = "member",column = "memberId",javaType = Member.class,one = @One(select = "com.itheima.dao.IMemberDao.findById")),
            //查出List<Traveller> 应该是使用中间表来查询
            //这里的逻辑是，拿到orders的id，去中间表查询traveller，应该查到多个traveller,所以下面应该是many而不是one
            //另外，他这里查travllers，是先根据ordersId查到travellerId，再用travellerId去查出Traveller.这实际上用到的是子查询
            @Result(property = "travellers",column = "id",javaType =java.util.List.class,many = @Many(select="com.itheima.dao.ITravellerDao.findByOrdersId"))


    })
    public Orders findById(String ordersId) throws Exception;
}
