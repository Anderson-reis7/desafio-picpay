package com.picpay.services;


import com.picpay.domain.transaction.Transaction;
import com.picpay.domain.user.User;
import com.picpay.dtos.TransactionDto;
import com.picpay.repositories.TransactionRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private NotificationService notificationService;

    public Transaction createTransection(TransactionDto transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.senderId());
        User reciever = this.userService.findUserById(transaction.receiverId());

        userService.transactionValidation(sender, transaction.value());

        if (!this.authorizeTransaction(sender, transaction.value())){
                throw new Exception("Unauthorized transaction!");
        }

        Transaction transac = new Transaction();
        transac.setAmount(transaction.value());
        transac.setSender(sender);
        transac.setReciever(reciever);
        transac.setTimeStamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        reciever.setBalance(reciever.getBalance().add(transaction.value()));

        this.transactionRepository.save(transac);
        this.userService.saverUser(sender);
        this.userService.saverUser(reciever);

        this.notificationService.sendNotification(sender, "Transaction successful!");
        this.notificationService.sendNotification(reciever, "Transaction received successfully!");

        return transac;
    }
    public boolean authorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<Map> authoTrans = restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);

        if (authoTrans.getStatusCode() == HttpStatus.OK && authoTrans.getBody().get("message").equals("Autorizado")){
            return true;
        }
        return false;
    }


}
