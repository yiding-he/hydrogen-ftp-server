package com.hyd.ftpserver.admin;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yiding_he
 */
@ConfigurationProperties(prefix = "admin")
public class AdminConfig {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
