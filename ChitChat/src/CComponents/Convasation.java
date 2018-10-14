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
	
	protected Image image;//ͷ��
	protected JLabel nicknameLabel;//�ǳ�
	protected JLabel styleWord;//����ǩ��
	public int ID;//�~̖
	public int onlineState;//�ھQ��B
	public long lastTimeSpeak;//�ϴΰl�ԕr�g��
	public int isRead;//�Ƿ����x
	public String spoke;//�ϴΰl��
	public String nickname;//�ǷQ
	public String style;//���Ժ���

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

