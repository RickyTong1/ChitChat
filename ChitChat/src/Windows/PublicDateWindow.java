package Windows;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
/*���ϴ��ڹ��õ���*/
public class PublicDateWindow extends JFrame{
	JLabel nickName_label;				//�ǳƱ�ǩ
	JLabel sex_label;					//�Ա��ǩ
	JLabel birthday_label;				//���ձ�ǩ
	JLabel age_label;					//�����ǩ
	JLabel signature_label;				//����ǩ����ǩ
	JTextField nickName;				//�ǳ��ı���
	JTextField sex;						//�Ա��ı���
	JTextField birthday;				//�����ı���
	JTextField age;						//�����ı���
	JTextField signature;				//����ǩ���ı���
	Box bigBox;							//ˮƽ����
	Box labelBox,textFieldBox;			//��ֱ����
	PublicDateWindow(){						//���췽��
		init();							//���ó�ʼ������
		setLayout(new FlowLayout());
		setLabelLocation();
		setTextFieldLocation();
		setBoxLocation();
		setVisible(true);
		setTitle("���Ͽ�");
		setResizable(false);
		setSize(300,300);
		setLocation(300,300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	/*��ʼ�����*/
	void init() {
		nickName_label = new JLabel("�ǳƣ�");
		sex_label = new JLabel("�Ա�");
		birthday_label = new JLabel("���գ�");
		age_label = new JLabel("���䣺");
		signature_label = new JLabel("����ǩ����");
		nickName = new JTextField(10);
		sex = new JTextField(10);
		birthday = new JTextField(10);
		age = new JTextField(10);
		signature = new JTextField(10);
		bigBox = Box.createHorizontalBox();
		labelBox = Box.createVerticalBox();
		textFieldBox = Box.createVerticalBox();
	}
	/*�����ǩλ�ã����λ�ã�*/
	void setLabelLocation() {
		labelBox.add(nickName_label);
		labelBox.add(Box.createVerticalStrut(21));
		labelBox.add(sex_label);
		labelBox.add(Box.createVerticalStrut(21));
		labelBox.add(birthday_label);
		labelBox.add(Box.createVerticalStrut(21));
		labelBox.add(age_label);
		labelBox.add(Box.createVerticalStrut(21));
		labelBox.add(signature_label);
	}
	/*�趨�ı���λ�ã����λ�ã�*/
	void setTextFieldLocation() {
		textFieldBox.add(nickName);
		textFieldBox.add(Box.createVerticalStrut(9));
		textFieldBox.add(sex);
		textFieldBox.add(Box.createVerticalStrut(9));
		textFieldBox.add(birthday);
		textFieldBox.add(Box.createVerticalStrut(9));
		textFieldBox.add(age);
		textFieldBox.add(Box.createVerticalStrut(9));
		textFieldBox.add(signature);
	}
	/*��������ӵ�����*/
	void setBoxLocation() {
		bigBox.add(labelBox);
		bigBox.add(Box.createHorizontalStrut(20));
		bigBox.add(textFieldBox);
		add(bigBox);
	}
}
