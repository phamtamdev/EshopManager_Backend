package com.tambao.EshopManeger_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "shipping")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "shipping_fee")
    private Double shippingFee;

    @OneToMany(mappedBy = "shipping", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Orders> orders;
}
