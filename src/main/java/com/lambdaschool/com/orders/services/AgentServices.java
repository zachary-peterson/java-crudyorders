package com.lambdaschool.com.orders.services;

import com.lambdaschool.com.orders.models.Agent;

import java.util.List;

public interface AgentServices
{
    Agent save(Agent agent);

    Agent findAgentById(long agentid);
}
