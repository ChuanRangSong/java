package cn.cloudcore.controller;

import cn.cloudcore.model.User;
import cn.cloudcore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/login")
    public String login(User user, Model model) {
        System.out.println(user.getUsername() + ":" + user.getPassword());
        model.addAttribute("user", userService.findByUsername(user.getUsername()));
        return "main";
    }

    @RequestMapping("/checkLogin")
    public @ResponseBody User checkLogin(User user) {
        user.setUsername("World!");
        userService.findByUsername(user.getUsername());
        return user;
    }

}
