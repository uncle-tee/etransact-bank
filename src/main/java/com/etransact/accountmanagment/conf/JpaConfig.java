package com.etransact.accountmanagment.conf;

import com.etransact.accountmanagment.serviceImpl.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class JpaConfig {
    @Bean
    public AuditorAware auditorAware() {
        System.out.println("We are running");
        return new AuditorAwareImpl();
    }
}
