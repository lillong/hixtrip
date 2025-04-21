package com.hixtrip.sample.domain.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单表
 * @author lill
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Order {

    //支付状态-未支付
    public final static Integer PLAY_STATUS_NO= 0;

    //支付状态-成功
    public final static Integer PLAY_STATUS_SUCCEED = 1;

    //支付状态-失败
    public final static Integer PLAY_STATUS_FAIL = -1;

    /**
     * ID
     */
    private Long id;

    /**
     * 订单编号
     */
    private Long orderNo;

    /**
     * 价格
     */
    private Long price;

    /**
     * 购买数量
     */
    private Long amount;

    /**
     * 购买人id
     */
    private Long buyerId;

    /**
     * 购买人id
     */
    private String buyerName;

    /**
     * 商品规格
     */
    private Long skuId;

    /**
     * 卖方id
     */
    private Long sellerId;

    /**
     * 支付状态
     */
    private Integer payStatus;

    /**
     * 描述
     */
    private String des;

    /**
     * 创建订单
     *
     * @param buyerId 购买人
     * @param skuId   商品规格id
     * @param amount  购买数量
     * @return order  订单
     */
    public static Order create(Long buyerId, Long skuId, Long amount) {
        return Order.builder()
                .buyerId(buyerId)
                .skuId(skuId)
                .amount(amount)
                .sellerId(1L)
                .buyerName("小"+buyerId)
                .payStatus(PLAY_STATUS_NO)
                .build();
    }

    /**
     * 生成订单号
     */
    public void generateOrderNo() {
        this.orderNo = System.currentTimeMillis();
    }

    /**
     * 支付成功
     */
    public void paySuccess() {
        this.payStatus= PLAY_STATUS_SUCCEED;
    }

    /**
     * z支付失败
     */
    public void payFail() {
        this.payStatus= PLAY_STATUS_FAIL;
    }
}
