package cn.xhb.simpleDBMS.util;

import cn.xhb.simpleDBMS.server.vo.TableStructure;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author HaibiaoXu
 * @date Create in 15:01 2019/12/1
 * @modified By
 */
public class ClientUtil {
    /**
     * 表格数据居中
     *
     * @param table
     */
    public static void setTableColumnCenter(JTable table) {
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, r);
    }

    public static Object[][] getTableNamesData(List<String> names) {
        Object[][] tableNamesData = new Object[names.size()][1];
        for (int i = 0; i < names.size(); i++) {
            tableNamesData[i][0] = names.get(i);
        }
        return tableNamesData;
    }

    public static Object[][] getTableStructuresData(List<TableStructure> structures) {
        Object[][] tableStructuresData = new Object[structures.size()][4];
        for (int i = 0; i < structures.size(); i++) {
            tableStructuresData[i][0] = structures.get(i).getColumnName();
            tableStructuresData[i][1] = structures.get(i).getColumnTypeName();
            tableStructuresData[i][2] = structures.get(i).getColumnDisplaySize();
            tableStructuresData[i][3] = structures.get(i).getColumnClassName();
        }
        return tableStructuresData;
    }

    public static List getTableDatasData(List<HashMap> datas, List<TableStructure> structures) {
        List rs = new ArrayList();
        int colNum = structures.size();
        String[] colNames = new String[colNum];

        int n = 0;
        for (TableStructure structure : structures) {
            colNames[n++] = structure.getColumnName();
        }

        int rowNum = datas.size();
        Object[][] tableDatasData = new Object[rowNum][colNum];
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                tableDatasData[i][j] = datas.get(i).get(colNames[j]);
            }
        }
        rs.add(colNames);
        rs.add(tableDatasData);
        return rs;
    }


}
