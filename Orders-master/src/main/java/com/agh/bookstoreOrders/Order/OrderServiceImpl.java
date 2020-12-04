package com.agh.bookstoreOrders.Order;

import com.agh.bookstoreOrders.OrderProduct.OrderProduct;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;



    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

}
