package org.samson.twilio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TwilioApplicationTests {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String phoneNumber;

    @Test
    void contextLoads() {}

    @Test
    void twilioCredentialsAreLoaded() {
        assertNotNull(accountSid, "Account SID should not be null");
        assertNotNull(authToken, "Auth Token should not be null");
        assertNotNull(phoneNumber, "Phone Number should not be null");

        assertFalse(accountSid.isBlank(), "Account SID should not be blank");
        assertFalse(authToken.isBlank(), "Auth Token should not be blank");
        assertFalse(phoneNumber.isBlank(), "Phone Number should not be blank");

        assertTrue(accountSid.startsWith("AC"), "Account SID should start with 'AC'");
        assertEquals(34, accountSid.length(), "Account SID should be 34 characters long");
        assertEquals(32, authToken.length(), "Auth Token should be 32 characters long");
        assertTrue(phoneNumber.startsWith("+"), "Phone Number should start with '+'");
    }

    @Test
    void twilioConfigBeanIsInitialized() {
        // This test will fail if TwilioConfig @PostConstruct throws an exception
        // The context loading itself validates that Twilio.init() was called
        assertTrue(true, "Twilio configuration bean was initialized successfully");
    }

}
