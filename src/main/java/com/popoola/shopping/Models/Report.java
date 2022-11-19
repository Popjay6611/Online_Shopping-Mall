package com.popoola.shopping.Models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
public class Report implements Serializable {
    private static final long serialVersionUID = 236781L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @CreationTimestamp
    private LocalDateTime created;

    @ElementCollection(targetClass=OrderedProducts.class)
    private List<OrderedProducts> ordersList;

    public Long getChatId() {
        return reportId;
    }

    public void setChatId(Long chatId) {
        this.reportId = reportId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public List<OrderedProducts> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<OrderedProducts> ordersList) {
        this.ordersList = ordersList;
    }
}
