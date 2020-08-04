package cn.xhb.simpleDBMS.client.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author HaibiaoXu
 * @date Create in 9:41 2020/7/1
 * @modified By
 */
public class ClientLoginUI extends JFrame {
    private JPanel contentPane;
    private JTextField clientNameInput;
    private JTextField mysqlUsernameInput;
    private JPasswordField mysqlUserPwdInput;
    private JTextField dbURLInput;
    private JComboBox comboBox;

    public ClientLoginUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("登录界面");
        setBounds(100, 100, 817, 644);
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((screenWidth - 817) / 2, (screenHeight - 644) / 2);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 0, 800, 600);
        contentPane.add(panel);
        panel.setLayout(null);

        clientNameInput = new JTextField();
        clientNameInput.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        clientNameInput.setBounds(400, 180, 150, 30);
        panel.add(clientNameInput);
        clientNameInput.setColumns(15);

        mysqlUsernameInput = new JTextField();
        clientNameInput.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        mysqlUsernameInput.setBounds(400, 240, 150, 30);
        panel.add(mysqlUsernameInput);
        mysqlUsernameInput.setColumns(15);

        mysqlUserPwdInput = new JPasswordField();
        clientNameInput.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        mysqlUserPwdInput.setColumns(15);
        mysqlUserPwdInput.setBounds(400, 300, 150, 30);
        panel.add(mysqlUserPwdInput);

        dbURLInput = new JTextField();
        dbURLInput.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        dbURLInput.setBounds(400, 360, 150, 30);
        panel.add(dbURLInput);
        dbURLInput.setColumns(15);

        JLabel sloganLabel = new JLabel("SimpleDBMS登录");
        sloganLabel.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        sloganLabel.setBounds(280, 50, 500, 50);
        panel.add(sloganLabel);

        JLabel tipsInput = new JLabel("tips:功能未完善，输入连接名即可");
        tipsInput.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        tipsInput.setBounds(260, 550, 500, 30);
        panel.add(tipsInput);

        JLabel clientNameLabel = new JLabel("连接名:");
        clientNameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        clientNameLabel.setBounds(302, 180, 120, 20);
        panel.add(clientNameLabel);

        JLabel mysqlUsrNameLabel = new JLabel("用户名:");
        mysqlUsrNameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        mysqlUsrNameLabel.setBounds(302, 240, 120, 20);
        panel.add(mysqlUsrNameLabel);


        JLabel pwdLabel = new JLabel("密码:");
        pwdLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        pwdLabel.setBounds(302, 300, 120, 20);
        panel.add(pwdLabel);

        final JLabel dbURLLabel = new JLabel("DB_URL:");
        dbURLLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        dbURLLabel.setBounds(302, 360, 120, 20);
        panel.add(dbURLLabel);

        JButton connectBtn = new JButton("连 接");
        connectBtn.setForeground(Color.BLACK);
        connectBtn.setBackground(Color.WHITE);
        connectBtn.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        connectBtn.setBounds(283, 462, 113, 42);
        panel.add(connectBtn);

        JButton resetBtn = new JButton("重 置");
        resetBtn.setBackground(Color.WHITE);
        resetBtn.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        resetBtn.setBounds(427, 462, 113, 42);
        panel.add(resetBtn);


        connectBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (clientNameInput.getText() != null && clientNameInput.getText().trim().length() > 0) {
                    String clientName = clientNameInput.getText();
                    ClientHomeUI clientHomeUI = new ClientHomeUI(clientName);
                    clientHomeUI.frame.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "请输入连接名！", "连接失败", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        resetBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clientNameInput.setText("");
                mysqlUsernameInput.setText("");
                mysqlUserPwdInput.setText("");
                dbURLInput.setText("");
            }
        });


    }
}
