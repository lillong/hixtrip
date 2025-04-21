package com.hixtrip.sample.app.callback;

import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lill
 */
public abstract class AbsPayCallbackStrategy implements PayCallbackStrategy {
    PayDomainService payDomainService;


    public AbsPayCallbackStrategy(PayDomainService payDomainService) {
        this.payDomainService = payDomainService;
    }

    @Transactional
    @Override
    public void handle(CommandPay commandPay) {
        this.doHandle(commandPay);
        payDomainService.payRecord(commandPay);
    }

    protected abstract void doHandle(CommandPay commandPay);
}
