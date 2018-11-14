package io.ymq.example.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import io.ymq.example.elasticsearch.run.Startup;
import io.ymq.example.elasticsearch.utils.ElasticsearchUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.junit.Test;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2017-11-23 20:33
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Startup.class)
public class searchListDataTest {

    @Test
    public void searchTest() throws ParseException {

        List<String> list = new ArrayList<String>();
        list.add("C000211171122000281");
        list.add("C000211171122000494");



        long startTime = DateUtils.parseDate("2017-11-22 00:00:00", "yyyy-MM-dd HH:mm:ss").getTime();
        long endTime = DateUtils.parseDate("2017-11-23 00:00:00", "yyyy-MM-dd HH:mm:ss").getTime();

        String index = "all_assignservice-*";
        String type = "all_assignservice";

        int size = 1000;

        List<String> messageList = new ArrayList<String>();

        for (String workOrderno : list) {
            String matchStr = "message=" + workOrderno;
            String message = searchListData(index, type, startTime, endTime, size, matchStr);
            messageList.add("订单号：" + workOrderno + "----" + message);
        }

        for (String str : messageList) {
            System.out.println(str);
        }

    }


    /**
     * 使用分词查询
     *
     * @param index     索引名称
     * @param type      类型名称,可传入多个type逗号分隔
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param size      文档大小限制
     * @param matchStr  过滤条件（xxx=111,aaa=222）
     * @return
     */
    public String searchListData(String index, String type, long startTime, long endTime, Integer size, String matchStr) throws ParseException {

        StringBuffer stringBuffer = new StringBuffer();

        List<Map<String, Object>> tmpMap1 = ElasticsearchUtils.searchListData(index, type, startTime, endTime, size, matchStr);

        Set<String> guidList = new HashSet<String>();

        Set<String> requestIdList = new HashSet<String>();

        for (Map<String, Object> guid : tmpMap1) {

            String message = guid.get("message").toString();

            Integer startIndex = message.indexOf("guid");

            if (message.indexOf("guid") > 1 && message.indexOf("action=>insertWorkOrder") > 1) {

                guidList.add(message.substring(startIndex + 7, startIndex + 26));
            }
        }

        for (String guid : guidList) {

            matchStr = "message=" + guid;

            List<Map<String, Object>> tmpMap2 = ElasticsearchUtils.searchListData(index, type, startTime, endTime, size, matchStr);

            for (Map<String, Object> requestId : tmpMap2) {
                if (requestId.get("message").toString().indexOf("crm自动推荐入参:{\"bigClassId") > 1) {
                    requestIdList.add(requestId.get("requestId").toString());
                }
            }
        }

        for (String requestId : requestIdList) {

            String assignservice_matchStr = "requestId=" + requestId;

            String assignservice_index = "all_esmart-assign-*";

            String assignservice_type = "all_esmart-assign";

            List<Map<String, Object>> tmpMap3 = ElasticsearchUtils.searchListData(assignservice_index, assignservice_type, startTime, endTime, size, assignservice_matchStr);

            StringBuffer zygs = new StringBuffer();
            StringBuffer tjsj = new StringBuffer();
            for (Map<String, Object> item : tmpMap3) {

                String message = item.get("message").toString();

                if (message.indexOf("最优公司") > 1) {
                    zygs.append("多能工:");
                    zygs.append(message.substring(message.indexOf(":[") + 2, message.indexOf("]]")));
                    zygs.append("----");
                }

                if (message.indexOf("推荐时间") > 1) {
                    tjsj.append("推荐时间:");

                    String str = message.substring(message.indexOf("{") + 1, message.lastIndexOf("]") - 1);

                    String[] list = str.split("],");

                    for (String it : list) {

                        Integer startIndex = it.indexOf("=[");

                        String empId = it.substring(0, startIndex).trim();

                        tjsj.append("empId:").append(empId).append("----");
                        String timeStr = it.substring(startIndex + 2).trim();

                        String[] time = timeStr.split(",");

                        for (String i : time) {

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
                            Date date = simpleDateFormat.parse(i.trim());
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String sDate = sdf.format(date);
                            tjsj.append(sDate).append("----");

                        }
                    }

                }
            }
            stringBuffer.append(zygs.toString()).append(tjsj.toString());
        }

        for (String requestId : requestIdList) {

            matchStr = "requestId=" + requestId;

            List<Map<String, Object>> tmpMap3 = ElasticsearchUtils.searchListData(index, type, startTime, endTime, size, matchStr);
            for (Map<String, Object> item : tmpMap3) {

                String message = item.get("message").toString();

                if (message.indexOf("crm自动推荐时间-->派工RPC调用-->返回结果为：{") > 1) {

                    Integer startIndex = message.indexOf("{");
                    Integer endIndex = message.lastIndexOf("}") + 1;

                    JSONObject jsonObject = JSONObject.parseObject(message.substring(startIndex, endIndex));

                    jsonObject = JSONObject.parseObject(jsonObject.get("data").toString());

                    Object recommendDabi = jsonObject.get("recommendDabi");

                    if (recommendDabi != null) {
                        Object startDate = jsonObject.get("startTime");
                        Object endDate = jsonObject.get("endTime");
                        if (startDate != null) {
                            stringBuffer.append("预约开始时间：");
                            stringBuffer.append(recommendDabi == null ? "" : recommendDabi).append(" ").append(startDate == null ? "" : startDate);
                            stringBuffer.append(recommendDabi == null ? "" : "----");
                            stringBuffer.append("预约结束时间：");
                            stringBuffer.append(recommendDabi == null ? "" : recommendDabi).append(" ").append(endDate == null ? "" : endDate);
                        }

                    }

                }
            }
        }
        return stringBuffer.toString();
    }


}
