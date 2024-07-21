package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.dto.ProductImageDto;
import com.tambao.EshopManeger_Backend.service.Impl.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-image")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductImageIsIcon(@PathVariable("productId") Integer productId, @RequestParam(value = "isIcon") Boolean icon) {
        ProductImageDto productImageDto = productImageService.findByProductIdIsIcon(productId, icon);
        return new ResponseEntity<>(productImageDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getProductImageIsIcon(@RequestParam("productId") Integer productId) {
        List<ProductImageDto> productImageDtos = productImageService.findByProductId(productId);
        if(productImageDtos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(productImageDtos);
    }

    @PostMapping
    public ResponseEntity<?> addProductImage(@RequestBody ProductImageDto productImageDto) {
        productImageDto.setId(0);
        productImageDto = productImageService.save(productImageDto);
        return new ResponseEntity<>(productImageDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductImage(@PathVariable Integer id, @RequestBody ProductImageDto productImageDto) {
        return new ResponseEntity<>(productImageService.update(id, productImageDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductImage(@PathVariable Integer id) {
        productImageService.deleteById(id);
        return ResponseEntity.ok("Deleted product image with id " + id);
    }
}
