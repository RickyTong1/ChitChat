package utils;

import javax.swing.JOptionPane;
import CComponents.Convasation;
import CComponents.MessageAnswerType;
import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;
import CComponents.MessageBlobType;
import Client.MainWindow;
import Client.Message;
import Client.SendMessage;
import Client.ServerNote;
import Constants.Internet;
import Constants.SocketConstants;
import Property.Property;
import Windows.FriendWindow;
import Windows.LoginWindow;
import Windows.PersonalData;
import Windows.StrangerWindow;
import fileTransportation.ChooseFolder;
import fileTransportation.GetFilePath;
import javafx.application.Application;

public class ClientTranslation {// �������յ���Blob
	public static MainWindow mainWindow;

	public static synchronized void typeTrans(MessageBlob e) {// lock
		System.out.println("Type: " + e.type + "\nAnswer: " + e.answer + "\n");
		switch (e.type) {

		case CHAT_TEXT: {
			boolean hsChatWindow = MainWindow.ctsList.get(e.senderID).hasChatWin;
			if (hsChatWindow) {// ��������ҳ�Ļ�
				MainWindow.ctsList.get(e.senderID).chatWindow.addNewMsg(e.senderID, e.nickname, e.text);
			}
			if (MainWindow.ctsList.get(e.senderID).hasMessage) {// ������Ϣ�еĻ�
				if (hsChatWindow)
					MainWindow.ctsList.get(e.senderID).message.isRead = Internet.READ;// ����������ҳ,����Ϣ������ʾ��Ϣ,��������.
				MainWindow.ctsList.get(e.senderID).message.refreshMsg(e.text);
			} else {
				MainWindow.addNewMessage(e);
			}
		}
			break;

		case CHAT_PICTURE: {

		} // ��������
			break;

		case LOGIN: {
			switch (e.onlineState) {
			case Internet.LOGIN_SUCCESS: {
				if (e.senderID == LoginWindow.userID) {
					mainWindow = new MainWindow(e.senderID);
					return;// TODO
				}
				if (MainWindow.ctsList.get(e.senderID) == null)
					return;
				MainWindow.ctsList.get(e.senderID).onlineState = Internet.ONLINE;
				// i.onlineState = Internet.ONLINE;
				MainWindow.repaintContact(e.senderID);
			}
				break;
			case Internet.UNKOWN_USER: {
				JOptionPane.showMessageDialog(null, "�û���������!", "", JOptionPane.PLAIN_MESSAGE);

				new LoginWindow();
			}
				break;
			case Internet.PSWD_ERR: {
				JOptionPane.showMessageDialog(null, "�������!", "", JOptionPane.PLAIN_MESSAGE);
				new LoginWindow();
			}
				break;
			case Internet.ALREDY_LOGGED: {

				JOptionPane.showMessageDialog(null, "���Ѿ��ڱ𴦵�¼,���ȷ��������,��һ����������.", "", JOptionPane.PLAIN_MESSAGE);
				mainWindow = new MainWindow(e.senderID);
			}
				break;

			case Internet.FORCED_LOGGED_OUT: {
				JOptionPane.showMessageDialog(null, "�����˺��ڱ�����,���ѱ�������!\n���Ǳ��˲���,���޸�����.", "", JOptionPane.PLAIN_MESSAGE);
				if (mainWindow != null)
					mainWindow.dispose();// ���ڹر�
				new LoginWindow();
			}
			case Internet.SERVER_ERR: {
				JOptionPane.showMessageDialog(null, "������쳣!", "", JOptionPane.PLAIN_MESSAGE);
				new LoginWindow();
			}
			}
		}
			break;

		case LOGOUT: {
			Convasation i = MainWindow.ctsList.get(e.senderID);
			i.onlineState = Internet.OFFLINE;
			MainWindow.repaintContact(e.senderID);

		}
			break;

		case ADD_CONTACT_ANSWER: {
			if (e.answer == MessageAnswerType.POSITIVE) {
				MessageBlob quest_for_friend_list = new MessageBlob();// ��ϵ���б��ʼ��
				quest_for_friend_list.type = MessageBlobType.FRIEND_LIST_QUEST;
				quest_for_friend_list.senderIP = Property.NATIVE_IP;
				quest_for_friend_list.senderID = MainWindow.ID;
				new SendMessage(Property.SERVER_IP, SocketConstants.SERVER_PORT,
						MessageBlobOperator.pack(quest_for_friend_list));
			}

			if (e.answer == MessageAnswerType.NEGATIVE)
				new ServerNote(e.senderID, "�û�" + e.senderID + "�ܾ�����������.", Constants.Constants.MSG_MODE_REFUSE);
		}
			break;

		case ONLINE_STATE_QUEST: {
			MessageBlob message = new MessageBlob();
			message.type = MessageBlobType.ONLINE_STATE_ANSWER;
			new SendMessage(Property.SERVER_IP, SocketConstants.SERVER_PORT, MessageBlobOperator.pack(message));
		}
			break;

		case REGISTER: {
			if (e.answer == MessageAnswerType.POSITIVE)
				JOptionPane.showMessageDialog(null, "ע��ɹ�,�˺�Ϊ:" + e.targetID);
			else
				JOptionPane.showMessageDialog(null, "����ǳ��Ѿ���������Ŷ!" + e.targetID);
		}
			break;

		case FIND_FRIEND: {

			if (e.answer == MessageAnswerType.POSITIVE)
				new StrangerWindow(MainWindow.ID, e);
			else {
				if (e.onlineState == 0) {
					JOptionPane.showMessageDialog(null, "��ѯʧ��!�����˺��Ƿ���ȷ.");
					return;
				}
				if (e.onlineState == 2) {
					JOptionPane.showMessageDialog(null, "��ѯʧ��!��������.");
					return;
				}

			}

		}
			break;

		case SELF_PROFILE_ANSWER: {

			if (e.answer == MessageAnswerType.POSITIVE) {
				if (MainWindow.hasPersonalWindow == false)
					MainWindow.persInfoInit(e);// ��Ϣ��ʼ��
				else {
					new PersonalData(e);
					// MainWindow.hasPersonalWindow = true;
				}

			}

			else
				JOptionPane.showMessageDialog(null, "������Ϣ��ȡ�쳣.������������" + ".\n������: CLN_CLNTRNS_SPAR", "",
						JOptionPane.PLAIN_MESSAGE);

		}
			break;

		case FRIEND_PROFILE_ANSWER: {
			if (e.answer == MessageAnswerType.POSITIVE) {
				new FriendWindow(MainWindow.ID, e);

			} else
				JOptionPane.showMessageDialog(null, "������Ϣ��ȡ�쳣.������������" + ".\n������: CLN_CLNTRNS_FPAR", "",
						JOptionPane.PLAIN_MESSAGE);
		}
			break;

		case CHAT_CONTENT_ANSWER: {
			if (e.answer == MessageAnswerType.POSITIVE) {
				System.out.println(e.messageslist[0]);

				for (int i = e.totalCounts - 1; i >= 0; i--) {
					System.out.println("ChatContent loaded!");
					MainWindow.ctsList.get(e.targetID).chatWindow.addNewMsg(e.messageslist[i].senderID,
							e.messageslist[i].nick, e.messageslist[i].text);
				}
			} else
				JOptionPane.showMessageDialog(null, "�����¼��ȡʧ��!������������.");

		}
			break;

		case FRIEND_LIST_ANSWER: {
			if (e.answer == MessageAnswerType.POSITIVE)
				MainWindow.ContactsInit(e);
		}
			break;
		case SELF_VERIFY: {
			if (e.answer == MessageAnswerType.POSITIVE) {
				for (int i = 0; i < e.verifylist.length; i++)
					if (e.verifylist[i].status.equals("WAITING"))
						new ServerNote(e.verifylist[i].id, "�û�" + e.verifylist[i].nickname + "���������Ϊ����.",
								Constants.Constants.MSG_MODE_ADDFD);
			} else
				JOptionPane.showMessageDialog(null, "������֤�����ȡʧ��!������������.");
		}
			break;
		case SELF_FILE: {
			if (e.answer == MessageAnswerType.POSITIVE)
				for (int i = 0; i < e.filelist.length; i++)
					if (e.filelist[i].fileState == 0)//
						new ServerNote(e.filelist[i].id, "�û�" + e.filelist[i].nickname + "��Ҫ���㷢���ļ�.",
								Constants.Constants.MSG_MODE_FILE);
					else
						JOptionPane.showMessageDialog(null, "�ļ������ȡʧ��!������������.");
		}
			break;
		case SEND_FILE: {
			GetFilePath.send();
		}
			break;
		case RECEIVE_FILE: {
			new ChooseFolder(e.fileName);
			ChooseFolder.save();
		}
			break;

		default:
			break;

		}
	}

}
