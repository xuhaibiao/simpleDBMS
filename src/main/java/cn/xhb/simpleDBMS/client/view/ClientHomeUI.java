package cn.xhb.simpleDBMS.client.view;

import cn.xhb.simpleDBMS.DTO.Request;
import cn.xhb.simpleDBMS.client.controller.ClientThread;
import cn.xhb.simpleDBMS.constants.Constant;
import cn.xhb.simpleDBMS.util.ClientUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author HaibiaoXu
 * @date Create in 17:13 2020/6/29
 * @modified By
 */
public class ClientHomeUI {
    public JFrame frame;
    public JTextArea showMsg = new JTextArea();
    public JTextField sqlInput;
    private JTable tableNames;
    private JTable tableDataInfo;
    private JTable tableStructureInfo;
    public DefaultTableModel tableNamesTableModel;
    public DefaultTableModel tableDataTableModel;
    public DefaultTableModel tableStructureTableModel;
    ClientThread client;
    public String name;

    public ClientHomeUI(String name) {
        this.name = name;
        client = new ClientThread(ClientHomeUI.this, name);
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("录像租赁系统");
        frame.setBounds(160, 30, 1600, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1600, 1000);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        //logo区域
        JLabel logo = new JLabel("SimpleDBMS Author——WHUT-徐海标");
        logo.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        logo.setBounds(550, 10, 600, 50);
        panel.add(logo);


        //信息交互区域
        showMsg.setLineWrap(true);
        showMsg.setForeground(Color.BLACK);
        showMsg.setFont(new Font("楷体", Font.BOLD, 16));
        showMsg.setBackground(Color.lightGray);
        showMsg.setEditable(false);
        JScrollPane showMsgScrollPane = new JScrollPane(showMsg);
        showMsgScrollPane.setBounds(1230, 150, 300, 650);
        panel.add(showMsgScrollPane);


        //输入SQL和确认按钮区域
        JLabel sqlLabel = new JLabel("SQL语句:");
        sqlLabel.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        sqlLabel.setBounds(60, 150, 200, 50);
        panel.add(sqlLabel);

        sqlInput = new JTextField();
        sqlInput.setBounds(250, 150, 600, 50);
        sqlInput.setColumns(100);
        sqlInput.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        panel.add(sqlInput);

        JButton btnDel = new JButton("执 行");
        btnDel.setBackground(Color.WHITE);
        btnDel.setBounds(1000, 145, 120, 60);
        btnDel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        panel.add(btnDel);

        //数据库所有表展开按钮
        JButton btnOpenTable = new JButton("展开所有表");
        btnOpenTable.setBackground(Color.WHITE);
        btnOpenTable.setBounds(60, 230, 120, 60);
        btnOpenTable.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        panel.add(btnOpenTable);

        //数据库所有表名展示
        tableNames = new JTable();
        tableNames.setRowHeight(35);
        tableNames.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        tableNames.setBackground(Color.WHITE);
        JTableHeader head = tableNames.getTableHeader();
        head.setPreferredSize(new Dimension(head.getWidth(), 35));
        head.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        tableNamesTableModel = new DefaultTableModel(null,
                new String[]{
                        "表名"
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableNames.setModel(tableNamesTableModel);
        JScrollPane tableNameScrollPane = new JScrollPane();
        tableNameScrollPane.setBounds(60, 300, 300, 500);
        tableNameScrollPane.setViewportView(tableNames);
        ClientUtil.setTableColumnCenter(tableNames);
        panel.add(tableNameScrollPane);

        //获取表信息（数据+结构）按钮
        JButton btnShowTableInfo = new JButton("打开选中表");
        btnShowTableInfo.setBackground(Color.WHITE);
        btnShowTableInfo.setBounds(240, 230, 120, 60);
        btnShowTableInfo.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        panel.add(btnShowTableInfo);

        //表数据展示区域
        tableDataInfo = new JTable();
        tableDataInfo.setRowHeight(35);
        tableDataInfo.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        tableDataInfo.setBackground(Color.WHITE);
        JTableHeader tableDatahead = tableDataInfo.getTableHeader();
        tableDatahead.setPreferredSize(new Dimension(tableDatahead.getWidth(), 35));
        tableDatahead.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        tableDataTableModel = new DefaultTableModel(null,
                new String[]{
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableDataInfo.setModel(tableDataTableModel);
        JScrollPane tableDataScrollPane = new JScrollPane();
        tableDataScrollPane.setBounds(400, 300, 800, 250);
        tableDataScrollPane.setViewportView(tableDataInfo);
        ClientUtil.setTableColumnCenter(tableDataInfo);
        panel.add(tableDataScrollPane);


        //表结构展示区域
        tableStructureInfo = new JTable();
        tableStructureInfo.setRowHeight(35);
        tableStructureInfo.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        tableStructureInfo.setBackground(Color.WHITE);
        JTableHeader tableStructurehead = tableStructureInfo.getTableHeader();
        tableStructurehead.setPreferredSize(new Dimension(tableStructurehead.getWidth(), 35));
        tableStructurehead.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        tableStructureTableModel = new DefaultTableModel(null,
                new String[]{
                        "字段名", "数据库类型", "长度", "Java类型"
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableStructureInfo.setModel(tableStructureTableModel);
        JScrollPane tableStructureScrollPane = new JScrollPane();
        tableStructureScrollPane.setBounds(400, 550, 800, 250);
        tableStructureScrollPane.setViewportView(tableStructureInfo);
        ClientUtil.setTableColumnCenter(tableStructureInfo);
        panel.add(tableStructureScrollPane);


        btnDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String sql = sqlInput.getText();
                client.sendMsg(new Request(null, sql, Constant.EXECUTE_SQL));
            }
        });

        btnOpenTable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                client.sendMsg(new Request(null, null, Constant.OPEN_TABLE));
            }
        });

        btnShowTableInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int selectedRowCount = tableNames.getSelectedRowCount();
                if (selectedRowCount == 0) {
                    JOptionPane.showMessageDialog(null, "请选择要打开的表！", "打开失败", JOptionPane.ERROR_MESSAGE);
                } else if (selectedRowCount > 1) {
                    JOptionPane.showMessageDialog(null, "无法打开多张表！", "打开失败", JOptionPane.ERROR_MESSAGE);
                } else {
                    int index = tableNames.getSelectedRow();
                    String tableName = String.valueOf(tableNames.getValueAt(index, 0));
                    client.sendMsg(new Request(null, tableName, Constant.SHOW_TABLE_INFO));
                }

            }
        });


    }
}
