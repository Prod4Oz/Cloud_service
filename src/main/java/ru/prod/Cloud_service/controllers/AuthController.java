package ru.prod.Cloud_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.prod.Cloud_service.services.AuthorizationService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public void login(){

    }
    @PostMapping("/logout")
    public void logout(){

    }
}
