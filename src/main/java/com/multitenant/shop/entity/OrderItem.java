package com.multitenant.shop.entity;

import com.multitenant.shop.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items", indexes = {
        @Index(name = "idx_order_item_tenant_id", columnList = "tenant_id"),
        @Index(name = "idx_order_item_order_id", columnList = "order_id"),
        @Index(name = "idx_order_item_product_id", columnList = "product_id")
})
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"order", "product"})
@ToString(callSuper = true, exclude = {"order", "product"})
public class OrderItem extends BaseEntity {

    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    @Column(name = "product_sku", length = 100)
    private String productSku;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "tax_amount", precision = 10, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "product_options", columnDefinition = "TEXT")
    private String productOptions; // JSON format for product variants/options

    @Column(name = "special_instructions", length = 500)
    private String specialInstructions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // Helper methods
    public BigDecimal getSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal getFinalPrice() {
        return totalPrice.subtract(discountAmount != null ? discountAmount : BigDecimal.ZERO);
    }
}