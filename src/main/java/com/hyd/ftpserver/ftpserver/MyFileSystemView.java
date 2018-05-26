package com.hyd.ftpserver.ftpserver;

import org.apache.commons.lang3.StringUtils;
import org.apache.ftpserver.ftplet.FileSystemView;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.ftpserver.ftplet.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author yiding.he
 */
public class MyFileSystemView implements FileSystemView {

    private static final Logger LOG = LoggerFactory.getLogger(MyFileSystemView.class);

    private User user;

    private String workingDirectory = "/";

    public MyFileSystemView(User user) {
        this.user = user;
    }

    @Override
    public FtpFile getHomeDirectory() throws FtpException {
        return new MyHomeFtpFile(this.user);
    }

    @Override
    public FtpFile getWorkingDirectory() throws FtpException {
        return workingDirectory.equals("/") ? getHomeDirectory() : getNativeFile(workingDirectory);
    }

    @Override
    public boolean changeWorkingDirectory(String dir) throws FtpException {
        this.workingDirectory = dir;
        return true;
    }

    @Override
    public FtpFile getFile(String file) throws FtpException {
        String path;

        if (file.startsWith("/")) {
            path = file;
        } else if (file.startsWith("./")) {
            path = workingDirectory + file.substring(2);
        } else if (file.startsWith("../")) {
            path = parentDirectory() + file.substring(3);
        } else {
            path = (workingDirectory.endsWith("/") ? workingDirectory : (workingDirectory + "/")) + file;
        }

        FtpFile ftpFile = path.equals("/") ? getHomeDirectory() : getNativeFile(path);
        LOG.info("Returning file " + ftpFile.getClass());
        return ftpFile;
    }

    private String parentDirectory() {
        if (workingDirectory.equals("/")) {
            return workingDirectory;
        } else {
            String parent = StringUtils.substringBeforeLast(workingDirectory, "/");
            if (parent.length() == 0) {
                parent = "/";
            }
            return parent;
        }
    }

    @Override
    public boolean isRandomAccessible() throws FtpException {
        return false;
    }

    @Override
    public void dispose() {

    }

    private NativeFtpFile getNativeFile(String directory) {
        return new NativeFtpFile(directory, new File("target" + directory), user);
    }
}
