package com.ecommerce.emailservice.service.impl;

import com.ecommerce.emailservice.dto.EmailRequest;
import com.ecommerce.emailservice.service.SendEmailService;
import com.ecommerce.emailservice.util.LoadHtmlFile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import java.io.IOException;

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
                    .message(Message.builder().subject(Content.builder().data(emailRequest.getSubject()).build())
                            .body(Body.builder().text(Content.builder().data(emailRequest.getBody()).build()).build()).build())
                    .source(emailRequest.getFrom())
                    .build();

            sesClient.sendEmail(request);

        }catch (RuntimeException e){
        throw  new RuntimeException("Failure sending email", e);
        }
    }

    @Override
    public void sendHtmlEmail(EmailRequest emailRequest) throws IOException {
        // Load HTML file
        String templatePath = "templates/send-otp.html";
        String htmlTemplate = LoadHtmlFile.loadTemplate(templatePath);
        // replace placeholder
        String personalizedHtml = htmlTemplate.replace("{{name}}", emailRequest.getName());

        // build the email request with ses
        try{
            Destination destination = Destination.builder().toAddresses(emailRequest.getTo()).build();
            Content subject = Content.builder().data(emailRequest.getSubject()).build();
            Content bodyContent = Content.builder().data(personalizedHtml).build();
            Body body = Body.builder().html(bodyContent).build();
            Message message = Message.builder().subject(subject).body(body).build();

            SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                    .source(emailRequest.getFrom())
                    .destination(destination)
                    .message(message).build();

            // send the email
            sesClient.sendEmail(sendEmailRequest);
            System.out.println("Email sent successfully");
        }catch (RuntimeException e){
            throw  new RuntimeException("Email sending failure", e);
        }

        // TODO: unimplemented
    }
}
