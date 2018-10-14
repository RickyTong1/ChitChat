package Windows;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;

/*好友（非陌生人）资料窗口，继承PublicDateWindow实现代码重复利用*/
public class FriendWindow extends PublicDateWindow implements ActionListener{
	JButton exitButton;					//退出按钮
	Box hbox;
	Box buttomBox;
	FriendWindow(){
		newInit();
	}
	/*newInit方法添加父类中没有的组件*/
	void newInit() {
		exitButton = new JButton("退出");
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
