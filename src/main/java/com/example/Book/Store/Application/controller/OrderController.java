package com.example.Book.Store.Application.controller;

import com.example.Book.Store.Application.requestdto.OrderRequest;
import com.example.Book.Store.Application.responsedto.OrderResponse;
import com.example.Book.Store.Application.serviceimpl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @PostMapping("/order/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestHeader ("Authorization") String authHeader, OrderRequest orderRequest)
    {
        String token=authHeader.substring(7);
        OrderResponse orderResponse=orderService.placeOrder(token,orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }
}
