package com.neuedu.sell.entity;

import com.neuedu.sell.enums.OrderStatusEnum;
import com.neuedu.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@DynamicUpdate//动态修改
public class OrderMaster {
    @Id
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

}
