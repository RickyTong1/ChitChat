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

public class Message extends Convasation {// ��Ϣ ��������ߵĻ���Ԫ��

	public Message(int id, int online, long time, int isread, String spoke, String nick, String style) {
		super(id, online, time, isread, spoke, nick, style);
		// TODO Auto-generated constructor stub
	}

	JLabel timeStick = null;//�ʹ�ĕr�g��
	String latestSpeak;//�ϴ��ʹ��Ԓ�ĕr�g
	JLabel lastSpoke;//�ʹ�Ļ�
	boolean isRead;//�Ƿ��Ѷ�
	Box all = Box.createHorizontalBox();
	Box elem = Box.createVerticalBox();
	
	final int L = Constants.MESSAGE_PANEL_WIDTH/2;//�ǷQ�c�r�g�����g��
	final int LAST_SPOKE_WORD_SIZE = 12;//�ϴη��������С
	final int TIMESTICK_WORD_SIZE = 10;
	@Override
	public Box create() {
	
		lastSpoke = new JLabel(spoke);//�O��label
		
		switch(super.isRead)
		{
		case Internet.READ:this.isRead = true;break;
		case Internet.UNREAD:this.isRead = false;break;
		}
		
		long time = new Date().getTime() - lastTimeSpeak;
		time /= 1000;// ��
		time /= 60;// ��

		switch ((int) time / 60)// Сʱ
		{
		case 0: {
			if (time * 60 < 1)
				latestSpeak = "�ո�";
			else if (time * 60 > 28 && time * 60 < 32)
				latestSpeak = "Լ��Сʱǰ";
			else
				latestSpeak = String.valueOf(time * 60) + "��֮ǰ";
			
		}break;
		default: {
			if (time >= 24) {
				latestSpeak = String.valueOf(time / 24) + "��֮ǰ";
				break;
			} else
				latestSpeak = String.valueOf(time) + "Сʱ ֮ǰ";
		
		}break;
		}
		
		timeStick = new JLabel(String.valueOf(latestSpeak));
		
		lastSpoke.setFont(new Font("����",Font.PLAIN,LAST_SPOKE_WORD_SIZE));
		
		nicknameLabel.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH-L/2
			,TIMESTICK_WORD_SIZE ));
		timeStick.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH-L/2
				,TIMESTICK_WORD_SIZE));
		lastSpoke.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH,LAST_SPOKE_WORD_SIZE));

		nicknameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);//���������
		timeStick.setAlignmentX(Component.RIGHT_ALIGNMENT);//ʱ�俿�Ҷ���
		lastSpoke.setAlignmentX(Component.LEFT_ALIGNMENT);
		timeStick.setFont(Fonts.MESSAGE_TIMESTICK);//����
		
		
		all.setAlignmentX(Component.LEFT_ALIGNMENT);
		all.add(nicknameLabel);
		all.add(Box.createHorizontalStrut(L));
		all.add(timeStick);		
		all.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH
				,Constants.MESSAGE_PANEL_HEIGHT/2));//all�������ô�С
		
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
