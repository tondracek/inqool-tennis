package cz.tondracek.inqooltennis.auth.service;

import cz.tondracek.inqooltennis.auth.dto.LoginRequestDto;
import cz.tondracek.inqooltennis.auth.dto.LoginResponseDto;
import cz.tondracek.inqooltennis.common.password.PasswordEncoder;
import cz.tondracek.inqooltennis.core.exception.BadRequestException;
import cz.tondracek.inqooltennis.core.security.JwtTokenService;
import cz.tondracek.inqooltennis.user.data.UserRepository;
import cz.tondracek.inqooltennis.user.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;

    public AuthServiceImpl(
            UserRepository userRepository,
            JwtTokenService jwtTokenService
    ) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
    }

    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.email());

        if (!PasswordEncoder.matches(request.password(), user.passwordHash()))
            throw new BadRequestException("Invalid email or password");

        String token = jwtTokenService.generateToken(user);
        return new LoginResponseDto(token);
    }
}
