package com.ecommerce.emailservice.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class LoadHtmlFile {

    public static String loadTemplate(String filePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(filePath);
        return new String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8);
    }
}
