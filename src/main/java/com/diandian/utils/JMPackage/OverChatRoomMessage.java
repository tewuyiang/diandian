package com.diandian.utils.JMPackage;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.common.BaseClient;
import cn.jmessage.api.common.JMessageConfig;
import cn.jmessage.api.message.MessageListResult;
import cn.jmessage.api.utils.StringUtils;
/**
 * ��������������Ϣ�ķ�װ
 * @author zhangwenke
 *
 */
public class OverChatRoomMessage extends BaseClient{
	
	private static final String appkey = "4d629fcc568f3796d306fff5";
	private static final String masterSecret = "412f89cb2c120dab277cd68c";
	
	private String mBaseReportPath;
    private String mV2RoomPath;

	public OverChatRoomMessage() {
        super(appkey, masterSecret, null, JMessageConfig.getInstance());
        JMessageConfig config = JMessageConfig.getInstance();
        mBaseReportPath = (String) config.get(JMessageConfig.API_REPORT_HOST_NAME);
        mV2RoomPath = "/v2/chatrooms";
    }

//  API
//	GET /users/{username}/messages?count=1000&begin_time={begin_time}&end_time={end_time}
//	GET /chatrooms/{chatroomid}/messages?count=100&begin_time={begin_time}&end_time={end_time}

//	�û�����url��https://report.im.jpush.cn/v2/users/user1/messages?count=1000&begin_time=2019-04-19+22%3A45%3A44&end_time=2019-04-25+22%3A45%3A45
//	������url��https://report.im.jpush.cn/v2/chatrooms/15746366/messages?count=100&begin_time=2019-04-19+22%3A49%3A51&end_time=2019-04-25+22%3A49%3A51
	
	public MessageListResult v2GetChatroomMessages(String chatRoomId, int count, String begin_time, String end_time)
            throws APIConnectionException, APIRequestException {
		
        String requestUrl = mBaseReportPath + mV2RoomPath + "/" + chatRoomId + "/messages?count=" + count;
        
        String beginEncoded = null;
        String endEncoded = null;
        if (null != begin_time && StringUtils.isNotEmpty(begin_time) && null != end_time
                && StringUtils.isNotEmpty(end_time)) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date beginDate = format.parse(begin_time);
                Date endDate = format.parse(end_time);
                if (endDate.getTime() - beginDate.getTime() < 0) {
                    throw new IllegalArgumentException("end time must lager than begin time");
                } else if (endDate.getTime() - beginDate.getTime() > 7 * 24 * 60 * 60 * 1000) {
                    throw new IllegalArgumentException("end time lager than begin time over 7 days");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                beginEncoded = URLEncoder.encode(begin_time, "utf-8");
                endEncoded = URLEncoder.encode(end_time, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            requestUrl = requestUrl + "&begin_time=" + beginEncoded + "&end_time=" + endEncoded;
        } else {
            throw new IllegalArgumentException("begin time or end time is null or empty");
        }
        ResponseWrapper response = _httpClient.sendGet(requestUrl);
        return MessageListResult.fromResponse(response, MessageListResult.class);
    }
}
