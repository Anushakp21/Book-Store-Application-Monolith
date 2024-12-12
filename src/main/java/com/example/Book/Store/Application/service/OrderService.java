package com.example.Book.Store.Application.service;

import com.example.Book.Store.Application.requestdto.OrderRequest;
import com.example.Book.Store.Application.responsedto.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(String token,OrderRequest orderRequest);
}
