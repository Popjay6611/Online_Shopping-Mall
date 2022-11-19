package com.popoola.shopping.Servuces.Implementation;

import com.popoola.shopping.Enums.OrderStatusEnum;
import com.popoola.shopping.Enums.ResponseEnum;
import com.popoola.shopping.Exceptions.ShoppingException;
import com.popoola.shopping.Models.*;
import com.popoola.shopping.Models.Orders;
import com.popoola.shopping.Repos.*;
import com.popoola.shopping.Servuces.Interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class OrderService implements IOrderService {

    @Autowired
    OrderRepo orderRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    ProductRepo productRepo;

    @Autowired
    ReportRepo reportRepo;
    @Autowired
    ProductService productService;
    @Autowired
    OrderedProductsRepo orderedProductsRepo;

    @Override
    public Page<Orders> findAll(Pageable pageable) {
        return orderRepo.findAllByOrderByOrderStatusAscCreatedDesc(pageable);
    }

    @Override
    public Page<Orders> findByStatus(Integer status, Pageable pageable) {
        return orderRepo.findAllByOrderStatusOrderByCreatedDesc(status, pageable);
    }

    @Override
    public Page<Orders> findByEmail(String email, Pageable pageable) {
        return orderRepo.findAllByCustomerEmailOrderByOrderStatusAscCreatedDesc(email, pageable);
    }

    @Override
    public Page<Orders> findByPhoneNumber(String phone, Pageable pageable) {
        return orderRepo.findAllByCustomerPhoneOrderByOrderStatusAscCreatedDesc(phone, pageable);
    }

    @Override
    public Orders findOne(Long orderId) {
        Orders order = orderRepo.findByOrderId(orderId);
        if (order == null) {
            throw new ShoppingException(ResponseEnum.ORDER_NOT_FOUND);
        }
        return order;
    }

    @Override
    @Transactional
    public Orders cancel(Long orderId) {
        Orders order = findOne(orderId);
        if (!order.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            throw new ShoppingException(ResponseEnum.ORDER_STATUS_ERROR);
        }

        order.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
        orderRepo.save(order);

        // Restore stock
        Iterable<OrderedProducts> products = order.getProducts();
        for (OrderedProducts prod: products){
            Product product = productRepo.findByProductId(prod.getProductId());
            if (product != null) {
                productService.increaseStock(prod.getProductId(), prod.getCount());
            }
        }
        return orderRepo.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public Orders finish(Long orderId) {
        Orders order = findOne(orderId);
        if (!order.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            throw new ShoppingException(ResponseEnum.ORDER_STATUS_ERROR);
        }

        order.setOrderStatus(OrderStatusEnum.Finished.getCode());
        orderRepo.save(order);

        Report report = new Report();

        for (OrderedProducts prod: order.getProducts()
             ) {
            report.getOrdersList().add(prod);
        }

        report.setCreated(LocalDateTime.now());
        reportRepo.save(report);

        return orderRepo.findByOrderId(orderId);
    }
}
