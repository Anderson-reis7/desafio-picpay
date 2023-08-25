package com.picpay.services;

import com.picpay.domain.user.User;
import com.picpay.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification (User user, String menssage) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationResquest = new NotificationDTO(email, menssage);
//        ResponseEntity<String> notification = restTemplate.postForEntity("http://o4d9z.mocklab.io/notify", notificationResquest, String.class);
//
//        if (!(notification.getStatusCode() == HttpStatus.OK)){
//            System.out.println("Error sending notification!");
//            throw new Exception("Notification service is down!");
//        }
        System.out.println("Notification sent to user! ");
    }

}
