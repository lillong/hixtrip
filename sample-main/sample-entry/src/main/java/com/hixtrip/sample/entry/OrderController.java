package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.callback.PayCallbackContext;
import com.hixtrip.sample.client.order.dto.CommandOrderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.vo.OrderVO;
import com.hixtrip.sample.domain.order.model.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * todo 这是你要实现的
 *
 * @author lill
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "订单")
public class OrderController {


    private final OrderService orderService;

    private final PayCallbackContext payCallbackContext;

    /**
     * 创建订单
     *
     * @param commandOrderCreateDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    @Operation(summary = "创建订单")
    public OrderVO order(@RequestBody CommandOrderCreateDTO commandOrderCreateDTO) {
        return orderService.create(commandOrderCreateDTO);
    }

    /**
     * todo 这是模拟创建订单后，支付结果的回调通知
     * 【中、高级要求】需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     *
     * @param commandPayDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    @Operation(summary = "支付回调")
    public void payCallback(@RequestBody CommandPayDTO commandPayDTO) {
        payCallbackContext.handleCallback(commandPayDTO);
    }

}
