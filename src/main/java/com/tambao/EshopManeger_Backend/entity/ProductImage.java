package com.tambao.EshopManeger_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_image")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "data", columnDefinition = "LONGTEXT")
    private String data;

    @Column(name = "url")
    private String url;

    @Column(name = "icon")
    private boolean icon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}
