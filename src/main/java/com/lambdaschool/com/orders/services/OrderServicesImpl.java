package com.lambdaschool.com.orders.services;

import com.lambdaschool.com.orders.models.Customer;
import com.lambdaschool.com.orders.models.Order;
import com.lambdaschool.com.orders.models.Payment;
import com.lambdaschool.com.orders.repositories.CustomersRepository;
import com.lambdaschool.com.orders.repositories.OrdersRepository;
import com.lambdaschool.com.orders.repositories.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class OrderServicesImpl implements OrderServices
{
    @Autowired
    private OrdersRepository orderRepos;

    @Autowired
    private CustomersRepository customRepos;

    @Autowired
    private PaymentsRepository payRepos;

    @Transactional
    @Override
    public Order save(Order order)
    {
        long custnum = order.getCustomer().getCustcode();

        Customer newCustomer = customRepos.findById(custnum)
            .orElseThrow(() -> new EntityNotFoundException("Customer Code " + order.getCustomer().getCustcode() + " could not be found, please try again!"));

        Order newOrder = new Order();
        if(order.getOrdnum() > 0) {
            newOrder.setOrdnum(order.getOrdnum());
        }

        newOrder.setCustomer(newCustomer);
        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrderdescription(order.getOrderdescription());

        newOrder.getPayments().clear();
        for (Payment p : order.getPayments())
        {
            Payment newPayment = new Payment();
            newPayment.setType(p.getType());
            newOrder.getPayments().add(newPayment);
        }

        return orderRepos.save(newOrder);
    }

    @Override
    public Order save(Order order, long ordid) {
        getOrderById(ordid);
        order.setOrdnum(ordid);
        return save(order);
    }

    private Order getOrderById(long ordid)
    {
        return orderRepos.findById(ordid).orElseThrow(() ->
            new EntityNotFoundException(String.format("Order with id %d not found", ordid)));
    }

    @Override
    public void delete(long ordid)
    {
        if (orderRepos.findById(ordid)
            .isPresent())
        {
            orderRepos.deleteById((ordid));
        } else
        {
            throw new EntityNotFoundException("Order Number " + ordid + " could not be found, please try again!");
        }
    }

    @Override
    public Order findById(long ordid)
    {
        return orderRepos.findById(ordid)
            .orElseThrow(() -> new EntityNotFoundException("The Order with ID: " + ordid + " could not be found, please try again!"));
    }
}
