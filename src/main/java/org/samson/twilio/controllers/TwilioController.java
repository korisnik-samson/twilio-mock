package org.samson.twilio.controllers;

import com.twilio.Twilio;
import org.samson.twilio.services.TwilioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwilioController {
    private final TwilioServices twilioServices;

    @Autowired
    public TwilioController(TwilioServices twilioServices) {
        this.twilioServices = twilioServices;
    }

    @GetMapping("/api/v1/twilio/test")
    public String test() {
        return "Twilio API is working!";
    }

    @GetMapping("/api/v1/sms")
    public void sendSMS() {
        this.twilioServices.sendSMS();
    }

    @GetMapping("/api/v1/voice")
    public void sendVoice() {
        this.twilioServices.sendVoice();
    }


}
