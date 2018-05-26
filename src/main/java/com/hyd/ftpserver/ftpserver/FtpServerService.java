package com.hyd.ftpserver.ftpserver;

import org.apache.ftpserver.ConnectionConfigFactory;
import org.apache.ftpserver.DataConnectionConfigurationFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.filesystem.nativefs.NativeFileSystemFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.impl.PropertiesUserManager;
import org.springframework.stereotype.Service;

/**
 * @author yiding.he
 */
@Service
public class FtpServerService {

    private FtpServer ftpServer;

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
    }

    private FtpServer createFtpServer() {
        ListenerFactory listener = new ListenerFactory();
        listener.setPort(2121);
        listener.setDataConnectionConfiguration(
                new DataConnectionConfigurationFactory().createDataConnectionConfiguration());

        ConnectionConfigFactory connection = new ConnectionConfigFactory();
        connection.setMaxLoginFailures(10);
        connection.setLoginFailureDelay(5000);
        connection.setAnonymousLoginEnabled(true);

        FtpServerFactory serverFactory = new FtpServerFactory();
        serverFactory.setUserManager(new PropertiesUserManager(
                new ClearTextPasswordEncryptor(),
                FtpServerService.class.getResource("/sample-ftp-user.properties"),
                "admin"
        ));
        serverFactory.setFileSystem(new NativeFileSystemFactory());
        serverFactory.addListener("default", listener.createListener());
        serverFactory.setConnectionConfig(connection.createConnectionConfig());

        return serverFactory.createServer();
    }
}
