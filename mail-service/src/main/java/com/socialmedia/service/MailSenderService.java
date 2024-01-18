package com.socialmedia.service;

import com.socialmedia.rabbitmq.model.MailActivationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender javaMailSender;

    public void sendMail(MailActivationModel model) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("mythmachiinex@gmail.com");
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("Hesap olusturma islemi basarili.....");
        mailMessage.setText("Aktivasyon Kodunuz: "+model.getActivationCode());
        mailMessage.setCc("mythmachiinex@gmail.com");
        javaMailSender.send(mailMessage);
    }
}
