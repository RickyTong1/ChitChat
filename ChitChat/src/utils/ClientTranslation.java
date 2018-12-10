package utils;

import javax.swing.JOptionPane;
import CComponents.Convasation;
import CComponents.MessageAnswerType;
import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;
import CComponents.MessageBlobType;
import Client.MainWindow;
import Client.SendMessage;
import Client.ServerNote;
import Constants.Internet;
import Constants.SocketConstants;
import Module.FriendWindow;
import Module.StrangerWindow;
import Windows.PersonalData;
import controller.FrdWindowCtrl;
import controller.LoginWindowCtrl;

public class ClientTranslation {// �������յ���Blob
	public static void typeTrans(MessageBlob e) {
		System.out.println("Trans Start CLN_TPTRN");
		switch (e.type) {

		case CHAT_TEXT: {
			if (MainWindow.ctsList.get(e.senderID).hasChatWin) {// ��������ҳ�Ļ�
				MainWindow.ctsList.get(e.senderID).chatWindow.addNewMsg(e.text);
			}
			if (MainWindow.ctsList.get(e.senderID).hasMessage) {// ������Ϣ�еĻ�
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
				if (e.senderID == LoginWindowCtrl.userID) {
					new MainWindow(e.senderID);
					return ;//TODO	
				}
				Convasation i = MainWindow.ctsList.get(e.senderID);
				i.onlineState = Internet.ONLINE;
				MainWindow.repaintContact(e.senderID);
			}
				break;
			case Internet.UNKOWN_USER: {
				JOptionPane.showMessageDialog(null, "�û���������!", "", JOptionPane.PLAIN_MESSAGE);
				new Popup("../View/LoginWindow.fxml", "��¼����");
			}
				break;
			case Internet.PSWD_ERR: {
				JOptionPane.showMessageDialog(null, "�������!", "", JOptionPane.PLAIN_MESSAGE);
				new Popup("../View/LoginWindow.fxml", "��¼����");
			}
				break;
			case Internet.ALREDY_LOGGED: {
				if (e.senderID == LoginWindowCtrl.userID) {
					JOptionPane.showMessageDialog(null, "���Ѿ��ڱ𴦵�¼,���ȷ��������,��һ����������.", "", JOptionPane.PLAIN_MESSAGE);
					new MainWindow(e.senderID);
				}
			}
				break;

			case Internet.FORCED_LOGGED_OUT: {
				JOptionPane.showMessageDialog(null, "�����˺��ڱ�����,���ѱ�������!\n���Ǳ��˲���,���޸�����.", "", JOptionPane.PLAIN_MESSAGE);
				new Popup("../View/LoginWindow.fxml", "��¼����");
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
				MainWindow.addContacts(e);
			}

			if (e.answer == MessageAnswerType.NEGATIVE)
				new ServerNote("�û�" + e.senderID + "�ܾ�����������.");
		}
			break;

		case ONLINE_STATE_QUEST: {
			MessageBlob message = new MessageBlob();
			message.type = MessageBlobType.ONLINE_STATE_ANSWER;
			new SendMessage(Property.Property.SERVER_IP, SocketConstants.GENERAL_PORT,
					MessageBlobOperator.pack(message));
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

			StrangerWindow str = new StrangerWindow(e.senderID, e.targetID);

		}
			break;

		case SELF_PROFILE_ANSWER: {

			if (e.answer == MessageAnswerType.POSITIVE) {
				MainWindow.persInfoInit(e);// ��Ϣ��ʼ��
				if (MainWindow.hasPersonalWindow)
					new PersonalData(e);
			}

			else
				JOptionPane.showMessageDialog(null, "������Ϣ��ȡ�쳣.������������" + ".\n������: CLN_CLNTRNS_SPAR", "",
						JOptionPane.PLAIN_MESSAGE);

		}
			break;

		case FRIEND_PROFILE_ANSWER: {
			if (e.answer == MessageAnswerType.POSITIVE) {
				new FriendWindow(e.senderID, e.targetID);
				FrdWindowCtrl.setContents(e);
			} else
				JOptionPane.showMessageDialog(null, "������Ϣ��ȡ�쳣.������������" + ".\n������: CLN_CLNTRNS_FPAR", "",
						JOptionPane.PLAIN_MESSAGE);
		}
			break;

		case CHAT_CONTENT_ANSWER: {
			if (e.answer == MessageAnswerType.POSITIVE)
				for (int i = 0; i < e.totalCounts; i++) {
					MainWindow.ctsList.get(e.senderID).chatWindow.addNewMsg(e.messageslist[i].text);
				}
			else
				JOptionPane.showMessageDialog(null, "�����¼��ȡʧ��!������������.");

		}
			break;

		case FRIEND_LIST_ANSWER: {
			if (e.answer == MessageAnswerType.POSITIVE)
				MainWindow.ContactsInit(e);
		}
			break;
		default:
			break;

		}
	}

}
