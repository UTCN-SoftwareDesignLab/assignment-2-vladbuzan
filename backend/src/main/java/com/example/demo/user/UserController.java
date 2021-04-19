package com.example.demo.user;

import com.example.demo.security.dto.MessageResponse;
import com.example.demo.user.dto.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.UrlMapping.USER;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> allRegularUsers() {
        try {
            var users = userService.allRegularUsersForList();
            return ResponseEntity.ok(users);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        try {
            var user = userService.updateUser(id, request);
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Couldn't update user"));
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> removeUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new MessageResponse("User removed successfully"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Couldn't delete user"));
        }
    }

}
