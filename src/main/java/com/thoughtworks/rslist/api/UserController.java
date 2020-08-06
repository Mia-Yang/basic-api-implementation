package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    public static List<User> userList = new ArrayList<>();

    @PostMapping("/user")
    public ResponseEntity resisterUser(@RequestBody @Valid User user) {
        userList.add(user);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>>getUser() {
        return ResponseEntity.ok(userList);
    }

}
