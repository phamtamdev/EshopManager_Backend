package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.dto.ProductVariantDto;
import com.tambao.EshopManeger_Backend.service.Impl.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-variant")
public class ProductVariantController {

    @Autowired
    private ProductVariantService productVariantService;

    @GetMapping
    public ResponseEntity<?> getAllProductVariants() {
        return new ResponseEntity<>(productVariantService.getAllProductVariants(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addProductVariant(@RequestBody ProductVariantDto productVariantDto) {
        productVariantDto.setId(0);
        productVariantDto = productVariantService.addProductVariant(productVariantDto);
        return new ResponseEntity<>(productVariantDto, HttpStatus.CREATED);
    }

    @GetMapping("/productId/{productId}")
    public ResponseEntity<?> getProductVariantByProductId(@PathVariable("productId") Integer productId) {
        List<ProductVariantDto> productVariantDtos = productVariantService.getProductVariantByProductId(productId);
        if(productVariantDtos.isEmpty()){
            return new ResponseEntity<>("Product Variant Not Found With ProductId = "+productId,HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(productVariantDtos);
    }

    @GetMapping("/{variantId}/variantId")
    public ResponseEntity<?> getProductVariantByVariantId(@PathVariable("variantId") Integer variantId) {
        List<ProductVariantDto> productVariantDtos = productVariantService.getByVariantId(variantId);
        return ResponseEntity.ok(productVariantDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductVariant(@PathVariable("id") Integer id, @RequestBody ProductVariantDto productVariantDto) {
        return ResponseEntity.ok(productVariantService.updateProductVariant(id, productVariantDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductVariant(@PathVariable("id") Integer id) {
        productVariantService.deleteProductVariant(id);
        return ResponseEntity.ok("Deleted product variant successfully");
    }
}
