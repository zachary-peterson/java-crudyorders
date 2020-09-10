package com.lambdaschool.com.orders.repositories;

import com.lambdaschool.com.orders.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Order, Long>
{
}
