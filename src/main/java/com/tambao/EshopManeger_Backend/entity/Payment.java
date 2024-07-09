package com.tambao.EshopManeger_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "payment")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "decription")
    private String decription;

    @Column(name = "payment_fee")
    private Double paymentFee;

    @OneToMany(mappedBy = "payment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Orders> orders;
}
