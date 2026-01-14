package org.hashlearning.ecomwebapp.model.dto;

public record OrderItemRequest(
        int productid,
        int quantity
) {
}
