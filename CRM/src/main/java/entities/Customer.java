package entities;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private Customer customer;
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
}
