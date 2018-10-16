package Client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

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
		
		//�жϲ�����ʱ���
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
		//��������
		lastSpoke.setFont(Fonts.MESSAGE_LASTSPEAK);
		timeStick.setFont(Fonts.MESSAGE_TIMESTICK);//����
		
		//�����Ƿ��Ѷ�����������ɫ
		if(isRead)
			lastSpoke.setForeground(Colors.MESSAGE_READ);
		else
			lastSpoke.setForeground(Colors.MESSAGE_UNREAD);
		//���ñ�ǩ��С
		nicknameLabel.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH-L/2
			,TIMESTICK_WORD_SIZE ));
		timeStick.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH-L/2
				,TIMESTICK_WORD_SIZE));
		lastSpoke.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH,LAST_SPOKE_WORD_SIZE));

		//���ö���
		nicknameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);//���������
		timeStick.setAlignmentX(Component.RIGHT_ALIGNMENT);//ʱ�俿�Ҷ���
		lastSpoke.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		/*
		 * allװ��һ�е�����
		 * elemΪ�ܳɵĴ����.
		 * */
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
		
		
		/*
		 * Ϊelem��Ӽ������Լ�popupmenu.
		 * */
		JPopupMenu operator = new JPopupMenu();
		JMenuItem deleteThis = new JMenuItem("�������");
		JMenuItem readThis = new JMenuItem("��Ϊ�Ѷ�");
		
		operator.add(deleteThis);
		operator.add(readThis);
		elem.add(operator);
		
		deleteThis.addActionListener(e->{
			MainWindow.deleteMessage(ID,elem);
		});
		readThis.addActionListener(e->{
			isRead = true;
			lastSpoke.setForeground(Colors.MESSAGE_READ);
			MainWindow.readMessage(ID);
		});
		
		elem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 if (e.isMetaDown()) {
	                    operator.show(e.getComponent(), e.getX(), e.getY());
	                }
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				elem.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_CHOSED));
			}
			@Override 
			public void mouseExited(MouseEvent e) {
				elem.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_BORDER_COLOR));
			}
			
		});
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
