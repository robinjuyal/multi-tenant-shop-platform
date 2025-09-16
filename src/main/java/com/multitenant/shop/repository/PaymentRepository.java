package com.multitenant.shop.repository;

import com.multitenant.shop.entity.Payment;
import com.multitenant.shop.enums.Enums;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
interface PaymentRepository extends BaseRepository<Payment> {

    Optional<Payment> findByTenantIdAndTransactionId(String tenantId, String transactionId);

    Optional<Payment> findByTenantIdAndOrderId(String tenantId, Long orderId);

    @Query("SELECT p FROM Payment p WHERE p.tenantId = :tenantId AND p.status = :status")
    List<Payment> findByTenantIdAndStatus(@Param("tenantId") String tenantId,
                                          @Param("status") Enums.PaymentStatus status);

    @Query("SELECT p FROM Payment p WHERE p.tenantId = :tenantId AND p.paymentMethod = :method")
    List<Payment> findByTenantIdAndPaymentMethod(@Param("tenantId") String tenantId,
                                                 @Param("method") Enums.PaymentMethod method);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.tenantId = :tenantId AND p.status = 'COMPLETED'")
    BigDecimal getTotalPayments(@Param("tenantId") String tenantId);

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.tenantId = :tenantId AND p.status = 'FAILED'")
    long countFailedPayments(@Param("tenantId") String tenantId);
}