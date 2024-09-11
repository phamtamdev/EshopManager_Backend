package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.Utils.SlugUtils;
import com.tambao.EshopManeger_Backend.dto.ProductDto;
import com.tambao.EshopManeger_Backend.entity.Brand;
import com.tambao.EshopManeger_Backend.entity.Category;
import com.tambao.EshopManeger_Backend.entity.Product;

public class ProductMapper {

    public static ProductDto mapToProductDTO(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(product.getQuantity());
        productDto.setDiscountedPrice(product.getDiscountedPrice());
        productDto.setSlug(product.getSlug());
        if(product.getBrand() != null){
            productDto.setBrandId(product.getBrand().getId());
        }
        if (product.getCategory() != null) {
            productDto.setCategoryId(product.getCategory().getId());
        }
        return productDto;
    }

    public static Product mapToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setSlug(SlugUtils.toSlug(productDto.getName()));

        if (productDto.getCategoryId() != null) {
            Category category = new Category();
            category.setId(productDto.getCategoryId());
            product.setCategory(category);
        }

        if (productDto.getBrandId() != null) {
            Brand brand = new Brand();
            brand.setId(productDto.getBrandId());
            product.setBrand(brand);
        }
        return product;
    }
}
