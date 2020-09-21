package com.poc.spring.openldap.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.query.SearchScope;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    private LdapTemplate ldapTemplate;

    public Optional<String[]> authenticate(String name, String password) {
        LdapQuery query = LdapQueryBuilder.query()
            .base("ou=users,dc=openldap,dc=spring,dc=poc,dc=com")
            .searchScope(SearchScope.ONELEVEL)
            .countLimit(1)
            .where("objectclass").is("inetOrgPerson").and("cn").is(name);

        return ldapTemplate.authenticate(
            query, password, (dirContext, ldapEntryIdentification) -> {
                try {
                    Attributes attributes = dirContext.getAttributes(ldapEntryIdentification.getAbsoluteDn());
                    final String givenName = (String) attributes.get("givenName").get();
                    final String displayName = (String) attributes.get("displayName").get();
                    return Optional.of(new String[] { givenName, displayName });

                } catch (NamingException e) {
                    LOG.error(e.getExplanation());
                }

                return Optional.empty();
            });
    }

    public List<String> getUserGroups(String name) {
        LdapQuery query = LdapQueryBuilder.query()
                .base("ou=Groups,dc=openldap,dc=spring,dc=poc,dc=com")
                .searchScope(SearchScope.ONELEVEL)
                .where("objectclass").is("groupOfUniqueNames")
                .and("uniqueMember").is("cn=" + name + ",ou=users,dc=openldap,dc=spring,dc=poc,dc=com");

        return ldapTemplate.search(query, (AttributesMapper<String>) attributes ->
                (String) attributes.get("cn").get());
    }
}
