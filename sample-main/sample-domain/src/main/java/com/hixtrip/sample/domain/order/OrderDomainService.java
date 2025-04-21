package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.beans.Transient;
import java.util.List;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 *
 * @author lill
 */
@Component
@RequiredArgsConstructor
public class OrderDomainService {

    private final OrderRepository orderRepository;
    private final CommodityDomainService commodityDomainService;
    private final InventoryDomainService inventoryDomainService;

    /**
     * 创建待付款订单
     */
    public Order createOrder(Order order) {
        //生成订单号
        order.generateOrderNo();
        //设置价格
        Long price = commodityDomainService.getSkuPrice(order.getSkuId());
        order.setPrice(price);
        Order save = orderRepository.save(order);
        Boolean isStock = inventoryDomainService.changeInventory(order.getSkuId(), -order.getAmount(), order.getAmount(), 0L);
        if (!isStock) {
            throw new RuntimeException("库存不足");
        }
        return save;
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        Order order = orderRepository.getById(commandPay.getOrderId());
        if (null == order) {
            throw new RuntimeException("订单不存在");
        }
        order.paySuccess();
        orderRepository.update(order);
        //获取订单数量
        Long amount = order.getAmount();
        inventoryDomainService.changeInventory(order.getSkuId(),
                0L,
                -amount,
                amount);
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        Order order = orderRepository.getById(commandPay.getOrderId());
        order.payFail();
        orderRepository.update(order);
        //失败后是否允许再次支付。如果运行则不需要更改库存，反之需要修改
    }
    /**
     * 重复支付
     */
    public void orderPayAlready(CommandPay commandPay) {
         //重复支付是否需要处理根据业务需求 这里抛出异常
        throw new RuntimeException("重复支付");
    }

}
