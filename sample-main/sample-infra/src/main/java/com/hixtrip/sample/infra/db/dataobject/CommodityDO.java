package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author lill
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_commodity", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class CommodityDO {
    /**
     * 商品id
     */
    @TableId
    Long id;

    /**
     * 商品名称
     */
    String name;

    /**
     * 卖方id
     */
    Long sellerId;


    /**
     * 库存数量
     */
    Integer stock;


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
