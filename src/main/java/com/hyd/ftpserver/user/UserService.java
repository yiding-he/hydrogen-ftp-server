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

    public List<User> queryAllUsers() {
        return dao.query(User.class, "select * from ftp_user");
    }

    public void addUser(User user) {
        user.setId(System.currentTimeMillis());
        dao.insert(user, "ftp_user");
    }

    public User queryUserById(Long userId) {
        return dao.find(User.class, "ftp_user", userId);
    }

    public void updateUser(User user) {
        if (user.getId() == null) {
            return;
        }

        dao.execute(SQL.Update("ftp_user")
                .Set("display_name=?", user.getDisplayName())
                .SetIfNotEmpty("password=?", user.getPassword())
                .Where("id=?", user.getId()));
    }
}
