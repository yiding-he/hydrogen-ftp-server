package com.hyd.ftpserver.user;

import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission;
import org.apache.ftpserver.usermanager.impl.TransferRatePermission;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.util.Arrays;
import java.util.List;

/**
 * @author yidin
 */
public class FtpUser {

    private Long id;

    private String username;

    private String displayName;

    private String password;

    private Integer disabled;

    public FtpUser() {
    }

    public FtpUser(String username, String displayName, String password) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    ///////////////////////////////////////////////

    private List<Group> groups;

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public User toUser() {
        BaseUser baseUser = new BaseUser();
        baseUser.setName(username);
        baseUser.setPassword(password);
        baseUser.setHomeDirectory("/");
        baseUser.setEnabled(true);
        baseUser.setMaxIdleTime(600);
        baseUser.setAuthorities(Arrays.asList(
                new ConcurrentLoginPermission(10, 5),
                new TransferRatePermission(1024768, 1024768),
                new WritePermission()
        ));
        return baseUser;
    }
}
