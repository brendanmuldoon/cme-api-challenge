package com.cme.palindromeapi.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@NoArgsConstructor
@Getter
@Setter
@ConfigurationProperties(prefix = "properties")
public class PatternConfig {

    private List<String> patterns;


}
