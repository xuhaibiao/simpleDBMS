package cn.xhb.simpleDBMS.server.controller;

import cn.xhb.simpleDBMS.DTO.Request;
import cn.xhb.simpleDBMS.DTO.Response;
import cn.xhb.simpleDBMS.constants.Constant;
import cn.xhb.simpleDBMS.server.service.IServerService;
import cn.xhb.simpleDBMS.server.service.impl.ServerServiceImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HaibiaoXu
 * @date Create in 17:30 2020/6/30
 * @modified By
 */
public class Server {
    private Map<String, Socket> clients;
    private IServerService service = new ServerServiceImpl();

    public Server() {
        try {
            int port = 8080;
            clients = new HashMap<String, Socket>();
            ServerSocket server = new ServerSocket(port);
            while (true) {
                //阻塞
                Socket socket = server.accept();
                Mythread mythread = new Mythread(socket);
                mythread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Mythread extends Thread {
        Socket serverSocket;
        private BufferedReader br;
        private BufferedReader selfBr;
        private PrintWriter pw;
        StringBuilder sendContent = new StringBuilder();
        String receiveContent;
        String notice;

        Mythread(Socket s) {
            serverSocket = s;
        }

        @Override
        public void run() {
            //客户端名称
            String name = null;
            Request request;
            try {
                //通过输入流（程序员为中心）从服务器套接字获取数据----socket连接成功的信息
                br = new BufferedReader(new InputStreamReader(serverSocket.getInputStream(), "UTF-8"));
                selfBr = new BufferedReader(new InputStreamReader(System.in));
                JSONObject jsonObject = JSONObject.parseObject(br.readLine());
                request = jsonObject.toJavaObject(Request.class);
                name = request.getName();
                clients.put(name, this.serverSocket);
                //如果有客户机连接进来，且为第一个，则自动打开数据库连接（本来应通过登录连接）
                if (clients.size() == 1) {
                    service.openMysqlCon();
                }
                sendContent.append(name).append(" 加入，当前客户机数量为： ").append(clients.size());

                sendMsg(new Response<String>(name, null, sendContent.toString(), null));
                while ((receiveContent = br.readLine()) != null) {
                    request = JSONObject.toJavaObject(JSON.parseObject(receiveContent), Request.class);
                    //将客户端发过来的消息根据规则分类
                    if (Constant.OPEN_TABLE.equals(request.getOperation())) {
                        try {
                            List<String> tableNames = service.getTableNames();
                            String msg = "成功获取" + tableNames.size() + "张表";
                            sendMsg(new Response<List<String>>(request.getName(), tableNames, msg, Constant.OPEN_TABLE));
                        } catch (Exception e) {
                            String msg = "获取失败，异常：" + e.toString();
                            sendMsg(new Response<List<String>>(request.getName(), null, msg, Constant.OPEN_TABLE));
                        }
                    } else if (Constant.EXECUTE_SQL.equals(request.getOperation())) {
                        try {
                            int delNum = service.executeDel(request.getParam());
                            if (delNum >= 0) {
                                String msg = "成功执行" + request.getParam() + "共影响" + delNum + "条数据，请重新打开表查看结果";
                                sendMsg(new Response<Object>(request.getName(), null, msg, Constant.EXECUTE_SQL));
                            }
                        } catch (Exception e) {
                            String msg = "删除失败，异常：" + e.toString();
                            sendMsg(new Response<Object>(request.getName(), null, msg, Constant.EXECUTE_SQL));
                        }

                    } else if (Constant.SHOW_TABLE_INFO.equals(request.getOperation())) {
                        try {
                            Map<String, List> tableInfo = service.getTableInfo(request.getParam());
                            String msg = "成功获取表信息";
                            sendMsg(new Response<Map<String, List>>(request.getName(), tableInfo, msg, Constant.SHOW_TABLE_INFO));

                        } catch (Exception e) {
                            String msg = "获取表信息失败，异常：" + e.toString();
                            sendMsg(new Response<Object>(request.getName(), null, msg, Constant.SHOW_TABLE_INFO));
                        }
                    }


                }
            } catch (Exception e) {
                System.out.println(name + "离开");
                clients.remove(name);
                //如果客户机全部断开，则数据库连接释放
                if (clients.size() == 0) {
                    service.closeMysqlCon();
                } else {
                    String msg = name + " 离开了，还有" + clients.size() + "台客户机在线";
                    sendMsg(new Response<Object>(name, null, msg, null));
                }
            }

        }

        void sendMsg(Response response) {
            try {
                //广发，单发
                if (response.getOperation() == null) {
                    for (String key : clients.keySet()) {
                        pw = new PrintWriter(clients.get(key).getOutputStream(), true);
                        pw.write(JSONObject.toJSONString(response) + "\n");
                        pw.flush();
                    }
                } else {
                    pw = new PrintWriter(clients.get(response.getName()).getOutputStream(), true);
                    pw.write(JSONObject.toJSONString(response) + "\n");
                    pw.flush();

                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sendContent.delete(0, sendContent.length());
            }
        }
    }
}

