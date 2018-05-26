package com.hyd.ftpserver.user;

import com.hyd.dao.DAO;
import com.hyd.dao.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yiding.he
 */
@Service
public class UserService {

    @Autowired
    private DAO dao;

    public List<FtpUser> queryAllUsers() {
        return dao.query(FtpUser.class, "select * from ftp_user");
    }

    public void addUser(FtpUser ftpUser) {
        ftpUser.setId(System.currentTimeMillis());
        dao.insert(ftpUser, "ftp_user");
    }

    public FtpUser queryUserById(Long userId) {
        return dao.find(FtpUser.class, "ftp_user", userId);
    }

    public FtpUser queryUserByName(String username) {
        return dao.queryFirst(FtpUser.class, "select * from ftp_user where username=?", username);
    }

    public void updateUser(FtpUser ftpUser) {
        if (ftpUser.getId() == null) {
            return;
        }

        dao.execute(SQL.Update("ftp_user")
                .Set("display_name=?", ftpUser.getDisplayName())
                .SetIfNotEmpty("password=?", ftpUser.getPassword())
                .Where("id=?", ftpUser.getId()));
    }
}
