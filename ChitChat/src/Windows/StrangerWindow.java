package Windows;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;

/*İ���ˣ��Ǻ��ѣ����ϴ���*/
public class StrangerWindow extends PublicDateWindow implements ActionListener{
	JButton addButton;					//��Ӹ�İ���˰�ť
	JButton exitButton;					//�˳���ť
	Box hbox;
	Box buttomBox;
	StrangerWindow(){				//���췽��
		newInit();					//��ʼ�����
		addListener();
	}
	/*��������ʼ�����*/
	void newInit() {
		addButton = new JButton("��Ӻ���");
		exitButton = new JButton("�˳�");
		hbox = Box.createHorizontalBox();
		buttomBox = Box.createVerticalBox();
		hbox.add(addButton);
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(exitButton);
		buttomBox.add(bigBox);
		buttomBox.add(Box.createVerticalStrut(20));
		buttomBox.add(hbox);
		add(buttomBox);
	}
	/*�������������Ӽ�����*/
	void addListener() {
		addButton.addActionListener(this);
		exitButton.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==addButton) {
			
		}
		if(e.getSource()==exitButton) {
			dispose();
		}
	}
}
