package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.dto.VariantDto;
import com.tambao.EshopManeger_Backend.service.Impl.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/variants")
public class VariantController {

    @Autowired
    private VariantService variantService;

    @GetMapping
    public ResponseEntity<?> getAllVariants(
            @RequestParam(value = "keyword",defaultValue = "", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0",required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(value = "field",defaultValue = "categoryId", required = false) String field,
            @RequestParam(value = "sort-order", defaultValue = "asc",required = false) String sortOrder
    ) {
        Page<VariantDto> variants = variantService.getVariantsWithPageAndSearchAndSorting(page, size, keyword, field, sortOrder);
        return new ResponseEntity<>(variants, HttpStatus.OK);
    }

    @GetMapping("/{variantId}")
    public ResponseEntity<?> getVariant(@PathVariable Integer variantId) {
        return ResponseEntity.ok(variantService.getVariantById(variantId));
    }

    @GetMapping("/{categoryId}/category")
    public ResponseEntity<?> getCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(variantService.getVariantsByCategoryId(categoryId));
    }

    @PostMapping
    public ResponseEntity<?> addVariant(@RequestBody VariantDto variantDto) {
        variantDto.setId(0);
        variantDto = variantService.saveVariant(variantDto);
        return new ResponseEntity<>(variantDto, HttpStatus.CREATED);
    }

    @PutMapping("/{variantId}")
    public ResponseEntity<?> updateVariant(@PathVariable Integer variantId, @RequestBody VariantDto variantDto) {
        return ResponseEntity.ok(variantService.updateVariant(variantId, variantDto));
    }

    @DeleteMapping("/{variantId}")
    public ResponseEntity<?> deleteVariant(@PathVariable Integer variantId) {
        variantService.deleteVariant(variantId);
        return ResponseEntity.ok("Variant deleted successfully");
    }
}
