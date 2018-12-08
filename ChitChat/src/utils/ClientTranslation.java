package utils;

import CComponents.MessageBlob;

public class ClientTranslation {// 解析接收到的Blob
	public void typeTrans(MessageBlob e) {
		switch (e.type) {

		case CHAT_TEXT: {
			
		}
			break;

		case CHAT_PICTURE: {

		}
			break;

		case LOGIN: {

		}
			break;

		case LOGOUT: {

		}
			break;

		case EDIT_MY_KEY: {

		}
			break;

		case DELETE_CONTACT: {

		}
			break;

		case ADD_CONTACT_QUEST: {

		}
			break;

		case ADD_CONTACT_ANSWER: {

		}
			break;

		case ONLINE_STATE_QUEST: {

		}
			break;

		case ONLINE_STATE_ANSWER: {

		}
			break;

		case REGISTER: {

		}
			break;

		case FIND_FRIEND: {

		}
			break;

		case SELF_PROFILE_QUEST: {

		}
			break;

		case FRIEND_PROFILE_QUEST: {

		}
			break;

		case SELF_PROFILE_ANSWER: {

		}
			break;

		case FRIEND_PROFILE_ANSWER: {

		}
			break;

		case SELF_PROFILE_UPDATE: {

		}
			break;

		case FRIEND_PROFILE_UPDATE: {

		}
			break;

		case CHAT_CONTENT_QUEST: {

		}
			break;

		case CHAT_CONTENT_ANSWER: {

		}
			break;

		case FRIEND_LIST_QUEST: {

		}
			break;

		case FRIEND_LIST_ANSWER: {

		}
			break;
		default:
			break;

		}
	}


}
