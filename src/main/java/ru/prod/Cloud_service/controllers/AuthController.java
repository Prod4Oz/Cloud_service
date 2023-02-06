package ru.prod.Cloud_service.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.prod.Cloud_service.dto.TokenDTO;
import ru.prod.Cloud_service.dto.UserDTO;
import ru.prod.Cloud_service.services.AuthorizationService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public TokenDTO login(@Valid @RequestBody UserDTO userDTO){
        return authorizationService.login(userDTO);
    }
    @PostMapping("/logout")
    public void logout(@RequestHeader("auth-token") @NotBlank String authToken) {
        authorizationService.logout(authToken);
    }
}
