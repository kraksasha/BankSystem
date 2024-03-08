package com.example.banksystem.Service;

import com.example.banksystem.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class PercentUser {
    private UserService userService;

    public PercentUser(UserService userService) {
        this.userService = userService;
        addPercent();
    }

    public synchronized void addPercent(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (userService.getUserNow() != null){
                        while (true){
                            try {
                                Thread.sleep(300000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            User user = userService.getMyUser();
                            double moneyPercent = user.getMoney()*0.05;
                            double moneyUser = user.getMoney() + moneyPercent;
                            user.setMoney(moneyUser);
                            userService.addNewUser(user);
                        }
                    }
                }
            }
        }).start();
    }
}
