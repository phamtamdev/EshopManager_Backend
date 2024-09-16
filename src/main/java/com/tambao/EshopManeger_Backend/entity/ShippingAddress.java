package com.tambao.EshopManeger_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "shipping_address")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "province")
    private String province;

    @Column(name = "district")
    private String district;

    @Column(name = "ward")
    private String ward;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "default_address")
    private Boolean defaultAddress;

    @Column(name = "fullAddress", columnDefinition = "LONGTEXT")
    private String fullAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "shippingAddress", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Orders> orders;

}
