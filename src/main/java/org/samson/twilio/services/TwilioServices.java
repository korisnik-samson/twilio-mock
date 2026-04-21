package org.samson.twilio.services;

import com.twilio.rest.api.v2010.account.Call;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;
import com.twilio.type.PhoneNumber;
import com.twilio.type.Twiml;
import org.springframework.stereotype.Service;

@Service
public class TwilioServices {

    public void sendSMS() { }

    public void sendVoice() {
        String helloTwiml = new VoiceResponse.Builder()
                .say(new Say.Builder("Hello from Twilio").voice(Say.Voice.POLLY_MATTHEW).build())
                .build()
                .toXml();

        Call call = Call.creator(
                new PhoneNumber("+381637216838"),
                new PhoneNumber("+17622268887"),
                new Twiml(helloTwiml)
        ).create();

        System.out.println(call.getSid());
    }

}
