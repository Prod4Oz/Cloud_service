package ru.prod.Cloud_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prod.Cloud_service.Entity_models.Token;
import ru.prod.Cloud_service.Entity_models.User;
import ru.prod.Cloud_service.dto.UserDTO;
import ru.prod.Cloud_service.exeptions.AuthorizationException;
import ru.prod.Cloud_service.exeptions.BadCredentialsException;
import ru.prod.Cloud_service.repositories.TokenRepository;
import ru.prod.Cloud_service.repositories.UserRepository;
import ru.prod.Cloud_service.utils.JWTUtil;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthorizationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JWTUtil jwtUtil;


    public String login(UserDTO userDTO) {
        final String login = userDTO.getLogin();
        final User user = userRepository.findById(login).orElseThrow(() ->
                new BadCredentialsException("User with login " + login + " not found"));

        if (!user.getPassword().equals(userDTO.getPassword())) {
            throw new BadCredentialsException("Incorrect password for user " + login);
        }
        final String authToken = jwtUtil.generateToken(login);
        tokenRepository.save(new Token(authToken));
        log.info("User " + login + " entered with token " + authToken);
        return authToken;

    }

    public void logout(String authToken) {
        tokenRepository.deleteById(deleteBearer(authToken));
        log.info("Token delete");
    }

    public User getUserByAuthToken(String authToken) {
        String token = deleteBearer(authToken);
        return userRepository.findByUsername(jwtUtil.validateTokenAndRetrieveClaimUsername(token));
    }


    public void checkToken(String authToken) {
        if (!tokenRepository.existsById(deleteBearer(authToken))) {
            log.error("Unauthorized");
            throw new AuthorizationException();
        }

    }

    public String deleteBearer(String authToken) {
        if (authToken != null && !authToken.isBlank() && authToken.startsWith("Bearer ")) {
            return authToken.substring(7);
        } else {
            return authToken;
        }
    }


}
