package quant.ecommerce.enums;

public enum OrderStatus {
    PENDING_CONFIRMATION,   // Chờ xác nhận
    PENDING_PICKUP,         // Chờ lấy hàng
    PENDING_DELIVERY,       // Đang giao hàng
    DELIVERED,              // Đã giao
    RETURNED,               // Đã hoàn trả
    CANCELLED               // Đã hủy
}
