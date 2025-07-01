
package com.letrasypapeles.backend.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConstantsTest {

    @Test
    void testConstantsAreAccessible() {
        assertEquals(300000, SecurityConstants.JWT_EXPIRATION_TIME);
        assertNotNull(SecurityConstants.JWT_SECRET);
        assertTrue(SecurityConstants.JWT_SECRET.length() >= 64);
    }
}
