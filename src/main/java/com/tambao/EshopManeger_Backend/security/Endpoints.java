package com.tambao.EshopManeger_Backend.security;

public class Endpoints {
    public static final String front_end_host = "http://localhost:3000";
    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/api/products",
            "/api/categories",
            "/account/**",
            "/api/shipping-address/**",
            "/api/wishlists/**",
            "/api/cart-items/**",
            "/api/reviews/**"
    };

    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/account/register",
            "/account/register-oauth",
            "/account/login",
            "/account/forgot",
            "/api/products/filters",
            "/account/",
            "/api/wishlists/**",
            "/api/carts/**",
            "/api/cart-items/**",
            "/api/reviews/**"
    };

    public static final String[] ADMIN_GET_ENDPOINTS = {
            "/api/users/**"
    };

    public static final String[] ADMIN_POST_ENDPOINTS = {
            "/api/products/**",
            "/api/categories/**",
            "api/product-image/**",
            "/api/product-variant/**",
            "/api/brands/**",
            "/api/variants/**"
    };

    public static final String[] ADMIN_DELETE_ENDPOINTS = {
            "/api/users/**",
            "/api/categories/**",
            "/api/products/**",
            "/api/product-image/**",
            "/api/product-variant/**",
            "/api/brands/**",
            "/api/variants/**"
    };
}
