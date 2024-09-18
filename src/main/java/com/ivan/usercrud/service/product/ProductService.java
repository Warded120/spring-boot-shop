package com.ivan.usercrud.service.product;

import com.ivan.usercrud.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(int id);
    Product save(Product product);
    void deleteById(int id);
    List<Product> filterBy(String sortType, String keyword);
    List<Product> findInterestingProducts();
}
