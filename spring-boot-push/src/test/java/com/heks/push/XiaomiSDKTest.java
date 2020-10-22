package com.heks.push;

import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import org.assertj.core.util.Lists;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author heks
 * @description: TODO
 * @date 2020/10/21
 */
public class XiaomiSDKTest {
    private static final Logger log = LoggerFactory.getLogger(XiaomiSDKTest.class);
    private static final Sender sender = new Sender("1234567890");

    public static void main(String[] args) throws IOException, ParseException {
        Constants.useOfficial();
        String messagePayload= "This is a message";
        String title = "notification title";
        String description = "notification description";
        Message message = new Message.Builder()
                .title(title)
                .description(description).payload(messagePayload)
                .restrictedPackageName("com.xueqiu.android")
                .notifyType(1)     // 使用默认提示音提示
                .build();
        Result result = sender.send(message, Lists.newArrayList("1234567890"), 0); //发送消息到一组设备上, regids个数不得超过1000个
        log.info("Server response: ", "MessageId: " + result.getMessageId()
                + " ErrorCode: " + result.getErrorCode().toString()
                + " Reason: " + result.getReason());
    }
}
