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
                review.getCreatedAt(),
                review.getHasPurchased(),
                review.getUser().getId(),
                review.getProduct().getId()
        );
    }

    public static Review mapToReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setCreatedAt(reviewDto.getCreatedAt());
        review.setHasPurchased(reviewDto.getHasPurchased());

        if(reviewDto.getUserId() != null){
            Users user = new Users();
            user.setId(reviewDto.getUserId());
            review.setUser(user);
        }

        if(reviewDto.getProductId() != null){
            Product product = new Product();
            product.setId(reviewDto.getProductId());
            review.setProduct(product);
        }
        return review;
    }
}
