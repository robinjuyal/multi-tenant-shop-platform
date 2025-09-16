package com.multitenant.shop.entity;

import com.multitenant.shop.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "product_images", indexes = {
        @Index(name = "idx_product_image_tenant_id", columnList = "tenant_id"),
        @Index(name = "idx_product_image_product_id", columnList = "product_id"),
        @Index(name = "idx_product_image_display_order", columnList = "display_order")
})
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"product"})
@ToString(callSuper = true, exclude = {"product"})
public class ProductImage extends BaseEntity {

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "alt_text", length = 255)
    private String altText;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    @Column(name = "file_name", length = 255)
    private String fileName;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "mime_type", length = 100)
    private String mimeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}