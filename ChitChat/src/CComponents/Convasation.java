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
	protected Image image;//ͷ��
	protected JLabel nicknameLabel;//�ǳ�
	protected JLabel styleWord;//����ǩ��
	//List<Convasation> list;//��ϵ��/��Ϣ�б�
	protected int ID;//�˺�(��ΪΨһ��ʶ��)
	ActionListener actListener;

	public Convasation(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	public abstract void refreshMsg();
	@Override
	public void run() {
		//TODO: ��дʹ�ܹ�ʵʱˢ��״̬.
	}
	@Override
	public abstract void paint(Graphics g);
}

