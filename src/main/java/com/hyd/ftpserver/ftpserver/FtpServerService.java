package com.hyd.ftpserver.ftpserver;

import com.hyd.ftpserver.user.GroupService;
import com.hyd.ftpserver.user.UserService;
import org.apache.ftpserver.ConnectionConfigFactory;
import org.apache.ftpserver.DataConnectionConfigurationFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FileSystemFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author yiding.he
 */
@Service
public class FtpServerService {

    private static final Logger LOG = LoggerFactory.getLogger(FtpServerService.class);

    @Autowired
    private MyUserManager myUserManager;

    @Autowired
    private FtpServerConfig ftpServerConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    private FtpServer ftpServer;

    @PostConstruct
    public void init() throws Exception {
        start();
    }

    public boolean isServerRunning() {
        return ftpServer != null && !ftpServer.isStopped();
    }

    public void stop() {
        if (!isServerRunning()) {
            return;
        }

        this.ftpServer.stop();
        this.ftpServer = null;
    }

    public void start() throws FtpException {
        if (isServerRunning()) {
            stop();
        }

        this.ftpServer = createFtpServer();
        this.ftpServer.start();
        LOG.info("FTP server started at port " + ftpServerConfig.getPort());
    }

    private FtpServer createFtpServer() {
        ListenerFactory listener = new ListenerFactory();
        listener.setServerAddress(ftpServerConfig.getAddress());
        listener.setPort(ftpServerConfig.getPort());
        listener.setDataConnectionConfiguration(
                new DataConnectionConfigurationFactory().createDataConnectionConfiguration());

        ConnectionConfigFactory connection = new ConnectionConfigFactory();
        connection.setMaxLoginFailures(10);
        connection.setLoginFailureDelay(100);
        connection.setAnonymousLoginEnabled(false);

        FtpServerFactory serverFactory = new FtpServerFactory();
        serverFactory.setUserManager(createUserManager());
        serverFactory.setFileSystem(createFileSystem());
        serverFactory.addListener("default", listener.createListener());
        serverFactory.setConnectionConfig(connection.createConnectionConfig());

        return serverFactory.createServer();
    }

    private UserManager createUserManager() {
        return myUserManager;
    }

    private FileSystemFactory createFileSystem() {
        return new MyFileSystemFactory(userService, groupService, ftpServerConfig);
    }
}
