package com.costin.springrestproject.dao;

import com.costin.springrestproject.entity.Customer;

import java.util.List;

public interface CustomerDAO {
    List<Customer> getCustomers();
    void saveCustomer(Customer customer);
    Customer getCustomer(int customerId);
    void deleteCustomer(int customerId);
    List<Customer> searchCustomers(String theName);
}
