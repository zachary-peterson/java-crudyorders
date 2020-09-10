package com.lambdaschool.com.orders.services;

import com.lambdaschool.com.orders.models.Agent;
import com.lambdaschool.com.orders.models.Customer;
import com.lambdaschool.com.orders.models.Order;
import com.lambdaschool.com.orders.models.Payment;
import com.lambdaschool.com.orders.repositories.AgentsRepository;
import com.lambdaschool.com.orders.repositories.CustomersRepository;
import com.lambdaschool.com.orders.repositories.OrdersRepository;
import com.lambdaschool.com.orders.repositories.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServicesImpl implements CustomerServices
{
    @Autowired
    private CustomersRepository customRepos;

    @Autowired
    private AgentsRepository agentRepos;

    @Autowired
    private OrdersRepository ordersRepos;

    @Autowired
    private PaymentsRepository payRepos;

    @Transactional
    @Override
    public Customer save(Customer customer)
    {
        Customer newCustomer = new Customer();

        if(customer.getCustcode() != 0)
        {
            findCustomerById(customer.getCustcode());
            newCustomer.setCustcode(customer.getCustcode());
        }

        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());

        Agent newAgent = agentRepos.findById(customer.getAgent().getAgentcode())
            .orElseThrow(() -> new EntityNotFoundException("Agent Code: " + customer.getAgent().getAgentcode() + " can not be found, please try again!"));
        newCustomer.setAgent(newAgent);

        newCustomer.getOrders().clear();
        for (Order o : customer.getOrders())
        {
            Order newOrder = new Order();
            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setCustomer(newCustomer);
            newOrder.setOrderdescription(o.getOrderdescription());

            for (Payment p : newOrder.getPayments())
            {
                Payment newPayment = payRepos.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment ID " + p.getPaymentid() + " could not be found, please try again!"));
                newOrder.getPayments().add(newPayment);
            }
            newCustomer.getOrders().add(newOrder);
        }

        return customRepos.save(newCustomer);
    }

    @Transactional
    @Override
    public Customer update(
        Customer customer,
        long custcode)
    {
        Customer updateCustomer = findCustomerById(custcode);

        if (customer.getCustname() != null)
        {
            updateCustomer.setCustname(customer.getCustname());
        }
        if (customer.getCustcity() != null)
        {
            updateCustomer.setCustcity(customer.getCustcity());
        }
        if (customer.getWorkingarea() != null)
        {
            updateCustomer.setWorkingarea(customer.getWorkingarea());
        }
        if (customer.getCustcountry() != null)
        {
            updateCustomer.setCustcountry(customer.getCustcountry());
        }
        if (customer.getGrade() != null)
        {
            updateCustomer.setGrade(customer.getGrade());
        }
        if(customer.openamtchange)
        {
            updateCustomer.setOpeningamt(customer.getOpeningamt());
        }
        if (customer.recamtchange)
        {
            updateCustomer.setReceiveamt(customer.getReceiveamt());
        }
        if (customer.payamtchange)
        {
            updateCustomer.setPaymentamt(customer.getPaymentamt());
        }
        if (customer.outstamtchange)
        {
            updateCustomer.setOutstandingamt(customer.getOutstandingamt());
        }
        if (customer.getPhone() != null)
        {
            updateCustomer.setPhone(customer.getPhone());
        }


        Agent newAgent = agentRepos.findById(customer.getAgent().getAgentcode())
            .orElseThrow(() -> new EntityNotFoundException("Agent Code: " + customer.getAgent().getAgentcode() + " can not be found, please try again!"));
        updateCustomer.setAgent(newAgent);

        if (customer.getOrders().size() > 0)
        {
            updateCustomer.getOrders().clear();
            for (Order o : customer.getOrders())
            {
                Order newOrder = new Order();
                newOrder.setOrdamount(o.getOrdamount());
                newOrder.setAdvanceamount(o.getAdvanceamount());
                newOrder.setCustomer(updateCustomer);
                newOrder.setOrderdescription(o.getOrderdescription());

                for (Payment p : newOrder.getPayments())
                {
                    Payment newPayment = payRepos.findById(p.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment ID " + p.getPaymentid() + " could not be found, please try again!"));
                    newOrder.getPayments().add(newPayment);
                }
                updateCustomer.getOrders().add(newOrder);
            }
        }

        return customRepos.save(updateCustomer);
    }

    @Override
    public void delete(long custcode)
    {
        if (customRepos.findById(custcode)
            .isPresent())
        {
            customRepos.deleteById((custcode));
        } else
        {
            throw new EntityNotFoundException("Customer Code " + custcode + " could not be found, please try again!");
        }
    }

    @Override
    public List<Customer> findAllCustomersOrders()
    {
        List<Customer> allCustomers = new ArrayList<>();
        customRepos.findAll().iterator().forEachRemaining(allCustomers::add);
        return allCustomers;
    }

    @Override
    public Customer findCustomerById(long custid)
    {
        return customRepos.findById(custid)
            .orElseThrow(() -> new EntityNotFoundException("Customer with ID: " + custid + " could not be found, please try again!"));
    }

    @Override
    public List<Customer> findByNameLike(String custname)
    {
        List<Customer> namesLike =  customRepos.findByCustnameContainingIgnoreCase(custname);
        return  namesLike;
    }
}
