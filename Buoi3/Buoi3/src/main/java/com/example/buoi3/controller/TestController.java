package com.example.buoi3.controller;

import com.example.buoi3.Phone;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {
    private List<Phone> list = new ArrayList<>();
    @GetMapping("/index")
    public String index() {
        return "index";
    }
//    RestController
//    public ModelAndView index() {
//        return new ModelAndView("index");
//    }

    @PostMapping("/index")
    public ResponseEntity<Phone> addPhone(@RequestParam String name) {
        Phone a = new Phone(name);
        list.add(a);
        return ResponseEntity.ok().body(a);
    }

    @GetMapping("/about")
    public String listPhone(Model model) {
        model.addAttribute("list", list);
        return "about";
    }

    @GetMapping("/data")
    public ResponseEntity<List<Phone>> data() {
        return ResponseEntity.ok().body(list);
    }
}
