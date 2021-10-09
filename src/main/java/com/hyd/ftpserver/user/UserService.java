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
        return dao.query(FtpUser.class, "select " +
                "    u.*, GROUP_CONCAT(gg.group_name SEPARATOR ', ') as group_names" +
                " from ftp_user u left join (" +
                "    select ug.user_id, g.group_name " +
                "    from ftp_user_group ug, ftp_group g where ug.group_id=g.id" +
                " ) gg on u.id=gg.user_id" +
                " group by u.id");
    }

    public List<FtpUser> queryAllUsersWithGroup(Long groupId) {
        return dao.query(FtpUser.class, "select u.*," +
                " case when ug.group_id=? then 'true' else 'false' end as selected" +
                " from ftp_user u " +
                " left join ftp_user_group ug on u.id=ug.user_id" +
                " and ug.group_id=?", groupId, groupId);
    }

    public void addUser(FtpUser ftpUser) {
        ftpUser.setId(System.currentTimeMillis());
        dao.execute("insert into ftp_user(id, username, display_name, password)values(?,?,?,?)",
                ftpUser.getId(), ftpUser.getUsername(), ftpUser.getDisplayName(), ftpUser.getPassword());
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

    public void deleteUser(String userId) {
        dao.execute("delete from ftp_user where id=?", Long.parseLong(userId));
    }
}
