package Windows;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import Constants.Window;
import DataBaseOperation.OperateSQLServer;

/*�޸��������*/
public class PasswordEdit extends JFrame {
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
	String tempID;						//��ʱ�����˺�
	String tempOriginalPswd;			//��ʱ����ԭʼ����
	String tempNewPswd;					//��ʱ����������
	String tempPswdConfirm;				//��ʱ����ȷ������
	PasswordEdit(){
		init();
		addComponent();
		addAction();
		setLayout(new FlowLayout());
		setVisible(true);
		setTitle("�޸�����");		
		setLocation(Window.getMiddleWidth(300),Window.getMiddleHeight(350));
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
		Acts act = new Acts();
		userID.addActionListener(act);
		originalPswd.addActionListener(act);
		newPswd.addActionListener(act);
		pswdConfirm.addActionListener(act);
		okButton.addActionListener(act);
		exitButton.addActionListener(act);
	}
	class Acts implements ActionListener{

		final int OK = 1;
		final int EXIT = 2;
		final int ID = 3;
		final int ORIGIN_PSWD = 4;
		final int NEW_PSWD = 5;
		final int NEW_PSWD_CONFIRM = 6;
		int turnTo(ActionEvent e) {
			if(e.getSource()==okButton)return OK;
			if(e.getSource()==exitButton)return EXIT;
			if(e.getSource()==userID) return ID;
			if(e.getSource()==originalPswd)return ORIGIN_PSWD;
			if(e.getSource()==newPswd)return NEW_PSWD;
			if(e.getSource()==pswdConfirm)return NEW_PSWD_CONFIRM;
			return -1;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switch(turnTo(arg0)) {
				case OK:{//OK��
					if(warning())return;//�������Ϸ���
					OperateSQLServer oss = new OperateSQLServer();
					oss.connectToDatabase();
					ResultSet rs = oss.getPersonalInformation(Integer.parseInt(tempID));
					try {
						if(rs.next()) {
							if(rs.getString(3).equals(tempOriginalPswd)) {
								oss.updateUserPassword(Integer.parseInt(tempID), tempNewPswd);
							}
							else{
								originalPswd.requestFocus();
								JOptionPane.showMessageDialog(null,"ԭ�������,����������!");
							}
						}
						else {
							userID.requestFocus();
							JOptionPane.showMessageDialog(null,"�û�������,����������!");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					oss.closeDatabase();				
				}break;
				case EXIT:{//�˳���
					dispose();
				}
				case ID: originalPswd.requestFocus();break;
				case ORIGIN_PSWD: newPswd.requestFocus();break;
				case NEW_PSWD: pswdConfirm.requestFocus();break;
				case NEW_PSWD_CONFIRM:{
					okButton.doClick();
				}
				default:
			}
		}
		boolean warning() {//�������Ϸ���
			tempID = userID.getText();
			tempOriginalPswd = originalPswd.getText();
			tempNewPswd = newPswd.getText();
			tempPswdConfirm = pswdConfirm.getText();
			if(tempID.equals("")) {
				userID.requestFocus();
				JOptionPane.showMessageDialog(null,"�˺Ų���Ϊ��!");	
				return true;
			}
			if(tempOriginalPswd.equals("")) {
				originalPswd.requestFocus();
				JOptionPane.showMessageDialog(null,"ԭʼ���벻��Ϊ��!");	
				return true;
			}
			if(tempNewPswd.equals("")) {
				newPswd.requestFocus();
				JOptionPane.showMessageDialog(null,"�����벻��Ϊ��!");	
				return true;
			}
			if(tempPswdConfirm.equals("")) {
				pswdConfirm.requestFocus();
				JOptionPane.showMessageDialog(null,"ȷ�����벻��Ϊ��!");	
				return true;
			}
			if(!tempPswdConfirm.equals(tempNewPswd)) {
				JOptionPane.showMessageDialog(null,"�����������벻һ�£���������������");
				newPswd.setText("");
				pswdConfirm.setText("");
				return true;
			}
			if(!userID.getText().matches("[0-9]+"))
			{
				JOptionPane.showMessageDialog(null,"�˺Ÿ�ʽ����Ŷ!Ӧ��ȫ��Ϊ����.");
				return true;
			}
			return false;
		}

	}
	
}
