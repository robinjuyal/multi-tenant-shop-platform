package com.multitenant.shop.entity;

import com.multitenant.shop.entity.BaseEntity;
import com.multitenant.shop.enums.Enums;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_product_tenant_id", columnList = "tenant_id"),
        @Index(name = "idx_product_shop_id", columnList = "shop_id"),
        @Index(name = "idx_product_category_id", columnList = "category_id"),
        @Index(name = "idx_product_sku", columnList = "sku"),
        @Index(name = "idx_product_slug", columnList = "slug"),
        @Index(name = "idx_product_status", columnList = "status"),
        @Index(name = "idx_product_featured", columnList = "is_featured")
})
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"shop", "category", "productImages", "orderItems", "reviews"})
@ToString(callSuper = true, exclude = {"shop", "category", "productImages", "orderItems", "reviews"})
public class Product extends BaseEntity {

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "slug", nullable = false, length = 255)
    private String slug;

    @Column(name = "sku", length = 100)
    private String sku;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "short_description", length = 500)
    private String shortDescription;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "compare_price", precision = 10, scale = 2)
    private BigDecimal comparePrice;

    @Column(name = "cost_price", precision = 10, scale = 2)
    private BigDecimal costPrice;

    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @Column(name = "tax_percentage", precision = 5, scale = 2)
    private BigDecimal taxPercentage = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Enums.ProductStatus status = Enums.ProductStatus.ACTIVE;

    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0;

    @Column(name = "min_stock_level")
    private Integer minStockLevel = 0;

    @Column(name = "track_inventory")
    private Boolean trackInventory = true;

    @Column(name = "allow_backorder")
    private Boolean allowBackorder = false;

    @Column(name = "weight", precision = 8, scale = 2)
    private BigDecimal weight;

    @Column(name = "length", precision = 8, scale = 2)
    private BigDecimal length;

    @Column(name = "width", precision = 8, scale = 2)
    private BigDecimal width;

    @Column(name = "height", precision = 8, scale = 2)
    private BigDecimal height;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Column(name = "is_digital")
    private Boolean isDigital = false;

    @Column(name = "requires_shipping")
    private Boolean requiresShipping = true;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "tags", length = 500)
    private String tags; // Comma-separated tags

    @Column(name = "brand", length = 100)
    private String brand;

    @Column(name = "model", length = 100)
    private String model;

    @Column(name = "color", length = 50)
    private String color;

    @Column(name = "size", length = 50)
    private String size;

    @Column(name = "material", length = 100)
    private String material;

    @Column(name = "warranty_period", length = 100)
    private String warrantyPeriod;

    @Column(name = "origin_country", length = 100)
    private String originCountry;

    @Column(name = "barcode", length = 50)
    private String barcode;

    @Column(name = "meta_title", length = 255)
    private String metaTitle;

    @Column(name = "meta_description", length = 500)
    private String metaDescription;

    @Column(name = "meta_keywords", length = 500)
    private String metaKeywords;

    @Column(name = "view_count")
    private Long viewCount = 0L;

    @Column(name = "average_rating", precision = 3, scale = 2)
    private BigDecimal averageRating = BigDecimal.ZERO;

    @Column(name = "review_count")
    private Integer reviewCount = 0;

    @ElementCollection
    @CollectionTable(
            name = "product_custom_attributes",
            joinColumns = @JoinColumn(name = "product_id"),
            indexes = @Index(name = "idx_product_attributes_product_id", columnList = "product_id")
    )
    @MapKeyColumn(name = "attribute_name", length = 100)
    @Column(name = "attribute_value", columnDefinition = "TEXT")
    private Map<String, String> customAttributes = new HashMap<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    // Helper methods
    public boolean isInStock() {
        if (!trackInventory) return true;
        return stockQuantity > 0 || allowBackorder;
    }

    public boolean isLowStock() {
        if (!trackInventory) return false;
        return stockQuantity <= minStockLevel;
    }

    public BigDecimal getDiscountedPrice() {
        if (discountPercentage != null && discountPercentage.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal discount = price.multiply(discountPercentage).divide(new BigDecimal("100"));
            return price.subtract(discount);
        }
        return price;
    }

    public BigDecimal getPriceWithTax() {
        BigDecimal taxAmount = price.multiply(taxPercentage).divide(new BigDecimal("100"));
        return price.add(taxAmount);
    }
}