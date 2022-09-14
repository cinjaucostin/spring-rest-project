package com.costin.springrestproject.controller;

import com.costin.springrestproject.entity.Customer;
import com.costin.springrestproject.exception.CustomerNotFoundException;
import com.costin.springrestproject.service.CustomerService;
import com.costin.springrestproject.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/customers/{customerId}")
    public Customer getCustomer(@PathVariable int customerId) {
        int size = getCustomers().size();
        if(customerId >= size || customerId < 0) {
            throw new CustomerNotFoundException("Customer with id - " + customerId + " doesn't exists.");
        }

        return customerService.getCustomer(customerId);
    }

    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody Customer theCustomer) {
        theCustomer.setId(0);

        customerService.saveCustomer(theCustomer);

        return theCustomer;
    }

    @DeleteMapping("/customers/{customerId}")
    public String deleteCustomer(@PathVariable int customerId) {
        customerService.deleteCustomer(customerId);

        return "Deleted customer id - " + customerId;
    }

    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer theCustomer) {
        customerService.saveCustomer(theCustomer);

        return theCustomer;
    }

}
