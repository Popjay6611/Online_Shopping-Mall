package com.popoola.shopping.Servuces.Interfaces;

import com.popoola.shopping.Models.Chats;
import com.popoola.shopping.Models.DTOs.UpdateChatDTO;
import com.popoola.shopping.Models.DTOs.UpdateProductDTO;
import com.popoola.shopping.Models.Product;
import com.popoola.shopping.Models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IChatService {
    Chats findOne(Long chatId);
    List<Chats> findAll();
    Chats update(UpdateChatDTO updateChatDTO);

    Chats getChat(User user);

    Chats userUpdate(UpdateChatDTO updateChatDTO, User user);
    Chats save(Chats chat);
}
