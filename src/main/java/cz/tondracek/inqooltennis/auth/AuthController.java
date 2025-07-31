package cz.tondracek.inqooltennis.auth;

import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    @PostMapping("/login")
    ResponseEntity<Object /*TODO*/> login(
            @NotBlank String email,
            @NotBlank String password
    ) {
        return ResponseEntity.noContent().build();
    }
}
