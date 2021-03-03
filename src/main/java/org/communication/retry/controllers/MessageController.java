package org.communication.retry.controllers;

import org.communication.retry.entities.Message;
import org.communication.retry.repositories.CommunicationMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MessageController {

    private final CommunicationMessageRepository messageRepository;

    @Autowired
    public MessageController(CommunicationMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/index")
    public String listMessages(Model model){
        model.addAttribute("messages", messageRepository.findAll());
        return "index";
    }

    @GetMapping("/addForm")
    public String showAddMessageForm(Message message){
        return "add-form";
    }

    @PostMapping("/add")
    public String addMessage(@Valid Message message, BindingResult result, Model model){
        if(result.hasErrors()){
            return "add-form";
        }
        messageRepository.save(message);
        return "redirect:/index";
    }

    @GetMapping("/editForm/{id}")
    public String showEditMessageForm(@PathVariable("id") long id, Model model){
        Message message = messageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid message id "+id));
        model.addAttribute("message", message);
        return "update-form";
    }

    @PostMapping("/update/{id}")
    public String updateMessage(@PathVariable("id") long id, @Valid Message message, BindingResult result, Model model){
        if(result.hasErrors()){
            message.setId(id);
            return "update-form";
        }
        messageRepository.save(message);
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteMessage(@PathVariable("id") long id, Model model){
        Message message = messageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid message id "+id));
        messageRepository.delete(message);
        return "redirect:/index";
    }

}
