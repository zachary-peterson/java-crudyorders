package com.lambdaschool.com.orders.controllers;

import com.lambdaschool.com.orders.models.Customer;
import com.lambdaschool.com.orders.models.Order;
import com.lambdaschool.com.orders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController
{
    @Autowired
    OrderServices orderServices;

    // http://localhost:2019/orders/order/7
    @GetMapping(value = "/order/{ordid}", produces = "application/json")
    public ResponseEntity<?> findOrderById(@PathVariable long ordid)
    {
        Order orderById = orderServices.findById(ordid);
        return new ResponseEntity<>(orderById, HttpStatus.OK);
    }

    // POST http://localhost:2019/orders/order
    @PostMapping(value = "/order", consumes = "application/json")
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order newOrder)
    {
        Order o = orderServices.save(newOrder);

        // Response Headers => Location Header = URL to the new Restaurant
        // GET http://localhost:2019/restaurants/restaurant/1000
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{ordid}").buildAndExpand(o.getOrdnum()).toUri();
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    // PUT http://localhost:2019/orders/order/63
    @PutMapping(value = "/order/{ordid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateCustomer(@PathVariable long ordid, @Valid @RequestBody
        Order updateOrder)
    {
        updateOrder.setOrdnum(ordid);
        updateOrder = orderServices.save(updateOrder);
        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }

    // DELETE http://localhost:2019/orders/order/58
    @DeleteMapping(value = "/order/{ordid}")
    public ResponseEntity<?> deleteOrderByNum(@PathVariable long ordid){
        orderServices.delete(ordid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
