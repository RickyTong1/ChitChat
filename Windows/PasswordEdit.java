package Windows;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/*�޸��������*/
public class PasswordEdit extends JFrame implements ActionListener{
	JTextField userID;					//�û��˺�
	JTextField originalPswd;			//ԭ�����ı���
	JPasswordField newPswd;					//�����������
	JPasswordField pswdConfirm;				//ȷ�����������
	JButton okButton;					//ȷ�ϰ�ť
	JButton exitButton;					//�˳���ť
	/*��ǩ����*/
	JLabel userID_label;				//�˺ű�ǩ
	JLabel originalPswd_label;			//ԭ�������ǩ
	JLabel newPswd_label;				//�������ǩ
	JLabel pswdConfirm_label;			//ȷ���������ǩ
	Box hbox1,hbox2;					//ˮƽ���ֵĺ���
	Box vbox_label,vbox_textField,vbox_buttom;		//��ֱ���ֵĺ���
	PasswordEdit(){
		init();
		addComponent();
		addAction();
		setLayout(new FlowLayout());
		setVisible(true);
		setTitle("�޸�����");		
		setLocation(200,200);
		setSize(300,350);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	void init() {
		/*�����ʼ��*/
		userID = new JTextField(10);
		originalPswd = new JTextField(10);
		newPswd = new JPasswordField(10);
		pswdConfirm = new JPasswordField(10);
		okButton = new JButton("ȷ��");
		exitButton = new JButton("�˳�");
		userID_label = new JLabel("�˺ţ�");
		originalPswd_label = new JLabel("ԭ���룺");
		newPswd_label = new JLabel("�����룺");
		pswdConfirm_label = new JLabel("ȷ�����룺");
		/*��ֱ���ӳ�ʼ��*/
		vbox_label = Box.createVerticalBox();
		vbox_textField = Box.createVerticalBox();
		vbox_buttom = Box.createVerticalBox();
		/*ˮƽ���ӳ�ʼ��*/
		hbox1 = Box.createHorizontalBox();
		hbox2 = Box.createHorizontalBox();
	}
	void addComponent() {
		/*����ǩ���뵽������*/
		vbox_label.add(userID_label);
		vbox_label.add(Box.createVerticalStrut(26));
		vbox_label.add(originalPswd_label);
		vbox_label.add(Box.createVerticalStrut(26));
		vbox_label.add(newPswd_label);
		vbox_label.add(Box.createVerticalStrut(26));
		vbox_label.add(pswdConfirm_label);
		/*���ı�����뵽������*/
		vbox_textField.add(userID);
		vbox_textField.add(Box.createVerticalStrut(15));
		vbox_textField.add(originalPswd);
		vbox_textField.add(Box.createVerticalStrut(15));
		vbox_textField.add(newPswd);
		vbox_textField.add(Box.createVerticalStrut(15));
		vbox_textField.add(pswdConfirm);
		/*����ť�����ӵ�������*/
		hbox1.add(okButton);
		hbox1.add(Box.createHorizontalStrut(10));
		hbox1.add(exitButton);
		/*����Ƕ��*/
		hbox2.add(vbox_label);
		hbox2.add(Box.createHorizontalStrut(10));
		hbox2.add(vbox_textField);
		
		vbox_buttom.add(Box.createVerticalStrut(20));
		vbox_buttom.add(hbox2);
		vbox_buttom.add(Box.createVerticalStrut(30));
		vbox_buttom.add(hbox1);
		add(vbox_buttom);
		}
	/*���������Ӽ���������*/
	void addAction() {
		userID.addActionListener(this);
		originalPswd.addActionListener(this);
		newPswd.addActionListener(this);
		pswdConfirm.addActionListener(this);
		okButton.addActionListener(this);
		exitButton.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==userID) {
			originalPswd.requestFocus();
		}
		if(e.getSource()==originalPswd) {
			newPswd.requestFocus();
		}
		if(e.getSource()==newPswd) {
			pswdConfirm.requestFocus();
		}
		if(e.getSource()==pswdConfirm) {
			if(!pswdConfirm.equals(newPswd)) {
				JOptionPane.showMessageDialog(null,"�����������벻һ�£���������������");
			}
		}
		if(e.getSource()==okButton) {
			
		}
		if(e.getSource()==exitButton) {
			dispose();
		}
	}
}
