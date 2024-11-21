package com.ecommerce.emailservice.config;

import com.ecommerce.emailservice.util.AwsSecretsUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
public class AwsSesConfig {
    @Bean
    public SesClient sesClient(){
        AwsBasicCredentials awsBasicCredentials = null;
        try {
            awsBasicCredentials = AwsSecretsUtil.getAwsBasicCredentials("aws_key", "ap-northeast-1");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        awsBasicCredentials.accessKeyId();
        awsBasicCredentials.secretAccessKey();
        return SesClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .build();
    }
}
