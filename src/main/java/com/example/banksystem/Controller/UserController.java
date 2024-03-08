package com.example.banksystem.Controller;

import com.example.banksystem.DTO.Filter;
import com.example.banksystem.DTO.Transfer;
import com.example.banksystem.Entity.User;
import com.example.banksystem.Service.UserService;
import com.example.banksystem.Utils.JwtTokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {
    private UserService userService;
    private JwtTokenUtils jwtTokenUtils;
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, JwtTokenUtils jwtTokenUtils, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user){
        if (user.getPhone() == null || user.getEmail() == null){
            return new ResponseEntity<>("Поля login, phone, email обязательны для ввода", HttpStatus.BAD_REQUEST);
        }
        if (userService.checkUser(user)){
            String passwordEncode = passwordEncoder.encode(user.getPassword());
            user.setPassword(passwordEncode);
            userService.addNewUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Пользователь с таким логин или email или телефоном существует", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody User user){
        UserDetails userDetails = userService.loadUserByUsername(user.getLogin());
        String token = jwtTokenUtils.generateToken(userDetails);
        return new ResponseEntity<>(token,HttpStatus.OK);
    }

    @GetMapping("/mypage")
    public ResponseEntity<User> getUserPage(){
        return new ResponseEntity<>(userService.getMyUser(),HttpStatus.OK);
    }

    @GetMapping("/searchByPhone")
    public ResponseEntity<?> getUserByPhone(@RequestBody Filter filter){
        User user = userService.searchByPhone(filter.getPhone());
        if (user != null){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        return new ResponseEntity<>("Пользователь с таким номером не найден",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/searchByEmail")
    public ResponseEntity<?> getUserByEmail(@RequestBody Filter filter){
        User user = userService.searchByEmail(filter.getEmail());
        if (user != null){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        return new ResponseEntity<>("Пользователь с таким email не найден",HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/transfer/{id}")
    public ResponseEntity<?> transferMoneyToUser(@RequestBody Transfer transfer, @PathVariable(name = "id") Long id){
        double moneyUser = userService.getMyUser().getMoney();
        if (moneyUser <= transfer.getMoney()){
            return new ResponseEntity<>("Недостаточно средств", HttpStatus.BAD_REQUEST);
        }
        userService.transferMoney(transfer,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/searchByName")
    public ResponseEntity<?> getUsersByName(@RequestBody Filter filter){
        List<User> list = userService.searchByName(filter.getName());
        if (list.size() != 0){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }
        return new ResponseEntity<>("Пользователи с такой фамилией не найдены", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/searchByDateBorn")
    public ResponseEntity<?> getUsersByDateBorn(@RequestBody Filter filter){
        List<User> list = userService.searchByDateBorn(filter.getDataBorn());
        if (list.size() != 0){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }
        return new ResponseEntity<>("Пользователи младше этой даты не найдены", HttpStatus.BAD_REQUEST);
    }
}
