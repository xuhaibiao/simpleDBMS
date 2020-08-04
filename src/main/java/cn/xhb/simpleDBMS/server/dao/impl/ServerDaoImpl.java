package cn.xhb.simpleDBMS.server.dao.impl;

import cn.xhb.simpleDBMS.server.dao.IServerDao;

import java.sql.*;

/**
 * @author HaibiaoXu
 * @date Create in 13:31 2020/6/29
 * @modified By
 */
public class ServerDaoImpl implements IServerDao {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE = "simpledbms";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DATABASE + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "123456";
    private static Connection conn;
    private static Statement stmt;

    /**
     * 删除数据
     *
     * @param sql sql语句
     * @return 删除的数量
     */
    public int del(String sql) throws SQLException {
        return stmt.executeUpdate(sql);
    }

    /**
     * @param tableName 表名
     * @return map中两个list，一个数据信息，一个结构信息
     */
    public ResultSet listTableInfoByTableName(String tableName) throws SQLException {
        String sql;
        sql = "SELECT * FROM " + tableName;
        return stmt.executeQuery(sql);
    }

    /**
     * 查询所有表
     */
    public ResultSet listTable() throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getTables(DATABASE, null, null,
                new String[]{"TABLE"});
        return rs;

    }

    /**
     * 打开数据库连接
     */
    public void open() {
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 执行查询
            System.out.println("实例化Statement对象...");
            stmt = conn.createStatement();
            System.out.println("数据库连接成功！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭数据库连接
     */
    public void close() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ignored) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            System.out.println("客户机全部退出，数据库连接关闭");
        }
    }

}
