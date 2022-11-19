package com.popoola.shopping.Servuces.Implementation;

import com.popoola.shopping.Enums.ResponseEnum;
import com.popoola.shopping.Exceptions.ShoppingException;
import com.popoola.shopping.Models.Chats;
import com.popoola.shopping.Models.DTOs.UpdateChatDTO;
import com.popoola.shopping.Models.User;
import com.popoola.shopping.Repos.ChatRepo;
import com.popoola.shopping.Servuces.Interfaces.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class ChatService implements IChatService {

    @Autowired
    ChatRepo chatRepo;

    @Override
    public Chats findOne(Long chatId) {
        Chats chat = chatRepo.findByChatId(chatId);
        return chat;
    }

    @Override
    public List<Chats> findAll() {
        List<Chats> chats = chatRepo.findAll();
        return chats;
    }

    @Override
    @Transactional
    public Chats update(UpdateChatDTO updateChatDTO) {
        Chats chat = findOne(updateChatDTO.getId());

        if (chat == null) throw new ShoppingException(ResponseEnum.CHAT_DOES_NOT_EXIST);

        if (updateChatDTO.getReplies() != null) chat.getReplies().add(updateChatDTO.getReplies());


        return chatRepo.save(chat);
    }

    @Override
    @Transactional
    public Chats userUpdate(UpdateChatDTO updateChatDTO, User user) {
        Chats chat = user.getChats();

        if (chat == null) throw new ShoppingException(ResponseEnum.CHAT_DOES_NOT_EXIST);

        if (updateChatDTO.getMessages() != null) chat.getMessages().add(updateChatDTO.getMessages());


        return chatRepo.save(chat);
    }

    @Override
    public Chats getChat(User user) {
        return user.getChats();
    }

    @Override
    public Chats save(Chats chat) {
        if (chat == null) throw new ShoppingException(ResponseEnum.CHAT_DOES_NOT_EXIST);

        return chatRepo.save(chat);
    }
}
