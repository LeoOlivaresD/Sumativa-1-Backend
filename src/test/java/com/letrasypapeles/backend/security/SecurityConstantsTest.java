package com.letrasypapeles.backend.security;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class SecurityConstantsTest {

    @Test
    void testConstantsAreAccessible() {
        assertEquals(300000, SecurityConstants.JWT_EXPIRATION_TIME);
        assertNotNull(SecurityConstants.JWT_SECRET);
        assertTrue(SecurityConstants.JWT_SECRET.length() >= 64);
    }

    @Test
    void testPrivateConstructor() throws Exception {
        Constructor<SecurityConstants> constructor = SecurityConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        SecurityConstants instance = constructor.newInstance();
        assertNotNull(instance);
    }
}
