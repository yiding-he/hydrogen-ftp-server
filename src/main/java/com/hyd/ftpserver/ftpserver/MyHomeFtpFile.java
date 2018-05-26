package com.hyd.ftpserver.ftpserver;

import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.ftpserver.ftplet.User;

import java.util.Arrays;
import java.util.List;

/**
 * A virtual HOME directory for every user.
 *
 * @author yiding.he
 */
public class MyHomeFtpFile extends VirtualFtpFile {

    public MyHomeFtpFile(User user) {
        super(user, "/", true);
    }

    @Override
    public List<? extends FtpFile> listFiles() {
        return Arrays.asList(
                new VirtualFtpFile(getUser(), "/我的文档", true),
                new VirtualFtpFile(getUser(), "/产品文档", true)
        );
    }
}
