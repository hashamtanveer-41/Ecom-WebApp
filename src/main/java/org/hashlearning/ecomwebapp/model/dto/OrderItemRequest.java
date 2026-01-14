package org.hashlearning.ecomwebapp.model.dto;

public record OrderItemRequest(
        int productId,
        int quantity
) {
}
