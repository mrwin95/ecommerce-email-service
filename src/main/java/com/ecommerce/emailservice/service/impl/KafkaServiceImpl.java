package com.ecommerce.emailservice.service.impl;

import com.ecommerce.emailservice.dto.EmailRequest;
import com.ecommerce.emailservice.service.KafkaService;
import com.ecommerce.emailservice.service.SendEmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    private SendEmailService sendEmailService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public KafkaServiceImpl(SendEmailService sendEmailService){
        this.sendEmailService = sendEmailService;
    }

    @Override
    @KafkaListener(topics = {"otp-auth-topic"}, groupId = "")
    public void listenOTP(String message) {
        try {

            JsonNode jsonNode = objectMapper.readTree(message);
            String email = jsonNode.get("email").asText();
            String otp = jsonNode.get("otp").asText();

            log.info("email: {}, otp: {}", email, otp);

            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setTo(email);
            emailRequest.setSubject("Send OTP from Kafka");
            emailRequest.setOtp(otp);

            sendEmailService.sendHtmlEmail(emailRequest);

        }catch (RuntimeException e){
            throw  e;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
