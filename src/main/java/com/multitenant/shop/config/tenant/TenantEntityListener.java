package com.multitenant.shop.config.tenant;

import com.multitenant.shop.entity.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class TenantEntityListener {

    @PrePersist
    public void setTenantAndAuditOnCreate(BaseEntity entity) {
        String tenantId = TenantContext.getTenantId();
        if (tenantId != null && entity.getTenantId() == null) {
            entity.setTenantId(tenantId);
            log.debug("Set tenant ID {} for new entity: {}", tenantId, entity.getClass().getSimpleName());
        }

        String currentUser = getCurrentUser();
        if (currentUser != null && entity.getCreatedBy() == null) {
            entity.setCreatedBy(currentUser);
        }
    }

    @PreUpdate
    public void setAuditOnUpdate(BaseEntity entity) {
        String currentUser = getCurrentUser();
        if (currentUser != null) {
            entity.setUpdatedBy(currentUser);
        }
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getPrincipal().equals("anonymousUser")) {
            return authentication.getName();
        }
        return "system";
    }
}