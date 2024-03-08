package com.example.banksystem.Service;

import com.example.banksystem.DTO.Transfer;
import com.example.banksystem.Entity.Email;
import com.example.banksystem.Entity.Phone;
import com.example.banksystem.Entity.Role;
import com.example.banksystem.Entity.User;
import com.example.banksystem.Repository.EmailRepository;
import com.example.banksystem.Repository.PhoneRepository;
import com.example.banksystem.Repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PhoneRepository phoneRepository;
    private EmailRepository emailRepository;
    private User userNow;

    public User getUserNow() {
        return userNow;
    }

    public UserService(UserRepository userRepository, PhoneRepository phoneRepository, EmailRepository emailRepository) {
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
        this.emailRepository = emailRepository;
    }

    public User findByLogin(String login){
        List<User> list = userRepository.findAll();
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getLogin().equals(login)){
                return list.get(i);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        userNow = user;
        return new org.springframework.security.core.userdetails.User(user.getFirstName(),
                user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority>
    mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new
                SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public void addNewUser(User user){
        userRepository.save(user);
        Phone phone = new Phone();
        Email email = new Email();
        phone.setNumber(user.getPhone());
        phone.setUserId(user.getId());
        email.setEmail(user.getEmail());
        email.setUserId(user.getId());
        phoneRepository.save(phone);
        emailRepository.save(email);
    }

    public boolean checkUser(User user){
        List<User> list = userRepository.findAll();
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getLogin().equals(user.getLogin())){
                return false;
            }
            for (int j = 0; j < list.get(i).getPhones().size(); j++){
                if (list.get(i).getPhones().get(j).getNumber().equals(user.getPhone())){
                    return false;
                }
            }
            for (int k = 0; k < list.get(i).getEmails().size(); k++){
                if (list.get(i).getEmails().get(k).getEmail().equals(user.getEmail())){
                    return false;
                }
            }
        }

        return true;
    }

    public User getMyUser(){
        User user = userRepository.findById(userNow.getId()).get();
        return user;
    }

    public User searchByPhone(String phone){
        List<User> list = userRepository.findAll();
        for (int i = 0; i < list.size(); i++){
            for (int j = 0; j < list.get(i).getPhones().size(); j++){
                if (list.get(i).getPhones().get(j).getNumber().equals(phone)){
                    return list.get(i);
                }
            }
        }
        return null;
    }

    public User searchByEmail(String email){
        List<User> list = userRepository.findAll();
        for (int i = 0; i < list.size(); i++){
            for (int j = 0; j < list.get(i).getEmails().size(); j++){
                if (list.get(i).getEmails().get(j).getEmail().equals(email)){
                    return list.get(i);
                }
            }
        }
        return null;
    }

    public void transferMoney(Transfer transfer, Long id){
        User userFrom = getMyUser();
        User user = userRepository.findById(id).get();
        double transferTo = user.getMoney() + transfer.getMoney();
        double transferFrom = userFrom.getMoney() - transfer.getMoney();
        user.setMoney(transferTo);
        userFrom.setMoney(transferFrom);
        userRepository.save(userFrom);
        userRepository.save(user);
    }

    public List<User> searchByName(String name){
        List<User> list = userRepository.findAll();
        List<User> listUserByName = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getLastName().equalsIgnoreCase(name)){
                listUserByName.add(list.get(i));
            }
        }
        return listUserByName;
    }

    public List<User> searchByDateBorn(String dateBorn){
        String dateElement[] = dateBorn.split(".");
        int dateYear = Integer.parseInt(dateElement[2]);
        List<User> list = userRepository.findAll();
        List<User> listUserByDateBorn = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            String date[] = list.get(i).getDateBorn().split(".");
            int dateYearUser = Integer.parseInt(date[2]);
            if (dateYearUser > dateYear){
                listUserByDateBorn.add(list.get(i));
            }
        }
        return listUserByDateBorn;
    }

}
