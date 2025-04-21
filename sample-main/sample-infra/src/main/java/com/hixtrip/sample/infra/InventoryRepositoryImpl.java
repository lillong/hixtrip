package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.infra.manager.LuaScriptManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * infra层是domain定义的接口具体的实现
 *
 * @author lill
 */
@Component
@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepository {

    private final static String RK_PREFIX_STOCK = "STOCK:";

    private final RedisTemplate<String, Object> redisTemplate;


    private final LuaScriptManager luaScriptManager;


    @Override
    public Integer getInventory(Long skuId) {
        Object o = redisTemplate.opsForHash().get(RK_PREFIX_STOCK + skuId, "sellable");
        if (o != null) {
            return Integer.parseInt(o.toString());
        }
        throw new RuntimeException("库存不存在");
    }

    @Override
    public Boolean changeInventory(Long skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        //使用脚本管理器获取lua脚本保证并发时的原子性，实现无锁设计
        String reduceStockLua = luaScriptManager.getChangeInventory();
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(reduceStockLua);
        redisScript.setResultType(Long.class);
        String key = RK_PREFIX_STOCK + skuId;
        // 执行脚本,减少库存，使用lua脚本保证并发时的原子性。如有跟数据库交互可以用异步消息队列保证DB的原子性
        Long execute = redisTemplate.execute(
                redisScript,
                Collections.singletonList(key),
                sellableQuantity, withholdingQuantity, occupiedQuantity
        );
        return null != execute && execute >= 0;
    }


}
