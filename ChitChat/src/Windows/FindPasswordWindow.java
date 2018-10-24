package Windows;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Constants.Window;
/*�һ��������*/
public class FindPasswordWindow extends JFrame implements ActionListener{
	JTextField userID;					//�û��˺�
	JTextField emailTextField;		//�����ı���
	JTextField codeText;			//��֤���ı���
	JButton send;					//���Ͱ�ť
	JPasswordField password;					//���������
	JPasswordField passwordConfirm;				//ȷ�����������
	JButton okButton;					//ȷ�ϰ�ť
	JButton exitButton;					//�˳���ť
	/*��ǩ����*/
	JLabel userID_label;				//�˺ű�ǩ
	JLabel codeLabel;				//��֤���ǩ
	JLabel emailLabel;					//�����ǩ
	JLabel password_label;				//�����ǩ
	JLabel passwordConfirm_label;			//ȷ�������ǩ
	Box hbox1,hbox2;					//ˮƽ���ֵĺ���
	Box emailBox;					//�������
	Box vbox_label,vbox_textField,vbox_buttom;		//��ֱ���ֵĺ���
	FindPasswordWindow(){					//���췽��
		init();							//���ó�ʼ������
		addComponent();					//��������������
		addListener();					//������Ӽ���������
		setLayout(new FlowLayout());
		setVisible(true);
		setTitle("�һ�����");		
		setLocation(Window.getMiddleWidth(300),Window.getMiddleHeight(350));
		setSize(300,350);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	void init() {
		/*�����ʼ��*/
		userID = new JTextField(10);
		emailTextField = new JTextField(10);
		codeText = new JTextField(10);
		codeText.setToolTipText("�������յ�����֤��");
		password = new JPasswordField(10);
		passwordConfirm = new JPasswordField(10);
		okButton = new JButton("ȷ��");
		exitButton = new JButton("�˳�");
		send = new JButton("����");
		userID_label = new JLabel("�˺ţ�");
		password_label = new JLabel("ԭ���룺");//TODO ????
		passwordConfirm_label = new JLabel("ȷ�����룺");
		emailLabel = new JLabel("���䣺");
		codeLabel = new JLabel("��֤�룺");
		/*��ֱ���ӳ�ʼ��*/
		vbox_label = Box.createVerticalBox();
		vbox_textField = Box.createVerticalBox();
		vbox_buttom = Box.createVerticalBox();
		/*ˮƽ���ӳ�ʼ��*/
		hbox1 = Box.createHorizontalBox();
		hbox2 = Box.createHorizontalBox();
		emailBox = Box.createHorizontalBox();
	}
	/*����������*/
	void addComponent() {
		/*����ǩ���뵽������*/
		vbox_label.add(userID_label);
		vbox_label.add(Box.createVerticalStrut(27));
		vbox_label.add(emailLabel);
		vbox_label.add(Box.createVerticalStrut(27));
		vbox_label.add(codeLabel);
		vbox_label.add(Box.createVerticalStrut(27));
		vbox_label.add(password_label);
		vbox_label.add(Box.createVerticalStrut(27));
		vbox_label.add(passwordConfirm_label);
		
		/*���ı�����뵽������*/
		vbox_textField.add(userID);
		vbox_textField.add(Box.createVerticalStrut(15));
		vbox_textField.add(emailBox);
		vbox_textField.add(Box.createVerticalStrut(15));
		vbox_textField.add(codeText);
		vbox_textField.add(Box.createVerticalStrut(15));
		vbox_textField.add(password);
		vbox_textField.add(Box.createVerticalStrut(15));
		vbox_textField.add(passwordConfirm);
		
		/*����ť�����ӵ�������*/
		hbox1.add(okButton);
		hbox1.add(Box.createHorizontalStrut(10));
		hbox1.add(exitButton);
		/*����Ƕ��*/
		emailBox.add(emailTextField);
		emailBox.add(Box.createHorizontalStrut(10));
		emailBox.add(send);
		hbox2.add(vbox_label);
		hbox2.add(Box.createHorizontalStrut(5));
		hbox2.add(vbox_textField);
		
		vbox_buttom.add(Box.createVerticalStrut(20));
		vbox_buttom.add(hbox2);
		vbox_buttom.add(Box.createVerticalStrut(30));
		vbox_buttom.add(hbox1);
		add(vbox_buttom);
	}
	/*�������Ӽ���������*/
	void addListener() {
		userID.addActionListener(this);
		emailTextField.addActionListener(this);
		codeText.addActionListener(this);
		password.addActionListener(this);
		passwordConfirm.addActionListener(this);
		okButton.addActionListener(this);
		exitButton.addActionListener(this);
		send.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==userID) {					//�˺��ı���
			emailTextField.requestFocus();
		}
		if(e.getSource()==emailTextField) {			//�����ı���
			codeText.requestFocus();
		}
		if(e.getSource()==codeText) {				//��֤���ı���
			password.requestFocus();
		}				
		if(e.getSource()==password) {				//���������
			passwordConfirm.requestFocus();
		}
		if(e.getSource()==passwordConfirm) {		//ȷ�����������
			if(!passwordConfirm.getText().equals(password.getText()))
				JOptionPane.showMessageDialog(null,"�����������벻һ�£���������������");
		}
		if(e.getSource()==okButton) {				//ȷ����ť
			
		}
		if(e.getSource()==exitButton) {				//�˳���ť
			dispose();
		}
		if(e.getSource()==send) {					//���Ͱ�ť
			//TODO
		}
	}
}