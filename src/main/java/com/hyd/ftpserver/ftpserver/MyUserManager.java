package com.hyd.ftpserver.ftpserver;

import com.hyd.ftpserver.user.FtpUser;
import com.hyd.ftpserver.user.UserService;
import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yiding.he
 */
@Component
public class MyUserManager implements UserManager {

    private static final Logger LOG = LoggerFactory.getLogger(MyUserManager.class);

    @Autowired
    private UserService userService;

    @Autowired
    FtpServerConfig ftpServerConfig;

    @Override
    public User getUserByName(String username) throws FtpException {

        FtpUser ftpUser = userService.queryUserByName(username);

        if (ftpUser == null) {
            LOG.info("User '" + username + "' not found.");
        } else {
            LOG.info("User '" + username + "' found.");
        }

        return ftpUser == null ? null : ftpUser.toUser();
    }

    @Override
    public String[] getAllUserNames() throws FtpException {
        return userService.queryAllUsers().stream()
                .map(FtpUser::getUsername).toArray(String[]::new);
    }

    @Override
    public void delete(String username) throws FtpException {

    }

    @Override
    public void save(User user) throws FtpException {

    }

    @Override
    public boolean doesExist(String username) throws FtpException {
        return username.equals("user");
    }

    @Override
    public User authenticate(Authentication authentication) throws AuthenticationFailedException {
        try {
            if (authentication instanceof UsernamePasswordAuthentication) {
                String username = ((UsernamePasswordAuthentication) authentication).getUsername();
                return getUserByName(username);
            } else {
                return null;
            }
        } catch (FtpException e) {
            throw new AuthenticationFailedException(e);
        }
    }

    @Override
    public String getAdminName() throws FtpException {
        return "admin";
    }

    @Override
    public boolean isAdmin(String username) throws FtpException {
        return false;
    }
}
