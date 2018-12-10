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

public class ClientTranslation {// 解析接收到的Blob
	public static void typeTrans(MessageBlob e) {
		System.out.println("Trans Start CLN_TPTRN");
		switch (e.type) {

		case CHAT_TEXT: {
			if (MainWindow.ctsList.get(e.senderID).hasChatWin) {// 打开了聊天页的话
				MainWindow.ctsList.get(e.senderID).chatWindow.addNewMsg(e.text);
			}
			if (MainWindow.ctsList.get(e.senderID).hasMessage) {// 存在信息盒的话
				MainWindow.ctsList.get(e.senderID).message.refreshMsg(e.text);
			} else {
				MainWindow.addNewMessage(e);
			}
		}
			break;

		case CHAT_PICTURE: {

		} // 后续补充
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
				JOptionPane.showMessageDialog(null, "用户名不存在!", "", JOptionPane.PLAIN_MESSAGE);
				new Popup("../View/LoginWindow.fxml", "登录界面");
			}
				break;
			case Internet.PSWD_ERR: {
				JOptionPane.showMessageDialog(null, "密码错误!", "", JOptionPane.PLAIN_MESSAGE);
				new Popup("../View/LoginWindow.fxml", "登录界面");
			}
				break;
			case Internet.ALREDY_LOGGED: {
				if (e.senderID == LoginWindowCtrl.userID) {
					JOptionPane.showMessageDialog(null, "你已经在别处登录,点击确定将上线,另一处处将下线.", "", JOptionPane.PLAIN_MESSAGE);
					new MainWindow(e.senderID);
				}
			}
				break;

			case Internet.FORCED_LOGGED_OUT: {
				JOptionPane.showMessageDialog(null, "您的账号在别处上线,您已被迫下线!\n若非本人操作,请修改密码.", "", JOptionPane.PLAIN_MESSAGE);
				new Popup("../View/LoginWindow.fxml", "登录界面");
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
				new ServerNote("用户" + e.senderID + "拒绝了您的请求.");
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
				JOptionPane.showMessageDialog(null, "注册成功,账号为:" + e.targetID);
			else
				JOptionPane.showMessageDialog(null, "这个昵称已经有人用了哦!" + e.targetID);
		}
			break;

		case FIND_FRIEND: {

			StrangerWindow str = new StrangerWindow(e.senderID, e.targetID);

		}
			break;

		case SELF_PROFILE_ANSWER: {

			if (e.answer == MessageAnswerType.POSITIVE) {
				MainWindow.persInfoInit(e);// 信息初始化
				if (MainWindow.hasPersonalWindow)
					new PersonalData(e);
			}

			else
				JOptionPane.showMessageDialog(null, "个人信息拉取异常.请检查网络连接" + ".\n错误码: CLN_CLNTRNS_SPAR", "",
						JOptionPane.PLAIN_MESSAGE);

		}
			break;

		case FRIEND_PROFILE_ANSWER: {
			if (e.answer == MessageAnswerType.POSITIVE) {
				new FriendWindow(e.senderID, e.targetID);
				FrdWindowCtrl.setContents(e);
			} else
				JOptionPane.showMessageDialog(null, "好友信息拉取异常.请检查网络连接" + ".\n错误码: CLN_CLNTRNS_FPAR", "",
						JOptionPane.PLAIN_MESSAGE);
		}
			break;

		case CHAT_CONTENT_ANSWER: {
			if (e.answer == MessageAnswerType.POSITIVE)
				for (int i = 0; i < e.totalCounts; i++) {
					MainWindow.ctsList.get(e.senderID).chatWindow.addNewMsg(e.messageslist[i].text);
				}
			else
				JOptionPane.showMessageDialog(null, "聊天记录获取失败!请检查网络连接.");

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
