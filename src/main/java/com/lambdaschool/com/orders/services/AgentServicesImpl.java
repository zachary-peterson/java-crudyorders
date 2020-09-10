package com.lambdaschool.com.orders.services;

import com.lambdaschool.com.orders.models.Agent;
import com.lambdaschool.com.orders.models.Customer;
import com.lambdaschool.com.orders.repositories.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgentServicesImpl implements AgentServices
{
    @Autowired
    private AgentsRepository agentRepos;

    @Override
    public Agent save(Agent agent)
    {
        return agentRepos.save(agent);
    }

    @Override
    public Agent findAgentById(long agentid)
    {
        return agentRepos.findById(agentid)
            .orElseThrow(() -> new EntityNotFoundException("Customer with ID: " + agentid + " could not be found, please try again!"));
    }
}
