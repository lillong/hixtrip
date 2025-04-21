package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;


/**
 *
 * @author lill
 */
public interface OrderRepository  {


     Order save(Order order);

     void update(Order order);


     Order getById(Long id);

}
