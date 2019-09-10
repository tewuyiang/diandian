package com.diandian.utils.JMPackage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.message.MessageBody;
import cn.jmessage.api.common.model.message.MessagePayload;
import cn.jmessage.api.message.MessageListResult;
import cn.jmessage.api.message.MessageResult;
import cn.jmessage.api.message.MessageType;
import cn.jmessage.api.message.SendMessageResult;

/**
 * ��Ϣ��������
 * 1.�û�A��B����text��ʽ��Ϣ
 * 2.���շ����û���ϢB����Ϣ
 * 3.�����û�A���͵�ָ��id��Ϣ
 * @author zhangwenke
 *
 */
public class MessageUtil {
	private static final String appkey = "4d629fcc568f3796d306fff5";
	private static final String masterSecret = "412f89cb2c120dab277cd68c";
	
	/**
	 * ���Ͷ�λ��һ��һ
	 * @param fromId	���ͷ�id
	 * @param targetId	���շ�id
	 * @param text		�������ݣ���map����
	 * @return			SendMessageResult
	 */
	public static SendMessageResult sendMap(String fromId, String targetId, String text){
		JMessageClient client = new JMessageClient(appkey, masterSecret);
		/**
		 * MessagePayload������Ҫ�������1.targetType��������	2.fromType���ͷ�����	3.fromName		4.targetName, 	5. msgBody 
		 */
		MessagePayload message = new MessagePayload(1, "single", targetId, "user", fromId, 
													"4d629fcc568f3796d306fff5", null, null, true, true, 
													MessageType.TEXT, MessageBody.text(text), null);
		SendMessageResult messageResult = null;
		
		try {
			messageResult = client.sendMessage(message);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		return messageResult;
	}
	
	/**
	 * ��Ϊ������������(һ��)
	 * �õ��û�������Ϣ
	 * ���⣺�õ�������Ϣ��Ҫ����ȫ������ɸѡ
	 * @param userid	�����û�id
	 * @return
	 */
	public static String getMap(String userid) {
		JMessageClient client = new JMessageClient(appkey, masterSecret);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -7);  // ���Ϣ7��
		Date startdate = calendar.getTime();
		List<String> messageList = new ArrayList<String>();
		
		try {
			MessageListResult message = client.v2GetUserMessages(userid, 10, df.format(startdate), df.format(new Date()));
			System.out.println(message);
			
			MessageResult[] lists = message.getMessages();  // �����Ϣ�б�
			
			for(MessageResult list : lists){
				if(list.getTargetId().equals(userid)){
					String msgBody = list.getMsgBody().getText();  // �õ���Ϣ����
//					String fromId = list.getFromId();  // �õ����ͷ�id
//					System.out.println(fromId+"���͵ģ�"+msgBody);
					messageList.add(msgBody);
				}
			}
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		if (messageList.size() <=0)
			return null;
		else
			return messageList.get(messageList.size()-1);
	}
	/**
	 * ������Ϣ������2���ӱ���
	 * @param username
	 * @param msgId
	 * @return
	 */
	public static Map<String,String> delMap(String username, long msgId) {
		JMessageClient client = new JMessageClient(appkey, masterSecret);
		try {
			ResponseWrapper rw = client.retractMessage(username, msgId);
			System.out.println(rw);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		return null;
	}
}

