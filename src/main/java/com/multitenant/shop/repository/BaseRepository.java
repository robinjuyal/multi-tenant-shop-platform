package com.multitenant.shop.repository;

import com.multitenant.shop.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    @Query("SELECT e FROM #{#entityName} e WHERE e.tenantId = :tenantId AND e.isActive = true")
    List<T> findAllByTenantIdAndIsActive(@Param("tenantId") String tenantId);

    @Query("SELECT e FROM #{#entityName} e WHERE e.tenantId = :tenantId AND e.isActive = true")
    Page<T> findAllByTenantIdAndIsActive(@Param("tenantId") String tenantId, Pageable pageable);

    @Query("SELECT e FROM #{#entityName} e WHERE e.tenantId = :tenantId AND e.id = :id AND e.isActive = true")
    Optional<T> findByTenantIdAndIdAndIsActive(@Param("tenantId") String tenantId, @Param("id") Long id);

    @Query("SELECT e FROM #{#entityName} e WHERE e.tenantId = :tenantId AND e.id = :id")
    Optional<T> findByTenantIdAndId(@Param("tenantId") String tenantId, @Param("id") Long id);

    @Query("SELECT COUNT(e) FROM #{#entityName} e WHERE e.tenantId = :tenantId AND e.isActive = true")
    long countByTenantIdAndIsActive(@Param("tenantId") String tenantId);

    @Query("UPDATE #{#entityName} e SET e.isActive = false WHERE e.tenantId = :tenantId AND e.id = :id")
    void softDeleteByTenantIdAndId(@Param("tenantId") String tenantId, @Param("id") Long id);
}