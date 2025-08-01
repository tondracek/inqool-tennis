package cz.tondracek.inqooltennis.common.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hash(String password) {
        return encoder.encode(password);
    }

    public static boolean matches(String password, String passwordHash) {
        return encoder.matches(password, passwordHash);
    }

    public static org.springframework.security.crypto.password.PasswordEncoder getEncoder() {
        return encoder;
    }
}
