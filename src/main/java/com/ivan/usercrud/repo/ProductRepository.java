package com.ivan.usercrud.repo;

import com.ivan.usercrud.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    /*
    List<Product> findAllByOrderByName();
    List<Product> findAllByOrderByPrice();
    List<Product> findAllByOrderByCategory();
    List<Product> findAllByOrderByAmount();
    */
    @Query(value = """
        (SELECT * FROM shop.product WHERE name LIKE :keyword)
        UNION
        (SELECT * FROM shop.product WHERE category LIKE :keyword)
        ORDER BY name
        """, nativeQuery = true)
    List<Product> sortByNameFilterBy(@Param("keyword") String keyword);

    @Query(value = """
        (SELECT * FROM shop.product WHERE name LIKE :keyword)
        UNION
        (SELECT * FROM shop.product WHERE category LIKE :keyword)
        ORDER BY price
        """, nativeQuery = true)
    List<Product> sortByPriceFilterBy(@Param("keyword") String keyword);

    @Query(value = """
        (SELECT * FROM shop.product WHERE name LIKE :keyword)
        UNION
        (SELECT * FROM shop.product WHERE category LIKE :keyword)
        ORDER BY category
        """, nativeQuery = true)
    List<Product> sortByCategoryFilterBy(@Param("keyword") String keyword);

    @Query(value = """
        (SELECT * FROM shop.product WHERE name LIKE :keyword)
        UNION
        (SELECT * FROM shop.product WHERE category LIKE :keyword)
        ORDER BY amount
        """, nativeQuery = true)
    List<Product> sortByAmountFilterBy(@Param("keyword") String keyword);

    @Query(value = """
        SELECT * FROM shop.product
        ORDER BY price DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<Product> findInterestingProductsLimit(@Param("limit") int limit);
}