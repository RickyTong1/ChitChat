package Windows;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;
import CComponents.MessageBlobType;
import Client.MainWindow;
import Client.SendMessage;
import Constants.SocketConstants;
import javafx.scene.layout.Border;

/*好友（非陌生人）资料窗口，继承PublicDateWindow实现代码重复利用*/
public class FriendWindow extends JFrame implements ActionListener {
	JButton exitButton; // 退出按钮
	JButton saveRemark; // 保存备注按钮
	Box hbox, leftBox, rightBox;
	Box message;
	Box remarkBox;// 备注盒子
	Box buttomBox;
	Box signatureBox;
	int userID, contactsID;
	JTextArea signature;// 用于展示个性签名
	JTextField remark;// 用于展示备注
	JLabel leftJLabel[] = new JLabel[6];
	JLabel rightJLabel[] = new JLabel[4];

	public FriendWindow(int userID, MessageBlob e) {
		this.userID = userID;
		this.contactsID = e.targetID;
		init(e);
		setVisible(true);
		setTitle("好友资料卡");
		setBounds(200, 200, 300, 350);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// this.getContentPane().setBackground(Color.white);
	}

	/* newInit方法添加父类中没有的组件 */
	void init(MessageBlob e) {
		leftJLabel[5] = new JLabel("备注：");
		remarkBox = Box.createHorizontalBox();
		remark = new JTextField(15);
		/* 向信息盒子中加组件 */
		remarkBox.add(Box.createHorizontalStrut(60));
		remarkBox.add(leftJLabel[5]);
		remarkBox.add(Box.createHorizontalStrut(10));
		remarkBox.add(remark);
		signature = new JTextArea(1, 1);
		signature.setLineWrap(true);
//		signature.setOpaque(true);
		signature.setEditable(false);
		signature.setBackground(Color.white);
		signatureBox = Box.createHorizontalBox();
		leftJLabel[0] = new JLabel("昵称：");
		leftJLabel[1] = new JLabel("性别：");
		leftJLabel[2] = new JLabel("生日：");
		leftJLabel[3] = new JLabel("年龄：");
		leftJLabel[4] = new JLabel("个性签名：");

		rightJLabel[0] = new JLabel("昵称");
		rightJLabel[1] = new JLabel("性别");
		rightJLabel[2] = new JLabel("生日");
		rightJLabel[3] = new JLabel("年龄");
		// rightJLabel[4] = new JLabel("个性签名");

		rightJLabel[0].setText(e.nickname);
		rightJLabel[1].setText(e.gender);
		rightJLabel[2].setText(e.birth);
		String birth = e.birth;
		int maxSplit = 3;
		String[] source = birth.split("-", maxSplit);
		int oldYear = Integer.parseInt(source[0]);
		int newYear = Calendar.getInstance().get(Calendar.YEAR);
		int age = newYear - oldYear;
		rightJLabel[3].setText(String.valueOf(age));
		signature.setText(e.style);
		remark.setText(e.remark);

		leftBox = Box.createVerticalBox();
		rightBox = Box.createVerticalBox();
		message = Box.createHorizontalBox();
		leftBox.add(leftJLabel[0]);
		leftBox.add(Box.createVerticalStrut(20));
		leftBox.add(leftJLabel[1]);
		leftBox.add(Box.createVerticalStrut(20));
		leftBox.add(leftJLabel[2]);
		leftBox.add(Box.createVerticalStrut(20));
		leftBox.add(leftJLabel[3]);
//		leftBox.add(Box.createVerticalStrut(20));
//		leftBox.add(leftJLabel[4]);
//		rightBox.add(Box.createVerticalStrut(15));
		rightBox.add(rightJLabel[0]);
		rightBox.add(Box.createVerticalStrut(20));
		rightBox.add(rightJLabel[1]);
		rightBox.add(Box.createVerticalStrut(20));
		rightBox.add(rightJLabel[2]);
		rightBox.add(Box.createVerticalStrut(20));
		rightBox.add(rightJLabel[3]);
		rightBox.add(Box.createVerticalStrut(20));
		rightBox.add(signature);
		exitButton = new JButton("退出");
		saveRemark = new JButton("确认修改");
		hbox = Box.createHorizontalBox();
		/* 按钮盒子 */
		buttomBox = Box.createVerticalBox();
		hbox.add(saveRemark);
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(exitButton);
		/* 基本资料 */
		message.add(Box.createHorizontalStrut(60));
		message.add(leftBox);
		message.add(rightBox);
		message.add(Box.createHorizontalStrut(90));
		/* 个性签名盒子 */
		signatureBox.add(Box.createHorizontalStrut(30));
		signatureBox.add(leftJLabel[4]);
		signatureBox.add(Box.createHorizontalStrut(10));
		signatureBox.add(signature);
//		signatureBox.add(Box.createHorizontalStrut(10));
		buttomBox.add(remarkBox);
		buttomBox.add(Box.createVerticalStrut(10));
		buttomBox.add(message);
		buttomBox.add(Box.createVerticalStrut(10));
		buttomBox.add(signatureBox);
		buttomBox.add(Box.createVerticalStrut(10));
		buttomBox.add(hbox);
		buttomBox.add(Box.createVerticalStrut(40));
		add(buttomBox);
		exitButton.addActionListener(this);
		saveRemark.addActionListener(this);
		// Color background = new Color(238,233,233);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exitButton) {
			dispose();
		}
		if (e.getSource() == saveRemark) {
			MessageBlob message = new MessageBlob();
			message.type = MessageBlobType.FRIEND_PROFILE_UPDATE;
			message.senderIP = Property.Property.SERVER_IP;
			message.senderID = MainWindow.ID;
			message.targetID = contactsID;
			message.remark = remark.getText();
			new SendMessage(Property.Property.SERVER_IP, SocketConstants.GENERAL_PORT,
					MessageBlobOperator.pack(message));
			JOptionPane.showMessageDialog(null, "修改备注成功");
			MainWindow.ctsList.get(contactsID).remark = remark.getText();
			dispose();
		}
	}

}
