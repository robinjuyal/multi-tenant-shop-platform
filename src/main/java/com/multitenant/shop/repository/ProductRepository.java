package com.multitenant.shop.repository;

import com.multitenant.shop.entity.Product;
import com.multitenant.shop.enums.Enums;
import com.multitenant.shop.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends BaseRepository<Product> {

    Optional<Product> findByTenantIdAndSlug(String tenantId, String slug);

    Optional<Product> findByTenantIdAndSku(String tenantId, String sku);

    @Query("SELECT p FROM Product p WHERE p.tenantId = :tenantId AND p.shop.id = :shopId AND p.isActive = true")
    Page<Product> findByTenantIdAndShopId(@Param("tenantId") String tenantId,
                                          @Param("shopId") Long shopId,
                                          Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.tenantId = :tenantId AND p.category.id = :categoryId AND p.isActive = true")
    Page<Product> findByTenantIdAndCategoryId(@Param("tenantId") String tenantId,
                                              @Param("categoryId") Long categoryId,
                                              Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.tenantId = :tenantId AND p.status = :status")
    Page<Product> findByTenantIdAndStatus(@Param("tenantId") String tenantId,
                                          @Param("status") Enums.ProductStatus status,
                                          Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.tenantId = :tenantId AND p.isFeatured = true AND p.isActive = true")
    List<Product> findFeaturedProducts(@Param("tenantId") String tenantId);

    @Query("SELECT p FROM Product p WHERE p.tenantId = :tenantId AND p.price BETWEEN :minPrice AND :maxPrice AND p.isActive = true")
    Page<Product> findByTenantIdAndPriceBetween(@Param("tenantId") String tenantId,
                                                @Param("minPrice") BigDecimal minPrice,
                                                @Param("maxPrice") BigDecimal maxPrice,
                                                Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.tenantId = :tenantId AND " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.tags) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND p.isActive = true")
    Page<Product> searchProducts(@Param("tenantId") String tenantId,
                                 @Param("keyword") String keyword,
                                 Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.tenantId = :tenantId AND p.trackInventory = true AND p.stockQuantity <= p.minStockLevel")
    List<Product> findLowStockProducts(@Param("tenantId") String tenantId);

    @Query("SELECT p FROM Product p WHERE p.tenantId = :tenantId AND p.trackInventory = true AND p.stockQuantity = 0")
    List<Product> findOutOfStockProducts(@Param("tenantId") String tenantId);

    @Query("SELECT AVG(p.price) FROM Product p WHERE p.tenantId = :tenantId AND p.isActive = true")
    BigDecimal findAveragePrice(@Param("tenantId") String tenantId);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.tenantId = :tenantId AND p.shop.id = :shopId AND p.isActive = true")
    long countByTenantIdAndShopId(@Param("tenantId") String tenantId, @Param("shopId") Long shopId);

    boolean existsByTenantIdAndSlug(String tenantId, String slug);

    boolean existsByTenantIdAndSku(String tenantId, String sku);
}