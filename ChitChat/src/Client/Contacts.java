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
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Constants.*;
import CComponents.*;

public class Contacts extends Convasation {// 联系人 主窗口右侧的 元素

	public Contacts(int id, int online, long time, int isread, String spoke, String nick, String style) {
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
		styleWord.setFont(Fonts.CONTACTS_STYLEWORD);// 字体
		
		labelBx.add(super.nicknameLabel);
		labelBx.add(Box.createVerticalStrut(5));
		labelBx.add(super.styleWord);
		labelBx.setPreferredSize(new Dimension(Constants.CONTACTS_PANEL_WIDTH
				,Constants.CONTACTS_PANEL_HEIGHT));
		
		thisBx.add(labelBx);
		thisBx.add(Box.createHorizontalStrut(5));
		thisBx.add(onlineState);
		thisBx.setPreferredSize(new Dimension(Constants.CONTACTS_PANEL_WIDTH
				,Constants.CONTACTS_PANEL_HEIGHT));
		thisBx.setBorder(BorderFactory.createLineBorder(Colors.CONTACT_BORDER_COLOR));
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
