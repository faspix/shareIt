package com.shareit.config;

import com.shareit.ShareitApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@EnableEnversRepositories(basePackageClasses = ShareitApplication.class)
public class AuditConfiguration {

}
