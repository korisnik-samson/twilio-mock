package org.samson.twilio.config;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwilioConfig {

    private final String accountSid;
    private final String authToken;

    @Autowired
    public TwilioConfig(
            @Value("${twilio.account-sid}") String accountSid,
            @Value("${twilio.auth-token}") String authToken
    ) {
        this.accountSid = accountSid;
        this.authToken = authToken;
    }

    public void initTwilio() { Twilio.init(accountSid, authToken); }
}
