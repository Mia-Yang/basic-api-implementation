package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.exception.Error;
import com.thoughtworks.rslist.exception.RsEventNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
    public static List<RsEvent> rsList = initRsEvent();

    public static List<RsEvent> initRsEvent() {
        List<RsEvent> rsEventList = new ArrayList<>();
        User user = new User("Siyu" ,"female",25,"123@c.com","18888888888");
        rsEventList.add(new RsEvent("第一条事件", "无标签", user));
        rsEventList.add(new RsEvent("第二条事件", "无标签", user));
        rsEventList.add(new RsEvent("第三条事件", "无标签", user));
        return rsEventList;
    }

    @GetMapping("/rs/{index}")
    public ResponseEntity<RsEvent> getRsEvent(@PathVariable int index) {
        if(index < 1 || index > rsList.size()) {
            throw new RsEventNotValidException("invalid index");
        }
        return ResponseEntity.ok(rsList.get(index - 1));
    }

    @GetMapping("/rs/list")
    public ResponseEntity<List<RsEvent>> getRsEventBetween(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
        if (start != null && end != null) {
            return ResponseEntity.ok(rsList.subList(start - 1, end));
        }
        return ResponseEntity.ok(rsList);
    }

    @PostMapping("/rs/event")
    public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
        if(!userExist(rsEvent.getUser())){
            UserController.userList.add(rsEvent.getUser());
        }
        rsList.add(rsEvent);
        int index = rsList.size();
        return ResponseEntity.created(null).header("index", String.valueOf(index)).build();
    }

    public boolean userExist(User user) {
        return UserController.userList.stream().anyMatch(u -> u.getUserName().equals(user.getUserName()));
    }

    @PatchMapping("/rs/{index}")
    public ResponseEntity updateRsEvent(@PathVariable int index, @RequestBody RsEvent rsEvent) {
        if (rsEvent.getEventName() != null) {
            rsList.get(index - 1).setEventName(rsEvent.getEventName());
        }
        if (rsEvent.getKeyWord() != null) {
            rsList.get(index - 1).setKeyWord(rsEvent.getKeyWord());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/rs/{index}")
    public void deleteRsEvent(@PathVariable int index) {
        rsList.remove(index - 1);
    }

}
