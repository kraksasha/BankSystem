package com.example.banksystem.Service;

import com.example.banksystem.Entity.Email;
import com.example.banksystem.Entity.Phone;
import com.example.banksystem.Repository.EmailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private EmailRepository emailRepository;

    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public void addEmail(Email email, Long id){
        email.setUserId(id);
        emailRepository.save(email);
    }

    public void deleteEmail(Long id){
        emailRepository.deleteById(id);
    }

    public List<Email> getAllEmails(){
        return emailRepository.findAll();
    }
}
