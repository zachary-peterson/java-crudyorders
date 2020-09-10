package com.lambdaschool.com.orders.repositories;

import com.lambdaschool.com.orders.models.Agent;
import org.springframework.data.repository.CrudRepository;

public interface AgentRepository extends CrudRepository<Agent, Long>
{
}
