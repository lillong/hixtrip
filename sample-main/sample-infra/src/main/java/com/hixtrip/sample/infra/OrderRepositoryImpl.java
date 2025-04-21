package com.hixtrip.sample.infra;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.constant.RK;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * order存储服务
 *
 * @author lill
 */
@Service
@RequiredArgsConstructor
public class OrderRepositoryImpl extends ServiceImpl<OrderMapper, OrderDO> implements OrderRepository {


    private final OrderDOConvertor orderDOConvertor;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Order save(Order order) {
        OrderDO orderDO = orderDOConvertor.domainToDO(order);
        orderDO.setCreateTime(LocalDateTime.now());
        orderDO.setCreateBy(order.getBuyerName());
        orderDO.setCreateMonth(orderDO.getCreateTime().format(DateTimeFormatter.ofPattern("yyyyMM")));
        orderDO.setDelFlag(0L);
        this.save(orderDO);
        //为了查询速度更快，将订单id加入redis,分页时可使用
        cacheBuyerOrder(orderDO.getBuyerId(), orderDO.getId(), orderDO.getCreateTime());
        return orderDOConvertor.do2Domain(orderDO);
    }

    public void cacheBuyerOrder(Long buyerId, Long orderId, LocalDateTime createdTime) {
        long secondTimestamp = createdTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        String redisKey = RK.RK_BUYER_ORDER_LIST_PREFIX + buyerId;
        redisTemplate.opsForZSet().add(redisKey, orderId, secondTimestamp);
        // 设置过期时间
        redisTemplate.expire(redisKey, Duration.ofHours(6));
    }

    @Override
    public Order getById(Long id) {
        OrderDO orderDO = super.getById(id);
        return orderDOConvertor.do2Domain(orderDO);
    }

    @Override
    public void update(Order order) {
        OrderDO orderDO = orderDOConvertor.domainToDO(order);
        orderDO.setUpdateBy(order.getBuyerName());
        orderDO.setUpdateTime(LocalDateTime.now());
        this.updateById(orderDO);
    }
}
