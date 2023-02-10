package ru.prod.Cloud_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prod.Cloud_service.Entity_models.Token;
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
        var user = userRepository.findByUsername(login).orElseThrow(() ->
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
        if (authToken != null && !authToken.isBlank() && authToken.startsWith("Bearer ")) {
            String jwt = authToken.substring(7);

            tokenRepository.deleteById(jwt);
        } else {
            tokenRepository.deleteById(authToken);
        }
    }


    public void checkToken(String authToken) {
        if (!tokenRepository.existsById(authToken)) throw new AuthorizationException();
    }
}
