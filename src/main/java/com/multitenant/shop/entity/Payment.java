package com.multitenant.shop.entity;

import com.multitenant.shop.entity.BaseEntity;
import com.multitenant.shop.enums.Enums;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments", indexes = {
        @Index(name = "idx_payment_tenant_id", columnList = "tenant_id"),
        @Index(name = "idx_payment_order_id", columnList = "order_id"),
        @Index(name = "idx_payment_transaction_id", columnList = "transaction_id"),
        @Index(name = "idx_payment_status", columnList = "status"),
        @Index(name = "idx_payment_method", columnList = "payment_method")
})
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"order"})
@ToString(callSuper = true, exclude = {"order"})
public class Payment extends BaseEntity {

    @Column(name = "transaction_id", nullable = false, unique = true, length = 100)
    private String transactionId;

    @Column(name = "payment_gateway", length = 50)
    private String paymentGateway; // Razorpay, PayPal, Stripe, etc.

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private Enums.PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Enums.PaymentStatus status = Enums.PaymentStatus.PENDING;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", length = 3)
    private String currency = "INR";

    @Column(name = "gateway_transaction_id", length = 255)
    private String gatewayTransactionId;

    @Column(name = "gateway_response", columnDefinition = "TEXT")
    private String gatewayResponse; // JSON response from payment gateway

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "failure_reason", length = 500)
    private String failureReason;

    @Column(name = "refund_amount", precision = 10, scale = 2)
    private BigDecimal refundAmount = BigDecimal.ZERO;

    @Column(name = "refund_date")
    private LocalDateTime refundDate;

    @Column(name = "refund_reference", length = 100)
    private String refundReference;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Helper methods
    public boolean isSuccessful() {
        return status == Enums.PaymentStatus.COMPLETED;
    }

    public boolean isFailed() {
        return status == Enums.PaymentStatus.FAILED;
    }

    public boolean isRefunded() {
        return status == Enums.PaymentStatus.REFUNDED;
    }

    public BigDecimal getRefundableAmount() {
        return amount.subtract(refundAmount != null ? refundAmount : BigDecimal.ZERO);
    }
}