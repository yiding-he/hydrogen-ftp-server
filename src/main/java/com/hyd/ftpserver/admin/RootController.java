package com.hyd.ftpserver.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yiding_he
 */
@Controller
public class RootController {

    @RequestMapping({"", "/"})
    public String root() {
        return "redirect:/admin/";
    }
}
