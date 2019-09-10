package com.diandian.utils.JMPackage;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.common.model.RegisterPayload;
import cn.jmessage.api.user.UserClient;
import cn.jmessage.api.user.UserInfoResult;
/**
 * 1.���������û�
 * 2.����û���Ϣ
 * @author zhangwenke
 *
 */
public class UserUtil {

	protected static final Logger LOG = LoggerFactory.getLogger(UserUtil.class);
	private static final String appkey = "4d629fcc568f3796d306fff5";
	private static final String masterSecret = "412f89cb2c120dab277cd68c";
	
	/**
	 * ���������û�
	 * @param username �����û���
	 * @param password �û�����
	 */
	public static void registerUser(String username, String password) {
		UserClient client = new UserClient(appkey, masterSecret);

		try {
			List<RegisterInfo> users = new ArrayList<RegisterInfo>();
			RegisterInfo user = RegisterInfo.newBuilder().setUsername(username).setPassword(password).build();
			users.add(user);
			RegisterInfo[] regUsers = new RegisterInfo[users.size()];

			RegisterPayload payload = RegisterPayload.newBuilder().addUsers(users.toArray(regUsers)).build();
			ResponseWrapper registerUsers = client.registerUsers(payload);

			System.out.println("���������û���Ϣ:"+registerUsers);
		} catch (APIConnectionException e) {
			LOG.error("���Ӵ������Ժ����ԡ� ", e);
		} catch (APIRequestException e) {
			LOG.error("JPush�������Ĵ�����Ӧ�����鲢������", e);
			LOG.info("����״̬: " + e.getStatus());
			LOG.info("������Ϣ: " + e.getMessage());
		}
	}
	/**
	 * �õ��û���Ϣ
	 * @param username	�û���
	 * @return
	 */
	
	public static UserInfoResult getUser(String username) {
		UserClient client = new UserClient(appkey, masterSecret);
		
		UserInfoResult result = null;
		try {
			result = client.getUserInfo(username);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
