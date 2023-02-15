package ru.prod.Cloud_service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.prod.Cloud_service.Entity_models.User;
import ru.prod.Cloud_service.dto.UserDTO;
import ru.prod.Cloud_service.repositories.TokenRepository;
import ru.prod.Cloud_service.repositories.UserRepository;
import ru.prod.Cloud_service.utils.JWTUtil;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthorizationServiceTest {

    public static final String BAD_AUTH_TOKEN = "Bearer gaarahzfbzfbzaf";
    public static final String USER = "user";
    public static final String BAD_USER = "no_user";
    public static final String PASSWORD = "user";
    public static final String BAD_PASSWORD = "password";


    private final JWTUtil jwtUtil = createJWTUtilMock();

    private final UserRepository userRepository = createUserRepositoryMock();
    private final TokenRepository tokenRepository = createTokenRepositoryMock();


    private UserRepository createUserRepositoryMock() {
        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        when(userRepository.findById(USER)).thenReturn(Optional.of(new User(USER, PASSWORD, null)));
        when(userRepository.findById(BAD_USER)).thenReturn(Optional.empty());
        return userRepository;
    }

    private TokenRepository createTokenRepositoryMock() {
        final TokenRepository tokenRepository = Mockito.mock(TokenRepository.class);
        when(tokenRepository.existsById(jwtUtil.generateToken(USER))).thenReturn(true);
        when(tokenRepository.existsById(BAD_AUTH_TOKEN)).thenReturn(false);
        return tokenRepository;
    }

    private JWTUtil createJWTUtilMock() {
        final JWTUtil jwtUtil = Mockito.mock(JWTUtil.class);
        return jwtUtil;
    }


    @Test
    public void login() {
        final AuthorizationService authorizationService = new AuthorizationService(userRepository, tokenRepository, jwtUtil);
        Assertions.assertDoesNotThrow(() -> authorizationService.login(new UserDTO(USER, PASSWORD)));
    }

    @Test
    public void logout() {
        final AuthorizationService authorizationService = new AuthorizationService(userRepository, tokenRepository, jwtUtil);
        Assertions.assertDoesNotThrow(() -> authorizationService.logout(jwtUtil.generateToken(USER)));

    }

    @Test
    public void login_userNotFound() {
        final AuthorizationService authorizationService = new AuthorizationService(userRepository, tokenRepository, jwtUtil);
        Assertions.assertThrows(RuntimeException.class, () -> authorizationService.login(new UserDTO(BAD_USER, PASSWORD)));
    }


    @Test
    public void login_incorrectPassword() {
        final AuthorizationService authorizationService = new AuthorizationService(userRepository, tokenRepository, jwtUtil);
        Assertions.assertThrows(RuntimeException.class, () -> authorizationService.login(new UserDTO(USER, BAD_PASSWORD)));
    }

    @Test
    public void checkToken() {
        final AuthorizationService authorizationService = new AuthorizationService(userRepository, tokenRepository, jwtUtil);
        Assertions.assertDoesNotThrow(() -> authorizationService.checkToken(jwtUtil.generateToken(USER)));
    }

    @Test
    public void checkToken_failed() {
        final AuthorizationService authorizationService = new AuthorizationService(userRepository, tokenRepository, jwtUtil);
        Assertions.assertThrows(RuntimeException.class, () -> authorizationService.checkToken(BAD_AUTH_TOKEN));
    }
}