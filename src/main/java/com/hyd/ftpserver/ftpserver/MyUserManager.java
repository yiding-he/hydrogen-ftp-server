package com.hyd.ftpserver.ftpserver;

import com.hyd.ftpserver.user.UserService;
import org.apache.ftpserver.ftplet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yiding.he
 */
@Component
public class MyUserManager implements UserManager {

    @Autowired
    private UserService userService;

    @Override
    public User getUserByName(String username) throws FtpException {
        return userService.queryUserByName(username).toUser();
    }

    @Override
    public String[] getAllUserNames() throws FtpException {
        return new String[] {"user"};
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
            return getUserByName("user");
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
