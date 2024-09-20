package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.ReviewDto;

import java.util.List;

public interface IReviewService {
    List<ReviewDto> findByProduct_Id(int id);
    ReviewDto addReview(ReviewDto reviewDto);
}
