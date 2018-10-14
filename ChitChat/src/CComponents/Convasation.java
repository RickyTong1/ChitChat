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

public abstract class Convasation implements Runnable{
	
	protected Image image;//头像
	protected JLabel nicknameLabel;//昵称
	protected JLabel styleWord;//个性签名
	public int ID;//賬號
	public int onlineState;//在綫狀態
	public long lastTimeSpeak;//上次發言時間戳
	public int isRead;//是否已讀
	public String spoke;//上次發言
	public String nickname;//昵稱
	public String style;//個性簽名

	public Convasation(int id, int online, long time, int isread, String spoke,String nick, String style) {
		ID = id;
		onlineState = online;
		lastTimeSpeak = time;
		isRead = isread;
		this.spoke = spoke;
		nickname = nick;
		this.style = style;
		nicknameLabel = new JLabel(nickname);
		styleWord = new JLabel(this.style);
	}
	public abstract Box create();
	@Override
	public boolean equals(Object arg0) {
		Convasation e = (Convasation) arg0;
		if (this.ID == e.ID)
			return true;
		return false;
	}
	public boolean equals(int i) {
		if(this.ID == i)return true;
		return false;	
	}
	public abstract void refreshMsg();
	public abstract void run();
	
}

