package cz.tondracek.inqooltennis.auth.service;

import cz.tondracek.inqooltennis.auth.dto.LoginRequestDto;
import cz.tondracek.inqooltennis.auth.dto.LoginResponseDto;
import cz.tondracek.inqooltennis.common.password.PasswordEncoder;
import cz.tondracek.inqooltennis.core.exception.BadRequestException;
import cz.tondracek.inqooltennis.core.security.JwtTokenService;
import cz.tondracek.inqooltennis.user.data.UserRepository;
import cz.tondracek.inqooltennis.user.model.User;
import cz.tondracek.inqooltennis.user.model.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenService jwtTokenService;

    @InjectMocks
    private AuthServiceImpl authService;

    private final String rawPassword = "password";
    private final String email = "user@mail.com";
    private final String hashedPassword = PasswordEncoder.hash(rawPassword);
    private final User user = new User(UUID.randomUUID(), email, hashedPassword, UserRole.USER, false);

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() {
        String expectedToken = "mock-token";

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(jwtTokenService.generateToken(user)).thenReturn(expectedToken);

        LoginRequestDto request = new LoginRequestDto(email, rawPassword);
        LoginResponseDto response = authService.login(request);

        assertEquals(expectedToken, response.token());
        verify(userRepository).findByEmail(email);
        verify(jwtTokenService).generateToken(user);
    }

    @Test
    void login_shouldThrowException_whenPasswordIsInvalid() {
        LoginRequestDto request = new LoginRequestDto(email, "wrongpassword");
        when(userRepository.findByEmail(email)).thenReturn(user);

        assertThrows(BadRequestException.class, () -> authService.login(request));
    }
}