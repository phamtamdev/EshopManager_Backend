package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.dto.FilterDto;
import com.tambao.EshopManeger_Backend.dto.ProductDto;
import com.tambao.EshopManeger_Backend.dto.ProductImageDto;
import com.tambao.EshopManeger_Backend.dto.ReviewDto;
import com.tambao.EshopManeger_Backend.service.Impl.ProductImageService;
import com.tambao.EshopManeger_Backend.service.Impl.ProductService;
import com.tambao.EshopManeger_Backend.service.Impl.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
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
            @RequestParam(value = "keyword",required = false) String keyword,
            @RequestParam(value = "sort-order", required = false) String sortOrder
    ) {
        if(page == null || size == null){
            List<ProductDto> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } else if (field != null && keyword != null) {
            Page<ProductDto> products = productService.getProductsWithPageAndSortingAndSearch(field,keyword,page,size, sortOrder);
            return ResponseEntity.ok(products);
        } else if (field != null) {
            Page<ProductDto> products = productService.getProductsWithPageAndSorting(field,page,size,sortOrder);
            return ResponseEntity.ok(products);
        } else if (keyword != null) {
            Page<ProductDto> products = productService.getProductsWithPageAndSearch(keyword,page,size);
            return ResponseEntity.ok(products);
        } else {
            Page<ProductDto> products = productService.getProductsWithPage(page,size);
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getProductByCategoryId(
            @PathVariable("categoryId") Integer categoryId,
            @RequestParam(value = "brandId",required = false) Integer brandId,
            @RequestParam(value = "keyword",defaultValue = "", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0",required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "field", defaultValue = "id", required = false) String field,
            @RequestParam(value = "sort-order",defaultValue = "asc",required = false) String sortOrder
    ) {
        Page<ProductDto> products;
        if (brandId != null) {
            products = productService.getProductsByCategoryIdAndBrandIdWithSearchAndSort(
                    categoryId, brandId, page, size, field, sortOrder, keyword);
        } else {
            products = productService.getProductsByCategoryIdWithSearchAndSort(
                    categoryId, page, size, field, sortOrder, keyword);
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") int id) {
        return ResponseEntity.ok(productService.getProductById(id));
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

    @PostMapping("/filters")
    public ResponseEntity<?> filterProduct(
            @RequestBody FilterDto filters,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size
    ) {
        return ResponseEntity.ok(productService.getProductsByFilters(filters, page, size));
    }

    @GetMapping("/{slug}/slug")
    public ResponseEntity<?> getProductSlug(@PathVariable("slug") String slug) {
        return ResponseEntity.ok(productService.getBySlug(slug));
    }
}
