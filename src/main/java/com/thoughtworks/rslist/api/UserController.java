package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private List<User> userList = new ArrayList<>();

    @PostMapping("/user")
    public void resisterUser(@RequestBody @Valid User user) {
        userList.add(user);
    }

    @GetMapping("/user/{index}")
    public User getUser(@RequestParam int index) {
        return userList.get(index - 1);
    }
}
