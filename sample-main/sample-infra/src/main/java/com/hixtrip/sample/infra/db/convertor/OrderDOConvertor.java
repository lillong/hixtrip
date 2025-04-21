package com.hixtrip.sample.infra.db.convertor;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * DO对像 -> 领域对象转换器
 * @author lill
 */
@Mapper(componentModel = "spring")
public interface OrderDOConvertor {


    OrderDO domainToDO(Order order);

    List<Order> do2Domain(List<OrderDO> order);

    Order do2Domain(OrderDO order);

}
