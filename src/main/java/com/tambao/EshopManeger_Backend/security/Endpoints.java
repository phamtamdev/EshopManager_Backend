package com.tambao.EshopManeger_Backend.security;

public class Endpoints {
    public static final String front_end_host = "http://localhost:3000";
    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/products"
    };

    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/account/register",
            "/account/login",
    };
}
