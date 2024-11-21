package com.ecommerce.emailservice.controller;

import com.ecommerce.emailservice.dto.EmailRequest;
import com.ecommerce.emailservice.service.SendEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
public class SendEmailController {

    private static final Logger logger = LoggerFactory.getLogger(SendEmailController.class);
    @Qualifier("sesEmailService")
    private SendEmailService sendEmailService;

    public  SendEmailController(SendEmailService sendEmailService){
        this.sendEmailService = sendEmailService;
    }

    @PostMapping("/send-text")
    public ResponseEntity<String> sendTextEmail(@RequestBody EmailRequest emailRequest) throws Exception {
        try{
            logger.info("Send email to: {}", emailRequest.getTo());
            sendEmailService.sendEmailWithText(emailRequest);
            return ResponseEntity.ok("Text Email sent successfully");
        }catch (Exception e){
            logger.error("Error while sending email", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failure to send email: "+ e.getMessage());
        }

    }

    @PostMapping("/send-html")
    public ResponseEntity<String> sendAttachmentEmail(){
        return ResponseEntity.ok("HTML Email sent successfully");
    }
}
