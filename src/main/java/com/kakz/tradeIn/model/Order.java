package com.kakz.tradeIn.model;

import com.kakz.tradeIn.domain.OrderStatus;
import com.kakz.tradeIn.domain.OrderType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;
    @Column(nullable = false)
    private OrderType orderType;
    @Column(nullable = false)
    private OrderStatus status;
    @Column(nullable = false)
    private BigDecimal price;
    private LocalDateTime timestamp = LocalDateTime.now();
    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL)
    private OrderItem orderItem;
}
