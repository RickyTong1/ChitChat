package Client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import CComponents.Convasation;
import Constants.*;

public class Message extends Convasation {// 消息 主窗口左边的基本元素

	public Message(int id, int online, long time, int isread, String spoke, String nick, String style) {
		super(id, online, time, isread, spoke, nick, style);
		// TODO Auto-generated constructor stub
	}

	JLabel timeStick = null;//送达的時間戳
	String latestSpeak;//上次送达的話的時間
	JLabel lastSpoke;//送达的话
	boolean isRead;//是否已读
	Box all = Box.createHorizontalBox();
	Box elem = Box.createVerticalBox();
	
	final int L = Constants.MESSAGE_PANEL_WIDTH/2;//昵稱與時間戳的間距
	final int LAST_SPOKE_WORD_SIZE = 12;//上次发言字体大小
	final int TIMESTICK_WORD_SIZE = 10;
	@Override
	public Box create() {
	
		lastSpoke = new JLabel(spoke);//設定label
		
		switch(super.isRead)
		{
		case Internet.READ:this.isRead = true;break;
		case Internet.UNREAD:this.isRead = false;break;
		}
		
		long time = new Date().getTime() - lastTimeSpeak;
		time /= 1000;// 秒
		time /= 60;// 分

		switch ((int) time / 60)// 小时
		{
		case 0: {
			if (time * 60 < 1)
				latestSpeak = "刚刚";
			else if (time * 60 > 28 && time * 60 < 32)
				latestSpeak = "约半小时前";
			else
				latestSpeak = String.valueOf(time * 60) + "分之前";
			
		}break;
		default: {
			if (time >= 24) {
				latestSpeak = String.valueOf(time / 24) + "天之前";
				break;
			} else
				latestSpeak = String.valueOf(time) + "小时 之前";
		
		}break;
		}
		
		timeStick = new JLabel(String.valueOf(latestSpeak));
		
		lastSpoke.setFont(new Font("宋体",Font.PLAIN,LAST_SPOKE_WORD_SIZE));
		
		nicknameLabel.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH-L/2
			,TIMESTICK_WORD_SIZE ));
		timeStick.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH-L/2
				,TIMESTICK_WORD_SIZE));
		lastSpoke.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH,LAST_SPOKE_WORD_SIZE));

		nicknameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);//设置左对齐
		timeStick.setAlignmentX(Component.RIGHT_ALIGNMENT);//时间靠右对齐
		lastSpoke.setAlignmentX(Component.LEFT_ALIGNMENT);
		timeStick.setFont(Fonts.MESSAGE_TIMESTICK);//字体
		
		
		all.setAlignmentX(Component.LEFT_ALIGNMENT);
		all.add(nicknameLabel);
		all.add(Box.createHorizontalStrut(L));
		all.add(timeStick);		
		all.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH
				,Constants.MESSAGE_PANEL_HEIGHT/2));//all盒子设置大小
		
		elem.add(all);		
		elem.add(lastSpoke);
		elem.setAlignmentX(Component.LEFT_ALIGNMENT);
		elem.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH
				,Constants.MESSAGE_PANEL_HEIGHT));
		elem.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_BORDER_COLOR));
		elem.validate();
		return elem;
	}

	

	@Override
	public void refreshMsg() {
		// TODO Auto-generated method stub
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
