package com.psicovirtual.email.controller;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private GreenMail greenMail;

    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(new ServerSetup(1025, null, "smtp"));
        greenMail.start();
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }

    @Test
    void testSendEmail() throws Exception {
        String emailJson = "{\"emailType\":\"REG_USER\",\"emails\":[\"test@example.com\"]}";

        mockMvc.perform(MockMvcRequestBuilders.post("/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailJson))
                .andExpect(status().isOk());
    }

    @Test
    void testSendEmailInvalidRequest() throws Exception {
        String emailJson = "{\"emailType\":\"\",\"emails\":[\"test@example.com\"]";

        mockMvc.perform(MockMvcRequestBuilders.post("/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSendEmailInvalidEmail() throws Exception {
        String emailJson = "{\"emailType\":\"APPROVED_USER\",\"emails\":[\"test@\"]}";

        mockMvc.perform(MockMvcRequestBuilders.post("/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSendEmailInvalidEmailType() throws Exception {
        String emailJson = "{\"emailType\":\"UNKNOWN\",\"emails\":[\"test@tes.com\"]}";

        mockMvc.perform(MockMvcRequestBuilders.post("/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailJson))
                .andExpect(status().isBadRequest());
    }

}