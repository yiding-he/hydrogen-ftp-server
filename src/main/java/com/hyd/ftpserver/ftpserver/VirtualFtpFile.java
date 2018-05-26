package com.hyd.ftpserver.ftpserver;

import org.apache.commons.lang3.StringUtils;
import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.ftpserver.ftplet.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

/**
 * @author yiding.he
 */
public class VirtualFtpFile implements FtpFile {

    private User user;

    private String absolutePath;

    private boolean directory;

    public VirtualFtpFile(User user, String absolutePath, boolean directory) {
        this.user = user;
        this.absolutePath = absolutePath;
        this.directory = directory;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getAbsolutePath() {
        return absolutePath;
    }

    @Override
    public String getName() {
        return StringUtils.substringAfterLast(absolutePath, "/");
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public boolean isDirectory() {
        return directory;
    }

    @Override
    public boolean isFile() {
        return !directory;
    }

    @Override
    public boolean doesExist() {
        return true;
    }

    @Override
    public boolean isReadable() {
        return true;
    }

    @Override
    public boolean isWritable() {
        return false;
    }

    @Override
    public boolean isRemovable() {
        return false;
    }

    @Override
    public String getOwnerName() {
        return this.user.getName();
    }

    @Override
    public String getGroupName() {
        return this.user.getName();
    }

    @Override
    public int getLinkCount() {
        return 0;
    }

    @Override
    public long getLastModified() {
        return 0;
    }

    @Override
    public boolean setLastModified(long time) {
        return false;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public Object getPhysicalFile() {
        return null;
    }

    @Override
    public boolean mkdir() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean move(FtpFile destination) {
        return false;
    }

    @Override
    public List<? extends FtpFile> listFiles() {
        return Collections.emptyList();
    }

    @Override
    public OutputStream createOutputStream(long offset) throws IOException {
        return null;
    }

    @Override
    public InputStream createInputStream(long offset) throws IOException {
        return null;
    }
}
