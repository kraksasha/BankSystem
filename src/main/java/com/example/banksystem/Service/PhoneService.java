package com.example.banksystem.Service;

import com.example.banksystem.Entity.Phone;
import com.example.banksystem.Repository.PhoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneService {
    private PhoneRepository phoneRepository;

    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public void addNumber(Phone phone, Long id){
        phone.setUserId(id);
        phoneRepository.save(phone);
    }

    public void deleteNumber(Long id){
        phoneRepository.deleteById(id);
    }

}
