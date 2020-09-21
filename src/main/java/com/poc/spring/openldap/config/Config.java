package com.poc.spring.openldap.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class Config {

    @Value("${ldap.url}")
    private String ldapUrl;

    @Value("${ldap.principal}")
    private String ldapPrincipal;

    @Value("${ldap.password}")
    private String ldapPassword;

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();

        contextSource.setUrl(ldapUrl);
        contextSource.setUserDn(ldapPrincipal);
        contextSource.setPassword(ldapPassword);

        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate(LdapContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }
}
