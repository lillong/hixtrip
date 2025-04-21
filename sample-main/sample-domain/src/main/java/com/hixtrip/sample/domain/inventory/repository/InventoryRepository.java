package com.hixtrip.sample.domain.inventory.repository;

/**
 *
 * @author lill
 */
public interface InventoryRepository {
    /**
     * 获取当前库存
     * @param skuId 库存id
     * @return 库存数量
     */
    Integer getInventory(Long skuId);


    /**
     * 修改库存 递增（或递减） 负数为减少  正数为添加
     *
     * @param skuId 商品规格id
     * @param sellableQuantity    可售库存
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity    占用库存
     * @return
     */
    Boolean changeInventory(Long skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity);
}
