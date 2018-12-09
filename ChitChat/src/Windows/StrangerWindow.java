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

/*���ѣ���İ���ˣ����ϴ��ڣ��̳�PublicDateWindowʵ�ִ����ظ�����*/
public class StrangerWindow extends JFrame implements ActionListener{
	JButton exitButton;					//�˳���ť
	JButton addConstent;//��Ӻ��Ѱ�ť
	Box hbox,leftBox,rightBox;
	Box message;
	Box buttomBox;//���Ӱ�ť
	Box signatureBox;
	int userID,strangerID;
	JTextArea signature;//����չʾ����ǩ��
	JLabel leftJLabel[] = new JLabel [5];
	JLabel rightJLabel[] = new JLabel [4];
	public StrangerWindow(int id,int strangerID){
		userID = id;	
		this.strangerID = strangerID;
		setVisible(true);
		setTitle("İ�������Ͽ�");
		setBounds(200,200,300,350);
		init();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setBackground(Color.white);
		}
	/*newInit������Ӹ�����û�е����*/
	void init() {		
		signature = new JTextArea(1,1);//�ı���
		signature.setLineWrap(true);//�����ı����Զ�����
//		signature.setOpaque(true);
		signature.setEditable(false);//�����ı��������޸�
		signature.setBackground(Color.white);//�����ı�����ɫ
		signatureBox = Box.createHorizontalBox();
		/*˵����ǩ*/
		leftJLabel[0] = new JLabel("�ǳƣ�");
		leftJLabel[1] = new JLabel("�Ա�");
		leftJLabel[2] = new JLabel("���գ�");
		leftJLabel[3] = new JLabel("���䣺");
		leftJLabel[4] = new JLabel("����ǩ����");
		/*��Ϣ��ǩ*/
		rightJLabel[0] = new JLabel("�ǳ�");
		rightJLabel[1] = new JLabel("�Ա�");
		rightJLabel[2] = new JLabel("����");
		rightJLabel[3] = new JLabel("����");
		/*���ݿ����*/
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
//		oss.closeDatabase();//�ر����ݿ� TODO Server rebuild
		leftBox = Box.createVerticalBox();
		rightBox = Box.createVerticalBox();
		message = Box.createHorizontalBox();
		/*�����ʾ��ǩ*/
		leftBox.add(leftJLabel[0]);
		leftBox.add(Box.createVerticalStrut(20));
		leftBox.add(leftJLabel[1]);
		leftBox.add(Box.createVerticalStrut(20));
		leftBox.add(leftJLabel[2]);
		leftBox.add(Box.createVerticalStrut(20));
		leftBox.add(leftJLabel[3]);
		/*�����Ϣ��ǩ*/
		rightBox.add(rightJLabel[0]);
		rightBox.add(Box.createVerticalStrut(20));
		rightBox.add(rightJLabel[1]);
		rightBox.add(Box.createVerticalStrut(20));
		rightBox.add(rightJLabel[2]);
		rightBox.add(Box.createVerticalStrut(20));
		rightBox.add(rightJLabel[3]);

		exitButton = new JButton("�˳�");
		addConstent = new JButton("��Ӻ���");
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
		/*��Ӽ�����*/
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
