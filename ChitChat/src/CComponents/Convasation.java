package CComponents;


import javax.swing.JPanel;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

public abstract class Convasation extends Box implements Runnable{
	
	private static final long serialVersionUID = 1L;
	protected Image image;//头像
	protected JLabel nicknameLabel;//昵称
	protected JLabel styleWord;//个性签名
	//List<Convasation> list;//联系人/消息列表
	protected int ID;//账号(作为唯一标识符)
	ActionListener actListener;

	public Convasation(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	public abstract void refreshMsg();
	@Override
	public void run() {
		//TODO: 改写使能够实时刷新状态.
	}
	@Override
	public abstract void paint(Graphics g);
}

