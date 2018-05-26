package com.hyd.ftpserver.admin;

import com.hyd.ftpserver.ftpserver.FtpServerService;
import com.hyd.ftpserver.user.UserService;
import org.apache.ftpserver.ftplet.FtpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    private static final ModelAndView LOGIN = new ModelAndView("redirect:/admin/index");

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private FtpServerService ftpServerService;

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
                        .addObject("users", userService.queryAllUsers())
                        .addObject("server_running", ftpServerService.isServerRunning())
        );
    }

    @PostMapping("/server_action")
    public ModelAndView serverAction(String action) {

        return ifLoggedInThenReturn(() -> {

            try {
                if (action.equals("start")) {
                    ftpServerService.start();
                } else if (action.equals("stop")) {
                    ftpServerService.stop();
                }
            } catch (FtpException e) {
                LOG.error("", e);
            }

            return new ModelAndView("redirect:/admin/main");
        });
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
