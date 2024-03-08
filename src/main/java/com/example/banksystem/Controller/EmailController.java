package com.example.banksystem.Controller;

import com.example.banksystem.Entity.Email;
import com.example.banksystem.Service.EmailService;
import com.example.banksystem.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmailController {
    private EmailService emailService;
    private UserService userService;

    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping("/addEmail/{id}")
    public ResponseEntity<?> addNewEmail(@PathVariable(name = "id") Long id, @RequestBody Email email){
        emailService.addEmail(email,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteEmail/{id}")
    public ResponseEntity<?> deleteOldEmail(@PathVariable(name = "id") Long id){
        List<Email> list = userService.getMyUser().getEmails();
        if (list.size() < 2){
            return new ResponseEntity<>("Нельзя удалить единственный email", HttpStatus.BAD_REQUEST);
        }
        emailService.deleteEmail(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
