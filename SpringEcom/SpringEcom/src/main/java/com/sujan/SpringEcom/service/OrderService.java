package com.sujan.SpringEcom.service;


import com.sujan.SpringEcom.GlobalExceptionHandler;
import com.sujan.SpringEcom.dto.OrderItemRequest;
import com.sujan.SpringEcom.dto.OrderItemResponse;
import com.sujan.SpringEcom.dto.OrderRequest;
import com.sujan.SpringEcom.dto.OrderResponse;
import com.sujan.SpringEcom.model.Order;
import com.sujan.SpringEcom.model.OrderItem;
import com.sujan.SpringEcom.model.Product;
import com.sujan.SpringEcom.repo.OrderRepo;
import com.sujan.SpringEcom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    public OrderRepo orderRepo;
    @Autowired
    public ProductRepo productRepo;


    public Order placeOrder(OrderRequest request){

        Order order = new Order();

        String orderId = "ORD" + UUID.randomUUID().toString()
                .substring(0,8).toUpperCase();

        order.setOrderId(orderId);
        order.setCustomerName(request.customerName());
        order.setEmail(request.email());
        order.setOrderDate(LocalDate.now());
        order.setStatus("PLACED");

        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderItemRequest item : request.items()){

            OrderItem orderItem = new OrderItem();

            Product p = productRepo.findById(item.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            orderItem.setProduct(p);
            orderItem.setQuantity(item.quantity());

            orderItem.setTotalPrice(
                    p.getPrice().multiply(BigDecimal.valueOf(item.quantity()))
            );

            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        orderRepo.save(order);

        return orderRepo.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Save Failed"));
    }

    public List<OrderResponse> getOrders() {

        return null;
    }
}
