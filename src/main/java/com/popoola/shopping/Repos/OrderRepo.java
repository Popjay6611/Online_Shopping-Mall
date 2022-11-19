package com.popoola.shopping.Repos;

import com.popoola.shopping.Models.Orders;
import com.popoola.shopping.Models.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Orders, Integer> {
    Orders findByOrderId(Long orderId);

    Page<Orders> findAllByOrderStatusOrderByCreatedDesc(Integer orderStatus, Pageable pageable);

    Page<Orders> findAllByCustomerEmailOrderByOrderStatusAscCreatedDesc(String buyerEmail, Pageable pageable);

    Page<Orders> findAllByOrderByOrderStatusAscCreatedDesc(Pageable pageable);

    Page<Orders> findAllByCustomerPhoneOrderByOrderStatusAscCreatedDesc(String buyerPhone, Pageable pageable);
}
