package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.ReviewDto;
import com.tambao.EshopManeger_Backend.entity.Product;
import com.tambao.EshopManeger_Backend.entity.Review;
import com.tambao.EshopManeger_Backend.entity.Users;

public class ReviewMapper {

    public static ReviewDto mapToReviewDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getUser().getId(),
                review.getProduct().getId()
        );
    }

    public static Review mapToReview(ReviewDto reviewDto, Users user, Product product) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setUser(user);
        review.setProduct(product);
        return review;
    }
}
