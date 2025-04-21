package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


/**
 * @author lill
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_order", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class OrderDO {
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
    @TableField(updateStrategy =  FieldStrategy.NEVER)
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


    private Integer payStatus;

    /**
     * 描述
     */
    private String des;

    /**
     * 创建月份，用于demo的分表，如果是产品级不可以这样，但需要自定义分表算法
     */
    private String createMonth;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableLogic
    private Long delFlag;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}