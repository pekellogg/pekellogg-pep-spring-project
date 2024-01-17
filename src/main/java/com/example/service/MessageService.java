package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * @param transient message
     * @return persisted message
     */
    public Message persistMessage(Message message) {
        return messageRepository.save(message);
    }

    /**
     * @return all message entities
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * @param message entity's id
     * @return message entity
     */
    public Message getMessageById(long id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);

        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        } else {
            return null;
        }
    }

    public void deleteMessage(long id) {
        messageRepository.deleteById(id);
    }

    /**
     * @param id: message entity's id
     * @param replacement: message entity with updated field values
     * @return the updated message entity
     */
    public Message updateMessage(long id, Message replacement) {
        // findById returns a type Optional<Message>. This helps the developer avoid null pointer
        // exceptions. We can use the method .get() to convert an Optional<Message> to Message.
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setMessage_text(replacement.getMessage_text());
            messageRepository.save(message);
            return message;
        } else {
            return null;
        }
    }
}