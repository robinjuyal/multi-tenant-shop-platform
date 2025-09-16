package com.multitenant.shop.repository;

import com.multitenant.shop.entity.Shop;
import com.multitenant.shop.enums.Enums;
import com.multitenant.shop.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends BaseRepository<Shop> {

    Optional<Shop> findByDomain(String domain);

    Optional<Shop> findByTenantId(String tenantId);

    @Query("SELECT s FROM Shop s WHERE s.tenantId = :tenantId AND s.isActive = true")
    Optional<Shop> findActiveShopByTenantId(@Param("tenantId") String tenantId);

    List<Shop> findByShopTypeAndIsActive(Enums.ShopType shopType, Boolean isActive);

    @Query("SELECT s FROM Shop s WHERE s.city = :city AND s.isActive = true")
    List<Shop> findActiveShopsByCity(@Param("city") String city);

    @Query("SELECT s FROM Shop s WHERE s.state = :state AND s.isActive = true")
    List<Shop> findActiveShopsByState(@Param("state") String state);

    @Query("SELECT COUNT(s) FROM Shop s WHERE s.shopType = :shopType AND s.isActive = true")
    long countByShopTypeAndIsActive(@Param("shopType") Enums.ShopType shopType);

    @Query("SELECT s FROM Shop s WHERE s.deliveryAvailable = true AND s.isActive = true")
    List<Shop> findShopsWithDelivery();

    boolean existsByDomain(String domain);

    boolean existsByTenantId(String tenantId);
}