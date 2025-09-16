package com.multitenant.shop.repository;
import com.multitenant.shop.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
interface ReviewRepository extends BaseRepository<Review> {

    @Query("SELECT r FROM Review r WHERE r.tenantId = :tenantId AND r.product.id = :productId AND r.isApproved = true")
    Page<Review> findApprovedReviewsByProductId(@Param("tenantId") String tenantId,
                                                @Param("productId") Long productId,
                                                Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.tenantId = :tenantId AND r.user.id = :userId")
    List<Review> findByTenantIdAndUserId(@Param("tenantId") String tenantId, @Param("userId") Long userId);

    @Query("SELECT r FROM Review r WHERE r.tenantId = :tenantId AND r.rating = :rating")
    List<Review> findByTenantIdAndRating(@Param("tenantId") String tenantId, @Param("rating") Integer rating);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.tenantId = :tenantId AND r.product.id = :productId AND r.isApproved = true")
    Double getAverageRatingByProductId(@Param("tenantId") String tenantId, @Param("productId") Long productId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.tenantId = :tenantId AND r.product.id = :productId AND r.isApproved = true")
    long countApprovedReviewsByProductId(@Param("tenantId") String tenantId, @Param("productId") Long productId);

    @Query("SELECT r FROM Review r WHERE r.tenantId = :tenantId AND r.isApproved = false")
    List<Review> findPendingReviews(@Param("tenantId") String tenantId);
}