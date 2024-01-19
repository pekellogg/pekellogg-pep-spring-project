package com.example.controller;
import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.entity.Message;
import com.example.service.MessageService;
import com.example.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /*
     * Create a new account
     */
    @PostMapping("/register")
    public ResponseEntity<Account> saveAccountHandler(@RequestBody Account newAccount) throws BadRequestException, ConflictException {
        // BadRequestException 400: empty username, password length of 4 or less
        if (newAccount.getUsername().isEmpty() || newAccount.getPassword().length() <= 4) throw new BadRequestException();
        Account account = accountService.save(newAccount);
        // ConflictException 409: null from unsuccessful save due to duplicate username
        if (account == null) throw new ConflictException();
        return ResponseEntity.ok(account);
    }

    /*
     * Login existing account
     */
    @PostMapping("/login")
    public ResponseEntity<Account> loginAccountHandler(@RequestBody Account requestedAccount) throws UnauthorizedException {
        Account account =  accountService.login(requestedAccount);
        // UnauthorizedException 401: null from unsuccessful account retrieval for account having username + password
        if (account == null) throw new UnauthorizedException();
        return ResponseEntity.ok(account);
    }

    /*
     * Create a new message
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> saveMessageHandler(@RequestBody Message newMessage) throws BadRequestException {
        int accountId = newMessage.getPosted_by();
        String text = newMessage.getMessage_text();
        // BadRequestException 400: unfound account, null text field, empty text, text length exceeding 255 chars, null message from params
        if (!accountService.existsById(accountId) || text == null || text.isBlank() || text.length() > 255 || newMessage == null) {
            throw new BadRequestException();
        }
        Message message = messageService.save(newMessage);
        return ResponseEntity.ok(message);
    }

    /*
     * Retrieve all messages
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> createMessageHandler() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    /*
     * Retrieve message by message_id
     */
    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessageHandler(@PathVariable int id) {
        Message message = messageService.getMessageById(id);
        return ResponseEntity.ok(message);
    }

    /*
     * Delete message by message_id
     */
    @DeleteMapping("/messages/{id}")
    public ResponseEntity<String> deleteMessageByMessageId(@PathVariable Integer id) {
        String result = messageService.deleteMessageById(id);
        return ResponseEntity.ok(result);
    }

    /*
     * Update message_text
     */
    @PatchMapping("/messages/{id}")
    public ResponseEntity<Integer> updateMessageTextHandler(@RequestBody Message message, @PathVariable int id) throws BadRequestException {
        String text = message.getMessage_text();
        if (text.length() <= 255 && !text.isEmpty()) {
            Message updatedMessage = messageService.updateText(id, text);
            if (updatedMessage != null) {
                return ResponseEntity.ok(1);
            }
        }
        // BadRequestException 400: text exceeding 255 chars, text empty
        throw new BadRequestException();
    }

    /*
     * Retrieve all messages by account_id
     */
    @GetMapping("/accounts/{id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountIdHandler(@PathVariable int id) {
        List<Message> messages = messageService.getAllMessagesByAccountId(id);
        return ResponseEntity.ok(messages);
    }
}