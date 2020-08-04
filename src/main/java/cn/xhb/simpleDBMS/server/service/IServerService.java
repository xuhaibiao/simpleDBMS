package cn.xhb.simpleDBMS.server.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author HaibiaoXu
 * @date Create in 15:48 2020/6/30
 * @modified By
 */
public interface IServerService {
    /**
     * 获取所有表名
     *
     * @return 表名列表
     */
    List<String> getTableNames() throws SQLException;

    /**
     * 删除数据
     *
     * @param sql sql语句
     * @return 删除数量
     */
    int executeDel(String sql) throws SQLException;

    /**
     * 获取表信息
     *
     * @param tableName 表名
     * @return map中两个list，一个数据信息，一个结构信息
     */
    Map<String, List> getTableInfo(String tableName) throws SQLException;

    void openMysqlCon();

    void closeMysqlCon();
}
