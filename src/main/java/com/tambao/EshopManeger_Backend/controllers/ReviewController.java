package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.dto.ReviewDto;
import com.tambao.EshopManeger_Backend.service.Impl.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{productId}")
    public ResponseEntity<?> getReviewsByProductId(@PathVariable Integer productId) {
        return ResponseEntity.ok(reviewService.findByProduct_Id(productId));
    }

    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody ReviewDto reviewDto) {
        reviewDto.setId(0);
        reviewDto = reviewService.addReview(reviewDto);
        return new ResponseEntity<>(reviewDto, HttpStatus.CREATED);
    }
}
