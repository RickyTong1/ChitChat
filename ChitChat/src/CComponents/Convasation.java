package CComponents;


import javax.swing.JPanel;

import Client.Message;
import Windows.ChatWindow;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public abstract class Convasation{
	public boolean hasMessage = false;
	public boolean hasChatWin = false;
	public ChatWindow chatWindow = null;
	public Message message = null;
	
	protected Image image;//ͷ��
	protected JLabel nicknameLabel;//�ǳ�
	protected JLabel styleWord;//����ǩ��
	public int ID;//�~̖
	public int onlineState;//�ھQ��B
	public long lastTimeSpeak;//�ϴΰl�ԕr�g��
	public int isRead;//�Ƿ����x
	public String spoke;//�ϴΰl��
	public String nickname;//�ǷQ
	public String remark;//��ע
	public String style;//���Ժ���
	public String gender;//�Ա�

	public Convasation(int id, int online, long time
			, int isread, String spoke,String nick
			, String remark,String style,String gender) {
		ID = id;
		onlineState = online;
		lastTimeSpeak = time;
		isRead = isread;
		this.spoke = spoke;
		nickname = nick;
		this.remark = remark;
		this.style = style;
		this.gender = gender;
		
		if(remark == null || remark.equals(""))
			nicknameLabel = new JLabel(nickname);
		else
			nicknameLabel = new JLabel(remark);
		
		styleWord = new JLabel(this.style);
	}
	public abstract Box create();
//	public abstract void setIssue();
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
}

