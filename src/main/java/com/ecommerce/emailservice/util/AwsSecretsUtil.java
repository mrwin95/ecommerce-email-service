package com.ecommerce.emailservice.util;

import com.ecommerce.emailservice.dto.AwsCredentials;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class AwsSecretsUtil {

    public static AwsBasicCredentials getAwsBasicCredentials(String secretName, String region) throws JsonProcessingException {

        SecretsManagerClient secretsManagerClient = SecretsManagerClient.builder().region(Region.of(region)).build();
        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder().secretId(secretName).build();
        GetSecretValueResponse getSecretValueResponse;
        try {
            getSecretValueResponse = secretsManagerClient.getSecretValue(getSecretValueRequest);
        }catch (Exception e){
            throw  e;
        }
        String secret = getSecretValueResponse.secretString();
        ObjectMapper objectMapper = new ObjectMapper();
        AwsCredentials awsCredentials;
        try {
            awsCredentials = objectMapper.readValue(secret, AwsCredentials.class);
        }catch (JsonProcessingException e){
            throw  e;
        }

        String accessKeyId = awsCredentials.getAwsKeyId();
        String secretAccessKey = awsCredentials.getAwsSecretAccessKey();
        return AwsBasicCredentials.create(accessKeyId, secretAccessKey);

    }
}
