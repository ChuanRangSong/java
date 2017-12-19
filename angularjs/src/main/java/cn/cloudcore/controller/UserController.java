package cn.cloudcore.controller;

import cn.cloudcore.model.User;
import cn.cloudcore.service.IUserService;
import cn.cloudcore.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private Jedis jedis;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/doLogin")
    public String doLogin(User user, HttpServletResponse response) {

        User dbUser = userService.findByUsername(user.getUsername());

        if (null != dbUser) {
            if (dbUser.getPassword().equals(user.getPassword())) {
                String csessionId = UUID.randomUUID().toString() + dbUser.getId();
                Cookie cookie = new Cookie(Constants.CSESSIONID_NAME, csessionId);
                cookie.setMaxAge(-1);
                response.addCookie(cookie);
                jedis.setex(csessionId, 60, dbUser.getId() + "");
            }
        }

        return "redirect:/";
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
