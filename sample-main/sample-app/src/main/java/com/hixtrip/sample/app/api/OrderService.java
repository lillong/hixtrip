package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOrderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.vo.OrderVO;

import java.util.List;

/**
 * 订单的service层
 */
public interface OrderService {
    /**
     * 创建订单
     * @param orderCreateDTO
     * @return
     */
     OrderVO create(CommandOrderCreateDTO orderCreateDTO);


}
