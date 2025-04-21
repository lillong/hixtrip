package com.hixtrip.sample.client.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建订单的请求 入参
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandOrderCreateDTO {

    /**
     * 商品规格id
     */
    @Schema(title = "商品规格id")
    private Long skuId;

    /**
     * 购买数量
     */
    @Schema(title = "购买数量")
    private Long amount;

    /**
     * 用户id
     */
    @Schema(title = "userId")
    private Long userId;

}
