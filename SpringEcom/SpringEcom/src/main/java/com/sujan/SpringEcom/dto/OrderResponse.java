package com.sujan.SpringEcom.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
        int orderId,
        String customerName,
        String email,
        String status,
        BigDecimal price,
        List<OrderItemResponse> items
) {
}
