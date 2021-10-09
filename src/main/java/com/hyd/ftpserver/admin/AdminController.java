package com.hyd.ftpserver.admin;

import com.hyd.ftpserver.ftpserver.FtpServerService;
import com.hyd.ftpserver.user.FtpUser;
import com.hyd.ftpserver.user.Group;
import com.hyd.ftpserver.user.GroupService;
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

import java.util.List;
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
    private GroupService groupService;

    @Autowired
    private FtpServerService ftpServerService;

    private boolean loggedIn;

    private ModelAndView ifLoggedInThenReturn(Supplier<ModelAndView> supplier) {
        return loggedIn ? supplier.get() : LOGIN;
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
        return ifLoggedInThenReturn(() -> {
                    ModelAndView modelAndView = new ModelAndView("/admin/main");
                    modelAndView.addObject("users", userService.queryAllUsers());
                    modelAndView.addObject("groups", groupService.queryAllGroups());
                    modelAndView.addObject("server_running", ftpServerService.isServerRunning());
                    modelAndView.addObject("server_port", ftpServerService.getFtpServerConfig().getPort());
                    return modelAndView;
                }
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
            FtpUser ftpUser = new FtpUser(username, displayName, password);
            userService.addUser(ftpUser);
            return new ModelAndView("redirect:/admin/main");
        });
    }

    @GetMapping("edit_user")
    public ModelAndView editUserPage(Long userId) {
        return ifLoggedInThenReturn(() -> {
            FtpUser ftpUser = userService.queryUserById(userId);
            return new ModelAndView("/admin/add_user").addObject("user", ftpUser);
        });
    }

    @PostMapping("edit_user")
    public ModelAndView editUser(FtpUser ftpUser) {
        return ifLoggedInThenReturn(() -> {
            userService.updateUser(ftpUser);
            return new ModelAndView("redirect:/admin/main");
        });
    }

    @PostMapping("delete_user")
    public ModelAndView deleteUser(String userId) {
        return ifLoggedInThenReturn(() -> {
            userService.deleteUser(userId);
            return new ModelAndView("redirect:/admin/main");
        });
    }

    @GetMapping("add_group")
    public ModelAndView addGroupPage() {
        return ifLoggedInThenReturn(() ->
                new ModelAndView("/admin/add_group")
                        .addObject("users", userService.queryAllUsers()));
    }

    @PostMapping("add_group")
    public ModelAndView addGroup(String groupName, String[] userId) {
        return ifLoggedInThenReturn(() -> {
            groupService.createGroup(groupName, userId);
            return new ModelAndView("redirect:/admin/main");
        });
    }

    @GetMapping("edit_group")
    public ModelAndView editGroupPage(Long groupId) {
        return ifLoggedInThenReturn(() -> {
            Group group = groupService.queryGroupById(groupId);
            List<FtpUser> users = userService.queryAllUsersWithGroup(groupId);

            return new ModelAndView("/admin/add_group")
                    .addObject("users", users)
                    .addObject("group", group);
        });
    }

    @PostMapping("edit_group")
    public ModelAndView editGroup(Long groupId, String groupName, String[] userId) {
        return ifLoggedInThenReturn(() -> {
            groupService.updateGroup(groupId, groupName, userId);
            return new ModelAndView("redirect:/admin/main");
        });
    }

    @PostMapping("delete_group")
    public ModelAndView deleteGroup(Long groupId) {
        return ifLoggedInThenReturn(() -> {
            groupService.deleteGroup(groupId);
            return new ModelAndView("redirect:/admin/main");
        });
    }
}
