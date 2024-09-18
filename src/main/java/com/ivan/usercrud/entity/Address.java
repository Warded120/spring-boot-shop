package com.ivan.usercrud.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "address", schema = "shop")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "country")
    private String country;

    @Column(name = "region")
    private String region;

    @Column(name = "location_type")
    private String locationType;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private int houseNumber;
}
