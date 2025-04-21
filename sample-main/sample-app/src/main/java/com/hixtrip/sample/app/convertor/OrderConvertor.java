package com.hixtrip.sample.app.convertor;

import com.hixtrip.sample.client.order.vo.OrderVO;
import com.hixtrip.sample.domain.order.model.Order;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * DTO对像 -> 领域对象转换器
 * 转换器
 */
@Mapper(componentModel = "SPRING", builder = @Builder(disableBuilder = false))
public interface OrderConvertor {


    @Mapping(target = "name",source = "buyerName")
    OrderVO domain2VO(Order order);

    List<OrderVO> domain2VO(List<Order> orders);
}
