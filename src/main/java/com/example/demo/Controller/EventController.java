package com.example.demo.Controller;

import com.example.demo.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping("/event")
    public String evnet(Model model) {
        model.addAttribute("events", eventService.getEventes());
        return "events";
    }


}
