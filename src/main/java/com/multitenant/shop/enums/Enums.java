package com.multitenant.shop.enums;

public class Enums {

    public enum ShopType {
        RESTAURANT("Restaurant"),
        CAFE("Cafe"),
        HARDWARE_STORE("Hardware Store"),
        CAR_RENTAL("Car Rental"),
        ELECTRONICS("Electronics Store"),
        FASHION("Fashion Store"),
        GROCERY("Grocery Store"),
        PHARMACY("Pharmacy"),
        BOOKSTORE("Book Store"),
        GYM("Gym"),
        SALON("Beauty Salon"),
        OTHER("Other");

        private final String displayName;

        ShopType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum UserRole {
        SUPER_ADMIN("Super Admin"),
        SHOP_OWNER("Shop Owner"),
        SHOP_MANAGER("Shop Manager"),
        SHOP_STAFF("Shop Staff"),
        CUSTOMER("Customer");

        private final String displayName;

        UserRole(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum OrderStatus {
        PENDING("Pending"),
        CONFIRMED("Confirmed"),
        PREPARING("Preparing"),
        READY("Ready"),
        OUT_FOR_DELIVERY("Out for Delivery"),
        DELIVERED("Delivered"),
        CANCELLED("Cancelled"),
        REFUNDED("Refunded");

        private final String displayName;

        OrderStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum PaymentStatus {
        PENDING("Pending"),
        PROCESSING("Processing"),
        COMPLETED("Completed"),
        FAILED("Failed"),
        CANCELLED("Cancelled"),
        REFUNDED("Refunded");

        private final String displayName;

        PaymentStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum PaymentMethod {
        CASH("Cash"),
        CREDIT_CARD("Credit Card"),
        DEBIT_CARD("Debit Card"),
        UPI("UPI"),
        NET_BANKING("Net Banking"),
        WALLET("Digital Wallet");

        private final String displayName;

        PaymentMethod(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum ProductStatus {
        ACTIVE("Active"),
        INACTIVE("Inactive"),
        OUT_OF_STOCK("Out of Stock"),
        DISCONTINUED("Discontinued");

        private final String displayName;

        ProductStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum NotificationType {
        INFO("Information"),
        WARNING("Warning"),
        ERROR("Error"),
        SUCCESS("Success");

        private final String displayName;

        NotificationType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}