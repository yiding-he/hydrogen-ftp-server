package com.hyd.ftpserver.ftpserver;

import com.hyd.ftpserver.user.FtpUser;
import com.hyd.ftpserver.user.Group;
import org.apache.commons.lang3.StringUtils;
import org.apache.ftpserver.ftplet.FileSystemView;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * @author yiding.he
 */
public class MyFileSystemView implements FileSystemView {

    private static final Logger LOG = LoggerFactory.getLogger(MyFileSystemView.class);

    private FtpUser ftpUser;

    private List<Group> groups;

    private FtpServerConfig ftpServerConfig;

    private String workingDirectory = "/";

    public MyFileSystemView(
            FtpUser ftpUser,
            List<Group> groups,
            FtpServerConfig ftpServerConfig) {

        this.ftpUser = ftpUser;
        this.groups = groups;
        this.ftpServerConfig = ftpServerConfig;
    }

    @Override
    public FtpFile getHomeDirectory() throws FtpException {
        return new MyHomeFtpFile(ftpServerConfig.getSavePath(), this.ftpUser, this.groups);
    }

    @Override
    public FtpFile getWorkingDirectory() throws FtpException {
        return workingDirectory.equals("/") ? getHomeDirectory() : getNativeFile(workingDirectory);
    }

    @Override
    public boolean changeWorkingDirectory(String dir) throws FtpException {
        if (dir.startsWith("/")) {
            this.workingDirectory = dir;
        } else {
            this.workingDirectory += "/" + dir;
        }
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

    private NativeFtpFile getNativeFile(String path) {
        return new NativeFtpFile(path, toFile(path), this.ftpUser.toUser());
    }

    private File toFile(String path) {

        String category = path.startsWith("/我的文档") ? "users" : "groups";

        path = path.replaceFirst("/我的文档", "/" + ftpUser.getId());
        for (Group group : groups) {
            path = path.replaceFirst("/" + group.getGroupName(), "/" + group.getId());
        }

        File file = new File(new File(ftpServerConfig.getSavePath(), category), path);
        LOG.info("actual file: " + file.getPath());
        return file;
    }
}
