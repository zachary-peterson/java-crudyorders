package com.lambdaschool.com.orders.services;

import com.lambdaschool.com.orders.models.Customer;

import java.util.List;

public interface CustomerServices
{
    Customer save(Customer customer);
    List<Customer> findAllCustomersOrders();

    Customer findCustomerById(long custid);

    List<Customer> findByNameLike(String custname);

    Customer update(
        Customer customer,
        long custcode
    );

    void delete(long custcode);

}
