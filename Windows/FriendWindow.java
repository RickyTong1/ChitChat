package Windows;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;

/*���ѣ���İ���ˣ����ϴ��ڣ��̳�PublicDateWindowʵ�ִ����ظ�����*/
public class FriendWindow extends PublicDateWindow implements ActionListener{
	JButton exitButton;					//�˳���ť
	Box hbox;
	Box buttomBox;
	FriendWindow(){
		newInit();
	}
	/*newInit������Ӹ�����û�е����*/
	void newInit() {
		exitButton = new JButton("�˳�");
		hbox = Box.createHorizontalBox();
		buttomBox = Box.createVerticalBox();
		hbox.add(exitButton);
		buttomBox.add(bigBox);
		buttomBox.add(Box.createVerticalStrut(20));
		buttomBox.add(hbox);
		add(buttomBox);
		exitButton.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==exitButton) {
			dispose();
		}
	}
}
