package com.ecommerce.agent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("orders")
public class Order {
    @TableId(type = IdType.AUTO)

    private Long id;
    private String orderNo;
    private String goods;
    private Integer price;
    private String status;
//    private String createTime;
    private Date createTime;
}
