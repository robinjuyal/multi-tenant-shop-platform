package com.multitenant.shop.entity;

import com.multitenant.shop.entity.BaseEntity;
import com.multitenant.shop.enums.Enums;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders", indexes = {
        @Index(name = "idx_order_tenant_id", columnList = "tenant_id"),
        @Index(name = "idx_order_shop_id", columnList = "shop_id"),
        @Index(name = "idx_order_customer_id", columnList = "customer_id"),
        @Index(name = "idx_order_number", columnList = "order_number", unique = true),
        @Index(name = "idx_order_status", columnList = "status"),
        @Index(name = "idx_order_payment_status", columnList = "payment_status")
})
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"shop", "customer", "orderItems", "payment"})
@ToString(callSuper = true, exclude = {"shop", "customer", "orderItems", "payment"})
public class Order extends BaseEntity {

    @Column(name = "order_number", nullable = false, unique = true, length = 50)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Enums.OrderStatus status = Enums.OrderStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private Enums.PaymentStatus paymentStatus = Enums.PaymentStatus.PENDING;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Column(name = "tax_amount", precision = 10, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "shipping_charge", precision = 10, scale = 2)
    private BigDecimal shippingCharge = BigDecimal.ZERO;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "currency", length = 3)
    private String currency = "INR";

    // Customer Information
    @Column(name = "customer_name", nullable = false, length = 255)
    private String customerName;

    @Column(name = "customer_email", nullable = false, length = 255)
    private String customerEmail;

    @Column(name = "customer_phone", length = 20)
    private String customerPhone;

    // Billing Address
    @Column(name = "billing_address", columnDefinition = "TEXT")
    private String billingAddress;

    @Column(name = "billing_city", length = 100)
    private String billingCity;

    @Column(name = "billing_state", length = 100)
    private String billingState;

    @Column(name = "billing_country", length = 100)
    private String billingCountry;

    @Column(name = "billing_postal_code", length = 20)
    private String billingPostalCode;

    // Shipping Address
    @Column(name = "shipping_address", columnDefinition = "TEXT")
    private String shippingAddress;

    @Column(name = "shipping_city", length = 100)
    private String shippingCity;

    @Column(name = "shipping_state", length = 100)
    private String shippingState;

    @Column(name = "shipping_country", length = 100)
    private String shippingCountry;

    @Column(name = "shipping_postal_code", length = 20)
    private String shippingPostalCode;

    @Column(name = "special_instructions", columnDefinition = "TEXT")
    private String specialInstructions;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "estimated_delivery_time")
    private LocalDateTime estimatedDeliveryTime;

    @Column(name = "actual_delivery_time")
    private LocalDateTime actualDeliveryTime;

    @Column(name = "is_delivery")
    private Boolean isDelivery = false;

    @Column(name = "is_pickup")
    private Boolean isPickup = false;

    @Column(name = "coupon_code", length = 50)
    private String couponCode;

    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "cancelled_reason", length = 500)
    private String cancelledReason;

    @Column(name = "refund_amount", precision = 10, scale = 2)
    private BigDecimal refundAmount = BigDecimal.ZERO;

    @Column(name = "refund_reason", length = 500)
    private String refundReason;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Payment payment;

    // Helper methods
    public void calculateTotalAmount() {
        this.totalAmount = subtotal
                .add(taxAmount != null ? taxAmount : BigDecimal.ZERO)
                .add(shippingCharge != null ? shippingCharge : BigDecimal.ZERO)
                .subtract(discountAmount != null ? discountAmount : BigDecimal.ZERO);
    }

    public boolean isDelivered() {
        return status == Enums.OrderStatus.DELIVERED;
    }

    public boolean isCancelled() {
        return status == Enums.OrderStatus.CANCELLED;
    }

    public boolean isPaid() {
        return paymentStatus == Enums.PaymentStatus.COMPLETED;
    }
}