package com.demo.customerservice.platform.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * Define Auditor aware.
 */
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable("SystemUser");
    }
}