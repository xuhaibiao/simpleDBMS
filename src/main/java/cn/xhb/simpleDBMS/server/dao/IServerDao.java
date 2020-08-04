package cn.xhb.simpleDBMS.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author HaibiaoXu
 * @date Create in 14:36 2020/7/1
 * @modified By
 */
public interface IServerDao {
    int del(String sql) throws SQLException;

    ResultSet listTableInfoByTableName(String tableName) throws SQLException;

    ResultSet listTable() throws SQLException;

    void open();

    void close();
}
