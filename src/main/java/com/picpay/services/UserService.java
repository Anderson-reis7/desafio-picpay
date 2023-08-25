package com.picpay.services;

import com.picpay.domain.user.User;
import com.picpay.domain.user.UserType;
import com.picpay.dtos.UserDTO;
import com.picpay.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void transactionValidation(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Merchant type user cannot perform transaction");
        }
        if (sender.getBalance().compareTo(amount) < 0){
            throw new Exception("invalid transaction! You have no positive balance!");
        }
    }

    public User findUserById(Long id) throws Exception {
        return userRepository.findUserById(id).orElseThrow(() -> new Exception("User not found!"));
    }

    public void saverUser(User user){
        this.userRepository.save(user);
    }
    public User createUser(UserDTO userDTO){
        User newUser = new User(userDTO);
        this.saverUser(newUser);
        return newUser;
    }
    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }
}
