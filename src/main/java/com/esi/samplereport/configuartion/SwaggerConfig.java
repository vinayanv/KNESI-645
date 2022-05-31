package com.esi.samplereport.configuartion;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi fclApi() {
        return GroupedOpenApi.builder()
                .group("FCL")
                .pathsToMatch("/fcl/**")
                .build();
    }

    @Bean
    public GroupedOpenApi lclApi() {
        return GroupedOpenApi.builder()
                .group("LCL")
                .pathsToMatch("/lcl/**")
                .build();
    }

    @Bean
    public GroupedOpenApi ballApi() {
        return GroupedOpenApi.builder()
                .group("BAAL")
                .pathsToMatch("/baal/**")
                .build();
    }
}
