package com.multitenant.shop.repository;

import com.multitenant.shop.entity.Order;
import com.multitenant.shop.enums.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
interface OrderRepository extends BaseRepository<Order> {

    Optional<Order> findByTenantIdAndOrderNumber(String tenantId, String orderNumber);

    @Query("SELECT o FROM Order o WHERE o.tenantId = :tenantId AND o.shop.id = :shopId")
    Page<Order> findByTenantIdAndShopId(@Param("tenantId") String tenantId,
                                        @Param("shopId") Long shopId,
                                        Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.tenantId = :tenantId AND o.customer.id = :customerId")
    Page<Order> findByTenantIdAndCustomerId(@Param("tenantId") String tenantId,
                                            @Param("customerId") Long customerId,
                                            Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.tenantId = :tenantId AND o.status = :status")
    Page<Order> findByTenantIdAndStatus(@Param("tenantId") String tenantId,
                                        @Param("status") Enums.OrderStatus status,
                                        Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.tenantId = :tenantId AND o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findByTenantIdAndCreatedAtBetween(@Param("tenantId") String tenantId,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.tenantId = :tenantId AND o.paymentStatus = 'COMPLETED'")
    BigDecimal getTotalRevenue(@Param("tenantId") String tenantId);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.tenantId = :tenantId AND o.status = :status")
    long countByTenantIdAndStatus(@Param("tenantId") String tenantId, @Param("status") Enums.OrderStatus status);

    boolean existsByTenantIdAndOrderNumber(String tenantId, String orderNumber);
}