package org.hashlearning.ecomwebapp.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Order {
    @Id
    private Long id;
    @Column(unique = true)
    private String orderId;
    private String email;
    private String status;
    private LocalDate orderDate;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}
