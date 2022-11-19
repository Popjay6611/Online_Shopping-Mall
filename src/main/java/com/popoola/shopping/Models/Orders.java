package com.popoola.shopping.Models;

import com.sun.istack.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Orders implements Serializable {
    private static final long serialVersion = 34299L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @OneToMany(cascade = CascadeType.ALL,
    fetch = FetchType.LAZY, mappedBy = "order")
    private Set<OrderedProducts> products = new HashSet<>();

    @NotNull
    private  long customerId;

    @NotNull
    private BigDecimal orderAmount;

    @NotEmpty
    private String customerEmail;

    @NotEmpty
    private String customerPhone;

    @NotEmpty
    private String customerAddress;
    @NotNull
    @ColumnDefault("0")
    private Integer orderStatus;

    @CreationTimestamp
    private LocalDateTime created;

    @CreationTimestamp
    private LocalDateTime updated;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Set<OrderedProducts> getProducts() {
        return products;
    }

    public void setProducts(Set<OrderedProducts> products) {
        this.products = products;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public Orders() {
    }

    public Orders(User user) {
      this.customerAddress = user.getAddress();
      this.customerEmail = user.getEmail();
      this.customerPhone = user.getPhoneNumber();
      this.customerId = user.getUserId();
      this.orderAmount = user.getCart().getProducts().stream().map(item -> item.getProductPrice().multiply(new BigDecimal(item.getCount())))
              .reduce(BigDecimal::add)
              .orElse(new BigDecimal(0));
      this.orderStatus = 0;

    }

}
