package dao;

import entities.Product;

import java.util.List;

public interface ProductDAO {
    void save(Product product);
    void update(Product product);
    void delete(Product product);
    Product getById(int id);
    List<Product> getAll();
}
