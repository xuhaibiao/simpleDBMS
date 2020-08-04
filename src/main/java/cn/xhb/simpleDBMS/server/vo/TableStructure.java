package cn.xhb.simpleDBMS.server.vo;

/**
 * @author HaibiaoXu
 * @date Create in 21:48 2020/6/30
 * @modified By
 */
public class TableStructure {
    //字段名
    private String columnName;
    //字段数据库类型
    private String columnTypeName;
    //字段长度
    private int columnDisplaySize;
    //字段Java类型
    private String columnClassName;

    public TableStructure() {

    }

    public TableStructure(String columnName, String columnTypeName, int columnDisplaySize, String columnClassName) {
        this.columnName = columnName;
        this.columnTypeName = columnTypeName;
        this.columnDisplaySize = columnDisplaySize;
        this.columnClassName = columnClassName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnTypeName() {
        return columnTypeName;
    }

    public void setColumnTypeName(String columnTypeName) {
        this.columnTypeName = columnTypeName;
    }

    public int getColumnDisplaySize() {
        return columnDisplaySize;
    }

    public void setColumnDisplaySize(int columnDisplaySize) {
        this.columnDisplaySize = columnDisplaySize;
    }

    public String getColumnClassName() {
        return columnClassName;
    }

    public void setColumnClassName(String columnClassName) {
        this.columnClassName = columnClassName;
    }
}
