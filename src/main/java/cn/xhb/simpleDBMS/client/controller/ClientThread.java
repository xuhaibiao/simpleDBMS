package cn.xhb.simpleDBMS.client.controller;

import cn.xhb.simpleDBMS.DTO.Request;
import cn.xhb.simpleDBMS.DTO.Response;
import cn.xhb.simpleDBMS.client.view.ClientHomeUI;
import cn.xhb.simpleDBMS.constants.Constant;
import cn.xhb.simpleDBMS.server.vo.TableStructure;
import cn.xhb.simpleDBMS.util.ClientUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;


/**
 * @author HaibiaoXu
 * @date Create in 18:19 2020/6/29
 * @modified By
 */
public class ClientThread extends Thread {
    private ClientHomeUI ui;
    private BufferedReader br;
    private PrintWriter pw;
    private String name;

    public ClientThread(ClientHomeUI ui, String name) {
        this.ui = ui;
        this.name = name;
        try {
            Socket client = new Socket("127.0.0.1", 8080);
            println("连接服务器成功：端口8080");
            br = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));
            pw = new PrintWriter(client.getOutputStream(), true);
            // 如果为 true，则 println、printf 或 format 方法将刷新输出缓冲区
            sendMsg(new Request());
        } catch (IOException e) {
            println("连接服务器失败：端口8080");
            println(e.toString());
            e.printStackTrace();
        }
        this.start();
    }

    @Override
    public void run() {
        JSONObject jsonObject;
        while (true) {
            try {
                jsonObject = JSONObject.parseObject(br.readLine());
            } catch (IOException e) {
                println("服务器断开连接");
                break;
            }
            if (jsonObject != null) {
                //将服务端发过来的消息根据规则分类
                Response response = jsonObject.toJavaObject(Response.class);
                if (response.getMsg() != null) {
                    println(response.getMsg());
                }
                if (Constant.OPEN_TABLE.equals(response.getOperation())) {
                    //刷新表名列表
                    this.ui.tableNamesTableModel.setDataVector(ClientUtil.getTableNamesData((List<String>) response.getData()), new String[]{
                            "表名"
                    });
                    this.ui.tableNamesTableModel.fireTableDataChanged();
                } else if (Constant.EXECUTE_SQL.equals(response.getOperation())) {

                } else if (Constant.SHOW_TABLE_INFO.equals(response.getOperation())) {
                    String body = JSONObject.toJSONString(response.getData());
                    HashMap<String, List> tableInfo = JSONObject.parseObject(body, HashMap.class);
                    List<TableStructure> structures = JSONObject.parseArray(JSONObject.toJSONString(tableInfo.get(Constant.STRUCTURE_INFO)), TableStructure.class);
                    List<HashMap> datas = JSONObject.parseArray(JSONObject.toJSONString(tableInfo.get(Constant.DATA_INFO)), HashMap.class);
                    //刷新表数据内容

                    //1.获取表名和数据
                    List tableDatasData = ClientUtil.getTableDatasData(datas, structures);
                    String[] colNames = (String[]) tableDatasData.get(0);
                    Object[][] tableData = (Object[][]) tableDatasData.get(1);

                    //2.动态构建表
                    this.ui.tableDataTableModel.setDataVector(tableData, colNames);
                    //刷新表结构内容
                    this.ui.tableStructureTableModel.setDataVector(ClientUtil.getTableStructuresData(structures), new String[]{
                            "字段名", "数据库类型", "长度", "Java类型"
                    });
                    this.ui.tableStructureTableModel.fireTableDataChanged();
                    this.ui.tableDataTableModel.fireTableDataChanged();
                }
            }
        }
    }

    public void sendMsg(Request request) {
        request.setName(this.name);
        try {
            pw.write(JSONObject.toJSONString(request) + "\n");
            pw.flush();
        } catch (Exception e) {
            println(e.toString());
        }
    }

    private void println(String msg) {
        this.ui.showMsg.setText(this.ui.showMsg.getText() + ">>" + msg + "\n");
    }

}

