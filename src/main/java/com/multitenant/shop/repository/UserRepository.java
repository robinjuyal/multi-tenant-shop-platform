package com.multitenant.shop.repository;

import com.multitenant.shop.entity.User;
import com.multitenant.shop.enums.Enums;
import com.multitenant.shop.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByTenantIdAndEmail(String tenantId, String email);

    Optional<User> findByTenantIdAndPhone(String tenantId, String phone);

    Optional<User> findByTenantIdAndEmailOrPhone(String tenantId, String email, String phone);

    Optional<User> findByEmailVerificationToken(String token);

    Optional<User> findByPasswordResetToken(String token);

    List<User> findByTenantIdAndRole(String tenantId, Enums.UserRole role);

    @Query("SELECT u FROM User u WHERE u.tenantId = :tenantId AND u.shop.id = :shopId")
    List<User> findByTenantIdAndShopId(@Param("tenantId") String tenantId, @Param("shopId") Long shopId);

    @Query("SELECT u FROM User u WHERE u.tenantId = :tenantId AND u.shop.id = :shopId AND u.role = :role")
    List<User> findByTenantIdAndShopIdAndRole(@Param("tenantId") String tenantId,
                                              @Param("shopId") Long shopId,
                                              @Param("role") Enums.UserRole role);

    @Query("SELECT COUNT(u) FROM User u WHERE u.tenantId = :tenantId AND u.role = :role AND u.isActive = true")
    long countByTenantIdAndRoleAndIsActive(@Param("tenantId") String tenantId, @Param("role") Enums.UserRole role);

    @Query("SELECT u FROM User u WHERE u.tenantId = :tenantId AND u.isEmailVerified = false")
    List<User> findUnverifiedUsers(@Param("tenantId") String tenantId);

    boolean existsByTenantIdAndEmail(String tenantId, String email);

    boolean existsByTenantIdAndPhone(String tenantId, String phone);
}