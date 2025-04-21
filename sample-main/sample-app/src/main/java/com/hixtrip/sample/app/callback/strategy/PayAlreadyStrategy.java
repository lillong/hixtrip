package com.hixtrip.sample.app.callback.strategy;

import com.hixtrip.sample.app.callback.AbsPayCallbackStrategy;
import com.hixtrip.sample.app.callback.PayStatusEnum;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

@Component
public class PayAlreadyStrategy extends AbsPayCallbackStrategy {

    private final   OrderDomainService domainService;

    public PayAlreadyStrategy(PayDomainService payDomainService, OrderDomainService domainService) {
        super(payDomainService);
        this.domainService = domainService;
    }

    @Override
    protected void doHandle(CommandPay commandPay) {
        domainService.orderPayAlready(commandPay);
    }

    @Override
    public PayStatusEnum supportStatus() {
        return PayStatusEnum.ALREADY;
    }
}
