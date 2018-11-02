package com.neuedu.sell.dto;

import com.neuedu.sell.entity.OrderDetail;
import com.neuedu.sell.enums.OrderStatusEnum;
import com.neuedu.sell.enums.PayStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus=OrderStatusEnum.NEW.getCode();
    private Integer payStatus=PayStatusEnum.NOT_PAY.getCode();
    private Date createTime;
    private Date updateTime;
    /* 订单详情集合 */
    private List<OrderDetail> orderDetails;
}
