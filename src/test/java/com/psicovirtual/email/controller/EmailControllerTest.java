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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private GreenMail greenMail;

    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(new ServerSetup(8025, null, "smtp"));
        greenMail.start();
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }

    @Test
    void testSendEmail() throws Exception {
        String emailJson = """
                {
                  "emailType": "WELCOME_USER",
                  "language": "ENGLISH",
                  "emails": [
                    "test@hotmail.com"
                  ],
                  "values": {
                    "NAME": "JHON"                  }
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/emails/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailJson))
                .andExpect(status().isOk());
    }

    @Test
    void testSendEmailInvalidRequest() throws Exception {

        String emailJson = """
                {
                  "emailType": "WELCOME_USER",
                  "language": "FRANCOIS",
                  "emails": [
                    "test@hotmail.com"
                  ],
                  "values": {
                    "NAME": "TEST",
                    "YEAR": "2025",
                    "COMPANY": "Psicoanalisis Virtual",
                    "LOGO" : "https://test.com/logo.png"
                  }
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/emails/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSendEmailInvalidEmail() throws Exception {

        String emailJson = """
                {
                  "emailType": "WELCOME_USER",
                  "language": "ENGLISH",
                  "emails": [
                    "test@"
                  ],
                  "values": {
                    "NAME": "TEST",
                    "YEAR": "2025",
                    "COMPANY": "Psicoanalisis Virtual",
                    "LOGO" : "https://test.com/logo.png"
                  }
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/emails/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSendEmailInvalidEmailType() throws Exception {
        String emailJson = """
                {
                  "emailType": "",
                  "language": "ENGLISH",
                  "emails": [
                    "test@"
                  ],
                  "values": {
                    "NAME": "TEST",
                    "YEAR": "2025",
                    "COMPANY": "Psicoanalisis Virtual",
                    "LOGO" : "https://test.com/logo.png"
                  }
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/emails/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailJson))
                .andExpect(status().isBadRequest());
    }

}