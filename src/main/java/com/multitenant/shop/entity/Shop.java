package com.multitenant.shop.entity;

import com.multitenant.shop.entity.BaseEntity;
import com.multitenant.shop.enums.Enums;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "shops", indexes = {
        @Index(name = "idx_shop_tenant_id", columnList = "tenant_id"),
        @Index(name = "idx_shop_domain", columnList = "domain", unique = true),
        @Index(name = "idx_shop_type", columnList = "shop_type")
})
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Shop extends BaseEntity {

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "shop_type", nullable = false)
    private Enums.ShopType shopType;

    @Column(name = "domain", nullable = false, unique = true, length = 255)
    private String domain;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @Column(name = "banner_url", length = 500)
    private String bannerUrl;

    @Column(name = "theme_color", length = 7, columnDefinition = "VARCHAR(7) DEFAULT '#007bff'")
    private String themeColor = "#007bff";

    @Column(name = "is_open")
    private Boolean isOpen = true;

    @Column(name = "opening_time", length = 5)
    private String openingTime; // HH:MM format

    @Column(name = "closing_time", length = 5)
    private String closingTime; // HH:MM format

    @Column(name = "working_days", length = 50)
    private String workingDays; // Comma separated: MON,TUE,WED,THU,FRI

    @Column(name = "website_url", length = 500)
    private String websiteUrl;

    @Column(name = "social_media_links", columnDefinition = "TEXT")
    private String socialMediaLinks; // JSON format

    @Column(name = "delivery_available")
    private Boolean deliveryAvailable = false;

    @Column(name = "pickup_available")
    private Boolean pickupAvailable = true;

    @Column(name = "online_ordering")
    private Boolean onlineOrdering = false;

    @Column(name = "min_order_value")
    private Double minOrderValue = 0.0;

    @Column(name = "delivery_charge")
    private Double deliveryCharge = 0.0;

    @Column(name = "delivery_radius")
    private Double deliveryRadius = 5.0; // in kilometers

    @ElementCollection
    @CollectionTable(
            name = "shop_custom_settings",
            joinColumns = @JoinColumn(name = "shop_id"),
            indexes = @Index(name = "idx_shop_settings_shop_id", columnList = "shop_id")
    )
    @MapKeyColumn(name = "setting_key", length = 100)
    @Column(name = "setting_value", columnDefinition = "TEXT")
    private Map<String, String> customSettings = new HashMap<>();

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Category> categories;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
}