package com.popoola.shopping.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.popoola.shopping.Models.DTOs.Message;
import com.sun.istack.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.*;

@Entity
public class Chats implements Serializable {
    private static final long serialVersionUID = 236781L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;
    @NotEmpty
    @Email
    private String customerEmail;

    @NotNull
    private long customerId;

    @JsonIgnore
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @CreationTimestamp
    private Date created;

    @CreationTimestamp
    private Date updated;


    @ElementCollection(targetClass=String.class)
    private Set<String> messages = new HashSet<>();
    @ElementCollection(targetClass=String.class)
    private Set<String> replies = new HashSet<>();


    @Override
    public String toString() {
        return "Chats{" +
                "chatId=" + chatId +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerId=" + customerId +
                ", user=" + user +
                ", created=" + created +
                ", updated=" + updated +
                ", messages=" + messages +
                ", replies=" + replies +
                '}';
    }

    public Chats() {
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Set<String> getMessages() {
        return messages;
    }

    public void setMessages(Set<String> messages) {
        this.messages = messages;
    }

    public Set<String> getReplies() {
        return replies;
    }

    public void setReplies(Set<String> replies) {
        this.replies = replies;
    }



    public Chats( User user) {
        this.customerEmail = user.getEmail();
        this.user = user;

        this.customerId = user.getUserId();

    }


}
