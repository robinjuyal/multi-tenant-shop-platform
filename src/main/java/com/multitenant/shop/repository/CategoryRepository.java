package com.multitenant.shop.repository;

import com.multitenant.shop.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Category Repository
@Repository
interface CategoryRepository extends BaseRepository<Category> {

    Optional<Category> findByTenantIdAndSlug(String tenantId, String slug);

    @Query("SELECT c FROM Category c WHERE c.tenantId = :tenantId AND c.shop.id = :shopId AND c.parentCategory IS NULL")
    List<Category> findRootCategoriesByShopId(@Param("tenantId") String tenantId, @Param("shopId") Long shopId);

    @Query("SELECT c FROM Category c WHERE c.tenantId = :tenantId AND c.parentCategory.id = :parentId")
    List<Category> findSubCategories(@Param("tenantId") String tenantId, @Param("parentId") Long parentId);

    @Query("SELECT c FROM Category c WHERE c.tenantId = :tenantId AND c.isFeatured = true AND c.isActive = true")
    List<Category> findFeaturedCategories(@Param("tenantId") String tenantId);

    boolean existsByTenantIdAndSlug(String tenantId, String slug);
}