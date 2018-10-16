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

public class Contacts extends Convasation {// 联系人 主窗口右侧的 元素

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
		 * 设置在线状态图标
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
		
		//设置字体
		styleWord.setFont(Fonts.CONTACTS_STYLEWORD);// 字体
		
		//labelbx装文字
		labelBx.add(super.nicknameLabel);
		labelBx.add(Box.createVerticalStrut(5));
		labelBx.add(super.styleWord);
		labelBx.setPreferredSize(new Dimension(Constants.CONTACTS_PANEL_WIDTH
				,Constants.CONTACTS_PANEL_HEIGHT));
		
		//总成box
		thisBx.add(labelBx);
		thisBx.add(Box.createHorizontalStrut(5));
		thisBx.add(onlineState);
		thisBx.setPreferredSize(new Dimension(Constants.CONTACTS_PANEL_WIDTH
				,Constants.CONTACTS_PANEL_HEIGHT));
		thisBx.setBorder(BorderFactory.createLineBorder(Colors.CONTACT_BORDER_COLOR));
		
		//设置监听器和popupmenu
		JPopupMenu operator = new JPopupMenu();
		JMenuItem deleteThis = new JMenuItem("删除该联系人");
		JMenuItem setRemark = new JMenuItem("设置备注");
		operator.add(deleteThis);
		operator.add(setRemark);
		deleteThis.addActionListener(e->{
			int choose = JOptionPane.showConfirmDialog(null, "确认删除该联系人?\n操作将不能撤销.", "提示"
					, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			//System.out.println(choose);
			if(choose == 0/*选是*/)
			{	
				MainWindow.deleteContacts(ID,thisBx);//发送删除指令
				
				
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
			System.out.println("文件不存在.");
		}
	}

	@Override
	public void refreshMsg() {
		// TODO 联系人状态刷新方法

	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
