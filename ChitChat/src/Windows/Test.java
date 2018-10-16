package Windows;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
/*测试右键菜单类*/
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
		item1 = new JMenuItem("菜单一");
		item2 = new JMenuItem("菜单二");
		item3 = new JMenuItem("菜单三");
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
			JOptionPane.showMessageDialog(null,"菜单一");
		}
		if(e.getSource()==item2) {
			JOptionPane.showMessageDialog(null,"菜单二");
		}
		if(e.getSource()==item3) {
			JOptionPane.showMessageDialog(null,"菜单三");
		}
	}
}
