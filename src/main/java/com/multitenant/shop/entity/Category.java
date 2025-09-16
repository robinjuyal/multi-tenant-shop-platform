package com.multitenant.shop.entity;

import com.multitenant.shop.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "categories", indexes = {
        @Index(name = "idx_category_tenant_id", columnList = "tenant_id"),
        @Index(name = "idx_category_shop_id", columnList = "shop_id"),
        @Index(name = "idx_category_parent_id", columnList = "parent_category_id"),
        @Index(name = "idx_category_slug", columnList = "slug")
})
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"shop", "parentCategory", "subCategories", "products"})
@ToString(callSuper = true, exclude = {"shop", "parentCategory", "subCategories", "products"})
public class Category extends BaseEntity {

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "slug", nullable = false, length = 255)
    private String slug;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Column(name = "meta_title", length = 255)
    private String metaTitle;

    @Column(name = "meta_description", length = 500)
    private String metaDescription;

    @Column(name = "meta_keywords", length = 500)
    private String metaKeywords;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Category> subCategories;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    // Helper methods
    public boolean isRootCategory() {
        return parentCategory == null;
    }

    public boolean hasSubCategories() {
        return subCategories != null && !subCategories.isEmpty();
    }
}