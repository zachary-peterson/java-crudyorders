package com.lambdaschool.com.orders.repositories;

import com.lambdaschool.com.orders.models.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentsRepository extends CrudRepository<Payment, Long>
{
}
