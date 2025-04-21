package com.hixtrip.sample.app.callback;

import com.hixtrip.sample.app.convertor.CommandPayConvertor;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @author lill
 */
@Component
public class PayCallbackContext {

    private final CommandPayConvertor commandPayConvertor;

    private final Map<PayStatusEnum, PayCallbackStrategy> strategyMap = new EnumMap<>(PayStatusEnum.class);

    @Autowired
    public PayCallbackContext(List<PayCallbackStrategy> strategies, CommandPayConvertor commandPayConvertor) {
        this.commandPayConvertor = commandPayConvertor;
        strategies.forEach(strategy ->
                strategyMap.put(strategy.supportStatus(), strategy)
        );
    }

    public void handleCallback(CommandPayDTO commandPayDTO) {
        PayStatusEnum payStatusEnum = commandPayConvertor.convertToPayStatusEnum(commandPayDTO);
        PayCallbackStrategy strategy = strategyMap.get(payStatusEnum);
        if (strategy == null) {
            throw new RuntimeException("暂时不支持的支付状态：" + payStatusEnum);
        }
        CommandPay commandPay = commandPayConvertor.dto2Domain(commandPayDTO);
        strategy.handle(commandPay);
    }

}
