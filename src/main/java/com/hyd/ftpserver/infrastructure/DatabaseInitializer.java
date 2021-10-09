package com.hyd.ftpserver.infrastructure;

import com.hyd.dao.DAO;
import com.hyd.dao.mate.util.ScriptExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author yiding.he
 */
@Component
public class DatabaseInitializer {

    @Autowired
    private DAO dao;

    @PostConstruct
    public void init() {
        ScriptExecutor.execute("classpath:/dbscript/init.sql", dao);
    }
}
