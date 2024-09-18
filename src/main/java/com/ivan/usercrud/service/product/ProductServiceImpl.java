package com.ivan.usercrud.service.product;

import com.ivan.usercrud.entity.Product;
import com.ivan.usercrud.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository theProductRepository) {
        productRepository = theProductRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(int id) {

        Optional<Product> theProduct = productRepository.findById(id);

        if(theProduct.isEmpty()) {
            throw new RuntimeException("Product with id: " + id + " not found");
        }

        return theProduct.get();
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> filterBy(String sortType, String keyword) {
        switch (sortType) {
            case "price" -> {
                return productRepository.sortByPriceFilterBy(keyword);
            }
            case "category" -> {
                return productRepository.sortByCategoryFilterBy(keyword);
            }
            case "amount" -> {
                return productRepository.sortByAmountFilterBy(keyword);
            }
            default -> {
                return productRepository.sortByNameFilterBy(keyword);
            }
        }
    }

    @Override
    public List<Product> findInterestingProducts() {
        return productRepository.findInterestingProductsLimit(5);
    }
}
