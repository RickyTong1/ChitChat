package Windows;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;

/*陌生人（非好友）资料窗口*/
public class StrangerWindow extends PublicDateWindow implements ActionListener{
	JButton addButton;					//添加该陌生人按钮
	JButton exitButton;					//退出按钮
	Box hbox;
	Box buttomBox;
	StrangerWindow(){				//构造方法
		newInit();					//初始化组件
		addListener();
	}
	/*方法：初始化组件*/
	void newInit() {
		addButton = new JButton("添加好友");
		exitButton = new JButton("退出");
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
	/*方法：给组件添加监视器*/
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
