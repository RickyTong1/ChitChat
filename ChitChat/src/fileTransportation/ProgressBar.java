package fileTransportation;

import java.awt.Color;
import java.awt.FlowLayout;
/*����������ʾ��������Ŀǰ�������޷����£�*/
//TODO

import javax.swing.*;
public class ProgressBar extends JFrame{
	JProgressBar progressBar;
	public ProgressBar(){
		setVisible(true);
		setSize(300, 300);
		setLocation(100,100);
		setLayout(new FlowLayout());
		setBar();
		//showBar();
	}
	public void setBar() {
		progressBar = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
		progressBar.setStringPainted(true);
		add(progressBar);	
		progressBar.setValue(0);
		progressBar.setBackground(Color.CYAN);
	}
	public void showBar(int a) {
		progressBar.setValue(a);
		if(a == 100) {		
			JOptionPane.showMessageDialog(null, "�ļ�������� ");
		}	
	}
}
