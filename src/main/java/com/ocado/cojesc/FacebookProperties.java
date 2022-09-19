package com.ocado.cojesc;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "facebook")
public class FacebookProperties {
    private String baseUrl;
    private String CookieButtonSelector;
    private String SeeMoreSelector;
    private double timeout;
}
