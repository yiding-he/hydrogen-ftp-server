package com.hyd.ftpserver.ftpserver;

import org.apache.ftpserver.ftplet.FileSystemFactory;
import org.apache.ftpserver.ftplet.FileSystemView;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;

/**
 * @author yiding.he
 */
public class MyFileSystemFactory implements FileSystemFactory {

    @Override
    public FileSystemView createFileSystemView(User user) throws FtpException {
        return new MyFileSystemView(user);
    }
}
