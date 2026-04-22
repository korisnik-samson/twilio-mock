package org.samson.twilio.controllers;

import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.Twiml;
import lombok.RequiredArgsConstructor;
import org.samson.twilio.services.TwilioServices;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TwilioController {
    private final TwilioServices twilioServices;

    @GetMapping("/healthz")
    public String test() {
        return "Twilio API is working!";
    }

    @GetMapping("/sms")
    public void sendSMS() { this.twilioServices.sendSMS(); }

    @PostMapping( "/calls")
    public ResponseEntity<Map<String, String>> makeCall(@RequestParam String to, @RequestParam Twiml twimlUrl) {
        Call call = twilioServices.makeCall(to, twimlUrl);

        return ResponseEntity.ok(Map.of(
                "status", "initiated",
                "callSid", call.getSid()
        ));
    }

    @PostMapping("/calls/custom")
    public ResponseEntity<Map<String, String>> makeCustomCall(
            @RequestParam String to,
            @RequestParam(required = false) String message,
            @RequestParam(required = false) String audioUrl) {

        Call call = twilioServices.makeCallWithCustomTwiml(to, message, audioUrl);

        return ResponseEntity.ok(Map.of(
                "status", "initiated",
                "callSid", call.getSid()
        ));
    }

    @PostMapping("/calls/advanced")
    public ResponseEntity<Map<String, String>> makeAdvancedCall(@RequestParam String to, @RequestParam String greeting,
            @RequestParam String audioUrl, @RequestParam String farewell) {

        String twiml = twilioServices.buildAdvancedTwiml(greeting, audioUrl, farewell);

        Call call = Call.creator(
                new com.twilio.type.PhoneNumber(to),
                new com.twilio.type.PhoneNumber(twilioServices.getTwilioPhoneNumber()),
                new Twiml(twiml)
        ).create();

        return ResponseEntity.ok(Map.of(
                "status", "initiated",
                "callSid", call.getSid(),
                "twiml", twiml
        ));
    }

    /*@GetMapping(value = "/twiml/preview", produces = MediaType.APPLICATION_XML_VALUE)
    public String previewTwiml(@RequestParam(required = false) String message, @RequestParam(required = false) String audioUrl) {
        return twilioServices.buildCustomTwiml(message, audioUrl);
    }*/

    @GetMapping(value = "/twiml/preview", produces = MediaType.APPLICATION_XML_VALUE)
    public String previewTwiml(@RequestParam(required = false) String message, @RequestParam(required = false) String audioUrl) {
        return twilioServices.buildCustomTwiml(message, audioUrl);
    }

    @PostMapping("/calls/local-audio")
    public ResponseEntity<Map<String, String>> makeCallWithLocalAudio(
            @RequestParam String to,
            @RequestParam(required = false) String message,
            @RequestParam String audioFileName) {

        Call call = twilioServices.makeCallWithLocalAudio(to, message, audioFileName);

        return ResponseEntity.ok(Map.of(
                "status", "initiated",
                "callSid", call.getSid(),
                "audioFile", audioFileName
        ));
    }
}
