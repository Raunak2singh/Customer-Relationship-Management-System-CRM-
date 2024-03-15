package dao;

import entities.Customer;
import entities.Opportunity;
import entities.Product; // Import Product entity

import java.util.List;

public interface OpportunityDAO {
    void save(Opportunity opportunity);
    void update(Opportunity opportunity);
    void delete(Opportunity opportunity);
    Opportunity getById(int id);
    List<Opportunity> getAll();
    List<Opportunity> getByCustomer(Customer customer);
    List<Opportunity> getByProduct(Product product); 
    void addOpportunityForProduct(Opportunity opportunity, Product product);
}
