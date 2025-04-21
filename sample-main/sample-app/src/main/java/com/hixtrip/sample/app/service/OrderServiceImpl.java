package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.dto.CommandOrderCreateDTO;
import com.hixtrip.sample.client.order.vo.OrderVO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * app层负责处理request请求，调用领域服务
 *
 * @author lill
 */
@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDomainService orderDomainService;

    private final OrderConvertor orderConvertor;


    @Override
    @Transactional
    public OrderVO create(CommandOrderCreateDTO orderCreateDTO) {
        Order order = Order.create(orderCreateDTO.getUserId(), orderCreateDTO.getSkuId(), orderCreateDTO.getAmount());
        Order create = orderDomainService.createOrder(order);
        return orderConvertor.domain2VO(create);
    }

}
