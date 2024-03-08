package com.example.banksystem.Controller;

import com.example.banksystem.Entity.Phone;
import com.example.banksystem.Service.PhoneService;
import com.example.banksystem.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PhoneController {
    private PhoneService phoneService;
    private UserService userService;

    public PhoneController(PhoneService phoneService, UserService userService) {
        this.phoneService = phoneService;
        this.userService = userService;
    }

    @PostMapping("/addPhone/{id}")
    public ResponseEntity<?> addNumberPhone(@PathVariable(name = "id") Long id, @RequestBody Phone phone){
        phoneService.addNumber(phone,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deletePhone/{id}")
    public ResponseEntity<?> deleteNumberPhone(@PathVariable(name = "id") Long id){
        List<Phone> list = userService.getMyUser().getPhones();
        if (list.size() < 2){
            return new ResponseEntity<>("Нельзя удалить единственный номер", HttpStatus.BAD_REQUEST);
        }
        phoneService.deleteNumber(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
