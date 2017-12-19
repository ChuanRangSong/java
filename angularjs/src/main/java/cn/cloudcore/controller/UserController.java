package cn.cloudcore.controller;

import cn.cloudcore.model.User;
import cn.cloudcore.service.IUserService;
import cn.cloudcore.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private Jedis jedis;

    @RequestMapping("/login")
    public String login(User user, Model model) {
        System.out.println(user.getUsername() + ":" + user.getPassword());
        model.addAttribute("user", userService.findByUsername(user.getUsername()));
        return "main";
    }

    @RequestMapping("/checkLogin")
    public @ResponseBody User checkLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String csessionId = null;
        for (Cookie cookie : cookies) {
            if (Constants.CSESSIONID_NAME.equals(cookie.getName())) {
                csessionId = cookie.getValue();
                break;
            }
        }
        if (null != csessionId) {
            String userId = jedis.get(csessionId);
            if (null != userId) {
                return userService.findById(Integer.parseInt(userId));
            }
        }

        return null;
    }

}
