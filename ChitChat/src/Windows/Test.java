package Windows;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
/*�����Ҽ��˵���*/
public class Test extends JFrame implements ActionListener{
	JPopupMenu jMenu;
	JTextField text;
	Box box;
	JMenuItem item1,item2,item3;
	Test(){
		init();
		add();
		addListener();
		setLayout(new FlowLayout());
		setBounds(300,300,300,300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	void init() {
		jMenu = new JPopupMenu();
		box = Box.createHorizontalBox();
		text = new JTextField(20);
		item1 = new JMenuItem("�˵�һ");
		item2 = new JMenuItem("�˵���");
		item3 = new JMenuItem("�˵���");
	}
	void add() {
		jMenu.add(item1);
		jMenu.add(item2);
		jMenu.add(item3);
		text.setComponentPopupMenu(jMenu);
		box.add(text);
		add(box);
	}
	void addListener() {
		item1.addActionListener(this);
		item2.addActionListener(this);
		item3.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==item1) {
			JOptionPane.showMessageDialog(null,"�˵�һ");
		}
		if(e.getSource()==item2) {
			JOptionPane.showMessageDialog(null,"�˵���");
		}
		if(e.getSource()==item3) {
			JOptionPane.showMessageDialog(null,"�˵���");
		}
	}
}
