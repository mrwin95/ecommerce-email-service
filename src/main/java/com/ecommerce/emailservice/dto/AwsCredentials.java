package com.ecommerce.emailservice.dto;

import lombok.*;


public class AwsCredentials {

    private String awsKeyId;
    private String awsSecretAccessKey;

    public AwsCredentials(){}
    public AwsCredentials(String awsKeyId, String awsSecretAccessKey) {
        this.awsKeyId = awsKeyId;
        this.awsSecretAccessKey = awsSecretAccessKey;
    }

    public String getAwsKeyId() {
        return awsKeyId;
    }

    public void setAwsKeyId(String awsKeyId) {
        this.awsKeyId = awsKeyId;
    }

    public String getAwsSecretAccessKey() {
        return awsSecretAccessKey;
    }

    public void setAwsSecretAccessKey(String awsSecretAccessKey) {
        this.awsSecretAccessKey = awsSecretAccessKey;
    }
}
