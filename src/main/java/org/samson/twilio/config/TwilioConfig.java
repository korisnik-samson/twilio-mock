package org.samson.twilio.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class TwilioConfig {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @PostConstruct
    public void initTwilio() {
        // log.info("Initializing Twilio with Account SID: {}", accountSid);

        if (accountSid == null || accountSid.isBlank() || authToken == null || authToken.isBlank()) {
            log.error("Twilio credentials are missing or empty!");
            throw new IllegalStateException("Twilio credentials not configured properly");
        }

        Twilio.init(accountSid, authToken);

        log.info("Twilio SDK initialized successfully");
    }
}
