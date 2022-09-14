package com.costin.springrestproject.dao;

import com.costin.springrestproject.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers() {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Query<Customer> queryResult = session
                .createQuery("from Customer order by lastName",
                        Customer.class);

        List<Customer> customers = queryResult.getResultList();

        session.getTransaction().commit();

        session.close();

        return customers;
    }

    @Override
    public void saveCustomer(Customer customer) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.saveOrUpdate(customer);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public Customer getCustomer(int customerId) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Customer theCustomer = session.get(Customer.class, customerId);

        session.getTransaction().commit();

        session.close();

        return theCustomer;
    }

    @Override
    public void deleteCustomer(int customerId) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Query theQuery = session.createQuery("delete from Customer where id=:customerId");

        theQuery.setParameter("customerId", customerId);

        theQuery.executeUpdate();

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public List<Customer> searchCustomers(String theName) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Query theQuery = null;

        if(theName != null && theName.trim().length() > 0) {
            theQuery = session.createQuery(
                    "from Customer" +
                            " where lower(firstName) like :theName" +
                            " or lower(lastName) like :theName",
                    Customer.class
            );
            theQuery.setParameter("theName", "%" + theName.toLowerCase() + "%");
        } else {
            theQuery = session.createQuery(
                    "from Customer",
                    Customer.class
            );
        }

        List<Customer> customers = theQuery.getResultList();

        session.getTransaction().commit();

        session.close();

        return customers;
    }

}
