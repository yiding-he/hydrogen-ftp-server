package com.hyd.ftpserver.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yiding.he
 */
@Controller
@RequestMapping("/admin")
public class IndexController {

    @Autowired
    private AdminConfig adminConfig;

    @RequestMapping("")
    public String index0() {
        return "redirect:/admin/";
    }

    @RequestMapping("/")
    public String index() {
        return "/admin/index";
    }

    @PostMapping("/login")
    public ModelAndView login(String username, String password) {

        if (adminConfig.getUsername().equals(username) &&
                adminConfig.getPassword().equals(password)) {
            return new ModelAndView("/admin/main");
        } else {
            return new ModelAndView("/admin/index").addObject("message", "Invalid username or password");
        }
    }
}
