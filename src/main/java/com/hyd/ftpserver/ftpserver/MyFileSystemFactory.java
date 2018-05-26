package com.hyd.ftpserver.ftpserver;

import com.hyd.ftpserver.user.FtpUser;
import com.hyd.ftpserver.user.Group;
import com.hyd.ftpserver.user.GroupService;
import com.hyd.ftpserver.user.UserService;
import org.apache.ftpserver.ftplet.FileSystemFactory;
import org.apache.ftpserver.ftplet.FileSystemView;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;

import java.util.List;

/**
 * @author yiding.he
 */
public class MyFileSystemFactory implements FileSystemFactory {

    private UserService userService;

    private GroupService groupService;

    private FtpServerConfig ftpServerConfig;

    public MyFileSystemFactory(
            UserService userService,
            GroupService groupService,
            FtpServerConfig ftpServerConfig) {

        this.userService = userService;
        this.groupService = groupService;
        this.ftpServerConfig = ftpServerConfig;
    }

    @Override
    public FileSystemView createFileSystemView(User user) throws FtpException {
        FtpUser ftpUser = userService.queryUserByName(user.getName());
        List<Group> groups = groupService.queryGroupsByUser(ftpUser.getId());
        return new MyFileSystemView(ftpUser, groups, ftpServerConfig);
    }
}
