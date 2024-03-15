package dao;

import entities.Customer;

import java.util.List;

public interface CustomerDAO {
    // Save a customer to the database
    void save(Customer customer);
    

    // Retrieve a customer by its ID
    Customer getById(long customerId);

    // Retrieve all customers from the database
    List<Customer> getAll();

    // Update an existing customer in the database
    void update(Customer customer);

    // Delete a customer from the database
    void delete(Customer customer);
}
