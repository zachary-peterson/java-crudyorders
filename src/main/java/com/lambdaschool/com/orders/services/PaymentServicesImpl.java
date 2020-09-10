package com.lambdaschool.com.orders.services;

import com.lambdaschool.com.orders.models.Payment;
import com.lambdaschool.com.orders.repositories.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServicesImpl implements PaymentServices
{
    @Autowired
    private PaymentsRepository paymentRepos;

    @Override
    public Payment save(Payment payment)
    {
        return paymentRepos.save(payment);
    }
}
