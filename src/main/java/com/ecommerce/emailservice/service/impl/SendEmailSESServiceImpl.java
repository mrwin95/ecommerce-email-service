package com.ecommerce.emailservice.service.impl;

import com.ecommerce.emailservice.dto.EmailRequest;
import com.ecommerce.emailservice.service.SendEmailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
@Qualifier("sesEmailService")
@Primary
public class SendEmailSESServiceImpl implements SendEmailService {

    private SesClient sesClient;

    public SendEmailSESServiceImpl(SesClient sesClient){ // hard to mock
        this.sesClient = sesClient;
    }
    @Override
    public void sendEmailWithText(EmailRequest emailRequest) {
        try {
            SendEmailRequest request = SendEmailRequest.builder()
                    .destination(Destination.builder().toAddresses(emailRequest.getTo()).build())
                    .message(Message.builder().subject(Content.builder().data(emailRequest.getSubject()).build()).body(Body.builder().text(Content.builder().data(emailRequest.getBody()).build()).build()).build())
                    .source(emailRequest.getFrom())
                    .build();

            sesClient.sendEmail(request);

        }catch (RuntimeException e){
        throw  new RuntimeException("Failure sending email", e);
        }
    }

    @Override
    public void sendEmailWithHtml(EmailRequest emailRequest) {
        // TODO: unimplemented
    }
}
