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

/**
 * TO DO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

/**
 * Spring MVC's @RestController includes:
 * @GetMapping
 * @PostMapping
 * @PutMapping
 * @PatchMapping
 * @DeleteMapping
**/

@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /*
     * Create a new account
     */
    @PostMapping("/register")
    public ResponseEntity<Account> saveAccountHandler(@RequestBody Account newAccount) throws BadRequestException, ConflictException {
        if (newAccount.getUsername().isEmpty() || newAccount.getPassword().length() <= 4) throw new BadRequestException();
        Account account = accountService.save(newAccount);
        if (account == null) throw new ConflictException();
        return ResponseEntity.ok(account);
    }

    /*
     * Login existing account
     */
    @PostMapping("/login")
    public ResponseEntity<Account> loginAccountHandler(@RequestBody Account requestedAccount) throws UnauthorizedException {
        Account account =  accountService.login(requestedAccount);
        if (account == null) throw new UnauthorizedException();
       return ResponseEntity.ok(account);
    }

    /*
     * Create a new message
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> saveMessageHandler(@RequestBody Message newMessage) throws BadRequestException {
        Message message = messageService.save(newMessage);
        String text = newMessage.getMessage_text();
        if (text.isEmpty() || text.length() > 254 || message == null) throw new BadRequestException();
        return ResponseEntity.ok(message);
    }

    /*
     * Fetch all messages
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> createMessageHandler() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    /*
     * Fetch message by message_id
     */
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageHandler(@PathVariable int message_id){
        Message message = messageService.getMessageById(message_id);
        return ResponseEntity.ok(message);
    }

    /*
     * Delete message by message_id
     */
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageHandler(@PathVariable int message_id){
        int binaryResult = messageService.deleteMessage(message_id);
        return ResponseEntity.ok(binaryResult);
    }

    /*
     * Update message_text
     */
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessageTextHandler(@RequestBody Message message, @PathVariable int message_id) throws BadRequestException {
        String text = message.getMessage_text();
        if (text.length() < 254 && !text.isEmpty()) {
            Message updatedMessage = messageService.updateText(message_id, text);
            if (updatedMessage != null) {
                return ResponseEntity.ok(1);
            }
        }
        throw new BadRequestException();
    }

    /*
     * Fetch all messages by account_id
     */
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountIdHandler(@PathVariable int account_id){
        List<Message> messages = messageService.getAllMessagesByAccountId(account_id);
        return ResponseEntity.ok(messages);
    }
}