package org.samson.twilio.services;

import com.twilio.rest.api.v2010.account.Call;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Pause;
import com.twilio.twiml.voice.Play;
import com.twilio.twiml.voice.Say;
import com.twilio.type.PhoneNumber;
import com.twilio.type.Twiml;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;

@Slf4j
@Service
@Getter
public class TwilioServices {

    /*String helloTwiml = new VoiceResponse.Builder()
            .say(new Say.Builder("Hello from Twilio").voice(Say.Voice.POLLY_MATTHEW).build())
            .build()
            .toXml();*/

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @Value("${twilio.base.url}")
    private String baseUrl;

    public void sendSMS() { }

    public Call makeCall(String toPhoneNumber, Twiml twimlUrl) {
        Call call = Call.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(twilioPhoneNumber),
                URI.create(String.valueOf(twimlUrl))
        ).create();

        log.info("Call initiated with SID: {}", call.getSid());

        return call;
    }

    public Call makeCallWithLocalAudio(String toPhoneNumber, String message, String audioFileName) {
        String audioUrl = baseUrl + "/audio/" + audioFileName;
        String twiml = buildCustomTwiml(message, audioUrl);

        Call call = Call.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(twilioPhoneNumber),
                new Twiml(twiml)
        ).create();

        log.info("Call initiated with SID: {} using local audio: {}", call.getSid(), audioUrl);

        return call;
    }

    public Call makeCallWithCustomTwiml(String toPhoneNumber, String message, String audioUrl) {
        String twiml = buildCustomTwiml(message, audioUrl);

        Call call = Call.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(twilioPhoneNumber),
                new Twiml(twiml)
        ).create();

        log.info("Call initiated with SID: {} using custom TwiML", call.getSid());

        return call;
    }

    public String buildCustomTwiml(String message, String audioUrl) {
        VoiceResponse.Builder responseBuilder = new VoiceResponse.Builder();

        if (message != null && !message.isBlank()) {
            responseBuilder.say(new Say.Builder(message)
                    .voice(Say.Voice.POLLY_JOANNA)
                    .build());
        }

        if (audioUrl != null && !audioUrl.isBlank()) responseBuilder.play(new Play.Builder(audioUrl).build());

        VoiceResponse response = responseBuilder.build();
        String twiml = response.toXml();

        log.debug("Generated TwiML: {}", twiml);

        return twiml;
    }

    public String buildAdvancedTwiml(String greeting, String audioUrl, String farewell) {
        VoiceResponse response = new VoiceResponse.Builder()
                .say(new Say.Builder(greeting)
                        .voice(Say.Voice.POLLY_JOANNA)
                        .build())
                .pause(new Pause.Builder().length(1).build())
                .play(new Play.Builder(audioUrl)
                        .build())
                .pause(new Pause.Builder().length(1).build())
                .say(new Say.Builder(farewell)
                        .voice(Say.Voice.POLLY_MATTHEW)
                        .build())
                .build();

        return response.toXml();
    }

}
