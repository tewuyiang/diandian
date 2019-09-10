package com.diandian.utils.JMPackage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.jiguang.common.connection.IHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.chatroom.ChatRoomClient;
import cn.jmessage.api.chatroom.ChatRoomListResult;
import cn.jmessage.api.chatroom.CreateChatRoomResult;
import cn.jmessage.api.common.model.chatroom.ChatRoomPayload;
import cn.jmessage.api.common.model.message.MessageBody;
import cn.jmessage.api.common.model.message.MessagePayload;
import cn.jmessage.api.message.MessageListResult;
import cn.jmessage.api.message.MessageResult;
import cn.jmessage.api.message.MessageType;
import cn.jmessage.api.message.SendMessageResult;
/**
 * �����ҵĹ������Ϣ���ͻ�ȡ
 * 1.����������
 * 2.��ȡ������id
 * 3.���������ҳ�Ա
 * 4.ɾ��������
 * 5.�������ҷ�����Ϣ
 * 6.��ȡָ�����ͷ������������ڵ���Ϣ
 * @author zhangwneke
 *
 */
public class ChatRoomUtil {

	protected IHttpClient _httpClient;
	protected String _baseUrl;
	private static final String appkey = "4d629fcc568f3796d306fff5";
	private static final String masterSecret = "412f89cb2c120dab277cd68c";

	/**
	 * ����������
	 * @param username ������ӵ����
	 * @param roomname ��������
	 * @throws APIRequestException 
	 * @throws APIConnectionException 
	 */
	public static void ChatRoomPayload(String username, String roomname) throws APIConnectionException, APIRequestException {
		ChatRoomClient chatRoomClient = new ChatRoomClient(appkey, masterSecret);
		ChatRoomPayload room = ChatRoomPayload.newBuilder().setOwnerUsername(username).setName(roomname).build();
		CreateChatRoomResult chatRoom = null;
		chatRoom = chatRoomClient.createChatRoom(room);
		System.out.println("����������������Ϣ:" + chatRoom);
	}

	/**
	 * ͨ���û������������id
	 * @param username
	 * @return roomId
	 */
	public static long getRoomIdByUserName(String username) {
		ChatRoomClient chatRoomClient = new ChatRoomClient(appkey, masterSecret);
		ChatRoomListResult rooms = null;
		long roomId = 0;
		try {
			rooms = chatRoomClient.getUserChatRoomInfo(username);
			roomId = rooms.getRooms()[0].getId();
		} catch (APIConnectionException | APIRequestException e) {
			e.printStackTrace();
			System.out.println("ͨ��id��ȡroomidʧ��");
		}
		return roomId;
	}

	/**
	 * Add members to chat room
	 * @param username
	 * @param roomname
	 * @throws APIRequestException 
	 * @throws APIConnectionException 
	 */
	public static void addMemberToChatRoom(long roomId, String username) {
		ChatRoomClient chatRoomClient = new ChatRoomClient(appkey, masterSecret);
		try {
			chatRoomClient.addChatRoomMember(roomId, username);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ɾ��������
	 * @param roomId
	 */
	public static void deleteChatRoom(long roomId) {
		ChatRoomClient chatRoomClient = new ChatRoomClient(appkey, masterSecret);
		
		ResponseWrapper rw = null;
		System.out.println(rw);
		try {
			rw = chatRoomClient.deleteChatRoom(roomId);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �������ҷ�����Ϣ
	 * @param fromId		���ͷ�id
	 * @param chatroomId	������id
	 * @param text			����
	 * @return
	 */
	public static SendMessageResult sendMapToRoom(String fromId, String chatroomId, String text){
		JMessageClient client = new JMessageClient(appkey, masterSecret);
		MessagePayload message = new MessagePayload(1, "chatroom", chatroomId, "user", fromId, 
													"4d629fcc568f3796d306fff5", null, null, true, true, 
													MessageType.TEXT, MessageBody.text(text), null);
		SendMessageResult messageResult = null;
		
		try {
			messageResult = client.sendMessage(message);
			System.out.println("���ͽ����"+messageResult);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		return messageResult;
	}
	
	/**
	 * ���������Ҵ����ߺ�Ŀ���û���ȡ��Ϣ
	 * @param chatRoomOwnId  ����id
	 * @param UserId		   ��ȡ��Ϣ���ͷ����û�id
	 * @return
	 */
	public static String getChatMemberMap(String chatRoomOwnId, String UserId) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -6);  // ���Ϣ7��
		Date startdate = calendar.getTime();
		
		String roomId = String.valueOf(getRoomIdByUserName(chatRoomOwnId));
		OverChatRoomMessage chatroom = new OverChatRoomMessage();
		List<String> messageList = new ArrayList<String>();
		try {
			MessageListResult message = chatroom.v2GetChatroomMessages(roomId, 100, df.format(startdate), df.format(new Date()));
			
			System.out.println(message);
			MessageResult[] lists = message.getMessages();  // �����Ϣ�б�
			
			for(MessageResult list : lists){
				if(list.getFromId().equals(UserId)){
					messageList.add(list.getMsgBody().getText());
				}
			}
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		if(messageList.size() >=1)
			return messageList.get(messageList.size()-1);
		else
			return null;
	}
	
/*		
	 * ���������Ҵ����߻�ȡ��Ϣ���û���ȡ����������Ϣ��
	 * @param chatRoomOwnId
	 * @return
	 *
	public static String getChatRoomOwnMap(String chatRoomOwnId) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -6);  // ���Ϣ7��
		Date startdate = calendar.getTime();
		
		String roomId = String.valueOf(getRoomIdByUserName(chatRoomOwnId));
		OverChatRoomMessage chatroom = new OverChatRoomMessage();
		List<String> messageList = new ArrayList<String>();
		try {
			MessageListResult message = chatroom.v2GetChatroomMessages(roomId, 100, df.format(startdate), df.format(new Date()));
			
			System.out.println(message);
			MessageResult[] lists = message.getMessages();  // �����Ϣ�б�
			
			for(MessageResult list : lists){
				if(list.getFromId().equals(chatRoomOwnId)){
					messageList.add(list.getMsgBody().getText());
				}
			}
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		if(messageList.size() >=1)
			return messageList.get(messageList.size()-1);
		else
			return null;
	}*/
	
}
