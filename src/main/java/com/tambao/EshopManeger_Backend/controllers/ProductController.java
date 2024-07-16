package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.dto.ProductDto;
import com.tambao.EshopManeger_Backend.dto.ProductImageDto;
import com.tambao.EshopManeger_Backend.dto.ReviewDto;
import com.tambao.EshopManeger_Backend.service.Impl.ProductImageService;
import com.tambao.EshopManeger_Backend.service.Impl.ProductService;
import com.tambao.EshopManeger_Backend.service.Impl.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<?> getProduct(
            @RequestParam(value = "page",required = false) Integer page,
            @RequestParam(value = "size",required = false) Integer size,
            @RequestParam(value = "field",required = false) String field,
            @RequestParam(value = "keyword",required = false) String keyword
    ) {
        if(page == null || size == null){
            List<ProductDto> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } else if (field != null && keyword != null) {
            Page<ProductDto> products = productService.getProductsWithPageAndSortingAndSearch(field,keyword,page,size);
            return ResponseEntity.ok(products);
        } else if (field != null) {
            Page<ProductDto> products = productService.getProductsWithPageAndSorting(field,page,size);
            return ResponseEntity.ok(products);
        } else if (keyword != null) {
            Page<ProductDto> products = productService.getProductsWithPageAndSearch(keyword,page,size);
            return ResponseEntity.ok(products);
        }else{
            Page<ProductDto> products = productService.getProductsWithPage(page,size);
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") int id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/{id}/product_images")
    public ResponseEntity<List<ProductImageDto>> getProductImages(@PathVariable("id") int id) {
        return ResponseEntity.ok(productImageService.findByProductId(id));
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviews(@PathVariable("id") int id) {
        return ResponseEntity.ok(reviewService.findByProduct_Id(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        productDto.setId(0);
        productDto = productService.createProduct(productDto);
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") int id, @RequestBody ProductDto productDto) {
        ProductDto updatedProductDto = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProductDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully.");
    }
}
