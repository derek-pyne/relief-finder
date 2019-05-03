package info.reliefinder.messenger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MessengerWebhookCatcherTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void verificationRequest_shouldReturnChallenge() throws Exception {
        mockMvc.perform(get("/messenger/webhook?" +
                        "hub.mode={mode}&hub.challenge={challenge}&hub.verify_token={token}",
                "subscribe", "challenge", "TOKEN"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void verificationRequest_withBadToken_shouldReturnChallenge() throws Exception {
        mockMvc.perform(get("/messenger/webhook?" +
                        "hub.mode={mode}&hub.challenge={challenge}&hub.verify_token={token}",
                "subscribe", "challenge", "BADTOKEN"))
                .andDo(print()).andExpect(status().isForbidden());
    }

}
