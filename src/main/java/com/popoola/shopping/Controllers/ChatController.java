package com.popoola.shopping.Controllers;
import com.popoola.shopping.Models.DTOs.UpdateChatDTO;
import com.popoola.shopping.Models.Message;
import com.popoola.shopping.Models.User;
import com.popoola.shopping.Servuces.Implementation.ChatService;
import com.popoola.shopping.Servuces.Implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

//@CrossOrigin("http:localhost:3000")
@Controller
//@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpleMessagingTemplate;

    @Autowired
    ChatService chatService;

    @Autowired
    UserService userService;

    @PutMapping("/send-message")
    public ResponseEntity<Object> sendMessage(@RequestBody UpdateChatDTO updateChatDTO, Principal principal){
        User user = userService.findByEmail(principal.getName());

        try{
            chatService.userUpdate(updateChatDTO, user);
           return ResponseEntity.ok(user.getChats());
        }
        catch (Exception ex) {
            ResponseEntity.badRequest().body("Sending message failed");
        }

        return null;
    }

    @PostMapping("/reply-message")
    public ResponseEntity<Object> replyMessage( @RequestBody UpdateChatDTO updateChatDTO, Principal principal,
    BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }

        return ResponseEntity.ok(chatService.update(updateChatDTO));
    }

    @GetMapping("/get-chat")
    public ResponseEntity<Object> getChat(Principal principal){
        User user = userService.findByEmail(principal.getName());
        return ResponseEntity.ok(user.getChats());
    }

    @GetMapping("/get-chats")
    public ResponseEntity<Object> getChats(){
        return ResponseEntity.ok(chatService.findAll());
    }

    @MessageMapping("/message") // /app/message
    @SendTo("/chatroom/public")
    private Message receivePublicMessage(@Payload Message message){
        return message;
    }

    @MessageMapping("/private-message")
    public Message receivePrivateMessage(@Payload Message message){

        simpleMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message); // /user/{username}/private
        return message;
    }


}
