package com.hixtrip.sample.app.convertor;

import com.hixtrip.sample.app.callback.PayStatusEnum;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.mapstruct.Mapper;

/**
 * @author lill
 */
@Mapper(componentModel = "SPRING")
public interface CommandPayConvertor {
    default PayStatusEnum convertToPayStatusEnum(CommandPayDTO commandPayDTO) {
        return PayStatusEnum.valueOf(commandPayDTO.getPayStatus());
    }

    CommandPay dto2Domain(CommandPayDTO commandPayDTO);

}
