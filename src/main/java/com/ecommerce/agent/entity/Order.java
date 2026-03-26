package com.ecommerce.agent.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

@Data
@TableName("orders")
public class Order {
    private Long id;
    private String orderNo;
    private String goods;
    private Integer price;
    private String status;
    private Date createTime;
}
