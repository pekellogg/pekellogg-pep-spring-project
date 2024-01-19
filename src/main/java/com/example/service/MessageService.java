package com.example.service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.EmptyResultDataAccessException;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * @param transient message
     * @return persisted message
     */
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    /**
     * @return all message entities
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * @return all message entities by posted_by
     */
    public List<Message> getAllMessagesByAccountId(int accountId) {
        return messageRepository.findAllByPostedBy(accountId);
    }

    /**
     * @param message entity's id
     * @return message entity
     */
    public Message getMessageById(int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);

        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        } else {
            return null;
        }
    }

    public int deleteMessage(int id) {
        try {
            messageRepository.deleteById(id);
            return 1;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    /**
     * @param id: message entity's id
     * @param replacement: message entity with updated field values
     * @return the updated message entity
     */
    public Message updateText(int id, String text) {
        // findById returns a type Optional<Message>. This helps the developer avoid null pointer
        // exceptions. We can use the method .get() to convert an Optional<Message> to Message.
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setMessage_text(text);
            messageRepository.save(message);
            return message;
        } else {
            return null;
        }
    }
}