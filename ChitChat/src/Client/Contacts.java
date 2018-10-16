package Client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import Constants.*;
import CComponents.*;

public class Contacts extends Convasation {// ��ϵ�� �������Ҳ�� Ԫ��

	public Contacts(int id, int online, long time, int isread
			, String spoke, String nick, String style) {
		
		super(id, online, time, isread, spoke, nick, style);
		// TODO Auto-generated constructor stub
	}
	boolean isOnline;
	Icon onlineImage;
	JLabel onlineState;
	@Override
	public Box create() {

		Box labelBx = Box.createVerticalBox();
		Box thisBx = Box.createHorizontalBox();
		/*
		 * ��������״̬ͼ��
		 *  
		 * */
		switch (super.onlineState) {
		case Internet.ONLINE: {
			isOnline = true;
			setIcon("C:\\Users\\Ricky\\eclipse-workspace\\ChitChat\\image\\Online.png");
			break;
		}
		case Internet.OFFLINE: {
			isOnline = false;
			setIcon("C:\\Users\\Ricky\\eclipse-workspace\\ChitChat\\image\\Offline.png");
			break;
		}
		}
		
		//��������
		styleWord.setFont(Fonts.CONTACTS_STYLEWORD);// ����
		
		//labelbxװ����
		labelBx.add(super.nicknameLabel);
		labelBx.add(Box.createVerticalStrut(5));
		labelBx.add(super.styleWord);
		labelBx.setPreferredSize(new Dimension(Constants.CONTACTS_PANEL_WIDTH
				,Constants.CONTACTS_PANEL_HEIGHT));
		
		//�ܳ�box
		thisBx.add(labelBx);
		thisBx.add(Box.createHorizontalStrut(5));
		thisBx.add(onlineState);
		thisBx.setPreferredSize(new Dimension(Constants.CONTACTS_PANEL_WIDTH
				,Constants.CONTACTS_PANEL_HEIGHT));
		thisBx.setBorder(BorderFactory.createLineBorder(Colors.CONTACT_BORDER_COLOR));
		
		//���ü�������popupmenu
		JPopupMenu operator = new JPopupMenu();
		JMenuItem deleteThis = new JMenuItem("ɾ������ϵ��");
		JMenuItem setRemark = new JMenuItem("���ñ�ע");
		operator.add(deleteThis);
		operator.add(setRemark);
		deleteThis.addActionListener(e->{
			int choose = JOptionPane.showConfirmDialog(null, "ȷ��ɾ������ϵ��?\n���������ܳ���.", "��ʾ"
					, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			//System.out.println(choose);
			if(choose == 0/*ѡ��*/)
			{	
				MainWindow.deleteContacts(ID,thisBx);//����ɾ��ָ��
				
				
			}
		});
		
		thisBx.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 if (e.isMetaDown()) {
	                    operator.show(e.getComponent(), e.getX(), e.getY());
	                }
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				thisBx.setBorder(BorderFactory.createLineBorder(Colors.CONTACT_CHOSED));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				thisBx.setBorder(BorderFactory.createLineBorder(Colors.CONTACT_BORDER_COLOR));
				
			}
			
		});
		
		return thisBx;

	}
	public void setIcon(String url) {
		try {
			this.onlineImage = new ImageIcon(ImageIO.read(new File(url)));
			this.onlineState = new JLabel(onlineImage);
			this.onlineState.setPreferredSize(new Dimension(Constants.CONTACTS_ONLINESTATE_WIDTH
					,Constants.CONTACTS_ONLINESTATE_WIDTH));
		} catch (IOException e) {
			System.out.println("�ļ�������.");
		}
	}

	@Override
	public void refreshMsg() {
		// TODO ��ϵ��״̬ˢ�·���

	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
