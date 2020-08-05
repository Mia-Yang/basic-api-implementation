package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
    private List<RsEvent> rsList = initRsEvent();

    private List<RsEvent> initRsEvent() {
        List<RsEvent> rsEventList = new ArrayList<>();
        User user = new User("Siyu" ,"female",25,"123@c.com","18888888888");
        rsEventList.add(new RsEvent("第一条事件", "无标签", user));
        rsEventList.add(new RsEvent("第二条事件", "无标签", user));
        rsEventList.add(new RsEvent("第三条事件", "无标签", user));
        return rsEventList;
    }

    @GetMapping("/rs/{index}")
    public RsEvent getRsEvent(@PathVariable int index) {
        return rsList.get(index - 1);
    }

    @GetMapping("/rs/list")
    public List<RsEvent> getRsEventBetween(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
        if (start != null && end != null) {
            return rsList.subList(start - 1, end);
        }
        return rsList;
    }

    @PostMapping("/rs/event")
    public void addRsEvent(@RequestBody RsEvent rsEvent) {
        rsList.add(rsEvent);
    }

    @PatchMapping("/rs/{index}")
    public void updateRsEvent(@PathVariable int index, @RequestBody RsEvent rsEvent) {
        if (rsEvent.getEventName() != null) {
            rsList.get(index - 1).setEventName(rsEvent.getEventName());
        }
        if (rsEvent.getKeyWord() != null) {
            rsList.get(index - 1).setKeyWord(rsEvent.getKeyWord());
        }
    }

    @DeleteMapping("/rs/{index}")
    public void deleteRsEvent(@PathVariable int index) {
        rsList.remove(index - 1);
    }
}
