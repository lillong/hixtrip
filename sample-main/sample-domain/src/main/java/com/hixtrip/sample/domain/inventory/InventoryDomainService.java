package com.hixtrip.sample.domain.inventory;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 库存领域服务
 * 库存设计，忽略仓库、库存品、计量单位等业务
 * @author lill
 */
@Component
@RequiredArgsConstructor
public class InventoryDomainService {

    private final InventoryRepository inventoryRepository;


    /**
     * 获取sku当前库存
     *
     * @param skuId 库存id
     */
    public Integer getInventory(Long skuId) {
        return inventoryRepository.getInventory(skuId);
    }

    /**
     * 修改库存 递增（或递减） 负数为减少  正数为添加
     *
     * @param skuId
     * @param sellableQuantity    可售库存
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity    占用库存
     * @return
     */
    public Boolean changeInventory(Long skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        return inventoryRepository.changeInventory(skuId,sellableQuantity,withholdingQuantity,occupiedQuantity);
    }
}
