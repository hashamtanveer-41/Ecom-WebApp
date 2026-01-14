package org.hashlearning.ecomwebapp.controllers;

import org.hashlearning.ecomwebapp.model.dto.OrderRequest;
import org.hashlearning.ecomwebapp.model.dto.OrderResponse;
import org.hashlearning.ecomwebapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest){
        OrderResponse orderResponse = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrderResponses(){
        return new ResponseEntity<>(orderService.getAllOrderResponses(), HttpStatus.FOUND);
    }
}
