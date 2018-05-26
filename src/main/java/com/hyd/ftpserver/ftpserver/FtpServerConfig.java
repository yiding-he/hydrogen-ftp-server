package com.hyd.ftpserver.ftpserver;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yidin
 */
@ConfigurationProperties(prefix = "ftp")
public class FtpServerConfig {

    private String address = "0.0.0.0";

    private int port = 2121;

    private String savePath = "./files";

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
