package com.hixtrip.sample.app.callback.strategy;

import com.hixtrip.sample.app.callback.AbsPayCallbackStrategy;
import com.hixtrip.sample.app.callback.PayStatusEnum;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

/**
 * @author lill
 */
@Component
public class PayFailStrategy extends AbsPayCallbackStrategy {

    private final OrderDomainService orderDomainService;

    public PayFailStrategy(PayDomainService payDomainService, OrderDomainService orderDomainService) {
        super(payDomainService);
        this.orderDomainService = orderDomainService;
    }

    @Override
    protected void doHandle(CommandPay commandPay) {
        orderDomainService.orderPayFail(commandPay);
    }

    @Override
    public PayStatusEnum supportStatus() {
        return PayStatusEnum.FAIL;
    }
}
