package cz.tondracek.inqooltennis.auth.service;

import cz.tondracek.inqooltennis.auth.dto.LoginRequestDto;
import cz.tondracek.inqooltennis.auth.dto.LoginResponseDto;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto request);
}

