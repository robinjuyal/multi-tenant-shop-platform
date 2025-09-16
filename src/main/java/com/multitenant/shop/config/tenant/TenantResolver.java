package com.multitenant.shop.config.tenant;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Component
@Slf4j
public class TenantResolver {

    @Value("${app.tenant.default-domain:localhost}")
    private String defaultDomain;

    @Value("${app.tenant.header-name:X-Tenant-ID}")
    private String tenantHeaderName;

    private static final Pattern SUBDOMAIN_PATTERN = Pattern.compile("^([a-zA-Z0-9\\-]+)\\.(.+)$");

    /**
     * Resolve tenant ID from HTTP request using multiple strategies:
     * 1. Header-based (X-Tenant-ID)
     * 2. Subdomain-based (tenant.domain.com)
     * 3. Domain-based (tenant-domain.com)
     */
    public String resolveTenant(HttpServletRequest request) {
        String tenantId = null;

        // Strategy 1: Header-based tenant resolution
        tenantId = resolveFromHeader(request);
        if (StringUtils.hasText(tenantId)) {
            log.debug("Tenant resolved from header: {}", tenantId);
            return tenantId;
        }

        // Strategy 2: Domain/Subdomain-based tenant resolution
        tenantId = resolveFromDomain(request);
        if (StringUtils.hasText(tenantId)) {
            log.debug("Tenant resolved from domain: {}", tenantId);
            return tenantId;
        }

        // Strategy 3: Path-based tenant resolution (optional)
        tenantId = resolveFromPath(request);
        if (StringUtils.hasText(tenantId)) {
            log.debug("Tenant resolved from path: {}", tenantId);
            return tenantId;
        }

        // Default tenant (usually for development)
        tenantId = getDefaultTenant();
        log.debug("Using default tenant: {}", tenantId);
        return tenantId;
    }

    private String resolveFromHeader(HttpServletRequest request) {
        return request.getHeader(tenantHeaderName);
    }

    private String resolveFromDomain(HttpServletRequest request) {
        String serverName = request.getServerName();

        if (serverName == null || serverName.equals(defaultDomain)) {
            return null;
        }

        // Check if it's a subdomain pattern (tenant.domain.com)
        var matcher = SUBDOMAIN_PATTERN.matcher(serverName);
        if (matcher.matches()) {
            String subdomain = matcher.group(1);
            // Exclude common subdomains
            if (!isCommonSubdomain(subdomain)) {
                return subdomain;
            }
        }

        // For custom domains, use the full domain as tenant ID
        return serverName.replace(".", "-").toLowerCase();
    }

    private String resolveFromPath(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        // Pattern: /api/tenant/{tenantId}/...
        if (requestURI.startsWith("/api/tenant/")) {
            String[] pathSegments = requestURI.split("/");
            if (pathSegments.length > 3) {
                return pathSegments[3];
            }
        }
        return null;
    }

    private boolean isCommonSubdomain(String subdomain) {
        return subdomain.equals("www") ||
                subdomain.equals("api") ||
                subdomain.equals("admin") ||
                subdomain.equals("app") ||
                subdomain.equals("dashboard") ||
                subdomain.equals("mail") ||
                subdomain.equals("ftp");
    }

    private String getDefaultTenant() {
        return "default";
    }

    public boolean isValidTenant(String tenantId) {
        // Add validation logic here
        // For now, accept any non-empty string
        return StringUtils.hasText(tenantId) && tenantId.length() <= 100;
    }
}