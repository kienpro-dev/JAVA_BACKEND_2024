package com.example.buoi4ontap.controller;

import com.example.buoi4ontap.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {
    private List<User> list = new ArrayList<>(
            Arrays.asList(
                    new User("kien1234", "1234"),
                    new User("tuan1234", "1234")
            ));

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login2(@RequestParam String username,
                         @RequestParam String password,
                         Model model) {
        User user = new User(username, password);
        for (User u : list) {
            if(u.getUsername().equals(username) && u.getPassword().equals(password)) {
                //redirect prefix
                return "redirect:/content";
            }
        }

        model.addAttribute("error", "Dang nhap that bai");
        return "login";
    }

    @GetMapping("/content")
    public String content(Model model) {
        model.addAttribute("List", list);
        return "content";
    }

    @GetMapping("/regist")
    public String regist() {
        return "regist";
    }

    @PostMapping("/regist")
    public String regist2(@ModelAttribute User user,
                          @RequestParam String password2,
                          Model model) {
        for(User u : list) {
            if(u.getUsername().equals(user.getUsername())) {
                model.addAttribute("error", "Tai khoan ton tai");
                return "regist";
            }
        }

        if(!user.getPassword().equals(password2)) {
            model.addAttribute("error", "Mat khau khong khop");
            return "regist";
        }

        list.add(user);
        model.addAttribute("error", "Dang ky thanh cong");
        return "regist";
    }

    @GetMapping("/data")
//    @ResponseBody
//    public List<User> data() {
//        return list;
//    }
    public ResponseEntity<List<User>> data() {
        return ResponseEntity.ok().body(list);
    }
}
