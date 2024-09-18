package com.ivan.usercrud.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product", schema = "shop")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private float price;

    @Column(name = "category")
    private String category;

    @Column(name = "amount")
    private int amount;

    @Column(name = "image")
    private String imageURL;

    public Product(String name, float price, String category, int amount) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.amount = amount;
    }
}
