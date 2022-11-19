package com.popoola.shopping.Repos;

import com.popoola.shopping.Models.Chats;
import com.popoola.shopping.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;

@Component
public interface ChatRepo extends JpaRepository<Chats, Integer> {
    Chats findByChatId(Long id);
}
