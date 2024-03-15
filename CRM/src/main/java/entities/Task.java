package entities;

import jakarta.persistence.*;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Opportunity opportunity;
    // Other attributes and getters/setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Opportunity getOpportunity() {
        return opportunity;
    }
    public void setOpportunity(Opportunity opportunity) {
        this.opportunity = opportunity;
    }
    
}