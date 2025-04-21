package com.hixtrip.sample.client.order.vo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author lill
 */
@Data
@SuperBuilder(toBuilder = true)
public class OrderVO {

    /**
     * 主键
     */
    private Long id;
    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 名称
     */
    private String name;


}
