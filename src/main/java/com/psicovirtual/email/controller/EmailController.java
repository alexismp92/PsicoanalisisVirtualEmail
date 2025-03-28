package com.psicovirtual.email.controller;

import com.psicovirtual.email.dto.EmailDTO;
import com.psicovirtual.email.exception.EmailException;
import com.psicovirtual.email.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/email")
@Validated
@Tag(name = "Email Service")
public class EmailController {

    private final EmailService emailService;

    @PostMapping(value = "/send")
    @Operation(summary = "Send an email notification",
            description = "Method which send an email notification to the desired emails",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Email sent successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<Void> send(@RequestBody EmailDTO emailDTO) throws EmailException {
        log.info("Email request received: " + emailDTO.toString());

        emailService.sendEmail(emailDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
