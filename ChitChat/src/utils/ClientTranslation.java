package utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;
import CComponents.Convasation;
import CComponents.MessageAnswerType;
import CComponents.MessageBlob;
import CComponents.MessageBlob.File;
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

public class ClientTranslation {// 解析接收到的Blob
	public static MainWindow mainWindow;
	public static MessageBlob files;

	public static synchronized <T> void typeTrans(MessageBlob e) {// lock
		System.out.println("Type: " + e.type + "\nAnswer: " + e.answer + "\n");
		switch (e.type) {

		case CHAT_TEXT: {
			boolean hsChatWindow = MainWindow.ctsList.get(e.senderID).hasChatWin;
			if (hsChatWindow) {// 打开了聊天页的话
				MainWindow.ctsList.get(e.senderID).chatWindow.addNewMsg(e.senderID, e.nickname, e.text);
			}
			if (MainWindow.ctsList.get(e.senderID).hasMessage) {// 存在信息盒的话
				if (hsChatWindow)
					MainWindow.ctsList.get(e.senderID).message.isRead = Internet.READ;// 若存在聊天页,让消息盒子显示消息,但不高亮.
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
			switch (e.onlineState) {// onlineState指示登录是否成功.
			case Internet.LOGIN_SUCCESS: {
				if (e.senderID == LoginWindow.userID) {
					
					if(mainWindow == null)
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
				JOptionPane.showMessageDialog(null, "用户名不存在!", "", JOptionPane.PLAIN_MESSAGE);

				new LoginWindow();
			}
				break;
			case Internet.PSWD_ERR: {
				JOptionPane.showMessageDialog(null, "密码错误!", "", JOptionPane.PLAIN_MESSAGE);
				new LoginWindow();
			}
				break;
			case Internet.ALREDY_LOGGED: {

				JOptionPane.showMessageDialog(null, "你已经在别处登录,点击确定将上线,另一处处将下线.", "", JOptionPane.PLAIN_MESSAGE);
				mainWindow = new MainWindow(e.senderID);
			}
				break;

			case Internet.FORCED_LOGGED_OUT: {
				JOptionPane.showMessageDialog(null, "您的账号在别处上线,您已被迫下线!\n若非本人操作,请修改密码.", "", JOptionPane.PLAIN_MESSAGE);
				if (mainWindow != null)
					mainWindow.dispose();// 窗口关闭
				new LoginWindow();
			}
				break;

			case Internet.SERVER_ERR: {
				JOptionPane.showMessageDialog(null, "服务端异常!", "", JOptionPane.PLAIN_MESSAGE);
				new LoginWindow();
			}
				break;
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
				MessageBlob quest_for_friend_list = new MessageBlob();// 联系人列表初始化
				quest_for_friend_list.type = MessageBlobType.FRIEND_LIST_QUEST;
				quest_for_friend_list.senderIP = Property.NATIVE_IP;
				quest_for_friend_list.senderID = MainWindow.ID;
				new SendMessage(Property.SERVER_IP, SocketConstants.SERVER_PORT,
						MessageBlobOperator.pack(quest_for_friend_list));
			}

			if (e.answer == MessageAnswerType.NEGATIVE)
				new ServerNote(e.senderID, "用户" + e.senderID + "拒绝了您的请求.", Constants.Constants.MSG_MODE_REFUSE, null);
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
				JOptionPane.showMessageDialog(null, "注册成功,账号为:" + e.targetID);
			else
				JOptionPane.showMessageDialog(null, "这个昵称已经有人用了哦!" + e.targetID);
		}
			break;

		case FIND_FRIEND: {

			if (e.answer == MessageAnswerType.POSITIVE)
				new StrangerWindow(MainWindow.ID, e);
			else {
				if (e.onlineState == 0) {
					JOptionPane.showMessageDialog(null, "查询失败!请检查账号是否正确.");
					return;
				}
				if (e.onlineState == 2) {
					JOptionPane.showMessageDialog(null, "查询失败!请检查网络.");
					return;
				}

			}

		}
			break;

		case SELF_PROFILE_ANSWER: {

			if (e.answer == MessageAnswerType.POSITIVE) {
				if (MainWindow.hasPersonalWindow == false)
					MainWindow.persInfoInit(e);// 信息初始化
				else {
					new PersonalData(e);
					// MainWindow.hasPersonalWindow = true;
				}
			} else
				JOptionPane.showMessageDialog(null, "个人信息拉取异常.请检查网络连接" + ".\n错误码: CLN_CLNTRNS_SPAR", "",
						JOptionPane.PLAIN_MESSAGE);

		}
			break;

		case FRIEND_PROFILE_ANSWER: {
			if (e.answer == MessageAnswerType.POSITIVE) {
				new FriendWindow(MainWindow.ID, e);

			} else
				JOptionPane.showMessageDialog(null, "好友信息拉取异常.请检查网络连接" + ".\n错误码: CLN_CLNTRNS_FPAR", "",
						JOptionPane.PLAIN_MESSAGE);
		}
			break;

		case CHAT_CONTENT_ANSWER: {
			if (e.answer == MessageAnswerType.POSITIVE) {
				// System.out.println(e.messageslist[0]);

				for (int i = e.totalCounts - 1; i >= 0; i--) {
					System.out.println("ChatContent loaded!");
					MainWindow.ctsList.get(e.targetID).chatWindow.addNewMsg(e.messageslist[i].senderID,
							e.messageslist[i].nick, e.messageslist[i].text);
				}
			} else
				JOptionPane.showMessageDialog(null, "聊天记录获取失败!请检查网络连接.");

		}
			break;

		case FRIEND_LIST_ANSWER: {
			if (e.answer == MessageAnswerType.POSITIVE) {
				//for(int i = 0; i < e.cone.contactslist)
				MainWindow.ContactsInit(e);
			}
		}
			break;
		case SELF_VERIFY: {
			if (e.answer == MessageAnswerType.POSITIVE) {
				for (int i = 0; i < e.verifylist.length; i++)
					if (e.verifylist[i].status.equals("WAITING"))
						new ServerNote(e.verifylist[i].id, "用户" + e.verifylist[i].nickname + "请求添加您为好友.",
								Constants.Constants.MSG_MODE_ADDFD, null);
			} else
				JOptionPane.showMessageDialog(null, "个人验证事务获取失败!请检查网络连接.");
		}
			break;
		case SELF_FILE: {
			if (e.answer == MessageAnswerType.POSITIVE) {
				files = e;
				/*
				 * 创建一个哈希表,,存放已经出现过的ID,list中的id与出现过的匹配,没出现过就放入其中. 有效避免重复.
				 * 创建一个Vector,把filelist转化为vector,便于查找.
				 */
				HashMap<Integer, Integer> ID_served = new HashMap<>();// 第一个是出现的id,第二个是出现的次数.
//				Vector<File>/*MessageBlob中的File.*/ filelistVtr = new Vector<File>() {
//					@Override
//					public File get(int index) {
//						int i = 0;
//						while(((File)this.elementData[i++]).id != index)//当元素中的id不等于index时继续执行
//						if (i < this.elementCount)return this.get(i);
//						return null;
//					}//重写get()方法
//				};
//				
//				for(int i = 0; i < e.filelist.length; i++) {
//					filelistVtr.add(e.filelist[i]);
//				}

				for (int i = 0; i < e.filelist.length; i++) {
					if (e.filelist[i].fileState == 0) {// 0表示未被下载过, 1表示被下载过.
						if (ID_served.containsKey(e.filelist[i].id)) {
							int count = ID_served.get(e.filelist[i].id);// 次数
							count++;// 加一
							ID_served.put(e.filelist[i].id, count++);// 放回去
						} else
							ID_served.put(e.filelist[i].id, 1);
					}
				}
				// 以上完成出现过的ID的统计查重.
				
				Iterator<Integer> iter = ID_served.keySet().iterator();
				while (iter.hasNext()) {
					int i = iter.next();
					int j = 0;
					for(j = 0 ; j < e.filelist.length; j++) {
						if(e.filelist[j].id == i)
							break;
					}
						new ServerNote(i
								,"用户"+ e.filelist[j].nickname+ "想要给你发送" + ID_served.get(i) + "个文件."
								,Constants.Constants.MSG_MODE_FILE, e.filelist[j].fileName);
					
				}
			} else
				JOptionPane.showMessageDialog(null, "文件事务获取失败!请检查网络连接.");
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
		case EDIT_MY_KEY:{
			if(e.answer == MessageAnswerType.POSITIVE)
				JOptionPane.showMessageDialog(null, "修改密码成功!");
			else
				JOptionPane.showMessageDialog(null, "修改密码失败.请检查网络连接或稍后重试.");

		}break;

		default:
			break;

		}
	}

}
