package com.tambao.EshopManeger_Backend.security;

public class Endpoints {
    public static final String front_end_host = "http://localhost:3000";
    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/api/products",
            "/api/categories",
            "/account/**"
    };

    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/account/register",
            "/account/login",
            "/account/forgot"
    };

    public static final String[] ADMIN_GET_ENDPOINTS = {
            "/api/users/**"
    };

    public static final String[] ADMIN_POST_ENDPOINTS = {
            "/api/products/**",
            "/api/categories/**",
            "api/product-image/**",
            "/api/product-variant/**"
    };

    public static final String[] ADMIN_DELETE_ENDPOINTS = {
            "/api/users/**",
            "/api/categories/**",
            "/api/products/**",
            "/api/product-image/**",
            "/api/product-variant/**"
    };
}
