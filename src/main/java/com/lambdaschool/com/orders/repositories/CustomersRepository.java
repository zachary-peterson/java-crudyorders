package com.lambdaschool.com.orders.repositories;

import com.lambdaschool.com.orders.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomersRepository extends CrudRepository<Customer, Long>
{

    List<Customer> findByCustnameContainingIgnoreCase(String custname);
}
