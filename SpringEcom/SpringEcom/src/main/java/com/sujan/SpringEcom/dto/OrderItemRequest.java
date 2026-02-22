package com.sujan.SpringEcom.dto;

import java.math.BigDecimal;

public record OrderItemRequest(
        int productId,
        int quantity
//        BigDecimal price
) {
}
