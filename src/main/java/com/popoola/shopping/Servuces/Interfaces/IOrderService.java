package com.popoola.shopping.Servuces.Interfaces;

import com.popoola.shopping.Models.Orders;
import com.popoola.shopping.Models.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IOrderService {
    Page<Orders> findAll(Pageable pageable);

    Page<Orders> findByStatus(Integer status, Pageable pageable);

    Page<Orders> findByEmail(String email, Pageable pageable);

    Page<Orders> findByPhoneNumber(String phone, Pageable pageable);

    Orders findOne(Long orderId);

    Orders finish(Long orderId);

    Orders cancel(Long orderId);

}
