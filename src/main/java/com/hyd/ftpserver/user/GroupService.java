package com.hyd.ftpserver.user;

import com.hyd.dao.DAO;
import com.hyd.ftpserver.ftpserver.FtpServerConfig;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author yidin
 */
@Service
public class GroupService {

    @Autowired
    private DAO dao;

    @Autowired
    FtpServerConfig ftpServerConfig;

    public void createGroup(String groupName, String[] userIds) {
        long groupId = System.currentTimeMillis();
        String sql = "insert into ftp_group(id, group_name) values(?,?)";

        DAO.runTransaction(() -> {
            dao.execute(sql, groupId, groupName);
            addUsersToGroup(userIds, groupId);

            createGroupDir(groupId);
        });
    }

    private void createGroupDir(long groupId) {
        try {
            File groupDirectory = new File(ftpServerConfig.getSavePath(), "groups/" + groupId);
            FileUtils.forceMkdir(groupDirectory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addUsersToGroup(String[] userIds, long groupId) {
        String addUserSql = "insert into ftp_user_group(user_id, group_id) values(?,?)";
        for (String userId : userIds) {
            dao.execute(addUserSql, userId, groupId);
        }
    }

    public List<Group> queryAllGroups() {
        return dao.query(Group.class, "select *, " +
                "(select count(*) from ftp_user_group where group_id=ftp_group.id) as user_count " +
                "from ftp_group");
    }

    public Group queryGroupById(Long groupId) {
        return dao.queryFirst(Group.class, "select * from ftp_group where id=?", groupId);
    }

    public void updateGroup(Long groupId, String groupName, String[] userIds) {
        DAO.runTransaction(() -> {
            dao.execute("update ftp_group set group_name=? where id=?", groupName, groupId);
            dao.execute("delete from ftp_user_group where group_id=?", groupId);
            addUsersToGroup(userIds, groupId);
        });
    }

    public void deleteGroup(Long groupId) {
        DAO.runTransaction(() -> {
            dao.execute("delete from ftp_group where id=?", groupId);
            dao.execute("delete from ftp_user_group where group_id=?", groupId);
        });
    }

    public List<Group> queryGroupsByUser(Long userId) {
        return dao.query(Group.class, "select * from ftp_group where id in(" +
                "select group_id from ftp_user_group where user_id=?)", userId);
    }
}
