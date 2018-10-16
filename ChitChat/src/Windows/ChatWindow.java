package Windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class ChatWindow extends JFrame implements ActionListener{
	static String time="sjkla";
    JTextField sendField;                 //发送文本区
    JButton send;                       //发送按钮
    Box box,hbox;
    Box chatBox;//封装聊天消息的盒子，加到聊天面板chatContent中
    JPanel chatContent;
    JScrollPane sp;
    JTextArea content;
    ChatWindow(){
       init();
       setLayout(null);
       setTitle("聊天信息");
       setBounds(100,100,500,600);
       setVisible(true);
       setResizable(false);
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void init(){	
       sendField = new JTextField(10);
       chatContent = new JPanel();
       chatContent.setBounds(0, 0, 489, 489);//设置面板位置大小
       chatContent.setBackground(SystemColor.WHITE);//设置面板颜色
       chatContent.setLayout(new FlowLayout(FlowLayout.LEFT));//设置面板左对齐
       send = new JButton("发送");
       chatBox = Box.createVerticalBox();
       box=Box.createVerticalBox();       
       hbox=Box.createHorizontalBox();
       hbox.add(Box.createHorizontalStrut(50));
       sendField.setBounds(70,510,300,30);//设置发送文本框的位置大小
       send.setBounds(380,510,60,30);//设置发送按钮的位置和大小
       add(sendField);//添加发送文本框
       add(send);//添加发送按钮
       sp = new JScrollPane(chatContent);//设置面板可滚动
       sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
       add(sp);
       sp.setBounds(0, 0, 489, 489);
       hbox.setBounds(0,500,500,100);
       add(hbox);
       box.add(Box.createVerticalStrut(30));
       box.add(hbox);
       add(box);
       sendField.addActionListener(this);
       send.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == sendField){
        	String webUrl="http://www.baidu.com";//百度时间
            time=getNetworkTime(webUrl);
            content = new JTextArea(sendField.getText(),1,1);
            content.setLineWrap(true);
            content.setBackground(Color.CYAN);
            content.setEditable(false);
            chatBox.add(new JLabel("<html>"+time+"<br></html>"));
            chatBox.add(content);           
            //chatContent.add(new JLabel(time));
            chatContent.add(chatBox);
            chatContent.validate();//刷新面板组件
            /*设置滚动条一直在最下方*/
            JScrollBar vertical = sp.getVerticalScrollBar();
            vertical.setValue(vertical.getMinimum());
        }
        if(e.getSource() == send){
        	String webUrl="http://www.baidu.com";//百度时间
            time=getNetworkTime(webUrl);
            content = new JTextArea(sendField.getText());
            content.setLineWrap(true);
            content.setBackground(Color.CYAN);
            content.setEditable(false);
            chatBox.add(new JLabel("<html>我&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+time+"<br></html>"));
            chatBox.add(content);
            //chatContent.add(new JLabel(time));
            chatContent.add(chatBox);
            chatContent.validate();//刷新面板组件
            /*设置滚动条一直在最下方*/
            JScrollBar vertical = sp.getVerticalScrollBar();
            vertical.setValue(vertical.getValue());
            vertical.getMinimum();
        }
    }
    /*获取网络时间*/
    public static String getNetworkTime(String webUrl) {
        try {
            URL url=new URL(webUrl);
            URLConnection conn=url.openConnection();
            conn.connect();
            long dateL=conn.getDate();
            Date date=new Date(dateL);
            SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd HH:mm");
            return dateFormat.format(date);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "";
    }
}
