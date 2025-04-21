package com.hixtrip.sample.app.callback;

import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 * @author lill
 */
public interface PayCallbackStrategy {
    PayStatusEnum supportStatus();       // 支持的状态

    void handle(CommandPay commandPay);
}
