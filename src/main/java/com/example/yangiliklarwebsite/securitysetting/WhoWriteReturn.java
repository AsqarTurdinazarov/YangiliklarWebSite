package com.example.yangiliklarwebsite.securitysetting;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class WhoWriteReturn {

    @Bean
    AuditorAware<Integer> auditorAware(){
        return new WhoWrite();
    }
}
