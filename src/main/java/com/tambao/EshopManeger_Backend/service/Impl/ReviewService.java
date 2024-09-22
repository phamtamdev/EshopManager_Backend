package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.ReviewDto;
import com.tambao.EshopManeger_Backend.entity.Review;
import com.tambao.EshopManeger_Backend.mapper.ReviewMapper;
import com.tambao.EshopManeger_Backend.repository.ReviewRepository;
import com.tambao.EshopManeger_Backend.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService implements IReviewService {
    @Autowired
    private ReviewRepository reviewRepository;


    @Override
    public List<ReviewDto> findByProduct_Id(int id) {
        List<Review> reviews = reviewRepository.findByProduct_Id(id);
        if(reviews.isEmpty()){
            return null;
        }
        return reviews.stream()
                .map(ReviewMapper::mapToReviewDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDto addReview(ReviewDto reviewDto) {
        reviewDto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        Review review = reviewRepository.save(ReviewMapper.mapToReview(reviewDto));
        return ReviewMapper.mapToReviewDto(review);
    }
}
