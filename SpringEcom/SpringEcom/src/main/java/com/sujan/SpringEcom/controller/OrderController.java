package com.sujan.SpringEcom.controller;

import com.sujan.SpringEcom.dto.OrderItemRequest;
import com.sujan.SpringEcom.dto.OrderItemResponse;
import com.sujan.SpringEcom.dto.OrderRequest;
import com.sujan.SpringEcom.dto.OrderResponse;
import com.sujan.SpringEcom.model.Order;
import com.sujan.SpringEcom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @PostMapping(value = "place", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest){
//        OrderResponse orderResponse = null;
        Order orderResponse = null;
        orderResponse = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderResponse,HttpStatus.OK);
    }


    @GetMapping(value = "orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderResponse>> getOrdersItem(){
        List<OrderResponse> orderResponse = null;
       orderResponse = orderService.getOrders();
        return new ResponseEntity<>(orderResponse,HttpStatus.OK);
    }


}
