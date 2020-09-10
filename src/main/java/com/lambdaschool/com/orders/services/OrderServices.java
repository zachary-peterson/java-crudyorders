package com.lambdaschool.com.orders.services;

import com.lambdaschool.com.orders.models.Order;

public interface OrderServices
{
    Order findById(long ordid);
    Order save(Order order);
    Order save(Order order, long ordid);
    void delete(long ordnum);
}
