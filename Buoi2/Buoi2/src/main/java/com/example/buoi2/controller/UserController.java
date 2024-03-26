package com.example.buoi2.controller;

import com.example.buoi2.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {
    List<User> list = Arrays.asList(new User("kien",18), new User("tuan",18), new User("ngoc",18));

    @RequestMapping("/index")
    public String index(Model model) {
        User u = new User("Kien", 18);
        model.addAttribute("user", u);
        model.addAttribute("list", list);
        return "index";
    }

    @RequestMapping("/about")
    public String about(@RequestParam String name, @RequestParam int age, Model model) {
        User u = new User(name, age);
        model.addAttribute("user", u);
        return "about";
    }

    @RequestMapping("/about")
    public String about(@ModelAttribute User user, Model model) {
        User u = new User(user.getName(), user.getAge());
        model.addAttribute("user", u);
        if(u.getAge() >= 18 && u.getName().length() >= 1) {
            return "redirect:display";
        } else {
            return "about";
        }

    }

    @RequestMapping("/display")
    public String display(Model model) {
        model.addAttribute("list", list);
        return "display";
    }
}
