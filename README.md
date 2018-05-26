# hydrogen-ftp-server
A java ftp server which supports multi-ftpUser and orgnization management.

## How to use

1. Start this project.
1. Open http://localhost:8080
1. Login with `admin/admin123` (can be changed in `application.properties`)
1. Add some user accounts.
1. Use a FTP client to open `ftp://localhost:2121` and login with these accounts.

## Configuration

All configurations are within `application.properties`.

* `server.port` - http management interface port
* `ftp.port` - port number of FTP service
* `ftp.save-path` - root of all files.

For more configurations check out class `com.hyd.ftpserver.ftpserver.FtpServerConfig` 