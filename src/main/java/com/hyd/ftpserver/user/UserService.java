package com.hyd.ftpserver.user;

import com.hyd.dao.DAO;
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
}
