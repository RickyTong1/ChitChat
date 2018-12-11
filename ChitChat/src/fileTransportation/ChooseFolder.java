package fileTransportation;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Constants.SocketConstants;

/*�������ڱ����豸�洢�ռ䣬�������յ����ļ����浽��λ��*/
/*Ŀǰ�и����⣬���ǵ����ļ������ʱ���ֱ�ӵ�رմ��ڻᱨ����Ϊ�޷����·��*/
public class ChooseFolder {
	public static String savePath;// ����·��

	public ChooseFolder() {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.showDialog(new JLabel(), "ѡ��");
		File file = jfc.getSelectedFile();

		if (file != null) {
			savePath = file.getAbsolutePath();
			System.out.println("�ļ�:" + file.getAbsolutePath());
			System.out.println(jfc.getSelectedFile().getName());
		}

	}

	public static void save() {
		if (savePath == null) {
			JOptionPane.showMessageDialog(null, "�����ļ�����Ϊ��");
		} else {
			ReceiveFile se = new ReceiveFile(savePath, SocketConstants.FILE_GRN_PORT);
			se.start();// �����߳�
		}
	}
}
