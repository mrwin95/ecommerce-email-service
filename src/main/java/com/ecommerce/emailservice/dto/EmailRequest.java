package com.ecommerce.emailservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class EmailRequest {

    private String from;
    private String to;
    private String subject;
    private String body;

    public EmailRequest(){}
    public EmailRequest(String from, String to, String subject, String body){
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }
    // Getters and Setters
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
