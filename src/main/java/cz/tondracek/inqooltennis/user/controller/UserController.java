package cz.tondracek.inqooltennis.user.controller;

import cz.tondracek.inqooltennis.user.dto.CreateUserDto;
import cz.tondracek.inqooltennis.user.dto.UpdateUserDto;
import cz.tondracek.inqooltennis.user.dto.UserDetailDto;
import cz.tondracek.inqooltennis.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDetailDto> createUser(
            @Valid @RequestBody CreateUserDto dto
    ) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailDto> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserDto dto
    ) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDto> findUserById(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDetailDto> findUserByEmail(
            @PathVariable String email
    ) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<UserDetailDto>> findAllActiveUsers() {
        return ResponseEntity.ok(userService.findAllActiveUsers());
    }
}
