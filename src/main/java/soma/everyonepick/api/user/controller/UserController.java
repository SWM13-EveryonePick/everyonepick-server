package soma.everyonepick.api.user.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soma.everyonepick.api.user.entity.User;
import soma.everyonepick.api.user.repository.UserRepository;
import soma.everyonepick.api.user.service.UserService;

import java.util.List;

/**
 * 사용자 정보 관련 Endpoint.
 */

@Api(value = "사용자 정보 관련 Endpoint", tags = "사용자 정보")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<User>> getUser() {
        return ResponseEntity.ok(userService.findAll());
    }
}
