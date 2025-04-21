package com.hixtrip.sample.app.callback.strategy;

import com.hixtrip.sample.app.callback.AbsPayCallbackStrategy;
import com.hixtrip.sample.app.callback.PayStatusEnum;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lill
 */
@Component
public class PaySuccessStrategy extends AbsPayCallbackStrategy {

    private final OrderDomainService orderDomainService;

    @Autowired
    public PaySuccessStrategy(PayDomainService payDomainService, OrderDomainService orderDomainService) {
        super(payDomainService);
        this.orderDomainService = orderDomainService;
    }

    @Override
    public PayStatusEnum supportStatus() {
        return PayStatusEnum.SUCCESS;
    }

    @Override
    protected void doHandle(CommandPay commandPay) {
        orderDomainService.orderPaySuccess(commandPay);
    }
}
