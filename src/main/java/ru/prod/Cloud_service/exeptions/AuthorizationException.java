package ru.prod.Cloud_service.exeptions;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
        super("User is not authorized");
    }
}
