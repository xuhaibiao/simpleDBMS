package cn.xhb.simpleDBMS.server.service.impl;

import cn.xhb.simpleDBMS.constants.Constant;
import cn.xhb.simpleDBMS.server.dao.IServerDao;
import cn.xhb.simpleDBMS.server.dao.impl.ServerDaoImpl;
import cn.xhb.simpleDBMS.server.service.IServerService;
import cn.xhb.simpleDBMS.server.vo.TableStructure;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HaibiaoXu
 * @date Create in 15:48 2020/6/30
 * @modified By
 */
public class ServerServiceImpl implements IServerService {

    private IServerDao serverDao = new ServerDaoImpl();

    public List<String> getTableNames() throws SQLException {
        List<String> list = new ArrayList<String>();
        ResultSet rs = serverDao.listTable();
        while (rs.next()) {
            list.add(rs.getString(3));
        }
        return list;
    }

    public int executeDel(String sql) throws SQLException {
        return serverDao.del(sql);
    }

    public Map<String, List> getTableInfo(String tableName) throws SQLException {
        Map<String, List> rsMap = new HashMap<String, List>(2);
        //存数据信息，map封装每一行各列名(key)的数据(value)
        List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
        //存结构信息
        List<TableStructure> structures = new ArrayList<TableStructure>();

        ResultSet rs = serverDao.listTableInfoByTableName(tableName);
        ResultSetMetaData metaData = rs.getMetaData();
        //获取列的数量
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];

        //存储列名
        for (int i = 0; i < columnCount; ++i) {
            //通过序号获取列名,起始值为1
            String columnName = metaData.getColumnLabel(i + 1);
            columnNames[i] = columnName;
            String columnClassName = metaData.getColumnClassName(i + 1);
            int columnDisplaySize = metaData.getColumnDisplaySize(i + 1);
            String columnTypeName = metaData.getColumnTypeName(i + 1);
            structures.add(new TableStructure(columnName, columnTypeName, columnDisplaySize, columnClassName));
        }

        // 展开结果集数据库
        while (rs.next()) {
            Map<String, String> map = new HashMap<String, String>(16);
            //添加数据到map
            for (int i = 1; i <= columnCount; ++i) {
                map.put(columnNames[i - 1], rs.getString(i));
            }
            datas.add(map);
        }
        rsMap.put(Constant.DATA_INFO, datas);
        rsMap.put(Constant.STRUCTURE_INFO, structures);
        return rsMap;
    }

    public void openMysqlCon() {
        serverDao.open();
    }

    public void closeMysqlCon() {
        serverDao.close();
    }
}

