package Windows;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JTextField;
/*�������Ͻ���*/
public class PersonalData extends PublicDateWindow implements ActionListener{
	JButton saveButton;				//�������ϰ�ť
	JButton exitButton;				//�˳���ť
	Box hbox;						//��ťˮƽ����
	Box buttomBox;					//�ײ����
	PersonalData(){					//���췽��
		newInit();					//���ó�ʼ������
		addListener();		//������Ӽ������������������Ӽ�����
	}
	/*��ʼ���������*/
	void newInit() {									
		saveButton = new JButton("ȷ��");
		exitButton = new JButton("�˳�");
		hbox = Box.createHorizontalBox();
		buttomBox = Box.createVerticalBox();
		hbox.add(saveButton);
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(exitButton);
		buttomBox.add(bigBox);
		buttomBox.add(Box.createVerticalStrut(20));
		buttomBox.add(hbox);
		add(buttomBox);
	}
	/*��Ӽ���������*/
	void addListener() {
		saveButton.addActionListener(this);
		exitButton.addActionListener(this);
		nickName.addActionListener(this);				//�ǳ��ı���
		sex.addActionListener(this);						//�Ա��ı���
		birthday.addActionListener(this);;				//�����ı���
		age.addActionListener(this);						//�����ı���
		signature.addActionListener(this); 				//����ǩ���ı���
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exitButton) {
			System.exit(0);
		}
		if(e.getSource()==saveButton) {
			
		}
		if(e.getSource()==nickName) {
			sex.requestFocus();
		}
		if(e.getSource()==sex) {
			signature.requestFocus();
		}
		if(e.getSource()==birthday) {
			
		}
		if(e.getSource()==signature) {
			
		}
		
	}
}
