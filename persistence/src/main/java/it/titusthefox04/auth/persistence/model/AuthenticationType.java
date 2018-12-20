package it.titusthefox04.auth.persistence.model;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;

/**
 * Different types of authentication.
 *
 * @author titusthefox04
 * @author titusthefox04
 */
public enum AuthenticationType {
    LDAP, DB;

    private static final ImmutableMap<String, AuthenticationType> STR2AUTH =
            ImmutableBiMap.<String, AuthenticationType>builder()
                    .put(LDAP.toString(), LDAP)
                    .put(DB.toString(), DB)
                    .build();

    public static AuthenticationType fromString(String str) {
        return STR2AUTH.get(str);
    }
}
