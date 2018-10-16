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
    JTextField sendField;                 //�����ı���
    JButton send;                       //���Ͱ�ť
    Box box,hbox;
    Box chatBox;//��װ������Ϣ�ĺ��ӣ��ӵ��������chatContent��
    JPanel chatContent;
    JScrollPane sp;
    JTextArea content;
    ChatWindow(){
       init();
       setLayout(null);
       setTitle("������Ϣ");
       setBounds(100,100,500,600);
       setVisible(true);
       setResizable(false);
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void init(){	
       sendField = new JTextField(10);
       chatContent = new JPanel();
       chatContent.setBounds(0, 0, 489, 489);//�������λ�ô�С
       chatContent.setBackground(SystemColor.WHITE);//���������ɫ
       chatContent.setLayout(new FlowLayout(FlowLayout.LEFT));//������������
       send = new JButton("����");
       chatBox = Box.createVerticalBox();
       box=Box.createVerticalBox();       
       hbox=Box.createHorizontalBox();
       hbox.add(Box.createHorizontalStrut(50));
       sendField.setBounds(70,510,300,30);//���÷����ı����λ�ô�С
       send.setBounds(380,510,60,30);//���÷��Ͱ�ť��λ�úʹ�С
       add(sendField);//��ӷ����ı���
       add(send);//��ӷ��Ͱ�ť
       sp = new JScrollPane(chatContent);//�������ɹ���
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
        	String webUrl="http://www.baidu.com";//�ٶ�ʱ��
            time=getNetworkTime(webUrl);
            content = new JTextArea(sendField.getText(),1,1);
            content.setLineWrap(true);
            content.setBackground(Color.CYAN);
            content.setEditable(false);
            chatBox.add(new JLabel("<html>"+time+"<br></html>"));
            chatBox.add(content);           
            //chatContent.add(new JLabel(time));
            chatContent.add(chatBox);
            chatContent.validate();//ˢ��������
            /*���ù�����һֱ�����·�*/
            JScrollBar vertical = sp.getVerticalScrollBar();
            vertical.setValue(vertical.getMinimum());
        }
        if(e.getSource() == send){
        	String webUrl="http://www.baidu.com";//�ٶ�ʱ��
            time=getNetworkTime(webUrl);
            content = new JTextArea(sendField.getText());
            content.setLineWrap(true);
            content.setBackground(Color.CYAN);
            content.setEditable(false);
            chatBox.add(new JLabel("<html>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+time+"<br></html>"));
            chatBox.add(content);
            //chatContent.add(new JLabel(time));
            chatContent.add(chatBox);
            chatContent.validate();//ˢ��������
            /*���ù�����һֱ�����·�*/
            JScrollBar vertical = sp.getVerticalScrollBar();
            vertical.setValue(vertical.getValue());
            vertical.getMinimum();
        }
    }
    /*��ȡ����ʱ��*/
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
