package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.dto.BrandDto;
import com.tambao.EshopManeger_Backend.service.Impl.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<?> getAllBrands(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "keyword",required = false) String keyword,
            @RequestParam(value = "field", required = false) String field,
            @RequestParam(value = "sort-order", required = false) String sortOrder
    ) {
        if(page == null || size == null){
            List<BrandDto> allBrands = brandService.getAllBrands();
            return new ResponseEntity<>(allBrands, HttpStatus.OK);
        }else if(keyword != null && field != null){
            Page<BrandDto> brands = brandService.getBrandsWithPageAndSearchAndSorting(page, size, keyword, field, sortOrder);
            return new ResponseEntity<>(brands, HttpStatus.OK);
        } else if (keyword != null) {
            Page<BrandDto> brands = brandService.getBrandsWithPageAndSearch(page, size, keyword);
            return new ResponseEntity<>(brands, HttpStatus.OK);
        } else if (field != null) {
            Page<BrandDto> brands = brandService.getBrandsWithPageAndSorting(page, size, field, sortOrder);
            return new ResponseEntity<>(brands, HttpStatus.OK);
        } else{
            Page<BrandDto> brands = brandService.getBrandsWithPage(page, size);
            return new ResponseEntity<>(brands, HttpStatus.OK);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBrands() {
        List<BrandDto> allBrands = brandService.getAllBrands();
        return new ResponseEntity<>(allBrands, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(brandService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/categoryId")
    public ResponseEntity<?> getBrandByCategoryId(@PathVariable(value = "categoryId") Integer categoryId) {
        return ResponseEntity.ok(brandService.findBrandByCategoryId(categoryId));
    }

    @GetMapping("/{categoryId}/categoryId/{brandName}")
    public ResponseEntity<?> getBrandByCategoryIdAndBrandName(@PathVariable(value = "categoryId") Integer categoryId, @PathVariable("brandName") String brandName) {
        return ResponseEntity.ok(brandService.findByCategoryIdAndName(categoryId, brandName));
    }

    @PostMapping
    public ResponseEntity<?> addBrand(@RequestBody BrandDto brandDto) {
        brandDto.setId(0);
        brandDto = brandService.save(brandDto);
        return new ResponseEntity<>(brandDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable Integer id, @RequestBody BrandDto brandDto) {
        return new ResponseEntity<>(brandService.update(id, brandDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Integer id) {
        brandService.delete(id);
        return ResponseEntity.ok("Deleted Brand Successfully");
    }
}
