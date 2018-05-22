package com.hyd.ftpserver.admin;

import com.hyd.ftpserver.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import java.util.function.Supplier;

/**
 * @author yiding.he
 */
@Controller
@SessionScope
@RequestMapping("/admin")
public class AdminController {

    private static final ModelAndView LOGIN = new ModelAndView("redirect:/admin/index");

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private UserService userService;

    private boolean loggedIn;

    private ModelAndView ifLoggedInThenReturn(Supplier<ModelAndView> supplier) {
        return loggedIn ? supplier.get() : LOGIN;
    }

    @RequestMapping("")
    public String index0() {
        if (loggedIn) {
            return "redirect:/admin/main";
        } else {
            return "redirect:/admin/index";
        }
    }

    @RequestMapping({"/", "/index"})
    public String index() {
        if (loggedIn) {
            return "redirect:/admin/main";
        } else {
            return "/admin/index";
        }
    }

    @PostMapping("/login")
    public ModelAndView login(String username, String password) {

        if (adminConfig.getUsername().equals(username) &&
                adminConfig.getPassword().equals(password)) {

            this.loggedIn = true;
            return new ModelAndView("redirect:/admin/main");
        } else {
            return new ModelAndView("/admin/index").addObject("message", "Invalid username or password");
        }
    }

    @GetMapping("/main")
    public ModelAndView main() {
        return ifLoggedInThenReturn(() ->
                new ModelAndView("/admin/main")
                        .addObject("users", userService.queryAllUsers()));
    }

    @GetMapping("/add_user")
    public ModelAndView addUserPage() {
        return ifLoggedInThenReturn(() -> new ModelAndView("/admin/add_user"));
    }

    @PostMapping("/add_user")
    public ModelAndView addUser(String username, String displayName, String password) {
        return ifLoggedInThenReturn(() -> {
            return new ModelAndView("/admin/main"); // not implemented yet
        });
    }
}
