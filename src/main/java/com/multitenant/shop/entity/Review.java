package com.multitenant.shop.entity;

import com.multitenant.shop.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "reviews", indexes = {
        @Index(name = "idx_review_tenant_id", columnList = "tenant_id"),
        @Index(name = "idx_review_product_id", columnList = "product_id"),
        @Index(name = "idx_review_user_id", columnList = "user_id"),
        @Index(name = "idx_review_rating", columnList = "rating"),
        @Index(name = "idx_review_approved", columnList = "is_approved")
})
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"product", "user"})
@ToString(callSuper = true, exclude = {"product", "user"})
public class Review extends BaseEntity {

    @Column(name = "rating", nullable = false)
    private Integer rating; // 1 to 5

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "is_approved")
    private Boolean isApproved = false;

    @Column(name = "helpful_count")
    private Integer helpfulCount = 0;

    @Column(name = "not_helpful_count")
    private Integer notHelpfulCount = 0;

    @Column(name = "reviewer_name", length = 255)
    private String reviewerName;

    @Column(name = "reviewer_email", length = 255)
    private String reviewerEmail;

    @Column(name = "is_verified_purchase")
    private Boolean isVerifiedPurchase = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Helper methods
    public boolean isPositiveReview() {
        return rating >= 4;
    }

    public boolean isNegativeReview() {
        return rating <= 2;
    }

    public int getHelpfulnessScore() {
        return helpfulCount - notHelpfulCount;
    }
}