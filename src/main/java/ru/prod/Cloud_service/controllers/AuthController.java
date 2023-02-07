package ru.prod.Cloud_service.controllers;

import jakarta.servlet.ServletException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.prod.Cloud_service.dto.TokenDTO;
import ru.prod.Cloud_service.dto.UserDTO;
import ru.prod.Cloud_service.services.AuthorizationService;

import java.io.IOException;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody UserDTO userDTO){
        String token = authorizationService.login(userDTO);
        return token != null ? new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("/logout")
    public void logout(@RequestHeader("auth-token") @NotBlank String authToken) {
        authorizationService.logout(authToken);
    }



}
