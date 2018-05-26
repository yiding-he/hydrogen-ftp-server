package com.hyd.ftpserver;

import com.hyd.ftpserver.admin.AdminConfig;
import com.hyd.ftpserver.ftpserver.FtpServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AdminConfig.class, FtpServerConfig.class})
public class HydrogenFtpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HydrogenFtpServerApplication.class, args);
    }
}
