package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.dto.CategoryDto;
import com.tambao.EshopManeger_Backend.mapper.CategoryMapper;
import com.tambao.EshopManeger_Backend.service.Impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "field", required = false) String field,
            @RequestParam(value = "keyword", required = false) String keyword) {
        if (page == null || size == null) {
            List<CategoryDto> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(categories);
        } else if (keyword != null && field != null) {
            Page<CategoryDto> categories = categoryService.getCategoriesWithPageAndSortingAndSearch(page, size, keyword, field);
            return ResponseEntity.ok(categories);
        } else if (keyword != null) {
            Page<CategoryDto> categories = categoryService.getCategoriesWithPageAndSearch(page, size, keyword);
            return ResponseEntity.ok(categories);
        } else if (field != null) {
            Page<CategoryDto> categories = categoryService.getCategoriesWithPageAndSorting(page, size, field);
            return ResponseEntity.ok(categories);
        } else {
            Page<CategoryDto> categories = categoryService.getCategoriesWithPage(page, size);
            return ResponseEntity.ok(categories);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody CategoryDto categoryDto) {
        categoryDto.setId(0);
        categoryDto = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Deleted Category");
    }

}
