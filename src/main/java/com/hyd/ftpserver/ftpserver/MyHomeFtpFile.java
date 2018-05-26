package com.hyd.ftpserver.ftpserver;

import com.hyd.ftpserver.user.FtpUser;
import com.hyd.ftpserver.user.Group;
import org.apache.commons.io.FileUtils;
import org.apache.ftpserver.ftplet.FtpFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A virtual HOME directory for every user.
 *
 * @author yiding.he
 */
public class MyHomeFtpFile extends VirtualFtpFile {

    private static final Logger LOG = LoggerFactory.getLogger(MyHomeFtpFile.class);

    private FtpUser ftpUser;

    private List<Group> groups;

    private String savePath;

    public MyHomeFtpFile(String savePath, FtpUser ftpUser, List<Group> groups) {
        super(ftpUser.toUser(), "/", true);
        this.savePath = savePath;
        this.ftpUser = ftpUser;
        this.groups = groups;
    }

    @Override
    public List<? extends FtpFile> listFiles() {

        try {
            File homeAbsoluteFile = new File(savePath, "users/" + ftpUser.getId());
            if (!homeAbsoluteFile.exists()) {
                FileUtils.forceMkdir(homeAbsoluteFile);
            }

            List<FtpFile> result = new ArrayList<>();
            result.add(new NativeFtpFile("/我的文档", homeAbsoluteFile, this.ftpUser.toUser()));

            for (Group group : groups) {
                File groupAbsoluteFile = new File(savePath, "groups/" + group.getId());
                result.add(new NativeFtpFile("/" + group.getGroupName(), groupAbsoluteFile, this.ftpUser.toUser()));
            }

            return result;
        } catch (IOException e) {
            LOG.error("", e);
            return Collections.emptyList();
        }
    }
}
