package com.ecommerce.emailservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwsCredentials {

    private String awsKeyId;
    private String awsSecretAccessKey;

}
