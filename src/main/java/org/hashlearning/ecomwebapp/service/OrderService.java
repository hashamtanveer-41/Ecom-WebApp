package org.hashlearning.ecomwebapp.service;

import org.hashlearning.ecomwebapp.model.Order;
import org.hashlearning.ecomwebapp.model.OrderItem;
import org.hashlearning.ecomwebapp.model.Product;
import org.hashlearning.ecomwebapp.model.dto.OrderItemRequest;
import org.hashlearning.ecomwebapp.model.dto.OrderItemResponse;
import org.hashlearning.ecomwebapp.model.dto.OrderRequest;
import org.hashlearning.ecomwebapp.model.dto.OrderResponse;
import org.hashlearning.ecomwebapp.repo.OrderRepo;
import org.hashlearning.ecomwebapp.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private OrderRepo orderRepo;

    // Function to place the order
    public OrderResponse placeOrder(OrderRequest orderRequest) {

        // Creating order and assigning the values to it
        Order order = new Order();
        String id = UUID.randomUUID().toString().substring(0,8).toUpperCase();
        order.setOrderId(id);
        order.setOrderDate(LocalDate.now());
        order.setStatus("Placed");
        order.setEmail(orderRequest.email());
        order.setCustomerName(orderRequest.customerName());

        // Creating a list to store all the ordered items
        List<OrderItem> orderItems = new ArrayList<>();

        // Iterating for all the requested items
        for (OrderItemRequest itemRequest: orderRequest.items()){

            // Checking for the product
            Product product = productRepo.findById(itemRequest.productId())
                    .orElseThrow(()->new RuntimeException("Product not found"));
            // Deducting the stock
            product.setStockQuantity(product.getStockQuantity()-itemRequest.quantity());
            // Updating the database
            productRepo.save(product);

            // Getting the order Item
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .order(order)
                    .quantity(itemRequest.quantity())
                    .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity())))
                    .build();
            // Adding the item to the list
            orderItems.add(orderItem);
        }
        // Adding the items ordered items in the order
        order.setOrderItems(orderItems);
        // Saving the order in the database and saving for the order response
        Order savedOrder= orderRepo.save(order);

        // List for the items ordered responses
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        // Iterating to each ordered item and setting the response for it and adding it to the list
        for (OrderItem item: order.getOrderItems()){
            OrderItemResponse orderItemResponse = new OrderItemResponse(
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getTotalPrice()
            );
            orderItemResponses.add(orderItemResponse);
        }
        // Generating the response according to the saved order
        OrderResponse orderResponse = new OrderResponse(savedOrder.getOrderId(),
                savedOrder.getCustomerName(),
                savedOrder.getEmail(),
                savedOrder.getStatus(),
                savedOrder.getOrderDate(),
                orderItemResponses);


        return orderResponse;
    }

    public List<OrderResponse> getAllOrderResponses() {
        return null;
    }
}
