package Windows;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import CComponents.MessageAnswerType;
import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;
import CComponents.MessageBlobType;
import Client.MainWindow;
import Client.SendMessage;
import Constants.SocketConstants;
import javafx.scene.layout.Border;

/*好友（非陌生人）资料窗口，继承PublicDateWindow实现代码重复利用*/
public class StrangerWindow extends JFrame implements ActionListener{
	JButton exitButton;					//退出按钮
	JButton addConstent;//添加好友按钮
	Box hbox,leftBox,rightBox;
	Box message;
	Box buttomBox;//盒子按钮
	Box signatureBox;
	int userID,strangerID;
	JTextArea signature;//用于展示个性签名
	JLabel leftJLabel[] = new JLabel [5];
	JLabel rightJLabel[] = new JLabel [4];
	public StrangerWindow(int id,int strangerID){
		userID = id;	
		this.strangerID = strangerID;
		setVisible(true);
		setTitle("陌生人资料卡");
		setBounds(200,200,300,350);
		init();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setBackground(Color.white);
		}
	/*newInit方法添加父类中没有的组件*/
	void init() {		
		signature = new JTextArea(1,1);//文本区
		signature.setLineWrap(true);//设置文本区自动换行
//		signature.setOpaque(true);
		signature.setEditable(false);//设置文本区不可修改
		signature.setBackground(Color.white);//设置文本区颜色
		signatureBox = Box.createHorizontalBox();
		/*说明标签*/
		leftJLabel[0] = new JLabel("昵称：");
		leftJLabel[1] = new JLabel("性别：");
		leftJLabel[2] = new JLabel("生日：");
		leftJLabel[3] = new JLabel("年龄：");
		leftJLabel[4] = new JLabel("个性签名：");
		/*信息标签*/
		rightJLabel[0] = new JLabel("昵称");
		rightJLabel[1] = new JLabel("性别");
		rightJLabel[2] = new JLabel("生日");
		rightJLabel[3] = new JLabel("年龄");
		/*数据库操作*/
//		oss = new OperateSQLServer();
//		oss.connectToDatabase();
//		ResultSet rs = oss.getPersonalInformation(strangerID);
//		try {
//			if(rs.next()) {
//				rightJLabel[0].setText(rs.getString(2));
//				rightJLabel[1].setText(rs.getString(8));
//				rightJLabel[2].setText(rs.getString(9));
//				String birth = rs.getString(9);
//				int maxSplit = 3;
//				String[] source = birth.split("-", maxSplit);
//				int oldYear = Integer.parseInt(source[0]);
//				int newYear = Calendar.getInstance().get(Calendar.YEAR);
//				int age = newYear-oldYear;
//				rightJLabel[3].setText(String.valueOf(age));
//				signature.setText(rs.getString(10));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		oss.closeDatabase();//关闭数据库 TODO Server rebuild
		leftBox = Box.createVerticalBox();
		rightBox = Box.createVerticalBox();
		message = Box.createHorizontalBox();
		/*添加提示标签*/
		leftBox.add(leftJLabel[0]);
		leftBox.add(Box.createVerticalStrut(20));
		leftBox.add(leftJLabel[1]);
		leftBox.add(Box.createVerticalStrut(20));
		leftBox.add(leftJLabel[2]);
		leftBox.add(Box.createVerticalStrut(20));
		leftBox.add(leftJLabel[3]);
		/*添加信息标签*/
		rightBox.add(rightJLabel[0]);
		rightBox.add(Box.createVerticalStrut(20));
		rightBox.add(rightJLabel[1]);
		rightBox.add(Box.createVerticalStrut(20));
		rightBox.add(rightJLabel[2]);
		rightBox.add(Box.createVerticalStrut(20));
		rightBox.add(rightJLabel[3]);

		exitButton = new JButton("退出");
		addConstent = new JButton("添加好友");
		hbox = Box.createHorizontalBox();
		buttomBox = Box.createVerticalBox();
		hbox.add(exitButton);
		hbox.add(Box.createHorizontalStrut(10));
		hbox.add(addConstent);
		message.add(Box.createHorizontalStrut(60));
		message.add(leftBox);
		message.add(rightBox);
		message.add(Box.createHorizontalStrut(90));
		signatureBox.add(Box.createHorizontalStrut(30));
		signatureBox.add(leftJLabel[4]);
		signatureBox.add(Box.createHorizontalStrut(10));
		signatureBox.add(signature);
//		signatureBox.add(Box.createHorizontalStrut(10));
		buttomBox.add(message);
		buttomBox.add(Box.createVerticalStrut(10));
		buttomBox.add(signatureBox);
		buttomBox.add(Box.createVerticalStrut(10));
		buttomBox.add(hbox);
		buttomBox.add(Box.createVerticalStrut(40));
		add(buttomBox);
		/*添加监视器*/
		exitButton.addActionListener(this);
		addConstent.addActionListener(this);
		//Color background = new Color(238,233,233);		
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==exitButton) {
			dispose();
		}
		else if(e.getSource()==addConstent) {
			if (userID == this.strangerID)
				return ;
			MessageBlob message = new MessageBlob();
			message.type = MessageBlobType.ADD_CONTACT_QUEST;
			message.answer = MessageAnswerType.WAITING;
			message.senderID = userID;
			message.targetID = this.strangerID;
			message.targetRemark = "";
			message.roomID = 0;
			message.roomName = "";
			new SendMessage(Property.Property.SERVER_IP
					,SocketConstants.GENERAL_PORT
					,MessageBlobOperator.pack(message));

		}
	}
}
