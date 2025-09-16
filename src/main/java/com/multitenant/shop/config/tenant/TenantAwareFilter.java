package com.multitenant.shop.config.tenant;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TenantAwareFilter {

    @PersistenceContext
    private EntityManager entityManager;

    public void enableTenantFilter() {
        String tenantId = TenantContext.getTenantId();
        if (tenantId != null) {
            Session session = entityManager.unwrap(Session.class);
            Filter filter = session.enableFilter("tenantFilter");
            filter.setParameter("tenantId", tenantId);
            log.debug("Enabled tenant filter for tenant: {}", tenantId);
        }
    }

    public void disableTenantFilter() {
        Session session = entityManager.unwrap(Session.class);
        session.disableFilter("tenantFilter");
        log.debug("Disabled tenant filter");
    }
}