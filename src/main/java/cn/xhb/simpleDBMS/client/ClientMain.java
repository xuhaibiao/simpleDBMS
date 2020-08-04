package cn.xhb.simpleDBMS.client;

import cn.xhb.simpleDBMS.client.view.ClientLoginUI;

import java.awt.*;

/**
 * @author HaibiaoXu
 * @date Create in 17:01 2020/6/29
 * @modified By
 */
public class ClientMain {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClientLoginUI loginUI = new ClientLoginUI();
                    loginUI.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
